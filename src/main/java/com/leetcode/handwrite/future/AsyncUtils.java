package com.leetcode.handwrite.future;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

@Service
public class AsyncUtils {
	@Autowired
	private CacheService searchService;
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final CountDownLatch countDownLatch = new CountDownLatch(3);
	private Map<String,ListenableFuture<SearchResult>> res = new ConcurrentHashMap<>();
	public final FutureCallback<SearchResult> showResult = new FutureCallback<SearchResult>() {
		public void onSuccess(SearchResult result) {
			// TODO 回调事件监听接口
			logger.debug("Poll keyword:{} ====> {} ", new Object[] { result.getKeyword(), result.getRecords() });
			logger.debug("Cost TotalTime:{}ms", new Object[] { result.getTotalTime() });
		}
		public void onFailure(Throwable thrown) {
			logger.warn(thrown.getMessage());
		}
	};


	public void offer(String name,String[] corpStrings) {
		if (corpStrings.length == 1){
			ListenableFuture<SearchResult> future1 = (ListenableFuture<SearchResult>) searchService.get(corpStrings[0]);
			res.put(name,future1);
//			Futures.addCallback(future1, showResult);
			asyncCountDown(future1);
		}else if(corpStrings.length > 1){
			List<String> corps = Arrays.asList(corpStrings);
			List<ListenableFuture<SearchResult>> futures = new ArrayList<ListenableFuture<SearchResult>>();
			for (String corp : corps) {
				futures.add((ListenableFuture<SearchResult>) searchService.get(corp));
			}
			ListenableFuture<List<SearchResult>> collectedResults = Futures.successfulAsList(futures);
			// 收集全部结果并合并返回
			AsyncFunction<List<SearchResult>, SearchResult> mergeFunc = new AsyncFunction<List<SearchResult>, SearchResult>() {
				// 针对返回的结果集进行处理
				@Override
				public ListenableFuture<SearchResult> apply(List<SearchResult> results) {
					StringBuilder keywords = new StringBuilder();
					List<String> records = new ArrayList<>();
					long startTime = Long.MAX_VALUE;
					long endTime = 0;
					for (SearchResult result : results) {
						if (result != null) {
							keywords.append(result.getKeyword());
							records.addAll(result.getRecords());
							// 所有异步任务中，统计开始最早和结束最晚，计算持续时间
							startTime = Math.min(startTime, result.getStartTime());
							endTime = Math.max(endTime, result.getEndTime());
						}
					}
					// 当有一个线程调用set操作，其他写线程只能进入同步队列中进行等待，而其他读线程会根据Sync当前的状态返回对应的结果。
					// 如果同时有多个线程访问，只有一个线程的操作会被接受，其他线程只有等待拥有锁的线程完成该操作，并获取那个操作的结果。
					SettableFuture<SearchResult> settableFuture = SettableFuture.create();
					settableFuture.set(new SearchResult(keywords.toString(), records, startTime, endTime));
					return settableFuture;
				}
			};
			// 收集返回结果
			ListenableFuture<SearchResult> mergedResult = Futures.transform(collectedResults, mergeFunc);
			// 针对成功和失败的结果分别返回
			res.put(name,mergedResult);
//			Futures.addCallback(mergedResult, showResult);
			asyncCountDown(mergedResult);
		}
	}

	// 结果查询接口
	public List<ListenableFuture<SearchResult>> search(List<String> keys){
		List<ListenableFuture<SearchResult>> listRes = new ArrayList<>();
		for(int i=0;i<keys.size();i++){
			listRes.add(res.get(keys.get(i)));
		}
		return listRes;
	}

	// 当已完成任务达到countLatch的容量时触发
	// 可做为任务完成时的回调
	public void await() throws InterruptedException {
		countDownLatch.await();
	}
	//统计已完成的任务数
	public <V> void asyncCountDown(ListenableFuture<? extends V> future) {
		future.addListener(new Runnable() {
			public void run() {
				countDownLatch.countDown();
			}
		}, MoreExecutors.sameThreadExecutor());
	}

}

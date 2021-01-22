package com.leetcode.handwrite.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CacheService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private Map<String, List<String>> lruCache = new ConcurrentHashMap<>();
	CacheService() {
		lruCache.put("Alibaba", Arrays.asList("alicloud", "dingding", "alipay"));
		lruCache.put("ByteDance", Arrays.asList("lark","douyin"));
		lruCache.put("Tencent", Arrays.asList("wechat","qq"));
		lruCache.put("Baidu", Arrays.asList("ditu", "tieba"));
	}
	@Async
	public Future<SearchResult> get(String keyword) {
		logger.debug("search keyword:{}", keyword);
		long startTime = System.currentTimeMillis();
		try {
			// simulate network latency
			Thread.sleep(ThreadLocalRandom.current().nextInt(10) * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<String> records = lruCache.get(keyword);
		long endTime = System.currentTimeMillis();
		if (records != null) {
			return new AsyncResult<SearchResult>(new SearchResult(keyword, records, startTime, endTime));
		} else {
			logger.debug("The keyword {} did not match any records", keyword);
			return new AsyncResult<SearchResult>(new SearchResult(keyword, new ArrayList<>(), 0, 0));
		}
	}
}




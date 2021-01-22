package com.leetcode.handwrite.future;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestAsync {
	private static final Logger logger = LoggerFactory.getLogger(TestAsync.class);
	public static void main(String[] args) {
		logger.info("Starting application.......");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		AsyncUtils utils = (AsyncUtils) context.getBean("asyncUtils");
		String[] corps = new String[]{"Alibaba","Baidu","Tencent","ByteDance"};
		List<String> keys= new ArrayList<>();
		for(int i=0;i<2;i++){
			String corp = corps[new Random().nextInt(2)];
			// 【单任务提交】
			utils.offer(corp,new String[]{corp});
			keys.add(corp);
		}
		// 【多任务提交】
		utils.offer("MultiTasks",corps);
		keys.add("MultiTasks");
		try {
			// 【事件监听】对事件结果进行监听
			utils.listen();
			System.out.println("===============以下为查询结果=================");
			List<ListenableFuture<SearchResult>> res = utils.search(keys);
			for(ListenableFuture<SearchResult> lf : res){
				// 【结果查询】收到结果后发起查询
				Futures.addCallback(lf, utils.showResult);
			}
			logger.info("=======All Tasks Completed=======");
			context.destroy();
		} catch (InterruptedException e) {
			// TODO 响应中断
			logger.error("任务查询失败，请重新再试");
		}

	}
}
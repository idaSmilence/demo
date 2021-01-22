package com.leetcode.handwrite.future;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

public class MyPool extends ThreadPoolTaskExecutor {
	@Override
	public <T> Future<T> submit(Callable<T> task) {
		ListeningExecutorService executor = MoreExecutors.listeningDecorator(getThreadPoolExecutor());
		try {
			return executor.submit(task);
		} catch (RejectedExecutionException ex) {
			// TODO 此处可定义拒绝策略
			throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
		}
	}
}

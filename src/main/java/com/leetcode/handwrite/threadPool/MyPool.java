package com.leetcode.handwrite.threadPool;
import lombok.SneakyThrows;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
public class MyPool {
    private static final int WORK_NUM = 5;
    private static final int TASK_NUM = 100;
    // Set模拟线程池
    private final Set<WorkThread> workThreads;
    // BQ默认任务队列
    private final BlockingQueue<Runnable> taskQueue;
    public MyPool(int workNum, int taskNum) {
        workNum = Math.max(workNum,WORK_NUM);
        taskNum = Math.max(taskNum,TASK_NUM);
        this.taskQueue = new ArrayBlockingQueue<>(taskNum);
        this.workThreads = new HashSet<>();
        for (int i = 0; i < workNum; i++) {
            WorkThread workThread = new WorkThread("Thread_" + i);
            workThread.start();
            workThreads.add(workThread);
        }
    }
    public void execute(Runnable task) throws InterruptedException {
        taskQueue.put(task);
    }
    private class WorkThread extends Thread {
        public WorkThread(String name){
            setName(name);
        }
        @SneakyThrows
        @Override
        public void run() {
            while (!interrupted()) {
                Runnable task = taskQueue.take();//获取任务
                System.out.println(getName()+" Run : " + task.toString());
                task.run();//执行任务
                currentThread().sleep(1000);
            }
        }
    }
}

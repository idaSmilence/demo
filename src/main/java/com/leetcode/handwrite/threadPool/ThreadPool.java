package com.leetcode.handwrite.threadPool;

public class ThreadPool {
    private static final int TASK_NUM = 50; // 任务的个数
    public static void main(String[] args) throws InterruptedException {
        // 初始化线程池
        // 1、创建容量为workNum个线程组成的set集合，
        // 2、线程调用run方法，阻塞式从任务队列拉取任务处理
        // 3、创建容量为taskNum长度的阻塞队列ABQ
        MyPool myPool = new MyPool(3, 50);
        // 1、创建50个命名任务
        // 2、将任务放入任务ABQ
        for (int i = 0; i < TASK_NUM; i++) {
            MyTask task = new MyTask("Task_" + i);
            myPool.execute(task);
        }
    }
}


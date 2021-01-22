package com.leetcode.handwrite.threadPool;
import lombok.SneakyThrows;
public class MyTask implements Runnable {
    private String name; //任务名称
    public MyTask(String name) {
        this.name = name;
    }
    @SneakyThrows
    @Override
    public void run() {
        System.out.println("        Task :" + name + " Start...");
//        Thread.sleep(1000);
        System.out.println("        Task :" + name + " End...");
    }
    @Override
    public String toString() {
        return "Name = " + name;
    }
}

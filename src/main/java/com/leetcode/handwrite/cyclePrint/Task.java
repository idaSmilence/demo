package com.leetcode.handwrite.cyclePrint;
import lombok.SneakyThrows;

public class Task implements Runnable {
    private String name; //任务名称
    public Task(String name) {
        this.name = name;
    }
    @SneakyThrows
    @Override
    public void run() {
        System.out.print(name);
    }
    @Override
    public String toString() {
        return "Name = " + name;
    }
}

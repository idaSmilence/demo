package com.leetcode.handwrite.publisher;

import lombok.SneakyThrows;

public class Consumer extends Thread {
    private int num;
    private String name;
    private Storage storage;
    public Consumer(Storage storage,String name) {
        this.storage = storage;
        this.name = name;
    }
    public void setNum(int num) {
        this.num = num;
    }
    @SneakyThrows
    public void run() {
        System.out.println("Thread:"+name+"启动，开始消费");
        storage.consume(this.num);
        System.out.println("Thread:"+name+"关闭，停止消费");
    }
}
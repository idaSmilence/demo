package com.leetcode.handwrite.publisher;

import lombok.SneakyThrows;

public class Producer extends Thread {
    private int num;
    private Storage storage;
    private String name;
    public Producer(Storage storage,String name) {
        this.storage = storage;
        this.name = name;
    }
    public void setNum(int num) {
        this.num = num;
    }
    @SneakyThrows
    public void run() {
        System.out.println("Thread:"+name+"启动，开始生产");
        storage.produce(this.num);
        System.out.println("Thread:"+name+"关闭，停止生产");

    }
}
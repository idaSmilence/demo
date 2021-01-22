package com.leetcode.handwrite.publisher;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
        Storage storage = new Storage();
        for(int i=0;i<10;i++){
            Consumer c = new Consumer(storage,"Consumer:"+i);
            c.setNum(new Random().nextInt(100));
            c.start();
        }
        for(int i=0;i<10;i++) {
            Producer p = new Producer(storage,"Producer:"+i);
            p.setNum(new Random().nextInt(100));
            p.start();
        }
    }
}

package com.leetcode.handwrite.blockingQueue;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
        BlockQueue queue = new BlockQueue(5);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    queue.add(i);
                    System.out.println("塞入" + i);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            for (; ; ) {
                try {
                    System.out.println("消费"+queue.take());
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
    }
}

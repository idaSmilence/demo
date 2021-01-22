package com.leetcode.handwrite.blockingQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockQueue {
    private List<Integer> container = new ArrayList<>();
    private volatile int size;
    private volatile int capacity;
    private Lock lock = new ReentrantLock();
    private final Condition take = lock.newCondition();
    private final Condition add = lock.newCondition();
    BlockQueue(int capacity) {
        this.capacity = capacity;
    }
    public void add(int data) throws InterruptedException {
        try {
            lock.lock();
            while (size >= capacity) {
                System.out.println("阻塞队列满了");
                add.await();
            }
            ++size;
            container.add(data);
            take.signal();
        } finally {
            lock.unlock();
        }
    }
    public int take() throws InterruptedException {
        try {
            lock.lock();
            while (size == 0) {
                System.out.println("阻塞队列空了");
                take.await();
            }
            --size;
            int res = container.get(0);
            container.remove(0);
            add.signal();
            return res;
        } finally {
            lock.unlock();
        }
    }
}


package com.leetcode.handwrite.printABC;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadB implements Runnable{
    public static Lock lock;
    public static int size;
    public static String name;
    public int[] count;
    ThreadB(Lock lock,int size, String name,int[] count){
        this.count = count;
        this.lock = lock;
        this.name = name;
        this.size = size;
    }
    @Override
    public void run() {
        while (count[0] < size) {
            lock.lock();
            if (count[0] % 5 == 0 ) {
                System.out.println(name + count[0]++);
            }
            lock.unlock();
        }
    }
}

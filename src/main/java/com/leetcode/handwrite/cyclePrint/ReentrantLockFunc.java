package com.leetcode.handwrite.cyclePrint;
import com.leetcode.handwrite.cyclePrint.impl.CyclePrint;
import lombok.SneakyThrows;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class ReentrantLockFunc extends CyclePrint {
    private int n;
    public ReentrantLockFunc(int n) {
        this.n = n;
    }
    Lock lock = new ReentrantLock(true);
    private final Condition foo = lock.newCondition();
    volatile boolean flag = true;
    @SneakyThrows
    public void foo(Runnable printFoo) {
        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                while (!flag) {
                    foo.await();
                }
                printFoo.run();
                flag = false;
                foo.signal();
            } finally {
                lock.unlock();
            }
        }
    }
    @SneakyThrows
    public void bar(Runnable printBar) {
        for (int i = 0; i < n;i++) {
            lock.lock();
            try {
                while(flag) {
                    foo.await();
                }
                printBar.run();
                flag = true;
                foo.signal();
            }finally {
                lock.unlock();
            }
        }
    }
    public void start(){
        System.out.println("=============ReentrantLockFunc================");
    }
}

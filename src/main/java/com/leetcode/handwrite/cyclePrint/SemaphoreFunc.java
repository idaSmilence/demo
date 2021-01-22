package com.leetcode.handwrite.cyclePrint;

import com.leetcode.handwrite.cyclePrint.impl.CyclePrint;
import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;

public class SemaphoreFunc extends CyclePrint {
    private int n;
    private Semaphore foo = new Semaphore(1);
    private Semaphore bar = new Semaphore(0);
    public SemaphoreFunc(int n) {
        this.n = n;
    }
    @SneakyThrows
    public void foo(Runnable printFoo) {
        for (int i = 0; i < n; i++) {
            foo.acquire();
            printFoo.run();
            bar.release();
        }
    }
    @SneakyThrows
    public void bar(Runnable printBar) {
        for (int i = 0; i < n; i++) {
            bar.acquire();
            printBar.run();
            foo.release();
        }
    }
    public void start(){
        System.out.println("=============SemaphoreFunc================");
    }
}

package com.leetcode.handwrite.cyclePrint;

import com.leetcode.handwrite.cyclePrint.impl.CyclePrint;
import lombok.SneakyThrows;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierFunc extends CyclePrint {
    private int n;
    public CyclicBarrierFunc(int n) {
        this.n = n;
    }
    CyclicBarrier cb = new CyclicBarrier(2);
    volatile boolean flag = true;
    @SneakyThrows
    public void foo(Runnable printFoo) {
        for (int i = 0; i < n; i++) {
            while(!flag);
            printFoo.run();
            flag = false;
            cb.await();
        }
    }
    @SneakyThrows
    public void bar(Runnable printBar) {
        for (int i = 0; i < n; i++) {
            cb.await();
            printBar.run();
            flag = true;
        }
    }
    public void start(){
        System.out.println("=============CyclicBarrierFunc================");
    }
}

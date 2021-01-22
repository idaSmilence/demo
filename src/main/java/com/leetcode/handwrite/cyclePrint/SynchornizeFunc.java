package com.leetcode.handwrite.cyclePrint;

import com.leetcode.handwrite.cyclePrint.impl.CyclePrint;
import lombok.SneakyThrows;

public class SynchornizeFunc extends CyclePrint {
    private int n;
    // 标志位，控制执行顺序，true执行printFoo，false执行printBar
    private volatile boolean type = true;
    private final Object foo = new Object(); // 锁标志
    public SynchornizeFunc(int n) {
        this.n = n;
    }
    @SneakyThrows
    public void foo(Runnable printFoo) {
        for (int i = 0; i < n; i++) {
            synchronized (foo) {
                while(!type){
                    foo.wait();
                }
                printFoo.run();
                type = false;
                foo.notifyAll();
            }
        }
    }
    @SneakyThrows
    public void bar(Runnable printBar) {
        for (int i = 0; i < n; i++) {
            synchronized (foo) {
                while(type){
                    foo.wait();
                }
                printBar.run();
                type = true;
                foo.notifyAll();
            }
        }
    }
    public void start(){
        System.out.println("=============SynchornizeFunc================");
    }
}

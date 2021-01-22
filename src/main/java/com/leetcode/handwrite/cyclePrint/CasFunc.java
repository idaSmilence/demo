package com.leetcode.handwrite.cyclePrint;

import com.leetcode.handwrite.cyclePrint.impl.CyclePrint;
import lombok.SneakyThrows;

public class CasFunc extends CyclePrint {
    private int n;
    public CasFunc(int n) {
        this.n = n;
    }
    volatile boolean permitFoo = true;
    @SneakyThrows
    public void foo(Runnable printFoo){
        for (int i = 0; i < n; ) {
            if(permitFoo) {
                printFoo.run();
                i++;
                permitFoo = false;
            }else{
                Thread.yield();
            }
        }
    }
    @SneakyThrows
    public void bar(Runnable printBar){
        for (int i = 0; i < n; ) {
            if(!permitFoo) {
                printBar.run();
                i++;
                permitFoo = true;
            }else{
                Thread.yield();
            }
        }
    }
    public void start(){
        System.out.println("=============CasFunc================");
    }
}

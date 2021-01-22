package com.leetcode.handwrite.cyclePrint;

import com.leetcode.handwrite.cyclePrint.impl.CyclePrint;
import lombok.SneakyThrows;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockQueueFunc extends CyclePrint {
    private int n;
    private BlockingQueue<Integer> bar = new LinkedBlockingQueue<>(1);
    private BlockingQueue<Integer> foo = new LinkedBlockingQueue<>(1);
    public BlockQueueFunc(int n) {
        foo.add(0);
        this.n = n;
    }
    @SneakyThrows
    public void foo(Runnable printFoo) {
        for (int i = 0; i < n; i++) {
            foo.take();
            printFoo.run();
            bar.put(1);
        }
    }
    @SneakyThrows
    public void bar(Runnable printBar){
        for (int i = 0; i < n; i++) {
            bar.take();
            printBar.run();
            foo.put(2);
        }
    }
    public void start(){
        System.out.println("=============BlockQueueFunc================");
    }
}

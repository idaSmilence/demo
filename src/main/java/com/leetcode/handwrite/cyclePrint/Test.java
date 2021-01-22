package com.leetcode.handwrite.cyclePrint;


import com.leetcode.handwrite.cyclePrint.Task;
import com.leetcode.handwrite.cyclePrint.impl.CyclePrint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        final int CNT = 10;
        List<CyclePrint> cpList = new ArrayList<>();
        /**
         *  synchronized (foo)
         *    while(flag)
         *      foo.wait();
         *    printBar.run();
         *    type = true;
         *    foo.notifyAll();
         **/
        cpList.add(new SynchornizeFunc(CNT));
        /**
         *  foo.acquire();
         *    printFoo.run();
         *  bar.release();
         */
        cpList.add(new SemaphoreFunc(CNT));
        /**
         *  while (!flag)
         *    foo.await();
         *  printFoo.run();
         *  flag = false;
         *  foo.signal();
         */
        cpList.add(new ReentrantLockFunc(CNT));
        /**
         *  while(!flag);
         *  printFoo.run();
         *  fin = false;
         *  cb.await();
         */
        cpList.add(new CyclicBarrierFunc(CNT));
        /**
         *  if(flag) {
         *    printFoo.run();
         *    i++;
         *    flag = false;
         *  }else{
         *    Thread.yield();
         *  }
         */
        cpList.add(new CasFunc(CNT));
        /**
         *  foo.take();
         *  printFoo.run();
         *  bar.put(i);
         */
        cpList.add(new BlockQueueFunc(CNT));
        while(!cpList.isEmpty()){
            CyclePrint sf=cpList.remove(0);
            System.out.println();
            sf.start();
            Thread t1 = new Thread(() -> {
                sf.foo(new Task(" A "));
            });
            Thread t2 = new Thread(() -> {
                sf.bar(new Task(" B "));
            });
            t1.start();
            t2.start();
            Thread.sleep(1000);
        }

    }
}

package com.leetcode.handwrite.printABC;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
public class PrinterABC implements Runnable{
    private final int cnt;
    private final ReentrantLock reentrantLock;
    private final Condition cur;
    private final Condition next;
    private final char c;
    public PrinterABC(ReentrantLock reentrantLock, Condition cur, Condition next, char c, int cnt) {
        this.reentrantLock = reentrantLock;
        this.cur = cur;
        this.next = next;
        this.c = c;
        this.cnt = cnt;
    }
    @Override
    public void run() {
        reentrantLock.lock();
        try {
            for (int i = 0; i < cnt; i++) {
                System.out.print(c);
                try {
                    next.signal();
                    cur.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            reentrantLock.unlock();
        }
    }
}

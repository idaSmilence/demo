package com.leetcode.handwrite.printABC;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
public class Test{
    public static void main(String[] args) throws InterruptedException {
//        printABC();
        print235();
    }
    private static void print235() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(); // 写锁
        int[] a = {1};
        new Thread(new ThreadA(lock,100,"A : ",a)).start();
        new Thread(new ThreadB(lock,100,"B : ",a)).start();
        new Thread(new ThreadC(lock,100,"C : ",a)).start();
    }
    private static void printABC() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(); // 写锁
        int cnt = 10;
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();
        Thread printerA = new Thread(new PrinterABC(lock, conditionA, conditionB, 'A',cnt));
        Thread printerB = new Thread(new PrinterABC(lock, conditionB, conditionC, 'B',cnt));
        Thread printerC = new Thread(new PrinterABC(lock, conditionC, conditionA, 'C',cnt));
        // 依次开始A B C线程
        printerA.start();
        Thread.sleep(100);
        printerB.start();
        Thread.sleep(100);
        printerC.start();
    }
}


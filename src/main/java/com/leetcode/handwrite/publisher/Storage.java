package com.leetcode.handwrite.publisher;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static int MAX_VALUE = 100;
    private List<Object> list = new ArrayList<>();
    public void produce(int num) throws Exception {
        synchronized (list) {
            while (list.size() + num > MAX_VALUE) {
                System.out.println("    剩余库存："+list.size()+",增加库存："+num+"失败，线程阻塞");
                list.wait();
            }
            for (int i = 0; i < num; i++) {
                list.add(new Object());
            }
            System.out.println("    剩余库存：" + list.size()+"增加库存"+num+"成功" );
            list.notifyAll();
        }
    }

    public void consume(int num) throws Exception {
        synchronized (list) {
            while (list.size() < num) {
                System.out.println("    剩余库存："+list.size()+",减少库存："+num+"失败，线程阻塞");
                list.wait();
            }
            for (int i = 0; i < num; i++) {
                list.remove(0);
            }
            System.out.println("    剩余库存：" + list.size()+"减少库存"+num+"成功" );
            list.notifyAll();
        }
    }
}






package com.leetcode.handwrite.lruCache;

public class Test {
    public static void main(String[] args) {
//        LruCache lru = new LruCache(3);
        LinkedHashMapLru lru = new LinkedHashMapLru(3);
        System.out.println("Get 5:"+lru.get(5));
        System.out.println("Put 1");
        lru.put(1,1);
        System.out.println("Put 2");
        lru.put(2,2);
        System.out.println("Get 1:"+lru.get(1));
        System.out.println("Put 3");
        lru.put(3,3);
        System.out.println("Put 4");
        lru.put(4,4);
        System.out.println("Get 2:"+lru.get(2));
        System.out.println("Get 1:"+lru.get(1));
    }
}

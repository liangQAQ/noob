package com.huangliang.concurrent;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 实现redis中key的最近最少使用淘汰算法LRU
 */
public class RedisLRUTest {

    private static Map map = new LinkedHashMap<String, String>();

    private static void getKey(String key) {
        if (map.containsKey(key)) {
            map.remove(key);
        }
        map.put(key, "");
    }

    private static void remove() {
        String key = null;
        Iterator iterator = map.keySet().iterator();
        if (iterator.hasNext()) {
            key = iterator.next().toString();
        }
        map.remove(key);
    }

    public static void main(String[] args) {
        getKey("b");
        getKey("c");
        getKey("c");
        getKey("d");
        getKey("a");
        getKey("b");

        System.out.println(map);
        remove();
        System.out.println(map);
    }
}

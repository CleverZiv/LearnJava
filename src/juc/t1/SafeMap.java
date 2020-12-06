package juc.t1;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by lengzefu on 2018/11/20.
 * 实现一个拥有读写锁的线程安全性Map
 */
public class SafeMap {

    Map<String, String> map = new HashMap<>();

    //读写锁对象
    private ReadWriteLock rwl = new ReentrantReadWriteLock();
    //读锁
    private Lock r = rwl.readLock();
    //写锁
    private Lock w = rwl.writeLock();

    /**
     * 读方法，用读写锁的读锁
     * @param key
     * @return
     */
    public String get(String key){
        r.lock();
        try{
            return map.get(key);
        }finally {
            r.unlock();
        }
    }

    /**
     * 写方法，用读写锁的写锁
     * @param key
     * @param value
     */
    public void put(String key, String value){
        w.lock();
        try{
            map.put(key, value);
        }finally {
            w.unlock();
        }
    }
}

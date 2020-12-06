package juc.t1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lengzefu on 2018/11/17.
 */
public class Demo8 {
    private int value;
    //获取锁实例
    MyLock lock = new MyLock();

    public int getNext(){
        //lock()：锁的开始
        lock.lock();
        int a = value++;
        //unlock()：锁的结束。中间部分即为同步的代码块
        lock.unlock();
        return a;
    }

    public static void main(String[] args){
        final Demo8 demo8 = new Demo8();
        /**
         * 同一个对象拥有同一个Lock对象（Demo8的属性）
         * 这样才能锁住所有使用该demo8实例的线程
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    System.out.println(Thread.currentThread().getName()+" " + demo8.getNext());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    System.out.println(Thread.currentThread().getName()+" " + demo8.getNext());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    System.out.println(Thread.currentThread().getName()+" " + demo8.getNext());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}

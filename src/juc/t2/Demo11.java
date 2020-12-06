package juc.t2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lengzefu on 2018/11/22.
 * Condition使用示例
 * 三个线程按顺序依次执行
 */
public class Demo11 {

    private int signal = 0;//标识
    private Lock lock = new ReentrantLock();
    //创建Condition对象，有几个线程任务就创建几个
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();

    /**
     * 创建三个线程任务要实际调用的方法：a,b,c
     */
    public void a(){
        lock.lock();
        //signal==0时执行打印任务
        while(signal != 0){
            try {
                conditionA.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("a");
        signal++;
        conditionB.signal();
        lock.unlock();
    }

    public void b(){
        lock.lock();
        if(signal != 1){
            try {
                conditionB.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("b");
        signal++;
        conditionC.signal();
        lock.unlock();
    }

    public void c(){
        lock.lock();
        if(signal != 2){
            try {
                conditionC.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("c");
        signal = 0;
        conditionA.signal();
        lock.unlock();
    }

    public static void main(String[] args){
        Demo11 demo11 = new Demo11();

        new Thread(new ThreadA(demo11)).start();
        new Thread(new ThreadB(demo11)).start();
        new Thread(new ThreadC(demo11)).start();
    }
}

/**
 * 创建三个线程任务，分别调用a,b,c三个方法
 */
class ThreadA implements Runnable{

    private Demo11 demo11;

    public ThreadA(Demo11 demo11) {
            this.demo11 = demo11;
    }

    @Override
    public void run() {
        while(true){
            demo11.a();
        }
    }
}

class ThreadB implements Runnable{

    private Demo11 demo11;

    public ThreadB(Demo11 demo11) {
        this.demo11 = demo11;
    }

    @Override
    public void run() {
        while(true){
            demo11.b();
        }

    }
}

class ThreadC implements Runnable{

    private Demo11 demo11;

    public ThreadC(Demo11 demo11) {
        this.demo11 = demo11;
    }

    @Override
    public void run() {
        while(true){
            demo11.c();
        }
    }
}

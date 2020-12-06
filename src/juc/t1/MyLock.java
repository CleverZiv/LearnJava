package juc.t1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by lengzefu on 2018/11/17.
 * 手动实现一个自己的锁
 * 暂时只先实现lock和unlock方法
 */
public class MyLock implements Lock {

    private boolean isLocked = false;

    //需要记录当前拥有锁的线程
    Thread lockBy = null;

    //需要记录锁被重入的次数
    int lockCount = 0;

    /**
     * 要求第一个线程进来时可以继续向下执行
     * 而之后的每个线程阻塞在这个方法里
     * 需要有一个标识来区分第一个和之后的线程
     */
    @Override
    public synchronized void lock() {
        //第一个线程进来不会执行while
        //线程进来时先获取线程，方便判断
        Thread currentThread = Thread.currentThread();
        while (isLocked && currentThread != lockBy){
            //wait()，需要在synchronized方法中实现
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //第一个线程需要修改标识、lockBy赋值、lockCount加1
        isLocked = true;
        lockBy = currentThread;
        lockCount++;
    }

    @Override
    public synchronized void unlock() {
        //只有当拥有锁的线程调用unlock时才有效
        if(lockBy == Thread.currentThread()){
            lockCount--;
            //只有当lockCount==0时才释放锁
            if(lockCount == 0){
                isLocked = false;
                notify();
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}

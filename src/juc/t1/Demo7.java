package juc.t1;

/**
 * Created by lengzefu on 2018/11/17.
 */
public class Demo7 {

    /**
     * a()和b()均加了synchrinized关键字，锁对象都是
     * Demo7的实例
     */
    public synchronized void a(){
        System.out.println("执行a方法");
        //为了放大其中一个线程的等待过程，让其睡眠3秒，否则无法感受到另外一个线程
        //确实等待了其先释放锁
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void b(){
        System.out.println("执行b方法");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        final Demo7 demo7 = new Demo7();
        //线程1
        new Thread(new Runnable() {
            @Override
            public void run() {
                demo7.a();
            }
        }).start();

        //线程2
        new Thread(new Runnable() {
            @Override
            public void run() {
                demo7.b();
            }
        }).start();
    }
}

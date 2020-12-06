package juc.t1;

/**
 * Created by lengzefu on 2018/11/15.
 */
public class Demo1 extends Thread {

    //重载构造函数，目的是为了在创建线程时指定线程名字，并不是必须的
    public Demo1(String name) {
        super(name);
    }

    //线程执行的方法体，必须的。
    @Override
    public void run() {
        System.out.print(getName() + "运行了....");
    }

    public static void main(String[] args){
        //Thread 接收实现了run方法的类对象 new Demo1(“线程1”)，此时t1是新建状态
        Thread t1 = new Thread(new Demo1("线程1"));

        //main 线程调用t1.start()，t1此时处于可运行状态，等待cpu时间分片
        t1.start();
    }
}

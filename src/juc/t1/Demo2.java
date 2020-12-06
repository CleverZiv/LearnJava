package juc.t1;

/**
 * Created by lengzefu on 2018/11/15.
 * 作为线程任务存在
 */
public class Demo2 implements Runnable {
    @Override
    public void run() {
        System.out.print("线程运行了...");
    }

    public static void main(String[] args){
        Thread t = new Thread(new Demo2());

        t.start();
    }
}

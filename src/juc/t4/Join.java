package juc.t4;

/**
 * Created by lengzefu on 2018/11/24.
 * join使用示例
 */
public class Join {

    public void a(Thread joinThread){
        System.out.println("方法a开始执行");
        //方法a中调用加塞线程
        joinThread.start();
        //必须使用join，才能使a本身所在线程进入wait状态
        try {
            joinThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("方法a执行完毕");
    }

    public void b(){
        System.out.println("方法b开始执行");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("方法b执行完毕");
    }

    public static void main(String[] args){
        final Join join = new Join();
        final Thread joinThread = new Thread(new Runnable() {
            @Override
            public void run() {
                join.b();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                join.a(joinThread);
            }
        }).start();
    }
}

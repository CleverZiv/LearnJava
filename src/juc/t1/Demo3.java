package juc.t1;

/**
 * Created by lengzefu on 2018/11/15.
 */
public class Demo3 {
    public static void main(String[] args){

        //方式1 继承Thread子类的方式
/*        new Thread(){
            @Override
            public void run() {
                System.out.println("1这段代码作为线程任务只需要执行一次");
            }
        }.start();*/

        //方式2 实现Runnable接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("2这段代码作为线程任务只需要执行一次");
            }
        }).start();
    }
}

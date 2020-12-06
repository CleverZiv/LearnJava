package juc.t1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lengzefu on 2018/11/15.
 */
public class Demo6 {

    public static void main(String[] args){
        //顶层接口是Executor，ExecutorService是其子接口，功能更强大
        //Executors是一个工具类，类比集合中的Collections
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        //excute()：向线程池中提交任务
        for(int i = 0; i < 100; i++){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread() + "完成了任务");
                }
            });
        }
        threadPool.shutdown();
    }
}

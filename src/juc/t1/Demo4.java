package juc.t1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by lengzefu on 2018/11/15.
 * 实现Callable接口来创建拥有返回值的线程
 */
public class Demo4 implements Callable<Integer>{

    //call()就相当于run()，存放线程任务
    @Override
    public Integer call() throws Exception {
        System.out.println("执行有返回值的线程");
        Thread.sleep(2000);
        return 1;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1. 先将实现了Callable接口的类对象封装到FutureTask中
        FutureTask<Integer> task = new FutureTask<Integer>(new Demo4());

        //2. 再创建线程接收task
        Thread t = new Thread(task);

        t.start();

        //3. 接收线程执行的返回值
        int res = task.get();
        System.out.println(res);

    }
}

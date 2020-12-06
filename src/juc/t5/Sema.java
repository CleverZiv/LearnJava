package juc.t5;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by lengzefu on 2018/11/25.
 * Semaphore示例
 */
public class Sema {

    public static void main(String[] args){
        int num = 8; //工人数
        Semaphore semaphore = new Semaphore(5);//机器数
        for(int i = 0; i < num; i++){
            new Worker(i, semaphore).start();
        }
    }


    /**
     * 工人，要进行的任务
     */
    static class Worker extends Thread{
        private int num;
        private Semaphore semaphore;
        public Worker(int num, Semaphore semaphore){
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            //首先需要获得许可
            try {
                semaphore.acquire();
                System.out.println("工人" + this.num + "占用一个机器在生产");
                Thread.sleep(new Random().nextInt(1000));
                System.out.println("工人" + this.num + "使用完毕机器");
                //使用完毕释放一个许可
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

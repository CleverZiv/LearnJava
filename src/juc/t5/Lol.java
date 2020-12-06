package juc.t5;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lengzefu on 2018/11/24.
 * CountDownLatch示例
 * LOL中必须等待5个队友全部连接成功才能进入游戏
 */
public class Lol {
    private static final int memberNum = 5; //成员数量

    /**
     * 模拟每个成员需要做的任务
     * 这里5个人需要做的一样，都是连接服务器
     */
    private static class ConnectTask implements Runnable{

        private int num;
        private CountDownLatch cdl;

        private ConnectTask(int num, CountDownLatch cdl){
            this.num = num;
            this.cdl = cdl;
        }

        @Override
        public void run() {
            System.out.println("第" + num + "成员正连接游戏");
            try {
                //模拟连接任务
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第" + num + "成员已成功连接游戏");
            //执行完一次就countDown一次
            cdl.countDown();
        }
    }

    public static void main(String[] args) {
        CountDownLatch cdl = new CountDownLatch(memberNum);
        //5个成员任务一样，直接利用for循环执行一样的任务
        for(int i = 1; i <=  memberNum; i++){
            new Thread(new ConnectTask(i, cdl)).start();
        }
        //虽然已经跳出for循环，但在for循环中开启的线程却还没执行完，而继续下去的操作
        //必须等待所有开启的线程执行完，才能再主线程中继续执行
        System.out.println("必须等待所有成员都连接上游戏才能开始游戏");
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有成员已经连接进游戏，主线程开始配置游戏资源");
        System.out.println("游戏资源配置完毕，begin the game!!");
    }

}

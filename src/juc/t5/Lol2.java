package juc.t5;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by lengzefu on 2018/11/24.
 * CyclicBarrier示例
 * 同样实现“LOL中必须等待5个队友全部连接成功才能进入游戏”的功能
 */
public class Lol2 {

    private static final int memberNum = 5; //成员数量
    /**
     * 每个成员需要进行的连接任务
     * @param barrier
     */
    public void ConnectTask(CyclicBarrier barrier) {
        try {
            Thread.sleep(new Random().nextInt(1000));
            System.out.println("第" + Thread.currentThread().getId() + "正在连接游戏");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("第" + Thread.currentThread().getId() + "已经连接游戏,正在等待所有成员就绪");
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final Lol2 lol2 = new Lol2();

        final CyclicBarrier barrier = new CyclicBarrier(memberNum, new Runnable() {
            @Override
            public void run() {
                System.out.println("全部成员就绪，begin the game!!");
            }
        });
        //开启成员线程
        for(int i = 0; i < memberNum; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    lol2.ConnectTask(barrier);
                }
            }).start();
        }
    }
}

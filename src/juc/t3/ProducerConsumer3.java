package juc.t3;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by lengzefu on 2018/11/23.
 * 使用阻塞队列实现生产者和消费者模式
 */
public class ProducerConsumer3 {

    private static final int  MAX_CAPACITY = 5;

    public static void main(String[] args){

        LinkedBlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(MAX_CAPACITY);

        Thread producer1 = new Thread(new Producer3("p1", blockingQueue, MAX_CAPACITY));
        Thread producer2 = new Thread(new Producer3("p2", blockingQueue, MAX_CAPACITY));

        Thread consumer1 = new Thread((new Consumer3("c1", blockingQueue, MAX_CAPACITY)));
        Thread consumer2 = new Thread((new Consumer3("c2", blockingQueue, MAX_CAPACITY)));
        Thread consumer3 = new Thread((new Consumer3("c3", blockingQueue, MAX_CAPACITY)));

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
    }

    /**
     * 生产者
     */
    public static class Producer3 implements Runnable{
        private String name;
        private LinkedBlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(MAX_CAPACITY);
        private int maxSize;

        public Producer3(String name, LinkedBlockingQueue blockingQueue, int maxSize){
            this.name = name;
            this.blockingQueue = blockingQueue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            //阻塞队列做了所有的事
            while(true){
                try {
                    blockingQueue.put(new Random().nextInt(100));
                    System.out.println("仓库没满，生产者" + name + "开始进行生产货物");
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Consumer3 implements Runnable{
        private String name;
        private LinkedBlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(MAX_CAPACITY);
        private int maxSize;

        public Consumer3(String name, LinkedBlockingQueue blockingQueue, int maxSize){
            this.name = name;
            this.blockingQueue = blockingQueue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            //阻塞队列做了所有的事
            while(true){
                try {
                    int x = blockingQueue.take();
                    System.out.println("消费者" + name + "消费了货物[" + x + "]");
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

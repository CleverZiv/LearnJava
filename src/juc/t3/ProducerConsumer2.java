package juc.t3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lengzefu on 2018/11/23.
 * 使用Condition实现生产者消费者模式
 */
public class ProducerConsumer2 {

    private static final int MAX_CAPACITY = 5;
    private static final Lock lock = new ReentrantLock();
    //有几个线程任务就开几个condition
    private static final Condition fullCondition = lock.newCondition();//队列满的条件
    private static final Condition emptyCondition = lock.newCondition();//队列空的条件

    public static void main(String[] args){
        Queue<Integer> queue = new LinkedList<>();

        Thread producer1 = new Thread(new Producer2("p1", queue, MAX_CAPACITY));
        Thread producer2 = new Thread(new Producer2("p2", queue, MAX_CAPACITY));

        Thread consumer1 = new Thread((new Consumer2("c1", queue, MAX_CAPACITY)));
        Thread consumer2 = new Thread((new Consumer2("c2", queue, MAX_CAPACITY)));
        Thread consumer3 = new Thread((new Consumer2("c3", queue, MAX_CAPACITY)));

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
    }

    /**
     * 生产者
     */
    public static class Producer2 implements Runnable{
        private String name;
        private Queue<Integer> queue;
        private int maxSize;

        public Producer2(String name, Queue<Integer> queue, int maxSize){
            this.name = name;
            this.queue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            while(true){
                lock.lock();
                while(queue.size() >= maxSize){
                    System.out.println("仓库已满，生产者" + name + "正等待消费者从仓库中取出货物");
                    try {
                        fullCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("仓库未满，生产者" + name + "开始进行生产货物");
                queue.offer(new Random().nextInt(100));
                //唤醒其他所有的生产者、消费者
                fullCondition.signalAll();
                //emptyCondition.signalAll();

                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }

        }
    }

    public static class Consumer2 implements Runnable{

        private String name;
        private Queue<Integer> queue;
        private int maxSize;

        public Consumer2(String name, Queue<Integer> queue, int maxSize){
            this.name = name;
            this.queue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            while (true){
                lock.lock();
                while(queue.size() <= 0){
                    try {
                        System.out.println("仓库为空，消费者" + name + "需要等待生产者进行生产");
                        emptyCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int x = queue.poll();
                System.out.println("消费者" + name + "消费了货物[" + x + "]");
                emptyCondition.signalAll();
                fullCondition.signalAll();

                try {
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }

        }
    }
}


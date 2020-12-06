package juc.t3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by lengzefu on 2018/11/23.
 * 生产者消费者模式
 * 使用wait和notify实现
 */
public class ProducerConsumer1 {

    private static final int MAX_CAPACITY = 5;
    public static void main(String[] args){

        Queue<Integer> queue = new LinkedList<>();

        Thread producer1 = new Thread(new Producer("p1", queue, MAX_CAPACITY));
        Thread producer2 = new Thread(new Producer("p2", queue, MAX_CAPACITY));

        Thread consumer1 = new Thread((new Consumer("c1", queue, MAX_CAPACITY)));
        Thread consumer2 = new Thread((new Consumer("c2", queue, MAX_CAPACITY)));
        Thread consumer3 = new Thread((new Consumer("c3", queue, MAX_CAPACITY)));

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
    }
}

/**
 * 生产者
 */
class Producer implements Runnable{
    private String name;
    private Queue<Integer> queue;
    private int maxSize;

    public Producer(String name, Queue<Integer> queue, int maxSize){
        this.name = name;
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while(true){
            synchronized (queue){
                while(queue.size() >= maxSize) {
                    //1.队列满，生产者进行等待
                    System.out.println("仓库已满，生产者" + name + "正等待消费者从仓库中取出货物");
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //2.队列不满即可进行生产
                System.out.println("仓库未满，生产者" + name + "开始进行生产货物");
                queue.offer(new Random().nextInt(100));
                //只要生产了货物，即只要有货物，消费者线程就可以被唤醒取走货物
                queue.notifyAll();
                //3. 等待
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

/**
 * 消费者
 */
class Consumer implements Runnable{
    private String name;
    private Queue<Integer> queue;
    private int maxSize;

    public Consumer(String name, Queue<Integer> queue, int maxSize){
        this.name = name;
        this.queue = queue;
        this.maxSize = maxSize;
    }
    @Override
    public void run() {
        while(true){
            synchronized (queue){
                while(queue.size() <= 0){
                    //1.队列为空，消费者等待
                    try {
                        System.out.println("仓库为空，消费者" + name + "需要等待生产者进行生产");
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //2. 队列不为空，就进行消费
                int x = queue.poll();
                System.out.println("消费者" + name + "消费了货物[" + x + "]");
                queue.notifyAll();
                //3. 每次消费完应该进行等待
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

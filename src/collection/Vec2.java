package collection;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Created by lengzefu on 2018/10/25.
 */
public class Vec2 {
    private static List<String> list = new Vector<String>();
    public static void main(String[] args){
        list.add("1");
        list.add("2");
        list.add("3");
        //新建两个线程并启动
        Thread thread1 = new Thread(new ItrThread());
        Thread thread2 = new Thread(new ModifyThread());
        thread1.start();
        thread2.start();
    }

    /**
     * 内部类，遍历集合的线程
     */
    private static class ItrThread implements Runnable{

        @Override
        public void run() {
            int i = 0;
            while(i < 10){
                Iterator iter = list.iterator();
                while(iter.hasNext()){
                    System.out.print((String) iter.next() + ", ");
                }
                System.out.println();
            }
        }
    }
    /**
     * 内部类，修改集合的线程
     */
    private static class ModifyThread implements Runnable{

        @Override
        public void run() {
            list.add(String.valueOf("9"));
        }
    }

}


package juc.t1;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lengzefu on 2018/11/15.
 * 利用定时器（Timer）执行
 */
public class Demo5 {
    public static void main(String[] args){

        Timer timer = new Timer();

        //定时器对象timer接收定时器任务TimerTask对象
        //TimerTask实现了Runnable接口
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 0;
            @Override
            public void run() {
                i++;
                System.out.println("这是第" + i +"次执行");
            }
        }, 0, 1000);
    }
}

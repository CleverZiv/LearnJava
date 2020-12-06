package juc.t1;

/**
 * Created by lengzefu on 2018/11/17.
 */
public class Demo9 {

    MyLock lock = new MyLock();

    public void a(){
        lock.lock();
        System.out.println("a");
        //a中调用b，能调用则可重入
        b();
        lock.unlock();
    }

    public void b(){
        lock.lock();
        System.out.println("b");
        lock.unlock();
    }

    public static void main(String[] args){
        final Demo9 demo9 = new Demo9();
        new Thread(new Runnable() {
            @Override
            public void run() {
                demo9.a();
            }
        }).start();
    }
}

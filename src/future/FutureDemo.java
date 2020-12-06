package future;

import java.util.concurrent.*;

/**
 * @Classname FutureDemo
 * @Date 2020/4/4 20:46
 * @Autor lengzefu
 */
public class FutureDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        Future future01 = threadPool.submit(new RunnableTask());
    }

    private static class RunnableTask implements Runnable {

        @Override
        public void run() {

        }
    }

    private static class CallableTask<T> implements Callable {

        @Override
        public Object call() throws Exception {
            return null;
        }
    }
}

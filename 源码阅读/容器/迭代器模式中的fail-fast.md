---
title: 迭代器模式中的fail-fast
date: 2018-10-29 15:59
tags: [设计模式]
categories: [学习,编程]
---
## 迭代器模式中的fail-fast ##
### 一、什么是fail-fast ###
看容器源码时，经常会在迭代器部分的注释看到这样类似的一句话：
> <p>The returned list iterator is <a href="#fail-fast"><i>fail-fast</i></a>.

很显然，fail-fast是用来描述迭代器具有的某种特性，那这种特性是什么呢？
由iterator()和listIterator()返回的迭代器是fail-fast的，在于程序对list进行迭代时，某个线程对该容器在== 结构 ==上做了修改，这时迭代器就会抛出ConcurrentModificationException异常信息。因此，面对并发的修改，迭代器快速而干净地失败，而不是在不确定的情况冒险。由elements()返回的Enumerations不是fail-fast的。需要注意的是，迭代器的fail-fast并不能得到保证，它不能保证一定出现该错误。一般来说，fail-fast会尽最大努力抛出ConcurrentModificationException异常。因此，为提高此类操作的正确性而编写一个依赖于此异常的程序是错误的做法，正确做法是：ConcurrentModificationException 应该仅用于检测 bug。

大意为在遍历一个集合时，当集合结构被修改，很有可能会抛出Concurrent Modification Exception。为什么说是很有可能呢？从下文中我们可以知道，== 迭代器的remove操作 ==（注意是迭代器的remove方法而不是集合的remove方法）修改集合结构就不会导致这个异常。

看到这里我们就明白了，fail-fast 机制是java容器（Collection和Map都存在fail-fast机制）中的一种错误机制。在遍历一个容器对象时，当容器结构被修改，很有可能会抛出ConcurrentModificationException，产生fail-fast。

### 二、什么时候会出现fail-fast ###

在以下两种情况下会导致fail-fast，抛出ConcurrentModificationException

*	单线程环境 
遍历一个集合过程中，集合结构被修改。注意，迭代器本身的remove()修改集合结构不会抛出这个异常（如listIterator.remove()）。
*	多线程环境 
当一个线程遍历集合过程中，而另一个线程对集合结构进行了修改。

#### 2.1 单线程环境的例子 ####
```java
import java.util.ListIterator;
import java.util.Vector;

/**
 * Created by lengzefu on 2018/10/25.
 */
public class Vec {
    public static void main(String[] args){
        try{
            Vec vec = new Vec();
            //测试迭代器的remove方法修改集合结构会不会触发checkForComodification异常
            vec.ItrRemoveTest();
            System.out.println("-------------------------");
            //测试集合的remove方法修改集合结构会不会触发checkForComodification异常
            vec.ListRemoveTest();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //测试迭代器的remove方法修改集合结构会不会触发checkForComodification异常
    private void ItrRemoveTest() {
        Vector list = new Vector<>();
        list.add("1");
        list.add("2");
        list.add("3");
        ListIterator itr = list.listIterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
            //迭代器的remove方法修改集合结构
            itr.remove();
        }
    }

    //测试集合的remove方法修改集合结构会不会触发checkForComodification异常
    private void ListRemoveTest() {
        Vector list = new Vector<>();
        list.add("1");
        list.add("2");
        list.add("3");
        ListIterator itr = list.listIterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
            //集合的remove方法修改集合结构
            list.remove("3");
        }
    }

}

```
运行结果
```java
1
2
3
-------------------------
1
java.util.ConcurrentModificationException
	at ……

```
从结果中可以看到迭代器itr的remove操作并没有出现ConcurrentModificationException异常。而集合的remove操作则产生了异常。

#### 2.2 多线程环境的例子 ####
```java

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


```
运行结果：
```java
Exception in thread "Thread-0" java.util.ConcurrentModificationException
	at java.util.Vector$Itr.checkForComodification(Vector.java:1210)
	at java.util.Vector$Itr.next(Vector.java:1163)
	at collection.Vec2$ItrThread.run(Vec2.java:34)
	at java.lang.Thread.run(Thread.java:748)
```
从结果中可以看出当一个线程遍历集合，而另一个线程对这个集合的结构进行了修改，确实有可能触发ConcurrentModificationException异常。

### 三、 实现原理 ###
```java
    /**
     * An optimized version of AbstractList.Itr
     */
    private class Itr implements Iterator<E> {
        int expectedModCount = modCount;

        public void remove() {
            if (lastRet == -1)
                throw new IllegalStateException();
            synchronized (Vector.this) {
                checkForComodification();
                Vector.this.remove(lastRet);
                expectedModCount = modCount;
            }
            cursor = lastRet;
            lastRet = -1;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            synchronized (Vector.this) {
                checkForComodification();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }


```

上述源码为了更清晰简单的展示，省略了多段代码。
从代码中可以看到，每次初始化一个迭代器都会执行int expectedModCount = modCount;。modcount意为moderate count，即修改次数，对集合内容的修改都将增大这个值，如modCount++;。在迭代器初始化过程中会执行int expectedModCount = modCount;来记录迭会通过checkForComodification()方法判断modCount和expectedModCount 是否相等，如果不相等就表示已经有线程修改了集合结构。
使用迭代器的remove()方法修改集合结构不会触发ConcurrentModificationException，现在可以在源码中看出来是为什么。在remove()方法的最后会执行expectedModCount = modCount;，这样itr.remove操作后modCount和expectedModCount依然相等，就不会触发ConcurrentModificationException了。

== 如何避免fail-fast ==
使用java.util.concurrent包下的类去取代java.util包下的类。所以，本例中只需要将Vector替换成java.util.concurrent包下对应的类即可




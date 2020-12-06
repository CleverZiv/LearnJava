---
title: LinkedList源码阅读笔记
date: 2018-10-30 22:37
tags: [Java源码阅读]
categories: [学习,编程]
---
## LinkedList ##
### 一、LinkedList简介 ###
LinkedList和ArrayList与Vector一样，实现了Lits接口，但它执行某些操作如插入（此处指随机出入，如果是依次在末尾插入，不一定效率更高）、和删除元素操作比ArrayList与Vector更高效，而随机访问操作效率低。除此之外，LinkedList还添加了可以使其用作栈、队列或双端队列的方法。LinkedList在实现方面与ArrayList与Vector有哪些不同呢？为什么它插入删除操作效率高？随机访问操作效率低？它是如何用作栈、队列或双端队列的？本文将分析LinkedList的内部结构及实现原理，帮助大家更好的使用它，并一一解答上述问题。
#### 1.1 类继承结构 ####
![Alt Text](http://ovrlh76oe.bkt.clouddn.com/LinkedList%E7%B1%BB%E7%BB%A7%E6%89%BF%E7%BB%93%E6%9E%84.png)
#### 1.2 数据结构 ####
LinkedList底层的数据结构是基于双向循环链表的，JDK1.7之前头结点不存储数据，而1.7之后头结点是存储数据的。看源码：
```java
/**
 * 指向头节点的指针
 * Invariant: (first == null && last == null) ||
 *            (first.prev == null && first.item != null)
 */
transient Node<E> first;
```
尤其注意注释中的“(first == null && last == null) ||(first.prev == null && first.item != null)”。头结点只有这两种情况：

![Alt Text](http://ovrlh76oe.bkt.clouddn.com/LInkedList%E5%BA%95%E5%B1%82%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84.png)
同理，查看尾节点的注释也是这样的意思。
链表中的节点组成如下：
![Alt Text](http://ovrlh76oe.bkt.clouddn.com/LinkedList%E8%8A%82%E7%82%B9%E5%9B%BE.png)

### 二、源码 ###
#### 2.1 注释 ####
> LinkedList是List接口和Deque接口的双向链表实现。它实现了所有的列表操作，允许所有的元素（包括null）。
所有对LinkedList的操作都看作是对双向链表的操作。操作需要遍历时需要从头或者尾开始，具体选头还是尾，取决于指定的索引离哪个更近。
注意此实现是线程不同步的。
“Collections.synchronizedList”可以在LinkedList创建之初将其包装起来，以保证线程安全。
List list = Collections.synchronizedList(new LinkedList(...))
LinkedList返回的iterator挥着listIterator都是fail-fast的。

#### 2.2 定义 ####
```java
public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable

```
类定义与类继承结构图是对应的。
从中我们可以了解到：
*	LinkedList <E>：说明LinkedList支持泛型。
*	extends AbstractSequentialList<E> ：AbstractSequentialList继承了AbstractList。AbstractList提供List接口的骨干实现，以最大限度地减少“随机访问”数据存储（如ArrayList）实现Llist所需的工作。但AbstractSequentialList 只支持按次序访问，而不像 AbstractList 那样支持随机访问。这是LinkedList随机访问效率低的原因之一。
*	implements List<E>：实现了List。实现了所有可选列表操作。
*	implements Deque<E>：Deque，Double ended queue，双端队列。LinkedList可用作队列或双端队列就是因为实现了它 
*	implements Cloneable：表明其可以调用clone()方法来返回实例的field-for-field拷贝。
*	implements java.io.Serializable：表明该类具有序列化功能。
与ArrayList对比发现，LinkedList并没有实现RandomAccess，而实现RandomAccess表明其支持快速（通常是固定时间）随机访问。此接口的主要目的是允许一般的算法更改其行为，从而在将其应用到随机或连续访问列表时能提供良好的性能。这是LinkedList随机访问效率低的原因之一。


#### 2.3 域 ####
```java
 /**
 * LinkedList节点个数
 */
transient int size = 0;

/**
 * 指向头节点的指针
 * Invariant: (first == null && last == null) ||
 *            (first.prev == null && first.item != null)
 */
transient Node<E> first;

/**
 * 指向尾节点的指针
 * Invariant: (first == null && last == null) ||
 *            (last.next == null && last.item != null)
 */
transient Node<E> last;

```

#### 2.4 构造方法 ####
```java
/**
 * 构造函数1：构造一个空链表.
 */
public LinkedList() {
}

/**
 * 构造函数2：根据指定集合c构造linkedList。先构造一个空linkedlist，
 * 在把指定集合c中的所有元素都添加到linkedList中。
 *
 * @param  c 指定集合
 * @throws NullPointerException 如果特定指定集合c为null
 */
public LinkedList(Collection<? extends E> c) {
    this();
    addAll(c);
}

```
共有两个构造函数
1.	LinkedList()
2.	LinkedList(Collection<? extends E> c)

#### 2.5 核心方法 ####
##### 2.5.1  getFirst() #####
```java
/**
 * 返回链表中的头结点的值.
 *
 * @return 返回链表中的头结点的值
 * @throws NoSuchElementException 如果链表为空
 */
public E getFirst() {
    final Node<E> f = first;
    if (f == null)
        throw new NoSuchElementException();
    return f.item;
}

```
##### 2.5.2 getLast() #####
```java
/**
 * 返回链表中的尾结点的值.
 *
 * @return 返回链表中的头结点的值
 * @throws NoSuchElementException 如果链表为空
 */
public E getLast() {
    final Node<E> l = last;
    if (l == null)
        throw new NoSuchElementException();
    return l.item;
}

```

##### 2.5.3	removeFirst() #####
```java
   /**
 * 删除并返回表头元素.
 *
 * @return 表头元素
 * @throws NoSuchElementException 链表为空
 */
public E removeFirst() {
    final Node<E> f = first;
    if (f == null)
        throw new NoSuchElementException();
    return unlinkFirst(f);
}

  private E unlinkFirst( Node<E> f) {
    // assert f == first && f != null;
    // 保存头结点的值
    final E element = f.item;
    // 保存头结点指向的下个节点
    final Node<E> next = f.next;
    //头结点的值置为null
    f.item = null;
    //头结点的后置指针指向null
    f.next = null; // help GC
    //将头结点置为next
    first = next;
    //如果next为null，将尾节点置为null，否则将next的后置指针指向null
    if (next == null)
        last = null;
    else
        next.prev = null;
    size--;
    modCount++;
    //返回被删除的头结点的值
    return element;
}

```
核心方法其实在于unlinkFirst(Node<E> f)。具体的步骤实际上非常简单，在注释中也很清楚。对着这个数据结构尝试自己画一画，注意只有一个元素的情况，删除之后fist == null && last == null

![Alt Text](http://ovrlh76oe.bkt.clouddn.com/%E8%8A%82%E7%82%B9%E5%9B%BE.png)

##### 2.5.4	removeLast() #####
```java
/**
 * 删除并返回表尾元素.
 *
 * @return 表尾元素
 * @throws NoSuchElementException 链表为空
 */
public E removeLast() {
    final Node<E> l = last;
    if (l == null)
        throw new NoSuchElementException();
    return unlinkLast(l);
}

/**
 * 删除尾节点l.并返回尾节点的值
 */
private E unlinkLast(Node<E> l) {
    // assert l == last && l != null;
    // 保存尾节点的值
    final E element = l.item;
    //获取新的尾节点prev
    final Node<E> prev = l.prev;
    //旧尾节点的值置为null
    l.item = null;
    //旧尾节点的后置指针指向null
    l.prev = null; // help GC
    //将新的尾节点置为prev
    last = prev;
    //如果新的尾节点为null，头结点置为null，否则将新的尾节点的后置指针指向null
    if (prev == null)
        first = null;
    else
        prev.next = null;
    size--;
    modCount++;
    //返回被删除的尾节点的值
    return element;
}

```

##### 2.5.5 addFirst(E e) #####
```java
  /**
 * 在表头插入指定元素.
 *
 * @param e 插入的指定元素
 */
public void addFirst(E e) {
    linkFirst(e);
}

/**
 * 在表头添加元素
 */
private void linkFirst(E e) {
    //使节点f指向原来的头结点
    final Node<E> f = first;
    //新建节点newNode，节点的前指针指向null，后指针原来的头节点
    final Node<E> newNode = new Node<>(null, e, f);
    //头指针指向新的头节点newNode 
    first = newNode;
    //如果原来的头结点为null，更新尾指针，否则使原来的头结点f的前置指针指向新的头结点newNode
    if (f == null)
        last = newNode;
    else
        f.prev = newNode;
    size++;
    modCount++;
}

```
##### 2.5.6 addLast(E e) #####
```java
  /**
 * 在表尾插入指定元素.
 * 
 * 该方法等价于add()
 *
 * @param e 插入的指定元素
 */ 
public void addLast(E e) {
    linkLast(e);
}
/**
 * 在表尾插入指定元素e
 */
void linkLast(E e) {
    //使节点l指向原来的尾结点
    final Node<E> l = last;
    //新建节点newNode，节点的前指针指向l，后指针为null
    final Node<E> newNode = new Node<>(l, e, null);
    //尾指针指向新的头节点newNode
    last = newNode;
    //如果原来的尾结点为null，更新头指针，否则使原来的尾结点l的后置指针指向新的头结点newNode
    if (l == null)
        first = newNode;
    else
        l.next = newNode;
    size++;
    modCount++;
}


```

##### 2.5.7 contains(Object o) #####
```java
 /**
 * 判断链表是否包含指定对象o
 * @param o 指定对象
 * @return 是否包含指定对象
 */
public boolean contains(Object o) {
    return indexOf(o) != -1;
}
/**
 * 正向遍历链表，返回指定元素第一次出现时的索引。如果元素没有出现，返回-1.
 * 
 * @param o 需要查找的元素
 * @return 指定元素第一次出现时的索引。如果元素没有出现，返回-1。
 */
public int indexOf(Object o) {
    int index = 0;
    if (o == null) {
        for (Node<E> x = first; x != null; x = x.next) {
            if (x.item == null)
                return index;
            index++;
        }
    } else {
        for (Node<E> x = first; x != null; x = x.next) {
            if (o.equals(x.item))
                return index;
            index++;
        }
    }
    return -1;
}

```

LinkedList中还有很多其它的方法，我不再一一举出源码，因为总体的方法逻辑是比较简单的，下面希望根据LinkedList除了可以用作列表还有可用做队列，栈，双端队列这个特点，对其方法进行分类。

![Alt Text](http://ovrlh76oe.bkt.clouddn.com/LInkedList%E6%96%B9%E6%B3%95%E5%88%86%E7%B1%BB.png)

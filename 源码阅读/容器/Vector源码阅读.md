---
title: Vector源码阅读笔记
date: 2018-10-28 22:37
tags: [Java源码阅读]
categories: [学习,编程]
---
## Vector ##
### 一、Vector简介 ###
#### 1.1 类继承结构 ####
![Alt Text](http://ovrlh76oe.bkt.clouddn.com/Vector%E7%B1%BB%E7%BB%A7%E6%89%BF%E7%BB%93%E6%9E%84.png)
#### 1.2 数据结构 ####
![Alt Text](http://ovrlh76oe.bkt.clouddn.com/ArrayList%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E5%9B%BE.png)
### 二、源码 ###
#### 2.1 注释 ####
>     Vector与ArrayList是非常相似的，注释中的说明也是类似的，可参考上篇博文“ArrayList源码阅读”。注释中有这样一句话：“Vector是同步的，如果不需要线程安全的实现，推荐使用ArrayList代替Vector”。

#### 2.2 定义 ####
```java
public class Vector<E> extends AbstractList<E> 
implements List<E>, RandomAccess, Cloneable, java.io.Serializable
```
类定义与类继承结构图是对应的。
从中我们可以了解到：
*	Vector<E>：说明它支持泛型。
*	extends AbstractList<E> ：继承了AbstractList。AbstractList提供List接口的骨干实现，以最大限度地减少“随机访问”数据存储（如ArrayList）实现Llist所需的工作。
*	implements List<E>：实现了List。实现了所有可选列表操作。
*	implements RandomAccess：表明Vector支持快速（通常是固定时间）随机访问。此接口的主要目的是允许一般的算法更改其行为，从而在将其应用到随机或连续访问列表时能提供良好的性能。
*	implements Cloneable：表明其可以调用clone()方法来返回实例的field-for-field拷贝。
*	implements java.io.Serializable：表明该类具有序列化功能。


#### 2.3 域 ####
```java
     /**
     * 保存vector元素的数组，数组的长度就是vector的容量，vector的容量至少要能
     * 保存所有的元素
     *
     * <p>任何在vector最后一个元素之后的数组元素都是null
     *
     * @serial
     */
    protected Object[] elementData;

    /**
     * vector中的实际元素数
     * {@code elementData[elementCount-1]} are the actual items.
     *
     * @serial
     */
    protected int elementCount;

    /**
     * vector在自动扩容时增加的容量，当vector的实际容量将要大于它的最大容量时，vector自动增加的容量
     * 当该值<=0时，vector的容量需要扩容时就扩为原来的2倍
     *
     * @serial
     */
    protected int capacityIncrement;

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -2767605614048989439L;
```
查看上面源码中的注释即可，比较简单。

#### 2.4 构造方法 ####
```java
    /**
     * 构造一个指定初始容量为initialCapacity，自增容量为capacityIncrement的空vector
     * @throws IllegalArgumentException if the specified initial capacity
     *         is negative
     */
    public Vector(int initialCapacity, int capacityIncrement) {
        super();
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        this.elementData = new Object[initialCapacity];
        this.capacityIncrement = capacityIncrement;
    }

    /**
     * 构造一个指定初始容量为initialCapacity，自增容量为0的空vector
	 * 实际上里面调用的是上一个构造函数
     * @throws IllegalArgumentException if the specified initial capacity
     *         is negative
     */
    public Vector(int initialCapacity) {
        this(initialCapacity, 0);
    }

    /**
     * 构造一个指定初始容量为10，自增容量为0的空vector
     * 实际上调用的是上一个构造函数
     */
    public Vector() {
        this(10);
    }

    /**
     * 使用指定的Collection构造vector
     * @throws NullPointerException if the specified collection is null
     * @since   1.2
     */
    public Vector(Collection<? extends E> c) {
        elementData = c.toArray();
        elementCount = elementData.length;
        // c.toArray might (incorrectly) not return Object[] (see 6260652)
        if (elementData.getClass() != Object[].class)
            elementData = Arrays.copyOf(elementData, elementCount, Object[].class);
    }

```
共有四个构造函数
1.	public Vector(int initialCapacity, int capacityIncrement)
2.	public Vector(int initialCapacity)
3.	public Vector()
4.	public Vector(Collection<? extends E> c)

#### 2.5 核心方法 ####
Vector类所有被外部访问到的方法，都是线程同步的，实现方法是“synchronized”的关键字。举几个栗子
##### 2.5.1 copyInto(object[] anArray) #####
```java
    /**
     * 将vector的所有元素复制进指定的数组anArray中
     * The item at index {@code k} in this vector is copied into
     * component {@code k} of {@code anArray}.
     *
     * @param  anArray the array into which the components get copied
     * @throws NullPointerException if the given array is null
     * @throws IndexOutOfBoundsException if the specified array is not
     *         large enough to hold all the components of this vector
     * @throws ArrayStoreException if a component of this vector is not of
     *         a runtime type that can be stored in the specified array
     * @see #toArray(Object[])
     */
    public synchronized void copyInto(Object[] anArray) {
        System.arraycopy(elementData, 0, anArray, 0, elementCount);
    }
```
方法被synchronized关键字修饰。

##### 2.5.2  trimToSize() #####
```java
    /**
     * 将底层数组的容量调整为当前vector实际元素的个数，来释放空间
     */
    public synchronized void trimToSize() {
        modCount++;
        int oldCapacity = elementData.length;
		//当实际元素个数小于底层数组的长度
        if (elementCount < oldCapacity) {
			//将底层数组的长度调整为实际大小
            elementData = Arrays.copyOf(elementData, elementCount);
        }
    }

```
小结：Vector与ArrayList的最大不同就是它的方法是线程同步的，实现同步的方式就是用“synchronized”修饰方法。
#### 2.6 扩容机制 ####
```java
    /**
     * 为保证vector可以容纳所有需要容纳的元素，在必要时进行扩容
     * 扩容规则：
     * 1、capacityIncrement大于0时，新的容量将在旧的容量的基础上加上capacityIncrement；
	 * 小于0时，增加到原来的2倍。
	 * 2、如果新的容量还是小于至少需要的容量，则将容量扩容为至少需要的容量。
	 * 3、如果至少需要的容量大于MAX_ARRAY_SIZE，则进行大容量分配，hugeCapacity()函数中进行
     * @param minCapacity the desired minimum capacity
     */
    public synchronized void ensureCapacity(int minCapacity) {
        if (minCapacity > 0) {
            modCount++;
            ensureCapacityHelper(minCapacity);
        }
    }
	/**
	* ensureCapacity()方法的unsynchronized实现。
	* 
	* ensureCapacity()是同步的，它可以调用本方法来扩容，而不用承受同步带来的消耗
	* 
	* @see #ensureCapacity(int)
	*/
	private void ensureCapacityHelper(int minCapacity) {
    // 如果至少需要的容量 > 数组缓冲区当前的长度，就进行扩容
		if (minCapacity - elementData.length > 0)
			grow(minCapacity);
	}
	/**
	* 分派给arrays的最大容量
	* 为什么要减去8呢？
	* 因为某些VM会在数组中保留一些头字，尝试分配这个最大存储容量，可能会导致array容量大于VM的limit，最终导致OutOfMemoryError。
	*/
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;


```

== 小结 ==：ArrayList和Vector的扩容逻辑稍微有些不同。vector的逻辑是： newCapacity = oldCapacity + ((capacityIncrement > 0) ? capacityIncrement : oldCapacity); 即如果capacityIncrement>0，就加capacityIncrement，如果不是就增加一倍。 
而ArrayList的逻辑是： 
newCapacity = oldCapacity + (oldCapacity >> 1); 即增加现有的一半。
后面的大容量分配逻辑是一样的。

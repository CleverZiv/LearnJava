## ArrayList ##
### 一、ArrayList简介 ###
#### 1.1 类继承结构 ####
![Alt Text](http://ovrlh76oe.bkt.clouddn.com/ArrayList%E7%B1%BB%E7%BB%93%E6%9E%84%E5%9B%BE.png)
#### 1.2 数据结构 ####
![Alt Text](http://ovrlh76oe.bkt.clouddn.com/ArrayList%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E5%9B%BE.png)
### 二、源码 ###
#### 2.1 注释 ####
>可调整大小的List接口的数组实现，实现所有可选列表操作，并允许所有元素，包括null。除了实现List接口之外，该类还提供了一些方法来操纵内部使用的存储列表的数组的大小。 （这个类是大致相当于Vector，不同之处在于它是不同步的）。
该size，isEmpty，get，set，iterator和listIterator操作在固定时间内运行。 add操作以摊余常数运行 ，即添加n个元素需要O（n）个时间。 所有其他操作都以线性时间运行（粗略地说）。 与LinkedList实施相比，常数因子较低。
每个ArrayList实例都有一个capacity。 capacity是用于存储列表中的元素的数组的大小。 它总是至少与列表大小一样大。 当元素添加到ArrayList时，其容量会自动增长。 没有规定增长政策的细节，除了添加元素具有不变的摊销时间成本。
应用程序可以添加大量使用ensureCapacity操作元件的前增大ArrayList实例的容量。 这可能会减少增量重新分配的数量。
请注意，此实现不同步。 如果多个线程同时访问ArrayList实例，并且至少有一个线程在结构上修改列表，则必须在外部进行同步。 （结构修改是添加或删除一个或多个元素的任何操作，或明确调整后台数组的大小;仅设置元素的值不是结构修改。）这通常是通过在一些自然地封装了列表。 如果没有这样的对象存在，列表应该使用Collections.synchronizedList方法“包装”。 这最好在创建时完成，以防止意外的不同步访问列表：
  List list = Collections.synchronizedList(new ArrayList(...)); 
快速失败：iterator和listIterator会返回快速失败，当迭代器被创建之后，该list的结构又被修改时。只有当迭代器自己修改list结构时（通过迭代器自身的remove或add方法）不会返回快速失败。返回快速失败的表现是抛出ConcurrentModificationException。因此，面对并发修改，迭代器的快速干净的失败（抛出异常）而不是冒着未来不确定性隐藏的风险。
请注意，迭代器的快速失败行为无法保证，因为一般来说，在不同步并发修改的情况下，无法做出任何硬性保证。快速失败的迭代器尽力ConcurrentModificationException 。 因此，编写依赖于此异常的程序的正确性将是错误的：迭代器的故障快速行为应仅用于检测错误。
这个类是Java Collections Framework的成员

#### 2.2 定义 ####
```java
public class ArrayList<E> extends AbstractList<E>implements List<E>, RandomAccess, Cloneable, java.io.Serializable
```
类定义与类继承结构图是对应的。
从中我们可以了解到：
*	ArrayList<E>：说明ArrayList支持泛型。
*	extends AbstractList<E> ：继承了AbstractList。AbstractList提供List接口的骨干实现，以最大限度地减少“随机访问”数据存储（如ArrayList）实现Llist所需的工作。
*	implements List<E>：实现了List。实现了所有可选列表操作。
*	implements RandomAccess：表明ArrayList支持快速（通常是固定时间）随机访问。此接口的主要目的是允许一般的算法更改其行为，从而在将其应用到随机或连续访问列表时能提供良好的性能。
*	implements Cloneable：表明其可以调用clone()方法来返回实例的field-for-field拷贝。
*	implements java.io.Serializable：表明该类具有序列化功能。

####2.3 域####
```java
 /**
     * 初始化默认容量.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 指定该ArrayList容量为0时，返回该空数组。
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 当调用无参构造方法，返回的是该数组。刚创建一个ArrayList 时，其内数据量为0。
     * 它与EMPTY_ELEMENTDATA的区别就是：该数组是默认返回的，而后者是在用户指定容量为0时返回。
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * 保存添加到ArrayList中的元素。
     * ArrayList的容量就是该数组的长度。
     * 该值为DEFAULTCAPACITY_EMPTY_ELEMENTDATA 时，当第一次添加元素进入ArrayList中时，数组将扩容至DEFAULT_CAPACITY。
     * 被标记为transient，在对象被序列化的时候不会被序列化
     */
    transient Object[] elementData; // non-private to simplify nested class access

    /**
     * ArrayList的实际大小（数组包含的元素个数）。
     *
     * @serial
     */
    private int size;

```
*	EMPTY_ELEMENTDATA和DEFAULTCAPACITY_EMPTY_ELEMENTDATA的区别
在调用无参构造函数的时候，返回的是后者，在调用指定了容量为0的构造函数时，返回的是前者；两者的初始容量一个为指定的0，一个为DEFAULT_CAPACITY（默认为10）；对于后者，当第一次添加元素时，就会扩容至默认初始容量DEFAULT_CAPACITY。
*	ArrayList的capacity和size的区别
Capacity是整个数据结构的大小，size是目前存储了多少元素的大小。
*	标记为transient的elementData是怎么进行序列化和反序列化的
ArrayList自定义了它的序列化和反序列化的方式，详情查看writeObject()和readObject方法。

####2.4 构造方法####
```java
/**
     * 构造一个指定初始化容量为initialCapacity的空ArrayList
     *
     * @param  initialCapacity  the initial capacity of the list
     * @throws IllegalArgumentException 如果初始化容量为负
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
			//指定初始化容量为0，对应的是EMPTY_ELEMENTDATA这个空数组
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        }
    }

    /**
     * 构造一个初始化容量为10的空列表
     */
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * 构造一个包含指定collection的元素的列表，这些元素的排列是由该collection的迭代器的返回顺序决定的
     *
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            // c.toArray might (incorrectly) not return Object[] (see 6260652)
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            // replace with empty array.
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }

```
共有三个构造函数
1.	ArrayList(int initialCapacity)：
2.	ArrayList()
3.	ArrayList(Collection<? extends E> c)

####2.5 核心方法####
#####2.5.1 get(int index)#####
```java
/**
     * Returns the element at the specified position in this list.
     *
     * @param  index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E get(int index) {
        rangeCheck(index);

        return elementData(index);
    }
	 /**
     * Checks if the given index is in range.  If not, throws an appropriate
     * runtime exception.  This method does *not* check if the index is
     * negative: It is always used immediately prior to an array access,
     * which throws an ArrayIndexOutOfBoundsException if index is negative.
     */
    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
	 @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }
```
方法步骤：
1.	RangeCheck()判断是否越界；
2.	直接通过数组下标获取元素
因为ArrayList的底层实现就是数组，get方法的时间复杂度为O(1)。
#####2.5.2 add(E e)#####
```java
/**
     * Appends the specified element to the end of this list.
     *
     * @param e element to be appended to this list
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     */
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
    }
	
	private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }
	
	private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }
	
    /**
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     */
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
	
	private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }
	
	private static int calculateCapacity(Object[] elementData, int minCapacity) {
		//如果数组是DEFAULTCAPACITY_EMPTY_ELEMENTDATA，则最小扩容的大小为DEFAULT_CAPACITY
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }
	
	/**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
```
add(E e)涉及到了ArrayList的扩容机制，整个方法的步骤如下：
1、	ensureExplicitCapacity(int minCapacity)：modCount++，modCount是父类AbstractList的变量，用于保存数据结构的结构修改次数。当添加一个元素之后的元素个数大于当前数组的容量时，进行扩容：
```java
if (minCapacity - elementData.length > 0)
            grow(minCapacity);
```
2、grow(int minCapacity)：扩容机制：将现数组的长度增加到1.5倍，然后取所需的长度（minCapacity）和增加到1.5倍的长度的较大值为新数组的长度；在此之前，判断这个新数组长度是否超过了ArrayList所允许的长度最大值即MAX_ARRAY_SIZE（定义private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;）超过的话就采用Integer.MAX_VALUE

##### 2.5.3 add(int index, E e) #####
```java
    /**
     * 在指定位置插入元素，然后将当前位置和其往后的元素向后移一位
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public void add(int index, E element) {
		//1、越界检查
        rangeCheckForAdd(index);

		//2、空间检查，是否需要扩容
        ensureCapacityInternal(size + 1);  // Increments modCount!!
		//arraycopy(Object src,  int  srcPos,Object dest, int destPos,int length)：
		//将src从srcPos位置往后的length长度的元素复制到dest中，从destPos位置开始
		//结果就是将index和其往后的元素向后移一位并且空出了indx位置
        System.arraycopy(elementData, index, elementData, index + 1,
                         size - index);
        elementData[index] = element;
        size++;
    }
   /**
     * A version of rangeCheck used by add and addAll.
	 *检查index位置是否合法
     */
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

```
add(int index E e)需要先对元素进行移动，然后完成插入操作，意味着需要O(n)。

##### 2.5.4 remove(int index) #####
```java

    /**
     * 删除指定位置的元素并返回该元素，并将该位置之后的元素向左移一位
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E remove(int index) {
		//1、检查index是否越界
        rangeCheck(index);

		//2、结构修改都要在modCount上加1，添加元素的方法这个语句在调用的ensureExplicitCapacity()方法中
        modCount++;
        E oldValue = elementData(index);

        int numMoved = size - index - 1;
        if (numMoved > 0)
			//3、将该位置之后的元素向左移一位
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
		//4、置为null，GC会对这块进行回收
        elementData[--size] = null; // clear to let GC do its work

        return oldValue;
    }

```
注意：为了让GC起作用，必须显式的为最后一个位置赋null值。上面代码中如果不手动赋null值，除非对应的位置被其他元素覆盖，否则原来的对象就一直不会被回收。
由于删除指定位置元素需要将后面的元素左移一位，因此时间复杂度为O(n)

#####2.5.5 remove(Object o)#####
```java
    /**
     * 如果list中存在o，删除第一个出现的o；如果不存在，list不变
     *存在并且删除成功返回true,否则返回false
     * @param o element to be removed from this list, if present
     * @return <tt>true</tt> if this list contained the specified element
     */
    public boolean remove(Object o) {
		//1、判断o是否为null，null不能使用.equals()方法
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
					//2、调用快速删除方法
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }

    /*
     * Private remove method that skips bounds checking and does not
     * return the value removed.
     */
    private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--size] = null; // clear to let GC do its work
    }

```

#### 2.6 经常调用的方法 ####
##### 2.6.1 System.arraycopy(Object src, int srcPos, Object dest, int desPos, int length) #####
其中：src表示源数组，srcPos表示源数组要复制的起始位置，desc表示目标数组，length表示要复制的长度。该方法实现的是浅复制，即只复制引用。

#####2.6.2 Arrays.copyOf()
Array.copyOf() 用于复制指定的数组内容以达到扩容的目的，该方法对不同的基本数据类型都有对应的重载方法。
本文只介绍其中的copyOf(U[] original, int newLength, Class<? extends T[]> newType)方法
```java
    /**
     *复制指定的数组, 如有必要用 null 截取或填充，以使副本具有指定的长度
     * 对于所有在原数组和副本中都有效的索引，这两个数组相同索引处将包含相同的值
     * 对于在副本中有效而在原数组无效的所有索引，副本将填充 null，当且仅当指定长度大于原数组的长度时，这些索引存在
     * 返回的数组属于 newType 类
     *
     * @param original 要复制的数组
     * @param 副本的长度
     * @param 副本的类
     * 
     * @return 原数组的副本，截取或用 null 填充以获得指定的长度
     * @throws NegativeArraySizeException 如果 newLength 为负
     * @throws NullPointerException 如果 original 为 null
     * @throws ArrayStoreException 如果从 original 中复制的元素不属于存储在 newType 类数组中的运行时类型
     * @since 1.6
     */
    public static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
        @SuppressWarnings("unchecked")
        T[] copy = ((Object)newType == (Object)Object[].class)
            ? (T[]) new Object[newLength]
            : (T[]) Array.newInstance(newType.getComponentType(), newLength);
        System.arraycopy(original, 0, copy, 0,
                         Math.min(original.length, newLength));
        return copy;
    }
```
#### 2.7 核心方法的时间复杂度 ####
![Alt Text](http://ovrlh76oe.bkt.clouddn.com/ArrayList%E6%96%B9%E6%B3%95%E6%97%B6%E9%97%B4%E5%A4%8D%E6%9D%82%E5%BA%A6.png)



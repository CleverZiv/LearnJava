package leetcode.stack;

import java.util.Iterator;

/**
 * @Classname ArrayStack
 * @Date 2020/8/15 10:33
 * @Autor lengxuezhang
 */
public class ArrayStack<Item> implements Iterable<Item> {
    //private Item[] a = new Item[2]; 创建泛型数组在Java中是不允许的
    private Item[] a = (Item[]) new Object[1];
    private int size = 0;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void push(Item item) {
        //扩容
        if (size == a.length) {
            resize(2 * a.length);
        }
        a[size++] = item;
    }

    private void resize(int max) {
        //将栈移动到一个大小为max的新数组
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < size; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    private Item pop() {
        //缩容
        Item item = a[--size];
        a[size] = null;
        if (size > 0 && size == a.length / 4) {
            resize(a.length / 4); //避免频繁执行扩容、缩容操作
        }
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {

        private int i = size;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            return a[--i];
        }

        @Override
        public void remove() {

        }
    }

/*    public static void main(String[] args) {
        // 使用泛型的作用是使得程序在编译期可以检查出与类型相关的错误，但是如果使用了泛型数组，这种能力就会受到破坏。
        // 假设可以创建泛型数组
        List<String>[] stringLists = new ArrayList<String>[1];
        List<Integer> intList = Arrays.asList(42);
        // 泛型擦除，List 继承自 Object，所以可以如此赋值
        // 在数组中，子类数组 是 父类数组 的子类，Object[] o = new ArrayList[1];Java中，数组是协变的，这意味着基类类型的数组可以接收子类类型的数组
        Object[] objects = stringLists;
        // 同理，泛型擦除后，List 类型变量赋值给 Object 类型变量
        // 但此时出现问题了，**** List<Integer> 实例添加到了声明为 List<String>[] 类型的数组中了 ******
        objects[0] = intList;
        String s = stringLists[0].get(0);;
    }*/
}

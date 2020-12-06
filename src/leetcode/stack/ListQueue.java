package leetcode.stack;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * @Classname ListQueue
 * @Date 2020/8/15 16:08
 * @Autor lengxuezhang
 */
public class ListQueue<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    class Node {
        Item item;
        Node next;
        public Node(Item item) {
            this.item = item;
        }
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    public void enqueue(Item item) {
        Node oldLast = last;
        last = new Node(item);
        last.next = null;
        if(isEmpty()) {
            first = last;
        }else{
            oldLast.next = last;
        }
        size++;
    }
    public Item dequeue() {
        if(isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        Item item = first.item;
        first = first.next;
        if(isEmpty()){
            last = null;
        }
        size--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        //实现同栈中的实现
        return null;
    }
}

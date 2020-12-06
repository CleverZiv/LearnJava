package leetcode.stack;

import java.util.Iterator;

/**
 * @Classname ListStack
 * @Date 2020/8/15 12:05
 * @Autor lengxuezhang
 */
public class ListStack<Item> implements Iterable<Item> {
    private Node first;
    private int size;
    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public void push(Item item) {
        Node oldFirst = first;
        first = new Node(item);
        first.next = oldFirst;
        size++;
    }

    private Item pop() {
        Item item = first.item;
        first = first.next;
        size--;
        return item;
    }
    class Node {
        Item item;
        Node next;

        public Node(Item item) {
            this.item = item;
        }
    }
    @Override
    public Iterator iterator() {
        return new MyListIterator();
    }

    class MyListIterator implements Iterator<Item> {
        int i = size;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            Item item = first.item;
            first = first.next;
            return item;
        }
    }
}

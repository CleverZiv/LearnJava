package leetcode.stack;

import java.util.Stack;

/**
 * @Classname MinStack
 * @Date 2020/7/26 18:40
 * @Autor lengzefu
 */
public class MinStack {
    Stack<Integer> stack1;
    Stack<Integer> stack2;

    /**
     * initialize your data structure here.
     */
    public MinStack() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void push(int x) {
        stack1.push(x);
        if(stack2.peek() > x) {
            stack2.push(x);
        }
    }

    public void pop() {
        int temp = stack1.pop();
        if(temp == stack2.peek()) {
            stack2.pop();
        }
    }

    public int top() {
        return stack1.peek();
    }

    public int getMin() {
        return stack2.peek();
    }
}


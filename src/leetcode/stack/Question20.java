package leetcode.stack;

import java.util.Stack;

/**
 * @Classname Question20
 * @Date 2020/7/21 0:59
 * @Autor lengzefu
 */
public class Question20 {

    public static void main(String[] args) {
        String s = "){";
        boolean res = isValid(s);
        System.out.println(res);
    }

    public static boolean isValid(String s) {
        /*
         * 利用栈来做这道题，栈可以通过对出栈、入栈的操作来表示成对的符号，具体思路：
         * 1.遇到左括号即入栈，遇到右括号即准备出栈；
         * 2.出栈成功的条件是，该右括号与栈顶的元素是可以组成一对括号的，如：’（‘和’）‘，如果不能，则括号无效；
         * 3.遍历结束后，判断栈是否为空
         */
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == '[' || s.charAt(i) == '{') {
                stack.push(s.charAt(i));
            }
            if (s.charAt(i) == ')' || s.charAt(i) == ']' || s.charAt(i) == '}') {
                if(stack.empty()) return false;
                Character temp1 = stack.peek();
                Character temp2 = stack.peek();
                switch (s.charAt(i)) {
                    case ')':
                        temp2 = '(';
                        break;
                    case ']':
                        temp2 = '[';
                        break;
                    case '}':
                        temp2 = '{';
                        break;
                }

                if (temp1.equals(temp2)) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.empty();

    }

}

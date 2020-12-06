package leetcode.stack;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @Classname Question402
 * @Date 2020/7/30 22:06
 * @Autor lengzefu
 */
public class Question402 {
    public static void main(String[] args) {
        String s = "10";
        String res = removeKdigits(s, 2);
        System.out.println(res);
    }

    private static String removeKdigits(String num, int k) {
        int length = num.length();
        //记录移除的数字个数
        int count = 0;
        LinkedList<Character> numStack = new LinkedList<>();
        int i = 0;

        for (i = 0; i < length; i++) {
            if (numStack.isEmpty()) {
                if (num.charAt(i) == '0') continue;
                numStack.push(num.charAt(i));
            } else if (num.charAt(i) >= numStack.peek()) {
                count++;
                if (count < k) {
                    continue;
                } else {
                    break;
                }
            } else {
                while (!numStack.isEmpty() && num.charAt(i) < numStack.peek()) {
                    numStack.pop();
                    count++;
                    if (count >= k) break;
                }
                if(num.charAt(i) != '0') {
                    numStack.push(num.charAt(i));
                }
                if (count >= k) break;
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!numStack.isEmpty()) {
            sb.append(numStack.removeFirst() + "");
        }
        if(i < length - 1) {
            String temp = num.substring(i + 1);
            sb.append(temp);
        }
        if(sb.toString().equals("")) return "0";
        return sb.toString();
    }
}

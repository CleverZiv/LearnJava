package leetcode.stack;

import java.util.LinkedList;

/**
 * @Classname Question394
 * @Date 2020/7/30 0:19
 * @Autor lengzefu
 */
public class Question394 {
    public static void main(String[] args) {
        String s = "3[a2[c]]";
        String res = decodeString(s);
        System.out.println(res);
    }

    private static String decodeString(String s) {
        /**
         * 1.s为null时，返回null
         * 2.遍历s
         *   若遇到左括号，则开始一个新的字符串循环，将 重复次数和待重复字符串分别入栈
         *   若遇到右括号，则分别弹出数字栈和字符串栈中的元素进行 重复拼接
         *   若遇到遇到数字，则计算 重复次数
         *   除此之外，计算 待重复字符串
         */
        LinkedList<String> partRes = new LinkedList<>();
        LinkedList<Integer> iterateTimes = new LinkedList<>();
        int times = 0;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {

            if(s.charAt(i) == '[') {
                partRes.addLast(sb.toString());
                iterateTimes.addLast(times);
                sb = new StringBuilder();
                times = 0;
            } else if(s.charAt(i) == ']') {
                StringBuilder temp = new StringBuilder();
                int tempTime = iterateTimes.removeLast();
                for(int j = 0; j < tempTime; j++) {
                    temp.append(sb);
                }
                sb = new StringBuilder(partRes.removeLast() + temp);
            }else if(s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                times = times * 10 + Integer.parseInt(s.charAt(i) + "");
            } else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();

    }
}

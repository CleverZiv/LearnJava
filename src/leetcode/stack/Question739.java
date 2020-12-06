package leetcode.stack;

import java.util.Stack;

/**
 * @Classname Question739
 * @Date 2020/7/21 22:16
 * @Autor lengzefu
 */
public class Question739 {
    public static void main(String[] args) {
        int[] T = {73, 74, 75, 71, 69, 72, 76, 73};
        T = dailyTemperatures3(T);
        for (int i = 0; i < T.length; i++) {
            System.out.println(T[i]);
        }
    }

    private static int[] dailyTemperatures(int[] T) {
        //特殊情况的处理
        if(null == T || T.length == 0) return null;
        if(T.length == 1){
            T[0] = 0;
            return T;
        }
        //1.遍历T
        for(int i = 0; i < T.length-1; i++) {
            int temp = T[i];
            T[i] = 0;
            for(int j = i+1; j < T.length; j++) {
                if(T[j] > temp) {
                    T[i] = j - i;
                    break;
                }
            }
        }
        T[T.length - 1] = 0;
        return T;
    }

    private static int[] dailyTemperatures2(int[] T) {
        //特殊情况的处理
        if(null == T || T.length == 0) return null;
        if(T.length == 1){
            T[0] = 0;
            return T;
        }
        int[] res = new int[T.length];
        res[T.length-1] = 0;
        for(int i = T.length - 2; i > -1; i--) {
            int j = i + 1;
            while(j < T.length && T[i] >= T[j]) {
                j = (res[i] == 0 ? ++j : j + res[j]);
            }
            if(j >= T.length){
                res[i] = 0;
            } else {
                res[i] = j - i;
            }

        }
        return res;
    }

    private static int[] dailyTemperatures3(int[] T) {
        /**
         * 单调递减栈（递减是针对元素，不是针对下标），存放栈的下标
         * 1.什么时候入栈？  栈为空或新元素小于等于栈顶元素
         * 2.什么时候出栈？  新元素大于栈顶元素
         * 3.什么时候计算与大于自己元素的距离？  出栈的时候，计算新元素与出栈元素的下标差
         */
        Stack<Integer> stack = new Stack<>();
        int length = T.length;
        int[] result = new int[length];
        for(int i = 0; i < length; i++) {
            while(!stack.empty() && T[i] > T[stack.peek()]) {
                int temp = stack.pop();
                result[temp] = i - temp;
            }
            stack.push(i);
        }
        return result;
    }
}

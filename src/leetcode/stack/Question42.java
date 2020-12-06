package leetcode.stack;

import java.util.Stack;

/**
 * @Classname Question42
 * @Date 2020/7/22 23:18
 * @Autor lengzefu
 */
public class Question42 {
    static Stack<Integer> indexStack = new Stack<Integer>();

    public static void main(String[] args) {
        int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
        int res = trap(height);
        System.out.println(res);
    }
    public static int trap(int[] height) {
        int res = 0;
        for(int i = 0; i < height.length; i++) {
            while(!indexStack.empty() && height[i] > height[indexStack.peek()]) {
                int lagestMid = indexStack.peek();
                indexStack.pop();
                if(indexStack.empty()) {
                    break;
                }
                int w = i - indexStack.peek() - 1;
                int h = Math.min(height[i], height[indexStack.peek()]) - height[lagestMid];
                res += w * h;
            }
            indexStack.push(i);
        }
        return res;
    }
}

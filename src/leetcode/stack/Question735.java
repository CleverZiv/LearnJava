package leetcode.stack;

import java.util.LinkedList;

/**
 * @Classname Question735
 * @Date 2020/8/1 11:32
 * @Autor lengzefu
 */
public class Question735 {
    public static void main(String[] args) {
        int[] asteroids = {5, 10, -5};
        int[] res = asteroidCollision(asteroids);
        for (int i : res) {
            System.out.println(i);
        }

    }

    private static int[] asteroidCollision(int[] asteroids) {
        if (asteroids == null || asteroids.length == 0) return null;

        LinkedList<Integer> stack = new LinkedList<>();
        for (int a : asteroids) {
            if (a > 0) stack.addLast(a);
            if (a < 0) {
                while (!stack.isEmpty() && stack.getLast() > 0 && a < 0 && stack.getLast() + a < 0) {
                        stack.removeLast();
                        //stack.addLast(a);
                }
                if(stack.isEmpty()) stack.addLast(a);
                else if (stack.getLast() + a == 0) {
                    stack.removeLast();
                } else if(stack.getLast() < 0) stack.addLast(a);
                //if(temp != 0) stack.push(temp);
            }
        }
        int[] res = new int[stack.size()];
        int i = 0;
        while (!stack.isEmpty()) {
            res[i++] = stack.removeFirst();
        }
        return res;

    }
}

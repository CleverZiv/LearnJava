package leetcode.stack;

import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * @Classname Question71
 * @Date 2020/7/27 23:20
 * @Autor lengzefu
 */
public class Question71 {
    public static void main(String[] args) {
        String s = "/a//b////c/d//././/..";
        String res = simplifyPath(s);
        System.out.println(res);
    }

    private static String simplifyPath(String path) {
        // 1.以“/”分割path

        /**
         * 遍历得到的字符串数组，进行逐个入栈
         * 1.何时入栈？当字符串为除""、"."、".."时入栈；
         * 2.何时出栈？当字符串为".."，出栈
         */
        LinkedList<String> deque = new LinkedList<>();
        String[] pathStr = path.split("/");
        for(int i = 0; i < pathStr.length; i++) {
            if(!pathStr[i].equals("") && !pathStr[i].equals(".") && !pathStr[i].equals("..")) {
                deque.add(pathStr[i]);
            }else if(pathStr[i].equals("..") && !deque.isEmpty()) {
                deque.removeLast();
            }
        }
        if(deque.isEmpty()) {
            return "/";
        }

        StringBuilder sb = new StringBuilder();
        for(String s : deque) {
            sb.append("/").append(s);
        }
        return sb.toString();
    }
}

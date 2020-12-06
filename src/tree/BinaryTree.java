package tree;

import java.util.LinkedList;
import java.util.Stack;


/**
 * Created by lengzefu on 2019/3/7.
 * 二叉树
 */
public class BinaryTree {
    int val;
    BinaryTree left;
    BinaryTree right;

    public BinaryTree(int val){
        this.val = val;
    }

    /**
     * 递归实现前序、中序、后序遍历方式
     * @param tree
     */
    public void preOrder(BinaryTree tree){
        if(tree == null) return;
        System.out.println(tree.val);
        preOrder(tree.left);
        preOrder(tree.right);
    }

    public void inOrder(BinaryTree tree){
        if(tree == null) return;
        inOrder(tree.left);
        System.out.println(tree.val);
        inOrder(tree.right);
    }

    public void postOrder(BinaryTree tree){
        if(tree == null) return;
        postOrder(tree.left);
        postOrder(tree.right);
        System.out.println(tree.val);
    }

    /**
     * 非递归的方式实现前序、中序、后序遍历方式
     * @param tree
     */
    public void preOrderNoRecursion(BinaryTree tree) {
        if(tree == null) return;
        Stack<BinaryTree> stack = new Stack<>();
        while(tree != null || !stack.isEmpty()){
            if(tree != null){
                stack.push(tree);
                System.out.println(tree.val);
                tree = tree.left;
            }else {
                BinaryTree item = stack.pop();
                tree = item.right;
            }
        }
    }

    public void inOrderNoRecursion(BinaryTree tree) {
        if(tree == null) return;
        Stack<BinaryTree> stack = new Stack<>();
        while(tree != null || !stack.isEmpty()){
            if(tree != null){
                stack.push(tree);
                tree = tree.left;
            }else {
                BinaryTree item = stack.pop();
                System.out.println(item.val);
                tree = item.right;
            }
        }
    }

    public void postOrderNoRecursion(BinaryTree tree) {
        if (tree == null)
            return;

        Stack<BinaryTree> stack = new Stack<>();
        BinaryTree node = tree;
        BinaryTree pre = null;
        stack.push(node);

        while (!stack.isEmpty()){
            node = stack.peek();
            if ((node.left == null && node.right == null) ||
                    (pre != null && (pre == node.left || pre == node.right))){
                System.out.println(node.val);
                pre = node;

                stack.pop();
            }else{
                if(node.right != null)
                    stack.push(node.right);

                if(node.left != null)
                    stack.push(node.left);
            }
        }

    }

    /**
     * 剑指offer上有按行打印和按之字形打印二叉树
     */

    public void rowOrder(BinaryTree tree) {
        if(tree == null) return;

        LinkedList<BinaryTree> queue = new LinkedList<>();//创建一个队列
        BinaryTree last = tree, nlast = null;//创建两个辅助变量
        queue.add(tree);
        while(!queue.isEmpty()){
            BinaryTree item = queue.poll();
            System.out.print(item.val + " ");
            //压入左孩子并更新nlast
            if(item.left != null) {
                queue.add(item.left);
                nlast = item.left;
            }
            //压入有孩子并更新nlast
            if(item.right != null) {
                queue.add(item.right);
                nlast = item.right;
            }
            //如果弹出的是last，说明一行已经打完，更新last为nlast
            if(item == last) {
                last = nlast;
                System.out.print("\n");//打印换行
            }
        }

    }

}

package leetcode.binarytree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname Question0406
 * @Date 2020/8/10 23:50
 * @Autor lengzefu
 */
public class Question0406 {

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(5);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(6);
        TreeNode node4 = new TreeNode(2);
        TreeNode node5 = new TreeNode(4);
        TreeNode node6 = new TreeNode(1);
        node1.left = node2; node1.right = node3;
        node2.left = node4; node2.right = node5;
        node4.left = node6;
        TreeNode res = inorderSuccessor(node1, node3);
        System.out.println(res);

    }

    private static TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null) {
            return root;
        }
        //对二叉树进行中序遍历
        List<TreeNode> nodeList = new ArrayList<>();
        inOrder(nodeList, root);
        //遍历nodeList
        TreeNode node = null;
        for (int i = 0; i < nodeList.size(); i++) {
            if(nodeList.get(i).val == p.val) {
                if(i == nodeList.size() - 1) {
                    return null;
                }else{
                    node = nodeList.get(i+1);
                    break;
                }
            }
        }
        return node;
    }

    private static void inOrder(List<TreeNode> nodes, TreeNode root) {
        if (root == null) {
            return;
        }
        inOrder(nodes, root.left);
        nodes.add(root);
        inOrder(nodes, root.right);

    }
}

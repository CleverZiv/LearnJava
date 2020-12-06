package tree;

/**
 * Created by lengzefu on 2019/3/7.
 */
public class Console {
    /**
     * 创建一个二叉树
     */
    public static BinaryTree createTree(){
        BinaryTree Mytree = new BinaryTree(1);
        Mytree.left = new BinaryTree(2);
        Mytree.left.left = new BinaryTree(4);
        Mytree.right = new BinaryTree(3);
        Mytree.right.right = new BinaryTree(5);
        Mytree.right.right.left = new BinaryTree(6);
        return Mytree;
    }

    public static BinarySearchTree createBinarySearchTree(){
        BinarySearchTree tree = new BinarySearchTree(5);
        tree.left = new BinarySearchTree(3); tree.right = new BinarySearchTree(7);
        tree.left.parent = tree; tree.left.right = new BinarySearchTree(4);
        tree.right.parent = tree; tree.right.left = new BinarySearchTree(6);
        tree.left.right.parent = tree.left; tree.right.left.parent = tree.right;
        return tree;
    }
    public static void main(String[] args) {
        //BinaryTree tree = createTree();
        //tree.inOrder(tree);
        //tree.preOrderNoRecursion(tree);
        //tree.inOrderNoRecursion(tree);
        //tree.postOrderNoRecursion(tree);
        //tree.rowOrder(tree);
        /*BinarySearchTree tree = createBinarySearchTree();
        System.out.print(tree.getMax(tree));*/
        int i = 1;
        int j = i++;
        System.out.println(i);
    }
}

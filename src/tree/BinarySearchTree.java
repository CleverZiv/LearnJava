package tree;

/**
 * Created by lengzefu on 2019/3/8.
 * 二叉查找树
 */
public class BinarySearchTree {
    int val;
    BinarySearchTree parent;
    BinarySearchTree left;
    BinarySearchTree right;

    public BinarySearchTree(int val) {
        this.val = val;
        this.parent = null;
        this.left = null;
        this.right = null;
    }
    /**
     * 遍历方法同二叉树的遍历
     */

    /**
     * 获取BST的最小值
     * 就是最左的孩子
     * @param tree
     * @return
     */
    public int getMin(BinarySearchTree tree) {
        if(tree == null) return -1;
        while(tree.left != null) {
            tree = tree.left;
        }
        return tree.val;
    }

    /**
     * 获取BST的最大值
     * 就是最右的孩子
     * @param tree
     * @return
     */
    public int getMax(BinarySearchTree tree) {
        if(tree == null) return 999;
        while(tree.right != null) {
            tree = tree.right;
        }
        return tree.val;
    }

    /**
     * 获取某节点item的前驱节点
     * BST的前驱节点 = 小于该节点的所有节点中的最大值
     * 即中序遍历中的前一个数字
     * @param item
     * @return
     */
    public BinarySearchTree preNode(BinarySearchTree item) {
        if(item == null) return null;
        //1、如果该节点存在左孩子，则就是下一个左孩子
        if(item.left != null) {
            return item.left;
        }
        //2、如果没有左孩子，有两种可能
        //2.1、item本身是一个“右孩子”，前驱结点就是item的父节点
        //2.2、item本身是一个“左孩子”，前驱结点就是item的一个祖先节点的父节点，且这个祖先节点为右孩子
        BinarySearchTree temp = item.parent;
        while(temp != null && temp.left == item){
            item = temp;
            temp = temp.parent;
        }
        return temp;
    }

    /**
     * 获取某节点item的后继节点
     * BST的后继节点 = 大于该节点的所有节点中的最小值
     * 即中序遍历的后一个数字
     * @param item
     * @return
     */
    public BinarySearchTree postNode(BinarySearchTree item) {
        if(item == null) return null;

        //1、如果存在右孩子，则右孩子就是后继节点
        if(item.right != null) return item.right;
        //2、如果不存在右孩子，有两种可能
        //2.1、item本身是一个左孩子，后继节点就是item的父节点
        //2.2、item本身是一个右孩子，后继节点就是item的一个祖先节点的父节点，且该祖先节点是左孩子
        BinarySearchTree temp = item.parent;
        while(temp != null && temp.right == item){
            item = temp;
            temp = temp.parent;
        }
        return temp;
    }

    /**
     * 查找值为val的节点（递归版）
     * @param tree
     * @param val
     * @return
     */
    public BinarySearchTree searchNode(BinarySearchTree tree, int val) {
        if(tree == null) return null;
        if(val > tree.val) {
            //大于则往右子树找
            return searchNode(tree.right, val);
        }else if(val < tree.val) {
            //小于则往左子树
            return searchNode(tree.left, val);
        }else return tree; //等于返回该值

    }

    /**
     * 查找值为val的节点（非递归版）
     * @param tree
     * @param val
     * @return
     */
    public BinarySearchTree searchNodeNoRecursion(BinarySearchTree tree, int val){
        if(tree == null) return null;
        while(tree != null) {
            if(val > tree.val) {
                tree = tree.right;
            }else if(val < tree.val) {
                tree = tree.left;
            }else return tree;
        }
        return tree;
    }

    /**
     * 往tree中插入item（递归版）
     * @param tree
     * @param item
     * @return
     */
    public BinarySearchTree insertNode(BinarySearchTree tree, BinarySearchTree item) {
        if(tree == null) return tree = item; //1、tree为空时，item插入为根节点
        else if(tree.val > item.val) {
            tree.left = insertNode(tree.left, item); //2、往左插
        }else if(tree.val < item.val) {
            tree.right = insertNode(tree.right, item);//3、往右插
        }
        return tree; //包含了插入值在tree中存在的情况，此时插入失败，直接返回tree
    }

    /**
     * 往tree中插入item（非递归版）
     * @param tree
     * @param item
     * @return
     */
    public BinarySearchTree inertNodeNoRecursion(BinarySearchTree tree,
                                                 BinarySearchTree item) {
        if(tree == null) return tree = item;
        BinarySearchTree temp = null;//记录父节点的临时变量
        BinarySearchTree root = tree;//记录根节点，方便最后返回
        while(tree != null) {
            temp = tree;
            if(tree.val < item.val) tree = tree.right;
            else tree= tree.left;
        }
        //跳出循环则表示定位到了item的父节点处
        item.parent = temp;
        //判断是左节点还是右节点
        if(item.val < temp.val) temp.left = item;
        else if (item.val > temp.val) temp.right = item;
        return root;
    }
    //删除
}

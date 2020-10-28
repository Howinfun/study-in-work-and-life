package com.hyf.algorithm.剑指offer;

/**
 * @author Howinfun
 * @desc
 * @date 2019/10/14
 */
public class 重建二叉树 {
    public static void main(String[] args) {
        重建二叉树 h = new 重建二叉树();
        int[] preorder = {1,2,4,5,6,3};
        int[] inorder = {4,2,5,6,1,3};
        TreeNode tree = h.reConstructBinaryTree(preorder,inorder);
        System.out.println("前序遍历：");
        h.printTreeByPre(tree);
        System.out.println();
        System.out.println("中序遍历：");
        h.printTreeByIn(tree);
    }

    /**
     * 二叉树->前序遍历
     * @param treeNode
     */
    public void printTreeByPre(TreeNode treeNode){
        if (treeNode != null){
            // 先打印root
            System.out.print(treeNode.val);
            // 再判断左子节点是否为空，如果不为空，继续递归
            if (treeNode.left != null){
                printTreeByPre(treeNode.left);
            }
            // 再判断右子节点是否为空，如果不为空，继续递归
            if (treeNode.right != null){
                printTreeByPre(treeNode.right);
            }
        }
    }

    /**
     * 中序遍历
     * @param treeNode
     */
    public void printTreeByIn(TreeNode treeNode){
        if (treeNode != null){
            if (treeNode.left != null){
                printTreeByIn(treeNode.left);
            }
            System.out.print(treeNode.val);
            if (treeNode.right != null){
                printTreeByIn(treeNode.right);
            }
        }
    }

    /**
     * 重构二叉树
     * @param pre 前序遍历
     * @param in 中序遍历
     * @return
     */
    public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        // 首先判断输入是否有误：前序遍历和中序遍历是否为空，前序遍历长度是否等于中序遍历长度
        if (pre == null || in == null || pre.length != in.length){
            throw new RuntimeException("输入的二叉树遍历有误");
        }
        TreeNode root = construct(pre,0,pre.length-1,in,0,in.length-1);
        return root;
    }

    /**
     *
     * @param pre 前序遍历
     * @param ps 前序遍历->开始位置
     * @param pe 前序遍历->结束位置
     * @param in 中序遍历
     * @param is 中序遍历->开始位置
     * @param ie 中序遍历->结束位置
     * @return
     */
    private TreeNode construct(int [] pre,int ps,int pe,int [] in,int is,int ie) {
        // 开始位置大于结束位置说明已经没有需要处理的元素了
        if (ps > pe) {
            return null;
        }
        // 前序遍历的第一个节点即为根节点
        int rootVal = pre[ps];
        // 获取根节点在中序遍历的位置，为下面找左子树和右子树做铺垫
        int index = is;
        while (index <= ie && in[index] != rootVal){
            index++;
        }
        // 如果最后还是没找到根节点，index会大于中序遍历的长度
        if (index > ie){
            throw new RuntimeException("输入的二叉树遍历有误");
        }
        // 当前根节点
        TreeNode root = new TreeNode(rootVal);
        /**
         * 找出左子树：左子树节点数：index-is个（因为根节点的位置在index，而中序遍历左子树的节点全在根节点左边）
         * 前序遍历位置：pre [ps+1,ps+(index-is)] 根节点后一位开始，一共（index-is）个节点
         * 中序遍历位置：in [is,index-1] 开始位置到根节点的前一个，都是左子树的节点
         * */
        root.left = construct(pre,ps+1,ps+index-is,in,is,index-1);
        /**
         * 找出右子树：右子树节点数：ie-index个（因为根节点的位置在index，而中序遍历右子树的节点全在根节点的右边）
         * 前序遍历位置：pre [ps+(index-is)+1,pe] 在左子树的节点后开始，就是右子树的节点
         * 中序遍历位置：in [index+1,ie] 在根节点的后一个开始，都是右子树的节点
         */
        root.right = construct(pre,ps+index-is+1,pe,in,index+1,ie);
        return root;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

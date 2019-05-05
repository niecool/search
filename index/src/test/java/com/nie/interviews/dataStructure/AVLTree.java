package com.nie.interviews.dataStructure;

/**
 * @author zhaochengye
 * @date 2019-04-30 10:56
 */
public class AVLTree {

    public AVLTreeNode singleRotateLeft(){
//        AVLTreeNode w=  x.getLeft();
        return null;
    }














    class AVLTreeNode{
        private int data;//结点的数据
        private int height;//树的高度
        private AVLTreeNode left;//指向左孩子结点
        private AVLTreeNode right;//指向左孩子结点

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public AVLTreeNode getLeft() {
            return left;
        }

        public void setLeft(AVLTreeNode left) {
            this.left = left;
        }

        public AVLTreeNode getRight() {
            return right;
        }

        public void setRight(AVLTreeNode right) {
            this.right = right;
        }
    }

}

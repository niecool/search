package com.nie.interviews.dataStructure;

import org.junit.Test;

/**
 * 搜索二叉树构建类
 *
 * @author zhaochengye
 * @date 2019-04-30 09:14
 */
public class BinaryTree<T extends Comparable> {

    private Entity<T> root;
    private int size;



    private boolean put(Entity<T> entityy){
        if(entityy == null || entityy.value == null){
            throw new NullPointerException();
        }
        if(root == null || size == 0){//初始化第一个节点
            root = entityy;
            size++;
            return true;
        }
        Entity<T> temp;
        Entity<T> parent;
        temp = root;
        while (true) {
            if (entityy.value.compareTo(temp.value) > 0) {//构建搜索树 大于当前temp节点
                parent = temp;
                temp = temp.right;
                if(temp == null){
                    parent.right = new Entity(entityy.value);
                    size++;
                    return true;
                }
            } else if (entityy.value.compareTo(temp.value) < 0) {
                parent = temp;
                temp = temp.left;
                if(temp == null){
                    parent.left = new Entity(entityy.value);
                    size++;
                    return true;
                }
            } else {
                return true;
            }
        }
    }

    @Test
    public  void testBinaryTree() {
        BinaryTree<Integer> binaryTree = new BinaryTree();
        Entity input = new Entity<Integer>(3);
        binaryTree.put(input);
        Entity input1 = new Entity<Integer>(4);
        binaryTree.put(input1);
        Entity input2 = new Entity<Integer>(5);
        binaryTree.put(input2);
        Entity input3 = new Entity<Integer>(1);
        binaryTree.put(input3);
        Entity input4 = new Entity<Integer>(4);//不能重复，否则没法排序。
        binaryTree.put(input4);
        System.out.println(binaryTree.size);
    }








    class Entity<T extends Comparable>{
        Entity<T> left;
        Entity<T> right;
        T value ;

        Entity(T t){
            this.value = t;
        }
        Entity(T t,Entity<T> left, Entity<T> right){
            this.value = t;
            this.left = left;
            this.right = right;
        }

        public Entity<T> getLeft() {
            return left;
        }

        public void setLeft(Entity<T> left) {
            this.left = left;
        }

        public Entity<T> getRight() {
            return right;
        }

        public void setRight(Entity<T> right) {
            this.right = right;
        }
    }
}

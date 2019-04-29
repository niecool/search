package com.nie.interviews;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author zhaochengye
 * @date 2019-04-28 14:49
 */
public class MyList<D> {

    private Object[] arr;
    public int size;
    private final static Object[] defaultArr = {};
    private final static Object[] initArr = new Object[10];
    private final static int MaxArrSize = Integer.MAX_VALUE;

    MyList(){
        arr = defaultArr;
    }

    public boolean add(D d){
        //1.确保容量
        ensureCapacity(size+1);
        //2.添加元素
        arr[size++]=d;
        return true;
    }
    public boolean remove(D d){
        //判断是否存在
        if(d == null){
            for (int i = 0; i < size; i++) {
                if(arr[i] == null){
                    fastRemove(i);
                    return true;
                }

            }
        }else {
            for (int i = 0; i < size; i++) {
                if(arr[i].equals(d)){
                    fastRemove(i);
                    return true;
                }

            }
        }


        return true;
    }

    private void fastRemove(int index) {
//        Object[] newArr = new Object[size-1];
        int copyNum = size-index-1;
        if(copyNum>0)
            System.arraycopy(arr,index+1,arr,index,copyNum);
        arr[--size] = null;
    }


    private void ensureCapacity(int needSize) {
        //1.是否是初次
        if(size == 0){
            arr = initArr;
        }
        //2.判断是否够容量,不够就扩容
        if(needSize - arr.length >0){
            grow(needSize);
        }
    }

    private void grow(int needSize) {
        //如果needSize < 0
        if(needSize<0) throw new IndexOutOfBoundsException();

        //扩容
        int newSize = size + size>>1;

        //不够就按照跨越扩容
        if(newSize - needSize<0){
            newSize = needSize;
        }

        //极端情况
        if(newSize > MaxArrSize){
            newSize = MaxArrSize;
        }

    }


    public static void main(String[] args) {
        MyList list = new MyList<String>();
        list.add("sss");
        System.out.println(list.size);
        System.out.println(list.arr[0]);
        System.out.println(list.arr[0] instanceof String);
        list.remove("sss");
        System.out.println(list.size);
        System.out.println(list.arr[0]);
        System.out.println(list.arr[0] instanceof String);

    }


}

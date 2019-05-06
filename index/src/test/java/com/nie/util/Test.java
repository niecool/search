package com.nie.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-24 21:40
 */
public class Test{
    public static void main(String[] args){
        // 初始化Bean1
        Test test = new Test();
        Bean1 bean1 = test.new Bean1();
        bean1.I++;
        // 初始化Bean2
//        Test test1 = new Test();
        Bean2 bean2 = new Bean2();
        bean2.J++;
        //初始化Bean3
        Bean bean = new Bean();
        Bean.Bean3 bean3 = bean.new Bean3();
        bean3.k++;
    }
    class Bean1{
        public int I = 0;
    }

    static class Bean2{
        public int J = 0;
    }
}

class Bean{
    class Bean3{
        public int k = 0;
    }


    public static void main(String[] args) {
        List<Integer> a = new ArrayList<>();
        System.out.println(a.hashCode());
        build(a);
        System.out.println(a.size()+"++++++++++");

    }


    /**
     *
     */
    public static void build(List<Integer> a){
        System.out.println(a.hashCode());
        List b = new ArrayList();
        b.add(1);
        b.add(2);
        a = b;
        System.out.println(a.size()+"===========");
    }
}
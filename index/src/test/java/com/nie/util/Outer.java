package com.nie.util;

/**
 * @author zhaochengye
 * @date 2019-04-24 20:42
 */
public class Outer {
    {
        System.out.println("Outer========");
    }

    public static void main(String[] args) {
        Outer outer = new  Outer();
        Inner inner = new Inner();
        Outer2 outer2 = new Outer2();
        Outer2.get();
//        Inner inner = outer.new Inner();
    }

    static class Inner{
        static {
            System.out.println("inner========");
        }
    }

    class Inner2{
        {
            System.out.println("inner2================");
        }
    }
}

  class Outer2{

    private final static Outer2 outer2 = new Outer2();
    static {
        System.out.println("outer2=====333");
    }
    public static void get(){
        System.out.println("outer2==========");
     }
}

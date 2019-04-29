package com.nie.interviews;

/**
 * @author zhaochengye
 * @date 2019-04-29 14:33
 */
public class Something {

    // constructor methods
    Something() {
        System.out.println("haha");
    }

    Something(String something) {
        System.out.println(something);
    }

    // static methods
    static String startsWith(String s) {
        return String.valueOf(s.charAt(0));
    }

    // object methods

    String endWith(String s) {
        return String.valueOf(s.charAt(s.length() - 1));
    }
    String combine(String s) {
        return String.valueOf(s.charAt(0)) + String.valueOf(s.charAt(s.length() - 1));
    }

    void endWith() {
    }


    @FunctionalInterface
    interface IConvert<F, T> {
        T convert(F form);
    }


}

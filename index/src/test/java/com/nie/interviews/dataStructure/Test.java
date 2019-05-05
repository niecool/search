package com.nie.interviews.dataStructure;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * @author zhaochengye
 * @date 2019-04-30 09:10
 */
public class Test {

    public static void main(String[] args) {
        HashMap<Integer,String> t = new HashMap<>();
        t.put(1,"a");
        t.put(2,"b");
        t.put(1,"c");
        System.out.println(t.size());
    }
}

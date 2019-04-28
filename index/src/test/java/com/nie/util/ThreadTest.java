package com.nie.util;

import static org.apache.logging.log4j.core.util.Loader.getClassLoader;

/**
 * @author zhaochengye
 * @date 2019-04-28 11:16
 */
public class ThreadTest extends Thread {


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(getClassLoader());
                }
            }).start();
        }

    }



}

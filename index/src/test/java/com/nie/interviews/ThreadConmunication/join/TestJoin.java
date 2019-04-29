package com.nie.interviews.ThreadConmunication.join;


import org.apache.log4j.Logger;

/**
 *
 * join也是用notify实现的
 * @author zhaochengye
 * @date 2019-04-29 19:12
 */
public class TestJoin {

    private static Logger LOGGER = Logger.getLogger(TestJoin.class);

    public static void main(String[] args) throws InterruptedException {
        TestJoin.join();
    }

    private static void join() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("running");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }) ;
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("running2");
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }) ;
        t1.start();
        t2.start();
        //等待线程1终止
        t1.join();
        //等待线程2终止
        t2.join();
        LOGGER.info("main over");
    }
}

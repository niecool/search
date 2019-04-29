package com.nie.interviews.ThreadConmunication.notify;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhaochengye
 * @date 2019-04-29 16:50
 */
public class TestThreadConmunication {
    /**
     * 1.场景：
     * 生产者和消费者模型：消费者反馈到生产者
     */
    @Test
    public void testConmunication(){
        AtomicInteger productNum = new AtomicInteger(0);
        for (int i = 0; i < 5; i++) {
            new ThreadA(productNum).start();
        }

//        for (int i = 0; i < 6; i++) {
            new ThreadB(productNum).start();
//        }

        while (true);


    }

}

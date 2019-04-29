package com.nie.interviews.ThreadConmunication.countDownLatch_CycleBarrier;

import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author zhaochengye
 * @date 2019-04-29 19:48
 */
public class countDownLatch {

    /**
     * countDownLatch AQS  AbstractQueuedSynchronized队列实现，在主线程阻塞，等待结果
     */
    private static Logger LOGGER = Logger.getLogger(countDownLatch.class);
    private static void countDownLatch() throws Exception{
        int thread = 3 ;
        long start = System.currentTimeMillis();
        final CountDownLatch countDown = new CountDownLatch(thread);
        for (int i= 0 ;i<thread ; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LOGGER.info("thread run");
                    try {
                        Thread.sleep(2000);
                        countDown.countDown();
                        LOGGER.info("thread end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        countDown.await();
        long stop = System.currentTimeMillis();
        LOGGER.info("main over total time="+(stop-start));
    }

    /**
     * 等到线程减到的时候再一起执行。
     * @throws Exception
     */
    private static void cyclicBarrier() throws Exception {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3) ;
        new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("thread run");
                try {
                    cyclicBarrier.await() ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                LOGGER.info("thread end do something");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("thread run");
                try {
                    cyclicBarrier.await() ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                LOGGER.info("thread end do something");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("thread run");
                try {
                    Thread.sleep(5000);
                    cyclicBarrier.await() ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                LOGGER.info("thread end do something");
            }
        }).start();
        LOGGER.info("main thread");
    }


    public static void main(String[] args) throws Exception {
//        countDownLatch.countDownLatch();

        cyclicBarrier();
    }


}

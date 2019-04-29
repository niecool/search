package com.nie.interviews.ThreadConmunication.notify;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhaochengye
 * @date 2019-04-29 16:49
 */
public class ThreadA extends Thread{

    private AtomicInteger productNum;

    ThreadA(AtomicInteger productNum){
        this.productNum = productNum;
    }
    @Override
    public void run() {
        synchronized (ThreadA.class) {
            while (true) {
                //1.生产商品，每秒一个；
                try {
                    System.out.println("producer:" + productNum);
                    ThreadA.class.wait(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                productNum.incrementAndGet();

                if (productNum.get() > 10) {
                    try {
                        System.out.println("=====================生产数量太多需要休息");
                        ThreadA.class.wait();
                        System.out.println("=====================当前线程被唤醒。" + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}

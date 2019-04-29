package com.nie.interviews.ThreadConmunication.notify;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhaochengye
 * @date 2019-04-29 16:50
 */
public class ThreadB extends Thread {
    private AtomicInteger productNum;
    private boolean flag;
    ThreadB(AtomicInteger productNum){
        this.productNum = productNum;
    }

    @Override
    public void run() {
        //1.每两秒消费一个
        synchronized (ThreadA.class) {
            while (true) {
                try {
                    System.out.println("consumer:" + productNum);
                    ThreadA.class.wait(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                productNum.decrementAndGet();

                if (productNum.get() > 5 && flag == false) {
                    flag = true;
                }
                if (productNum.get() < 5 && flag) {
                    ThreadA.class.notifyAll();
                }
            }
        }
    }
}

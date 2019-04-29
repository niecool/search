package com.nie.interviews.ThreadConmunication.interrupt;

/**
 * @author zhaochengye
 * @date 2019-04-29 20:00
 */
public class ThreadInterrupt implements Runnable {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            System.out.println("运行中");
        }
        System.out.println("线程终止");

    }

    public static void main(String[] args) throws InterruptedException {
        Thread s = new Thread(new ThreadInterrupt());
        s.start();
        Thread.sleep(2000);
        s.interrupt();

    }
}

package com.nie.interviews.ThreadConmunication.voltile;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaochengye
 * @date 2019-04-29 19:46
 */
public class VolatileTest implements Runnable{
    private static volatile boolean flag = true ;
    @Override
    public void run() {
        while (flag){
            System.out.println(Thread.currentThread().getName() + "正在运行。。。");
        }
        System.out.println(Thread.currentThread().getName() +"执行完毕");
    }
    public static void main(String[] args) throws InterruptedException {
        VolatileTest aVolatile = new VolatileTest();
        new Thread(aVolatile,"thread A").start();
        System.out.println("main 线程正在运行") ;
        TimeUnit.MILLISECONDS.sleep(100) ;
        aVolatile.stopThread();
    }
    private void stopThread(){
        flag = false ;
    }
}

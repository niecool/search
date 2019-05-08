package com.nie.interviews;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhaochengye
 * @date 2019-05-08 13:48
 */
public class Lock extends Thread {

    private ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    @Override
    public void run() {
        System.out.println("do something1111!"+Thread.currentThread().getName());
        haha();
        System.out.println("do something222!"+Thread.currentThread().getName());

        super.run();
    }
    
    /**
     * 
     */
    public void haha(){

        try{
            lock.lock();
            condition.signal();
            lock.unlock();

        }catch (Exception e){

        }
    }

    public static void main(String[] args) {
        new Lock().start();
        System.out.println("========");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

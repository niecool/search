package com.nie.interviews.ThreadConmunication.threadPool;

import com.nie.interviews.ThreadConmunication.join.TestJoin;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author zhaochengye
 * @date 2019-04-29 20:06
 */
public class ThreadPoolTest {
    private static Logger LOGGER = Logger.getLogger(ThreadPoolTest.class);
    private static void executorService() throws Exception{
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(10) ;
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,5,1, TimeUnit.MILLISECONDS,queue) ;
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("running");
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("running2");
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        poolExecutor.shutdown();
        while (!poolExecutor.awaitTermination(1, TimeUnit.SECONDS)){
            LOGGER.info("线程还在执行。。。");
        }
        LOGGER.info("main over");
    }

    public static void main(String[] args) throws Exception {
//        executorService();
        haha();
    }
    
    
    
    
    
    //异步线程
    
    /**
     * 
     */
    public static void haha(){
        List<String> strList = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<CompletableFuture<String>> retFuture = strList.stream().map(str -> CompletableFuture.supplyAsync(()->str.trim(), executor))
                .map(future -> future.thenApply(String::trim))
                .map(future -> future.thenCompose(str -> CompletableFuture.supplyAsync(()->str.trim(), executor)))
                .collect(Collectors.toList());

    }

}

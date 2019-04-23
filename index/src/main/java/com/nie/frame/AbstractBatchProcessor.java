package com.nie.frame;

import com.nie.model.BusinessProduct;
import com.nie.utils.NamedThreadFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author zhaochengye
 * @date 2019-04-23 14:29
 */
public abstract class AbstractBatchProcessor {

    private Logger log = Logger.getLogger(AbstractBatchProcessor.class);
    private volatile ExecutorService pool = null;

    /**
     * 批量执行的方法体
     * @param products
     * @throws Exception
     */
    public abstract void processFunction(List<BusinessProduct> products) throws Exception;

    public void batchProcess(List<BusinessProduct> products){
        //双重检查单例模式
        if(pool == null){
            synchronized (AbstractBatchProcessor.class){
                if(pool == null){
                    /**
                     *
                     * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
                     * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
                     * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
                     * ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
                     *
                     */
                    pool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),Runtime.getRuntime().availableProcessors()*2,
                            1000L, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(Runtime.getRuntime().availableProcessors()*2,true),
                            new NamedThreadFactory("BatchProcessor-"+this.getClass().getSimpleName()),new ThreadPoolExecutor.CallerRunsPolicy());
                }
            }
        }

        int batch = getBatchNum();
        if(batch == 0){
            batch = 100;
        }

        if(products == null || products.isEmpty()) {
            return;
        }

        CompletionService<Boolean> cs = new ExecutorCompletionService<Boolean>(pool);
        List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
        for (int i = 0; i < products.size()/batch; i++) {
            final List<BusinessProduct> subList = products.subList(i*batch,(i+1)*batch>products.size()?products.size():(i+1)*batch);
            if(subList.isEmpty()) break;
            Future<Boolean> future = cs.submit(new Callable<Boolean>() {// callable又返回值 runnable没有返回值
                @Override
                public Boolean call() throws Exception {
                    Boolean success = false;
                    try {
                        processFunction(subList);
                        success = true;
                    }catch (Exception e){
                        log.error("Process failed."+e);
                    }
                    return success;
                }
            });
            futures.add(future);
        }

        // Wait for the result.
        for(Future<Boolean> future : futures) {//这里只是判断循环次数

            try{
                // take as long as any future completed
                Boolean success = cs.take().get();//future.get()是阻塞方法，但是ExecutorCompletionService的take方法是按照结果先处理完的先放在队列里面，从而实现异步非阻塞。
                if(!success){
                    log.error("Process failed.");
                }
            } catch(Exception e) {
                log.error("Process failed : " , e);
            }
        }
    }


    public abstract int getBatchNum();

}

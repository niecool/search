package com.nie.service.dataLoad;


import com.nie.model.DataChangeMessage;
import com.nie.monitor.Observer;
import com.nie.monitor.ObserverServer;
import com.nie.monitor.impl.RealtimeDataChangeObserver;
import com.nie.monitor.impl.RealtimeObserverServer;
import com.nie.service.dataLoad.update.DataUpdateChecker;
import com.nie.service.dataLoad.update.DataUpdateCheckerManager;
import org.apache.log4j.Logger;
import org.apache.lucene.util.NamedThreadFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * @author zhaochengye
 * @date 2019-04-22 11:35
 */
public class DBWatcher extends Thread{
    private Logger log = Logger.getLogger(DBWatcher.class);

    private long maxWaitTime = 60 * 1000;
    private long minWaitTime = 10 * 1000;//当数据库没变化的时候会以最小时间查询
    private DataUpdateCheckerManager checkerManager;
    private int dbCheckerNumber = 2;//config in properties
    private boolean isRunning;
    private ExecutorService checkerPool;
    private ObserverServer observerServer;//一般是在spring中注入，现在是在构造器中注入。

    public DBWatcher(ObserverServer server, int threadPoolNum) {
        this.dbCheckerNumber = threadPoolNum;
        this.setName("DbWatcher");
        this.observerServer = server;
        this.setDaemon(true);
        isRunning = false;
        checkerManager = DataUpdateCheckerManager.getInstance();//单例模式，静态调用
    }


    @Override
    public void run(){
        checkerPool = Executors.newFixedThreadPool(dbCheckerNumber,new NamedThreadFactory("DBChecker"));
        isRunning = true;
        while (true) {
            try {
                Map<String, List<DataUpdateChecker>> checkerGroup = getUpdateCheckerGroup();
                if (checkerGroup == null || checkerGroup.isEmpty()) {
                    log.info("No checkers are available, sleep " + maxWaitTime);
                    Thread.sleep(maxWaitTime);
                    continue;
                }
                // 并行执行Checker
                List<DataUpdateChecker> timedCheckers = getTimedUpdateCheckers(checkerGroup);
                if (!timedCheckers.isEmpty()) {
                    doCheckInParrallel(timedCheckers);
                }
                // 计算下次检测的时间
                Long nextCheckTime = getNextcheckTime(checkerGroup);
                // 设置等待时间上限，以及时检测配置更新
                // 设置等待时间下限，避免出错情况下频繁重试
                long now = System.currentTimeMillis();
                long sleepTime = nextCheckTime > now ? nextCheckTime - now : 0;
                if (sleepTime > maxWaitTime)
                    sleepTime = maxWaitTime;
                else if (sleepTime < minWaitTime)
                    sleepTime = minWaitTime;

                log.info("sleepTime=" + sleepTime + ", now=" + now + ", nextCheckTime=" + nextCheckTime);
                Thread.sleep(sleepTime);

            }catch (Exception e){
                log.error(e);
                isRunning = false;
                break;
            }
        }
    }


    public Map<String, List<DataUpdateChecker>> getUpdateCheckerGroup() {
        // 获取可用的Checker
        Map<String, List<DataUpdateChecker>> checkerGroup = new HashMap<String, List<DataUpdateChecker>>();
        //todo
        String name = "product";//table name
        DataUpdateChecker checker = checkerManager.getChecker(name);
        List<DataUpdateChecker> list = new ArrayList<DataUpdateChecker>();
        list.add(checker);
        checkerGroup.put(name, list);
        return checkerGroup;
    }

    public List<DataUpdateChecker> getTimedUpdateCheckers(
            Map<String, List<DataUpdateChecker>> checkerGroup) {
        List<DataUpdateChecker> timedCheckers = new ArrayList<DataUpdateChecker>();

        // 按照Group检查到期的Checker，如果Group中有一个Checker到期，则所有的Checker都会被执行
        if (!(checkerGroup == null || checkerGroup.isEmpty())) {
            for (Map.Entry<String, List<DataUpdateChecker>> entry : checkerGroup
                    .entrySet()) {
                List<DataUpdateChecker> checkers = entry.getValue();
                boolean shouldCheck = false;
                for (DataUpdateChecker checker : checkers) {
                    if (checker.shouldCheck()) {
                        shouldCheck = true;
                        break;
                    }
                }
                if (shouldCheck) {
                    timedCheckers.addAll(checkers);
                }
            }
        }
        return timedCheckers;
    }


    public void doCheckInParrallel(List<DataUpdateChecker> checkers) {
        // 上次检测时间
        long lastCheckTime = System.currentTimeMillis();

        // 多线程并行执行检测
        List<Future<DataChangeMessage>> futures = new ArrayList<Future<DataChangeMessage>>();
        for (final DataUpdateChecker checker : checkers) {
            Future<DataChangeMessage> future = checkerPool
                    .submit(new Callable<DataChangeMessage>() {
                        @Override
                        public DataChangeMessage call() {
                            try {
                                DataChangeMessage dcm = checker.getDataChange();
                                return dcm;
                            } catch (Exception e) {
                                log.error("Failed to check update, :  "
                                        + checker, e);
                                return null;
                            }
                        }
                    });

            futures.add(future);
        }

        // 获取检测结果
        for (Future<DataChangeMessage> future : futures) {
            DataChangeMessage curChange = null;
            try {
                curChange = future.get();
                if (curChange != null && curChange.getIdList() != null
                        && !curChange.getIdList().isEmpty()) {
                    curChange.setVersion(Long.toString(lastCheckTime));
                    curChange.setTimeStamp(lastCheckTime);

                    /**
                     * 消息观察者模式
                     */
                    observerServer.sendMessage(curChange);
                }
            } catch (Exception e) {
                log.error(e);
            }
        }

        // Update last check time
        for(DataUpdateChecker checker : checkers) {
            checker.setLastCheckTime(lastCheckTime-1);//lastCheckTime-1 避免数据库判断条件加等于
        }

    }

    public Long getNextcheckTime(
            Map<String, List<DataUpdateChecker>> checkerGroup) {
        Long minNextCheckTime = Long.MAX_VALUE;
        for (List<DataUpdateChecker> checkers : checkerGroup.values()) {
            for (DataUpdateChecker checker : checkers) {
                if (minNextCheckTime > checker.nextCheckTime()) {
                    minNextCheckTime = checker.nextCheckTime();
                }
            }
        }
        return minNextCheckTime;
    }


    public long getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(long maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public long getMinWaitTime() {
        return minWaitTime;
    }

    public void setMinWaitTime(long minWaitTime) {
        this.minWaitTime = minWaitTime;
    }

    public DataUpdateCheckerManager getCheckerManager() {
        return checkerManager;
    }

    public void setCheckerManager(DataUpdateCheckerManager checkerManager) {
        this.checkerManager = checkerManager;
    }

    public int getDbCheckerNumber() {
        return dbCheckerNumber;
    }

    public void setDbCheckerNumber(int dbCheckerNumber) {
        this.dbCheckerNumber = dbCheckerNumber;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}

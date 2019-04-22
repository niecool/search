package com.nie.DBWatcher;

import com.nie.service.dataLoad.DBWatcher;
import com.nie.service.dataLoad.update.DataUpdateChecker;
import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaochengye
 * @date 2019-04-22 17:20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-config.xml")
public class DBWatcherTest {

    @Test
    public void testDbWatcher() throws InterruptedException, IOException {
        DBWatcher watcher = new DBWatcher();
        watcher.setDbCheckerNumber(2);
        Map<String, List<DataUpdateChecker>> group = watcher.getUpdateCheckerGroup();
        Long time = watcher.getNextcheckTime(group);
        List<DataUpdateChecker> checkers = new ArrayList<DataUpdateChecker>();
        watcher.start();
        while (true);//test框架会结束当前线程，进而影响子线程关闭。
    }



}

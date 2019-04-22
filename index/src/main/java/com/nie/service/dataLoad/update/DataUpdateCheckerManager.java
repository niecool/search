package com.nie.service.dataLoad.update;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaochengye
 * @date 2019-04-22 15:41
 */
public class DataUpdateCheckerManager {

    static Logger log = Logger.getLogger(DataUpdateCheckerManager.class);

    static DataUpdateCheckerManager instance = null;

    private Map<String, DataUpdateChecker> checkerMap;

    private DataUpdateCheckerManager(){
        checkerMap = new HashMap<String, DataUpdateChecker>();
    }

    public synchronized static DataUpdateCheckerManager getInstance(){
        if(instance == null) {
            instance = new DataUpdateCheckerManager();
            log.info("DataUpdateCheckerManager is initialized.");
        }
        return instance;
    }
    public synchronized void register(DataUpdateChecker checker){
        if(checker != null && checker.getCheckerName() != null) {
            checkerMap.put(checker.getCheckerName(), checker);
            log.info("Registered checker: " + checker.getCheckerName());
        }
    }

    public synchronized DataUpdateChecker getChecker(String name) {
        return checkerMap.get(name);
    }

    public synchronized List<String> getAllCheckerName(){
        List<String> nameList = new ArrayList<String>();
        nameList.addAll(checkerMap.keySet());
        return nameList;
    }

}

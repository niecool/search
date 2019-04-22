package com.nie.service.dataLoad.update;

import com.nie.model.DataChangeMessage;

import java.util.Date;

/**
 * @author zhaochengye
 * @date 2019-04-22 13:45
 */
public interface DataUpdateChecker {

     DataChangeMessage getDataChange();

     public long getLastCheckTime();

     public void setLastCheckTime(long lastCheckTime);

     public String getCheckerName();

     public void setCheckerName(String checkerName);

     /**
      * Is it the time to check?
      * @return
      */
     boolean shouldCheck();

     /**
      * When is next time to check?
      * @return
      */
     Long nextCheckTime();

}

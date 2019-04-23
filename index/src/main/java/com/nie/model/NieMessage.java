package com.nie.model;

import java.io.Serializable;

/**
 * @author zhaochengye
 * @date 2019-04-23 20:00
 */
public class NieMessage implements Serializable {

    private long timeStamp;//数据版本

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}

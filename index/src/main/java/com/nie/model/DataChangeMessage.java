package com.nie.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-22 14:11
 */
public class DataChangeMessage implements Serializable {
    private static final long serialVersionUID = -1274052362966490257L;

    //业务表类型
    public enum TYPE{
        PRODUCT
    }

    //更新类型
    public enum UPDATETYPE{
        DEFAULT
    }

    private String name;

    private String version;

    private List<Long> idList;

    private long receiveTimeStamp;

    private TYPE type;

    private UPDATETYPE updatetype = UPDATETYPE.DEFAULT;

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public long getReceiveTimeStamp() {
        return receiveTimeStamp;
    }

    public void setReceiveTimeStamp(long receiveTimeStamp) {
        this.receiveTimeStamp = receiveTimeStamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public UPDATETYPE getUpdatetype() {
        return updatetype;
    }

    public void setUpdatetype(UPDATETYPE updatetype) {
        this.updatetype = updatetype;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

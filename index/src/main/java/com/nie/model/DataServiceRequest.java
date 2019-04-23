package com.nie.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-23 18:59
 */
public class DataServiceRequest implements Serializable {

    private static final long serialVersionUID = -1720813553919334124L;

    private Collection<Long> productIds;
//    private List<FieldUpdateItemBase> fieldUpdateItems;
    private DataChangeMessage.UPDATETYPE updateType;
    private long timestamp;
    private String source;


    public Collection<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(Collection<Long> productIds) {
        this.productIds = productIds;
    }

    public DataChangeMessage.UPDATETYPE getUpdateType() {
        return updateType;
    }

    public void setUpdateType(DataChangeMessage.UPDATETYPE updateType) {
        this.updateType = updateType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}

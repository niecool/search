package com.nie.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-23 19:07
 */
public class DataServiceResponse implements Serializable {
    private static final long serialVersionUID = 4174814571164678158L;

    private String dataServiceIp;//处理该消息的机器ip

    private int extendProductCount;//扩展后的产品数

    private int deleteProductCount;//删除的产品数

    private List<ProductIndexable> productIndexableList;//返回的对象集合

    @Override
    public String toString() {
        return "DataServiceResponse [dataServiceIp=" + dataServiceIp + ", extendProductCount=" + extendProductCount + ", deleteProductCount=" + deleteProductCount + ", productIndexableList="
                + productIndexableList + "]";
    }

    public String getDataServiceIp() {
        return dataServiceIp;
    }

    public void setDataServiceIp(String dataServiceIp) {
        this.dataServiceIp = dataServiceIp;
    }

    public int getExtendProductCount() {
        return extendProductCount;
    }

    public void setExtendProductCount(int extendProductCount) {
        this.extendProductCount = extendProductCount;
    }

    public int getDeleteProductCount() {
        return deleteProductCount;
    }

    public void setDeleteProductCount(int deleteProductCount) {
        this.deleteProductCount = deleteProductCount;
    }

    public List<ProductIndexable> getProductIndexableList() {
        return productIndexableList;
    }

    public void setProductIndexableList(List<ProductIndexable> productIndexableList) {
        this.productIndexableList = productIndexableList;
    }
}

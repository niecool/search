package com.nie.frame.process;

import com.nie.model.pojo.Product;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author zhaochengye
 * @date 2019-04-23 17:32
 */
public class RealTimeProcessorContext extends ProcessorContext {

    private Collection<Long> initialProductIds;//初始产品id集合
    private Set<Long> indexProductIds;
//    private Map<String,String> inputProductsMap;
    private int extendProductCount;//扩展后的产品数
//    private int deleteProductCount;//删除的产品数

    private Map<String, Product> baseProductMap;

    public Collection<Long> getInitialProductIds() {
        return initialProductIds;
    }

    public void setInitialProductIds(Collection<Long> initialProductIds) {
        this.initialProductIds = initialProductIds;
    }

    public Set<Long> getIndexProductIds() {
        return indexProductIds;
    }

    public void setIndexProductIds(Set<Long> indexProductIds) {
        this.indexProductIds = indexProductIds;
    }
//
//    public Map<String, String> getInputProductsMap() {
//        return inputProductsMap;
//    }
//
//    public void setInputProductsMap(Map<String, String> inputProductsMap) {
//        this.inputProductsMap = inputProductsMap;
//    }
//
    public int getExtendProductCount() {
        return extendProductCount;
    }

    public void setExtendProductCount(int extendProductCount) {
        this.extendProductCount = extendProductCount;
    }
//
//    public int getDeleteProductCount() {
//        return deleteProductCount;
//    }
//
//    public void setDeleteProductCount(int deleteProductCount) {
//        this.deleteProductCount = deleteProductCount;
//    }


    public Map<String, Product> getBaseProductMap() {
        return baseProductMap;
    }

    public void setBaseProductMap(Map<String, Product> baseProductMap) {
        this.baseProductMap = baseProductMap;
    }
}

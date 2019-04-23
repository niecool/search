package com.nie.service.realtime;

import com.nie.model.DataChangeMessage;
import com.nie.model.ProductIndexable;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-23 21:51
 */
public class RealtimeIndexableBuilder {

    private static Logger log = Logger.getLogger(RealtimeIndexableBuilder.class);
    private int extendProductCount;
    private int deleteProductCount;

    public List<ProductIndexable> buildRouter(Collection<Long> productIds, String source) throws Exception {
        return  fullUpdate(productIds,source);
    }


    private List<ProductIndexable> fullUpdate( Collection<Long> productIds, String source)
            throws Exception {
        if(CollectionUtils.isEmpty(productIds)) {
            return null;
        }
        RealTimeIndexableAssembler samRealTimeIndexableAssembler = new RealTimeIndexableAssembler();
        List<ProductIndexable> productIndexableList = samRealTimeIndexableAssembler.buildProductIndexable(source, productIds);
        this.extendProductCount = samRealTimeIndexableAssembler.getExtendProductCount();
        this.deleteProductCount = samRealTimeIndexableAssembler.getDeleteProductCount();
        return productIndexableList;
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
}

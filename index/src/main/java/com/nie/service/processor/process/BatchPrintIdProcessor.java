package com.nie.service.processor.process;

import com.nie.frame.AbstractBatchProcessor;
import com.nie.model.BusinessProduct;

import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-23 16:09
 */
public class BatchPrintIdProcessor extends AbstractBatchProcessor {
    @Override
    public void processFunction(List<BusinessProduct> products) throws Exception {
        if(products.isEmpty()) throw new RuntimeException();
        for(BusinessProduct businessProduct : products){
            System.out.println(businessProduct.getId());
        }
    }

    @Override
    public int getBatchNum() {
        return 2;
    }


}

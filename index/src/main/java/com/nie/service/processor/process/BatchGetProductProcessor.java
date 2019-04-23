package com.nie.service.processor.process;

import com.nie.frame.AbstractBatchProcessor;
import com.nie.model.BusinessProduct;

import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-23 21:32
 */
public class BatchGetProductProcessor extends AbstractBatchProcessor {
    @Override
    public void processFunction(List<BusinessProduct> products) throws Exception {

    }

    @Override
    public int getBatchNum() {
        return 100;
    }
}

package com.nie.processor;

import com.nie.frame.AbstractBatchProcessor;
import com.nie.model.BusinessProduct;
import com.nie.service.processor.process.BatchPrintIdProcessor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-23 16:12
 */

public class BatchPrintIdProcessorTest {

    @Test
    public void testBatchPrintIdProcessor(){
        List<BusinessProduct> list = new ArrayList<>();
        for (long i = 1000; i < 1010; i++) {
            BusinessProduct p = new BusinessProduct();
            p.setId(i);
            list.add(p);
        }
        AbstractBatchProcessor abstractBatchProcessor = new BatchPrintIdProcessor();
        abstractBatchProcessor.batchProcess(list);

    }
}

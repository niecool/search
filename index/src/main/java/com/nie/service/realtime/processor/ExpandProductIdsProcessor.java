package com.nie.service.realtime.processor;

import com.nie.DaoManager.ProductDaoManager;
import com.nie.constant.Constant;
import com.nie.frame.Processor;
import com.nie.frame.process.ProcessorContext;
import com.nie.frame.process.RealTimeProcessorContext;
import com.nie.model.pojo.Product;
import com.nie.utils.ApplicationContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 补齐更改的商品信息
 * @author zhaochengye
 * @date 2019-04-24 16:59
 */
@Component
public class ExpandProductIdsProcessor implements Processor {

    @Autowired
    ProductDaoManager productDaoManager;

    @Override
    public String getName() {
        return ExpandProductIdsProcessor.class.getSimpleName();
    }

    @Override
    public void process(ProcessorContext processorContext) {
        RealTimeProcessorContext realTimeProcessorContext = (RealTimeProcessorContext)processorContext;
        Map<String, Product> baseProductMap = new HashMap<>();
//        SortedMap<String, ProductRecord> productRecordMap = realTimeProcessorContext.getProductRecordMap();
        Collection<Long> productIds = realTimeProcessorContext.getInitialProductIds();
        if(productIds.isEmpty())return;
//        productDaoManager = (ProductDaoManager)ApplicationContextUtils.getApplicationContext().getBean("productDaoManager");
        List<Product> productList = productDaoManager.queryProductByIds((List<Long>) productIds);

        if(productList.isEmpty())return;

        Set<Long> indexIds = new HashSet<>();
        for(Product baseProduct : productList){
            String key = Constant.INDEXID +baseProduct.getId();
            baseProductMap.put(key,baseProduct);
            indexIds.add(baseProduct.getId());
        }
        realTimeProcessorContext.setIndexProductIds(indexIds);//去重处理
        realTimeProcessorContext.setBaseProductMap(baseProductMap);
        realTimeProcessorContext.setExtendProductCount(baseProductMap.size());

    }
}

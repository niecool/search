package com.nie.service.realtime.processor;

import com.nie.constant.Constant;
import com.nie.frame.DataRecord;
import com.nie.frame.Processor;
import com.nie.frame.process.ProcessorContext;
import com.nie.frame.process.RealTimeProcessorContext;
import com.nie.model.ProductIndexable;
import com.nie.model.pojo.Product;
import com.nie.utils.BeansUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.beans.Beans;
import java.util.*;

/**
 * @author zhaochengye
 * @date 2019-04-25 16:34
 */
@Component
public class Convert2ProductIndexable implements Processor {

    private String[] props = {
         "id","productCname","productEname","categoryId","nameSubtitle","productDescription","inPrice",
         "productBrandId","color"
    };

    @Override
    public String getName() {
        return Convert2ProductIndexable.class.getSimpleName();
    }

    @Override
    public void process(ProcessorContext context) {
        RealTimeProcessorContext realTimeProcessorContext = (RealTimeProcessorContext)context;
        Map<String, Product> productMap = realTimeProcessorContext.getBaseProductMap();
        Set<Long> indexIds = realTimeProcessorContext.getIndexProductIds();
        if(indexIds.isEmpty())return;
        Map<String, DataRecord<String,ProductIndexable>> dataRecordMap = realTimeProcessorContext.getDataRecordsMap();
        if(CollectionUtils.isEmpty(dataRecordMap)) dataRecordMap = new HashMap<>();

        List<DataRecord> dataRecords = new ArrayList<>();
        for(Long id : indexIds){
            String key = Constant.INDEXID+id;
            Product product = productMap.get(key);
            ProductIndexable p = new ProductIndexable();
            convert(product,p);
            DataRecord<String,ProductIndexable> record = new DataRecord<>(key,p);
            dataRecords.add(record);//方便各种processor轮询
            dataRecordMap.put(key,record);//方便各种processor查询

        }

        realTimeProcessorContext.setDataRecords(dataRecords);
        realTimeProcessorContext.setDataRecordsMap(dataRecordMap);
    }

    /**
     * @param product
     * @param p
     */
    private void convert(Product product, ProductIndexable p){
        BeansUtils.copyBeanProperties(product, p, props);
        //类型有转换就放外面
        p.setCreateTime(product.getCreateTime());
        p.setUpdateTime(product.getUpdateTime());
    }
}

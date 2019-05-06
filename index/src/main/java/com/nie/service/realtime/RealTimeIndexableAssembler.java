package com.nie.service.realtime;

import com.nie.DaoManager.ProductDaoManager;
import com.nie.frame.DataRecord;
import com.nie.frame.Processor;
import com.nie.frame.process.RealTimeProcessorContext;
import com.nie.model.ProductIndexable;
import com.nie.service.realtime.processor.Convert2ProductIndexable;
import com.nie.service.realtime.processor.ExpandProductIdsProcessor;
import com.nie.service.realtime.processor.SegmentWordsProcessor;
import org.apache.log4j.Logger;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zhaochengye
 * @date 2019-04-23 21:54
 */
@Component
public class RealTimeIndexableAssembler extends ApplicationObjectSupport {
    private static Logger log = Logger.getLogger(RealTimeIndexableAssembler.class);

    private int extendProductCount;
    private int deleteProductCount;

    @Resource
    private ProductDaoManager productDaoManager;
    /**
     * 1.这里可以用到hadoop处理。
     * 2.也可以使用本地的处理程序，运用hadoop的执行思想，顺便了解一下hadoop是个不错的建议。
     * 3.另外可以不处理直接扔给elasticsearch处理。
     *
     * 本应用主要是使用自己的处理程序。
     */
    public List<ProductIndexable> buildProductIndexable(String source, Collection<Long> productIds){
        if(CollectionUtils.isEmpty(productIds)){
            return null;
        }

        //1.构建processorContext
        RealTimeProcessorContext realTimeProcessorContext = new RealTimeProcessorContext();
        realTimeProcessorContext.setInitialProductIds(productIds);

        //2.map处理阶段
        List<Processor<DataRecord>> processors = new ArrayList<Processor<DataRecord>>();
        List<Processor<DataRecord>> mapProcessors = getMapProcessor();
        for(Processor processor : mapProcessors){
            processor.process(realTimeProcessorContext);
        }
        //3.shuffle处理阶段

        //4.reduce处理阶段
        List<Processor<DataRecord>> reduceProcessors = getReduceProcessor();
        for(Processor processor : reduceProcessors){
            processor.process(realTimeProcessorContext);
        }
        //5.拿到RealTimeProcessorContext里面已经处理好的索引数据
        Map<String, DataRecord<String, ProductIndexable>> dataRecords = (Map<String, DataRecord<String, ProductIndexable>>)realTimeProcessorContext.getDataRecordsMap();
        List<ProductIndexable> productIndexableList = new ArrayList<>();
        if(dataRecords.isEmpty())return productIndexableList;
        Iterator iterator = dataRecords.values().iterator();
        while (iterator.hasNext()){
            DataRecord<String, ProductIndexable> dataRecord = (DataRecord<String, ProductIndexable>)iterator.next();
            productIndexableList.add(dataRecord.getV());
        }
        return productIndexableList;
    }





    @SuppressWarnings("unchecked")
    List<Processor<DataRecord>>getMapProcessor(){
        List<Processor<DataRecord>> mapProcessors = new ArrayList<Processor<DataRecord>>();
        mapProcessors.add((ExpandProductIdsProcessor)(getApplicationContext().getBean("expandProductIdsProcessor")));
        mapProcessors.add((Convert2ProductIndexable)(getApplicationContext().getBean("convert2ProductIndexable")));
        mapProcessors.add((SegmentWordsProcessor)(getApplicationContext().getBean("segmentWordsProcessor")));
        return mapProcessors;
    }

    List<Processor<DataRecord>>getReduceProcessor(){
        List<Processor<DataRecord>> reduceProcessors = new ArrayList<Processor<DataRecord>>();
//        reduceProcessors.add(new Convert2ProductIndexable());
        return reduceProcessors;
    }




















    public int getExtendProductCount() {
        return extendProductCount;
    }

    public int getDeleteProductCount() {
        return deleteProductCount;
    }

    public void setExtendProductCount(int extendProductCount) {
        this.extendProductCount = extendProductCount;
    }

    public void setDeleteProductCount(int deleteProductCount) {
        this.deleteProductCount = deleteProductCount;
    }
}


//工厂模式： 使用多态创建子类，调用父类方法。
//策略模式： 创建多个策略模式算法，创建context环境，让context环境执行strategy算法体
//观察者模式： 创建多个观察者，注册到服务端，然后服务端通知所有客户端数据改动。
//facade设计模式：将多个接口功能合并到一个大的facade接口暴露给调用者，避免调用者调用太多接口。
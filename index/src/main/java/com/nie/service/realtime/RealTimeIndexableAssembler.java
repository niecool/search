package com.nie.service.realtime;

import com.nie.model.ProductIndexable;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-23 21:54
 */
public class RealTimeIndexableAssembler {
    private static Logger log = Logger.getLogger(RealTimeIndexableAssembler.class);

    private int extendProductCount;
    private int deleteProductCount;
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

        //2.map处理阶段

        //3.shuffle处理阶段

        //4.reduce处理阶段

        //5.拿到RealTimeProcessorContext里面的数据
        return null;
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

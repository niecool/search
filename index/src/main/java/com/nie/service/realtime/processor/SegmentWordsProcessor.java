package com.nie.service.realtime.processor;

import com.nie.frame.DataRecord;
import com.nie.frame.Processor;
import com.nie.frame.process.ProcessorContext;
import com.nie.frame.process.RealTimeProcessorContext;
import com.nie.model.ProductIndexable;
import com.nie.segment.segmentor.NieSegmenter;
import com.nie.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * 将商品的相关属性分词，放入索引结果集里面
 *
 * @author zhaochengye
 * @date 2019-04-24 19:16
 */
@Component
public class SegmentWordsProcessor implements Processor {

    NieSegmenter nieSegmenter;
    public SegmentWordsProcessor(NieSegmenter nieSegmenter){
        this.nieSegmenter = nieSegmenter;
    }

    @Override
    public String getName() {
        return SegmentWordsProcessor.class.getSimpleName();
    }

    @Override
    public void process(ProcessorContext processorContext) {
        //
        RealTimeProcessorContext realTimeProcessorContext = (RealTimeProcessorContext)processorContext;
        List<DataRecord> dataRecords = realTimeProcessorContext.getDataRecords();
        dataRecords.parallelStream().map(dataRecord -> buildSegment(dataRecord)).collect(Collectors.toList());
    }


    private DataRecord<String, ProductIndexable> buildSegment(DataRecord<String, ProductIndexable> dataRecord){
        ProductIndexable pi = dataRecord.getV();
        List<String> noScoreWords = new ArrayList<>();
        //中文名称
        if(!StringUtil.isEmpty(pi.getProductCname())){
            List<String> pcn = nieSegmenter.segmentStr(pi.getProductCname());
            if(!pcn.isEmpty()){
                noScoreWords.addAll(pcn);
            }
        }
        //英文名称
        if(!StringUtil.isEmpty(pi.getProductEname())){
            List<String> pen = nieSegmenter.segmentStr(pi.getProductEname());
            if(!pen.isEmpty()){
                noScoreWords.addAll(pen);
            }
        }
        //名称简称
        if(!StringUtil.isEmpty(pi.getNameSubtitle())){
            List<String> ns = nieSegmenter.segmentStr(pi.getNameSubtitle());
            if(!ns.isEmpty()){
                noScoreWords.addAll(ns);
            }
        }
        //商品简介
        if(!StringUtil.isEmpty(pi.getProductDescription())){
            List<String> pd =nieSegmenter.segmentStr(pi.getProductDescription());
            if(!pd.isEmpty()){
                noScoreWords.addAll(pd);
            }
        }
        if(!noScoreWords.isEmpty()){
            pi.setNoScoreWords(noScoreWords.toArray(new String[noScoreWords.size()]));
        }

        return dataRecord;
    }
}

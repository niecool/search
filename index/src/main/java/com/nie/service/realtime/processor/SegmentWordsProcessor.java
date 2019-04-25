package com.nie.service.realtime.processor;

import com.nie.frame.Processor;
import com.nie.frame.process.ProcessorContext;
import org.springframework.stereotype.Component;

/**
 *
 * 将商品的相关属性分词，放入索引结果集里面
 *
 * @author zhaochengye
 * @date 2019-04-24 19:16
 */
@Component
public class SegmentWordsProcessor implements Processor {
    @Override
    public String getName() {
        return SegmentWordsProcessor.class.getSimpleName();
    }

    @Override
    public void process(ProcessorContext context) {
        //
    }
}

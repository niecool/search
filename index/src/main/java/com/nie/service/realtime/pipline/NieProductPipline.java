package com.nie.service.realtime.pipline;

import com.nie.frame.DataRecord;
import com.nie.frame.Processor;

import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-24 16:47
 */
public class NieProductPipline implements PiplineBuilder {
    @Override
    public List<? extends Processor<DataRecord>> build(PipelineType pipelineType) throws Exception {

        return null;
    }
}

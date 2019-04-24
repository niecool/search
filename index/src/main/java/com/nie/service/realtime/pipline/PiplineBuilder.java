package com.nie.service.realtime.pipline;

import com.nie.frame.DataRecord;
import com.nie.frame.Processor;

import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-24 16:48
 */
public interface PiplineBuilder {
    public List<? extends Processor<DataRecord>> build(PipelineType pipelineType) throws Exception;

    public enum PipelineType{
        DUMP,
        UPDATE,
        REPOS
    }
}

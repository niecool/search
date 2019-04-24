package com.nie.frame;

import com.nie.frame.process.ProcessorContext;

/**
 * @author zhaochengye
 * @date 2019-04-23 12:33
 */
public interface Processor<D extends DataRecord> {

    public String getName();

    public void process(ProcessorContext<D> context);
}

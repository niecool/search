package com.nie.frame;

/**
 * @author zhaochengye
 * @date 2019-04-23 12:33
 */
public interface Processor<D extends DataRecord> {

    public String getName();

    public void process(ProcessContext<D> context);
}

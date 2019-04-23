package com.nie.frame.process;

import com.nie.frame.DataRecord;

import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-23 14:11
 */
public class ProcessContext<D extends DataRecord> {

    private String indexName = null;

    private String source;//数据来源

    private List<D> dataRecords;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<D> getDataRecords() {
        return dataRecords;
    }

    public void setDataRecords(List<D> dataRecords) {
        this.dataRecords = dataRecords;
    }
}

package com.nie.frame.process;

import com.nie.frame.DataRecord;

import java.util.List;
import java.util.Map;

/**
 * @author zhaochengye
 * @date 2019-04-23 14:11
 */
public class ProcessorContext<D extends DataRecord> {

    private String indexName = null;

    private String source;//数据来源

    private List<D> dataRecords;//这里是支持任何map-reduce数据结构的数据以list组合结构传递过来处理。

    private Map<String,D> dataRecordsMap;

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


    public Map<String, D> getDataRecordsMap() {
        return dataRecordsMap;
    }

    public void setDataRecordsMap(Map<String, D> dataRecords) {
        this.dataRecordsMap = dataRecords;
    }
}

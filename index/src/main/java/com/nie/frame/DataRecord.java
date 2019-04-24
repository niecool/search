package com.nie.frame;

/**
 * 存在这样的数据结构是为了更好的扩展索引支持的数据类型。
 * @author zhaochengye
 * @date 2019-04-23 12:31
 */
public class DataRecord<K,V> {
    private K k;
    private V v;

    public DataRecord(K k, V v){
        this.k = k;
        this.v = v;
    }

    public K getK() {
        return k;
    }

    public void setK(K k) {
        this.k = k;
    }

    public V getV() {
        return v;
    }

    public void setV(V v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "DataRecord [k=" + k + ", v=" + v + "]";
    }
}

package com.nie.frame;

/**
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

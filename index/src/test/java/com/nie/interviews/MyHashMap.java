package com.nie.interviews;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author zhaochengye
 * @date 2019-04-28 17:02
 */
public class MyHashMap<K,V> {

    private K k;
    private V v;

    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    final float loadFactor;
    Node<K,V>[] table;
    int threshold;//阀值

    MyHashMap(){
        //初始化，确定因子
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    /**
     *
     */
    public boolean put(K k, V v){
        //1.table判断
        MyHashMap.Node<K,V>[] tab; MyHashMap.Node<K,V> p; int n, i;
        if((tab = table) == null || (n = tab.length) == 0){
            initTable();
        }
        return true;
    }

    private void initTable() {

    }


    static class Node<K ,V>{
        K k;
        V v;
        final int hash;
        MyHashMap.Node<K,V> next;

        Node(int hash, K key, V value, MyHashMap.Node<K,V> next){
            this.hash = hash;
            this.k = key;
            this.v= value;
            this.next = next;
        }

    }

    public static void main(String[] args) {
        HashMap<String,String> map = new HashMap();
        map.put("1","a");
        map.hashCode();
    }
}

package com.nie.mandy.client.com.nie.mandy.es.connection;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * 高级api
 */
public class EsHighClient {

    // private attribute
    private static final String hostname = "152.136.70.24";

    private static final int port = 9200;

    private static final String http = "http";

    // client
    private static RestHighLevelClient client = null;

    // avoid instance
    private EsHighClient() {
    }

    static {
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException("init es conneciton error ", e);
        }
    }

    /**
     * @return
     */
    public static RestHighLevelClient getClient() {
        return client;
    }

    /**
     * todo  config parameter
     */
    private static void init() {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost(hostname, port, http)));
    }

    public static void main(String[] args) {
        System.out.println(client);
    }

}

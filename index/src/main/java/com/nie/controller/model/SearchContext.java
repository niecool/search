package com.nie.controller.model;

import org.apache.lucene.search.Query;

import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-05-06 16:30
 */
public class SearchContext {

    private ProductRequest productRequest;

    private Query query;

    private List<String> segments;

    public ProductRequest getProductRequest() {
        return productRequest;
    }

    public void setProductRequest(ProductRequest productRequest) {
        this.productRequest = productRequest;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public List<String> getSegments() {
        return segments;
    }

    public void setSegments(List<String> segments) {
        this.segments = segments;
    }
}

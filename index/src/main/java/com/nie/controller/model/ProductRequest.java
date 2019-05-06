package com.nie.controller.model;

import java.io.Serializable;

/**
 * @author zhaochengye
 * @date 2019-05-06 16:20
 */
public class ProductRequest implements Serializable {
    private static final long serialVersionUID = -5261726969759618617L;

    String keyWord;

    Long productId;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}

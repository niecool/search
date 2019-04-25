package com.nie.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * lucene 的 field和solr的schema，就是搜索需要的field字段
 * @author zhaochengye
 * @date 2019-04-18 14:20
 */
public class ProductIndexable implements Serializable {
    private static final long serialVersionUID = 1271163084712641718L;

    private String id;//索引ID = productId

//    private String productCode;

    private String productCname;

    private String productEname;

    private String categoryId;

//    private String defaultPictureUrl;

    private String nameSubtitle;

    private String productDescription;

    private Double inPrice;

    private String productBrandId;

    private String color;

//    private Long createUser;

//    private Long updateUser;

    private Date createTime;

    private Date updateTime;

//    private Integer isDelete;
    //========================================================================
    private String[] noScoreWords;

    public String[] getNoScoreWords() {
        return noScoreWords;
    }

    public void setNoScoreWords(String[] noScoreWords) {
        this.noScoreWords = noScoreWords;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductCname() {
        return productCname;
    }

    public void setProductCname(String productCname) {
        this.productCname = productCname;
    }

    public String getProductEname() {
        return productEname;
    }

    public void setProductEname(String productEname) {
        this.productEname = productEname;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getNameSubtitle() {
        return nameSubtitle;
    }

    public void setNameSubtitle(String nameSubtitle) {
        this.nameSubtitle = nameSubtitle;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getInPrice() {
        return inPrice;
    }

    public void setInPrice(Double inPrice) {
        this.inPrice = inPrice;
    }

    public String getProductBrandId() {
        return productBrandId;
    }

    public void setProductBrandId(String productBrandId) {
        this.productBrandId = productBrandId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
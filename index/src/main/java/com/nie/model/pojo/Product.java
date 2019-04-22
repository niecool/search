package com.nie.model.pojo;

import java.sql.Timestamp;
import java.util.Date;

public class Product {
    private Long id;

    private String productCode;

    private String productCname;

    private String productEname;

    private String categoryId;

    private String defaultPictureUrl;

    private String nameSubtitle;

    private String productDescription;

    private Double inPrice;

    private String productBrandId;

    private String color;

    private Long createUser;

    private Long updateUser;

    private Timestamp createTime;

    private Timestamp updateTime;

    private Integer isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductCname() {
        return productCname;
    }

    public void setProductCname(String productCname) {
        this.productCname = productCname == null ? null : productCname.trim();
    }

    public String getProductEname() {
        return productEname;
    }

    public void setProductEname(String productEname) {
        this.productEname = productEname == null ? null : productEname.trim();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    public String getDefaultPictureUrl() {
        return defaultPictureUrl;
    }

    public void setDefaultPictureUrl(String defaultPictureUrl) {
        this.defaultPictureUrl = defaultPictureUrl == null ? null : defaultPictureUrl.trim();
    }

    public String getNameSubtitle() {
        return nameSubtitle;
    }

    public void setNameSubtitle(String nameSubtitle) {
        this.nameSubtitle = nameSubtitle == null ? null : nameSubtitle.trim();
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription == null ? null : productDescription.trim();
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
        this.productBrandId = productBrandId == null ? null : productBrandId.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
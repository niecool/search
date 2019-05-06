package com.nie.model.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class KeywordDict implements Serializable {
    private static final long serialVersionUID = 6209293399944480506L;

    public KeywordDict(){

    }
    private Long id;

    private String keyword;

    private Integer type;

    private String synonym;

    private String extented;

    private BigDecimal lastUpdateId;

    private Date lastUpdateTime;

    private Integer status;

    private String dataFrom;

    private String lastUpdateName;

    private Integer isCheck;

    private String relatived;

    private String incompatible;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym == null ? null : synonym.trim();
    }

    public String getExtented() {
        return extented;
    }

    public void setExtented(String extented) {
        this.extented = extented == null ? null : extented.trim();
    }

    public BigDecimal getLastUpdateId() {
        return lastUpdateId;
    }

    public void setLastUpdateId(BigDecimal lastUpdateId) {
        this.lastUpdateId = lastUpdateId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(String dataFrom) {
        this.dataFrom = dataFrom == null ? null : dataFrom.trim();
    }

    public String getLastUpdateName() {
        return lastUpdateName;
    }

    public void setLastUpdateName(String lastUpdateName) {
        this.lastUpdateName = lastUpdateName == null ? null : lastUpdateName.trim();
    }

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    public String getRelatived() {
        return relatived;
    }

    public void setRelatived(String relatived) {
        this.relatived = relatived == null ? null : relatived.trim();
    }

    public String getIncompatible() {
        return incompatible;
    }

    public void setIncompatible(String incompatible) {
        this.incompatible = incompatible == null ? null : incompatible.trim();
    }
    
    /**
     * 
     */
    public KeywordDict combine(){
        this.setKeyword(this.getKeyword()+this.getLastUpdateName());
        return this;
    }
    
}
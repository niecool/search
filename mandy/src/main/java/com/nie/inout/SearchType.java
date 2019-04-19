package com.nie.inout;

public enum SearchType {

    KeyWord(1, "100","关键词搜索"),
    Category(0, "200", "类目搜索");



    private int order;

    private String code;
    private String description;

    SearchType(int type,String code, String desc) {
        this.order = type;
        this.code = code;
        this.description = desc;
    }


    public int getOrderType() {
        return this.order;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCode() {
        return code;
    }


}

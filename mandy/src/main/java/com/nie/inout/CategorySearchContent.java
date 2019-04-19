package com.nie.inout;


import sun.nio.cs.ext.SJIS;

import java.util.List;

/**
 * 类目搜索的内容
 */
public class CategorySearchContent extends SearchContent {

    public CategorySearchContent() {
        super();
    }

    @Override
    protected boolean doCheck() {
        // todo  check content self
        return true;
    }

    // 类目id ,一个或者多个
    protected List<Long> categoryIds;
    protected long categoryId = 0; //

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }
    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }


}

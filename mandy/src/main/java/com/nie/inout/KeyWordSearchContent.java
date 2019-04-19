package com.nie.inout;

public class KeyWordSearchContent extends SearchContent {

    public KeyWordSearchContent() {
        super();
    }

    @Override
    protected boolean doCheck() {
        // todo
        return true;
    }

    /**
     * 关键词条件. null表示无
     */
    protected String keyword;


    //
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}

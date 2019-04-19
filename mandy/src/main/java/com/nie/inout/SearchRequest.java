package com.nie.inout;

/**
 *
 */
public class SearchRequest implements java.io.Serializable {

    private  SearchContent keyWordSearchContent;

    private  SearchContent categorySearchContent;


    /**
     * 搜索业务类型
     * @see SearchType
     */
    private SearchType searchType;

    // todo brand etc ...
}



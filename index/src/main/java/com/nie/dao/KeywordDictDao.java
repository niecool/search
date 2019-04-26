package com.nie.dao;

import com.nie.model.pojo.KeywordDict;

import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-26 16:37
 */
public interface KeywordDictDao {

    public List<KeywordDict> selectByExample(KeywordDictExample keywordDictExample);

    public Integer batchInsert(List<KeywordDict> keywordDicts);

    public Integer insert(KeywordDict keywordDict);


}

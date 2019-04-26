package com.nie.DaoManager;

import com.nie.dao.KeywordDictDao;
import com.nie.dao.KeywordDictExample;
import com.nie.model.pojo.KeywordDict;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-26 16:56
 */
@Component
public class KeywordDictDaoManager {

    @Resource
    private KeywordDictDao keywordDictDao;

    public Integer insert(KeywordDict keywordDict){
        return keywordDictDao.insert(keywordDict);
    }

    public Integer batchInsert(List<KeywordDict> keywordDicts){
        return keywordDictDao.batchInsert(keywordDicts);
    }
}

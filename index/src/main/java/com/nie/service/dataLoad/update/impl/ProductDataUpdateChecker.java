package com.nie.service.dataLoad.update.impl;

import com.nie.dao.ProductDao;
import com.nie.dao.ProductExample;
import com.nie.model.DataChangeMessage;
import com.nie.service.dataLoad.update.AbstractDataUpdateChecker;
import com.nie.service.dataLoad.update.DataUpdateCheckerManager;
import com.nie.utils.UniqueTs;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-22 14:21
 */
@Component
public class ProductDataUpdateChecker extends AbstractDataUpdateChecker {

    @Resource
    ProductDao productDao;

    @Override
    public DataChangeMessage getDataChange() {
        DataChangeMessage dcm = new DataChangeMessage();
        final String since = getLastCheckDate();
        List<Long> products = productDao.selectIdBySince(since);
        if(!CollectionUtils.isEmpty(products)){
            dcm.setIdList(products);
            dcm.setReceiveTimeStamp(UniqueTs.get().next());
            dcm.setType(DataChangeMessage.TYPE.PRODUCT);
        }
        return dcm;
    }

    @Override
    public Long nextCheckTime() {
        return getLastCheckTime() + getCheckInterval();
    }


    @PostConstruct
    @Override
    public void init() {
        String checkerName = "product";
        setCheckerName(checkerName);
        DataUpdateCheckerManager.getInstance().register(this);
    }


}
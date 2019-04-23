package com.nie.DaoManager;

import com.nie.dao.ProductDao;
import com.nie.dao.ProductExample;
import com.nie.model.pojo.Product;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-23 21:34
 */

public class ProductDaoManager {
    @Resource
    private ProductDao productDao;

    public List<Product> queryProductByIds(List<Long> ids){
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andIdIn(ids);
        return productDao.queryForList(productExample);
    }
}

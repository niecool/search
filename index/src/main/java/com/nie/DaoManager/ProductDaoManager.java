package com.nie.DaoManager;

import com.nie.dao.ProductDao;
import com.nie.dao.ProductExample;
import com.nie.model.pojo.Product;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-23 21:34
 */
@Component
public class ProductDaoManager {
    @Resource
    private ProductDao productDao;

    public List<Product> queryProductByIds(List<Long> ids){
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andIdIn(ids);
        return productDao.selectByExample(productExample);
    }

}

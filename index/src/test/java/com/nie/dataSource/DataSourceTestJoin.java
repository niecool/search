package com.nie.dataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.nie.dao.ProductDao;
import com.nie.dao.ProductExample;
import com.nie.model.pojo.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-config.xml")
public class DataSourceTestJoin {

    @Resource
    private ProductDao productDao;

    @Resource
    private DruidDataSource druidDataSource;

    @Test
    public void testMybatis()  {
        ProductExample productExample = new ProductExample();
        try {
        productExample.createCriteria().andProductCodeEqualTo("0000008651");
//            Long count = productDao.countByExample(productExample);
//            System.out.println(count);
            List list = productDao.selectByExample(productExample);
            System.out.println(list.size());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

package com.nie.util;

import com.nie.utils.ImportCSV2DB;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhaochengye
 * @date 2019-04-26 19:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-config.xml")
public class ImportCSV2DBTest extends ApplicationObjectSupport {

    /**
     *
     */
    @Test
    public void testInsertOneRecord(){
        ApplicationContext context = getApplicationContext();
        ImportCSV2DB importCSV2DB = (ImportCSV2DB) context.getBean("importCSV2DB");
//        importCSV2DB.insertOneRecord();
        importCSV2DB.insertAll();
    }
}

package com.nie.util;

import com.nie.DaoManager.ProductDaoManager;
import com.nie.utils.ApplicationContextUtils;
import org.junit.Test;

/**
 * @author zhaochengye
 * @date 2019-04-24 20:09
 */
public class UtilsTest {

    /**
     *
     */
    @Test
    public void testInnerStaticClazz(){
        System.out.println("aa");
        ApplicationContextUtils utils = new ApplicationContextUtils();
        ProductDaoManager productDaoManager = (ProductDaoManager)utils.getApplicationContext().getBean("productDaoManager");
        System.out.println(productDaoManager.toString());
    }
}

package com.nie.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 懒加载单例模式的最佳方式
 * @author zhaochengye
 * @date 2019-04-24 17:09
 */
public  class ApplicationContextUtils {

    public static ApplicationContext getApplicationContext(){
        return InnerStaticClazz.applicationContext;
    }

    static class InnerStaticClazz{
        private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring-config.xml");
    }
}

package com.nie.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author zhaochengye
 * @date 2019-04-25 17:05
 */
public class BeansUtils {

    public static void copyBeanProperties(final Object source,
                                          final Object target, final String[] properties) {

        final BeanWrapper src = new BeanWrapperImpl(source);
        final BeanWrapper trg = new BeanWrapperImpl(target);

        for (final String propertyName : properties) {
            trg.setPropertyValue(propertyName,
                    src.getPropertyValue(propertyName));
        }
    }

    public static void printProperty(Class<?> clz) {
        Field[] fields = clz.getDeclaredFields();
        String[] names = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            names[i] = fields[i].getName();
        }

        Arrays.sort(names);

        for (String name : names) {
            System.out.println("\"" + name + "\",");
        }
    }

}

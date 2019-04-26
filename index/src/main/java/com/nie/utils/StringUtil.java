package com.nie.utils;

/**
 * @author zhaochengye
 * @date 2019-04-27 00:25
 */
public final class StringUtil {

    public static boolean isEmpty(String str) {
        boolean is_empty_str = false;
        if (str == null || str.isEmpty()) {
            is_empty_str = true;
        }
        return is_empty_str;
    }
}

package com.itheima.utils;

/**
 * 模糊查询字符串拼接
 */
public class QueryUtils {
    public static String queryString(String queryString) {
        if (queryString != null && queryString.length() > 0) {
            queryString = "%" + queryString + "%";
        }
        return queryString;
    }
}

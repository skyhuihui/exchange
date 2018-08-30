package com.zag.support.jpa.util;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * 修正自动建表使用latin1作为字符集的错误
 */
public class CustomMysqlDialect extends MySQL5InnoDBDialect{

    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
package com.tickets.business;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * Created by CrazeWong on 2017/3/27.
 * Set the connection dialect myself
 */
public class MySQL5Dialect extends MySQL5InnoDBDialect {

    /* (non-Javadoc)
     * @see org.hibernate.dialect.MySQL5InnoDBDialect#getTableTypeString()
     * For UTF-8
     */
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}


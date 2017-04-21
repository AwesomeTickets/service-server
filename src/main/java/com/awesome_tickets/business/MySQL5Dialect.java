package com.awesome_tickets.business;

import org.hibernate.dialect.MySQL5InnoDBDialect;


public class MySQL5Dialect extends MySQL5InnoDBDialect {

    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}


package com.awesome_tickets.util;

import java.sql.Date;
import java.util.Calendar;

/**
 * Util for date operation.
 */
public class DateUtil {
    private static Calendar calendar = Calendar.getInstance();

    public static Date getNextDate(Date date) {
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day + 1);
        return new Date(calendar.getTimeInMillis());
    }
}

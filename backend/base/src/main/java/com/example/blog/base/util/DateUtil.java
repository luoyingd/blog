package com.example.blog.base.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class DateUtil {
    public static Date getFirstDayOfMonth(String year, String month) {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        final int start = Calendar.YEAR;
        cal.set(Calendar.DAY_OF_MONTH, start);
        return cal.getTime();
    }

    public static Date getLastDayOfMonth(String year, String month) {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return cal.getTime();
    }

    public static int calculateYearFromDate(Date date) {
        int setYear = 0;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            setYear = calendar.get(Calendar.YEAR);
        }
        return setYear;
    }

    public static int calculateMonthFromDate(Date date) {
        int setMonth = 0;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            setMonth = calendar.get(Calendar.MONTH);
        }
        return setMonth;
    }
}

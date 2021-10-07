package com.cnstar.interfacetestmock.util;

import com.cnstar.interfacetestmock.constant.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    public static String getCurrentDate() {
        return getCurrentDate(Constants.DATE_DAY_FORMAT);
    }

    public static String getCurrentDate(String pattern) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(cal.getTime());
    }

    public static String getYesterdayDate(String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(cal.getTime());
    }

    public static String getDayBefore(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date dt = str2Date(date, pattern);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return sdf.format(cal.getTime());
    }

    public static String getDateBefore(String pattern, int dayOffset) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -dayOffset);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(cal.getTime());
    }

    public static String getDateBefore(int dayOffset) {
        return getDateBefore(Constants.DATE_DAY_FORMAT, dayOffset);
    }

    public static String getDateBeforeMonth(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date dt = str2Date(date, pattern);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.add(Calendar.MONTH, -1);
        return sdf.format(cal.getTime());
    }

    public static String getDateBeforeYear(String date, int yearOffset) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_DAY_FORMAT);
        Date dt = str2Date(date, Constants.DATE_DAY_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.add(Calendar.YEAR, -yearOffset);
        return sdf.format(cal.getTime());
    }

    public static Date str2Date(String date, String pattern) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        DateFormat sdf = new SimpleDateFormat(pattern);
        Date dt = null;
        try {
            dt = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static Object getRandomRangeDate(String from, String to) {
        SimpleDateFormat timeformat = new SimpleDateFormat(Constants.DATE_DAY_FORMAT);
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(cal.DATE, -Integer.parseInt(from));
        String beginDate = timeformat.format(cal.getTime());
        cal.setTime(new Date());
        cal.add(cal.DATE, Integer.parseInt(to));
        String endDate = timeformat.format(cal.getTime());
        try {
            Date start = timeformat.parse(beginDate);
            Date end = timeformat.parse(endDate);
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());
            return timeformat.format(new Date(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }
}

package com.xly.mall.common.base.result;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
    public static final String SHORT_DATE_FORMAT_STR = "yyyy-MM-dd";
    public static final String LONG_DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String MAX_LONG_DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String EARLY_TIME = "00:00:00 000";
    public static final String LATE_TIME = "23:59:59";
    public static final String EARER_IN_THE_DAY = "yyyy-MM-dd 00:00:00.000";
    public static final String LATE_IN_THE_DAY = "yyyy-MM-dd 23:59:59.999";
    public static final long DAY_LONG = 86400000L;
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public DateUtil() {
    }

    public static Date getEarlyInTheDay(Date date) throws ParseException {
        String dateString = (new SimpleDateFormat("yyyy-MM-dd")).format(date) + " " + "00:00:00 000";
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(dateString);
    }

    public static Date getFirstOfDay(Date date) {
        String dateString = (new SimpleDateFormat("yyyy-MM-dd 00:00:00.000")).format(date);

        try {
            return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(dateString);
        } catch (ParseException var3) {
            return null;
        }
    }

    public static Date getLateInTheDay(Date date) throws ParseException {
        String dateString = (new SimpleDateFormat("yyyy-MM-dd")).format(date) + " " + "23:59:59";
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(dateString);
    }

    public static Date addSecond(Date date, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(13, second);
        return cal.getTime();
    }

    public static Date addMinute(Date date, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(12, minute);
        return cal.getTime();
    }

    public static long subtractNowDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long dateTimeInMillis = calendar.getTimeInMillis();
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(new Date());
        long nowTimeInMillis = nowCalendar.getTimeInMillis();
        return (nowTimeInMillis - dateTimeInMillis) / 86400000L;
    }

    public static long subtractSecond(Date startDate, Date endDate) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        long startTimeInMillis = startCalendar.getTimeInMillis();
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        long endTimeInMillis = endCalendar.getTimeInMillis();
        return (endTimeInMillis - startTimeInMillis) / 1000L;
    }

    public static Date parserStringToDate(String dateString, String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateString);
    }

    public static Date parse(String dateString, String format) {
        Date date = null;

        try {
            date = parserStringToDate(dateString, format);
        } catch (Exception var4) {
            ;
        }

        return date;
    }

    public static Date dateInterval(Date date, int dateInterval) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(5, dateInterval);
        return cal.getTime();
    }

    public static Date monthInterval(Date date, int monthInterval) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(2, monthInterval);
        return cal.getTime();
    }

    public static Date yearInterval(Date date, int yearInterval) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(1, yearInterval);
        return cal.getTime();
    }

    public static Date getBeginOfDay(Date date) {
        String beginDay = (new SimpleDateFormat("yyyy-MM-dd 00:00:00.000")).format(date);

        try {
            return parserStringToDate(beginDay, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException var3) {
            return null;
        }
    }

    public static Date getEndOfDay(Date date) {
        String endDay = (new SimpleDateFormat("yyyy-MM-dd 23:59:59.999")).format(date);

        try {
            return parserStringToDate(endDay, "yyyy-MM-dd HH:mm:ss.SSS");
        } catch (ParseException var3) {
            return null;
        }
    }

    public static String formatDate(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String formatDate(long millisecond, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date(millisecond);
        return dateFormat.format(date);
    }

    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static long truncateDate(Date beginDate, Date endDate) {
        if (endDate != null && beginDate != null) {
            GregorianCalendar end = new GregorianCalendar();
            end.setTime(endDate);
            GregorianCalendar begin = new GregorianCalendar();
            begin.setTime(beginDate);
            return (end.getTimeInMillis() - begin.getTimeInMillis()) / 86400000L;
        } else {
            return 0L;
        }
    }

    public static void main(String[] args) throws Exception {
        Date date1 = parserStringToDate("2014-07-02", "yyyy-MM-dd");
        Date date2 = parserStringToDate("2014-07-01", "yyyy-MM-dd");
        System.out.println(truncateDate(date1, date2));
        System.out.println(formatDate(getFirstOfDay(new Date()), "yyyy-MM-dd HH:mm:ss SSS"));
        System.out.println(formatDate(getEndOfDay(new Date()), "yyyy-MM-dd HH:mm:ss SSS"));
    }
}

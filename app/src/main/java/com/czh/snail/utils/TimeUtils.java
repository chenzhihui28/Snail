package com.czh.snail.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    public static final long DAY = 1000 * 60 * 60 * 24;
    public static final long HOUR = 1000 * 60 * 60;

    private TimeUtils() {
    }

    /**
     * 指定时间是否比当前时间早
     *
     * @param time
     * @return 1 ：指定时间比当前时间早；0：指定时间比当前时间晚；-1：无效时间
     */
    public static int timeBeforeNow(String time) {
        if (TextUtils.isEmpty(time)) {
            return -1;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        return timeBeforeNow(date);
    }

    /**
     * 指定时间是否比当前时间早
     *
     * @param time
     * @return 1 ：指定时间比当前时间早；0：指定时间比当前时间晚；-1：无效时间
     */
    public static int customTimeBeforeNow(String time, String formatStr) {
        if (TextUtils.isEmpty(time)) {
            return -1;
        }
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        return timeBeforeNow(date);
    }

    /**
     * 指定时间是否比当前时间早
     *
     * @param time
     * @return 1 ：指定时间比当前时间早；0：指定时间比当前时间晚；-1：无效时间
     */
    public static int timeBeforeNow(String time, String formatStr) {
        if (TextUtils.isEmpty(time)) {
            return -1;
        }
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        return timeBeforeNow(date);
    }

    /**
     * 指定时间是否比当前时间早
     *
     * @param time
     * @return 1 ：指定时间比当前时间早；0：指定时间比当前时间晚；-1：无效时间
     */
    public static int timeBeforeNow(Date time) {
        if (time == null) {
            return -1;
        }
        Date now = new Date();
        return time.before(now) ? 1 : 0;
    }

    /**
     * 指定时间是否比当前时间早
     *
     * @param time
     * @return 1 ：指定时间比当前时间早；0：指定时间比当前时间晚；-1：无效时间
     */
    public static int timeBeforeNow(Long time) {
        if (time == null || time == -1) {
            return -1;
        }
        Date now = new Date();
        return time <= now.getTime() ? 1 : 0;
    }

    /**
     * 指定时间是否比今天晚
     *
     * @param time
     * @return 1 ：指定时间是否比今天晚；0：指定时间是否比今天早；-1：无效时间
     */
    public static int timeAfterToday(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (TextUtils.isEmpty(time)) {
            return -1;
        }
        Date date;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }

        Calendar current = Calendar.getInstance();
        current.setTime(date);

        Calendar today = Calendar.getInstance(); // 今天
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        if (current.after(today)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 指定时间是否比昨天晚
     *
     * @param time
     * @return 1 ：指定时间是否比昨天晚；0：指定时间是否比昨天早；-1：无效时间
     */
    public static int timeAfterYesterDay(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (TextUtils.isEmpty(time)) {
            return -1;
        }
        Date date;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }

        Calendar current = Calendar.getInstance();
        current.setTime(date);

        Calendar today = Calendar.getInstance(); // 今天
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Date todayData = today.getTime();
        long yesterDaySeconds = todayData.getTime() - DAY;
        Calendar yesterDay = Calendar.getInstance(); // 昨天
        yesterDay.setTimeInMillis(yesterDaySeconds);
        if (current.after(yesterDay)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * @param time
     * @param format 格式为yyyy*MM*
     * @return
     */
    public static int getMaximunDay(String time, String format) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            calendar.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

    }

    public static String translate(String src, String srcFormat, String desFormat) {
        if (TextUtils.isEmpty(src) || TextUtils.isEmpty(srcFormat) || TextUtils.isEmpty(desFormat)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(srcFormat);
        SimpleDateFormat sdfDes = new SimpleDateFormat(desFormat);
        Date srcDate;
        try {
            srcDate = sdf.parse(src);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return sdfDes.format(srcDate);
    }

    public static String getWeek(String src, String srcFormat) {
        if (TextUtils.isEmpty(src) || TextUtils.isEmpty(srcFormat)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(srcFormat);
        Date srcDate;
        try {
            srcDate = sdf.parse(src);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return getWeek(srcDate);
    }

    //根据日期取得星期几
    public static String getWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.CHINA);
        String week = sdf.format(date);
        return week;
    }

    /**
     * 日期转换成字符串
     *
     * @param date
     * @return str
     */
    public static String DateToStr(Date date, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        String str = format.format(date);
        return str;
    }


    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static Date strToDate(String str, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}

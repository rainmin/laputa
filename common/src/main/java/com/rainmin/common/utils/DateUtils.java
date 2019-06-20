package com.rainmin.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2017/7/27 0027.
 */

public class DateUtils {
    public static String getNowDateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("最后更新时间： yyyy-MM-dd HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

    public static String getCurrentDateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return formatter.format(Calendar.getInstance().getTime());
    }

    public static String getCurrentSimpleDateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return formatter.format(Calendar.getInstance().getTime());

    }

    /**
     * 将毫秒转为时分秒
     *
     * @param duration 毫秒数
     * @return 时分秒
     */
    public static String timeParse(long duration) {
        String time = "";
        long hour = duration / 3600000;

        long minute = (duration % 3600000) / 60000;
        if (hour < 10) {
            time += "0";
        }
        time += hour + "时";
        if (minute < 10) {
            time += "0";
        }
        time += minute + "分";
        return time;
    }

    /**
     * 将毫秒转为具体日期
     * @param millisecond
     * @return
     */
    public static String convert2Date(long millisecond) {
        try {
            Date date = new Date(millisecond);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            return formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

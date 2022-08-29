package com.itCar.base.tools;

import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: DateUtil 
 * @Description: TODO 时间日期工具
 * @author: liuzg
 * @Date: 2021/7/29 10:08
 * @Version: v1.0
 */
@Slf4j
public class DateUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取当前时间 + 输入的小时
     *
     * @param hourse
     * @return
     */
    public static String getDate(Integer hourse) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // 24小时制
        cal.add(Calendar.HOUR, hourse);
        return sdf.format(cal.getTime());
    }

    /**
     * 根据传入的日期加天数
     *
     * @param date
     * @param day
     * @return
     */
    public static String addDate(String date, Integer day) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(format.parse(date));
            cal.add(Calendar.DATE, day);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        return format.format(cal.getTime());
    }


    /**
     * 将Date对象转换为指定格式的字符串
     *
     * @param date   Date对象
     * @param format 格式化字符串
     * @return Date对象的字符串表达形式
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 根据日期获取星期
     * liuzhu
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static String daysBetween(String startDate, String endDate) throws ParseException {
        long nd = 1000 * 24 * 60 * 60;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置时间格式
        Date newStartDate = sdf.parse(startDate);
        Date newEndDate = sdf.parse(endDate);
        long diff = (newEndDate.getTime()) - (newStartDate.getTime()); //计算出毫秒差
        // 计算差多少天
        String day = diff / nd + "";
        return day;
    }
}

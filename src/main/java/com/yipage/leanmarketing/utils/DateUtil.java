package com.yipage.leanmarketing.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.util.*;

public class DateUtil {

    /**
     * 获取当前时间的0点时间和后一天的0点时间
     */
    public static Map<String, Date> getCurrentZeroTimeAndNextZeroTime() {

        Map<String, Date> dayMap = new HashMap<String, Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date one = calendar.getTime();
        dayMap.put("zero", zero);
        dayMap.put("one", one);
        return dayMap;
    }

    public static void main(String[] args) {
        //获取当前的时间戳
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        gc.add(2, 6);
        System.out.println(gc.getTime());
        System.out.println(getFetureDate(7));
        System.out.println(getCurrentZeroTimeAndNextZeroTime());
        System.out.println(getZeroTime(1));
    }

    /**
     * 获取未来 第 feture 天的日期
     *
     * @param feture
     * @return
     */
    public static Date getFetureDate(int feture) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + feture);
        Date fetureday = calendar.getTime();
        return fetureday;
    }

    /**
     * 获取某天的0点时间
     */
    public static Date getZeroTime(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 计算几天之后的时间
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addTime(Date date, Integer amount) {

        return DateUtils.addDays(date, amount);
    }


}

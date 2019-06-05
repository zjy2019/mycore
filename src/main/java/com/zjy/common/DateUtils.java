package com.zjy.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zjy
 * @des
 * @date 2019/6/4
 */
public class DateUtils {

    public static void main(String[] args) {

    }

    public static Date getToDayLastTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        return calendar.getTime();
    }

    public static Date getDateAddDays(Date cdate,int days){
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(cdate);
        if(days>0) {
            calendar.add(Calendar.DAY_OF_YEAR, days);
        }else{
            calendar.add(Calendar.DAY_OF_MONTH, days);
        }
        return calendar.getTime();
    }
    public static Date getDateAddHours(Date cdate,int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(cdate);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }
    public static Date getDateAddDays(int days){
        return  getDateAddDays(new Date(),days);
    }


    /**
     *
     * @param date
     * @param  "yyyy-MM-dd HH:mm:ss.SSS"
     * @return
     */
    public static String format(Date date,String dateFormat){
        return ((DateFormat)new SimpleDateFormat(dateFormat).clone()).format(date);
    }

    public static  Date formatFromStr  (String str,String dateformat) throws  Exception{
        SimpleDateFormat sdf=new SimpleDateFormat(dateformat);//小写的mm表示的是分钟
        return sdf.parse(str);
    }
    public static  String formatFromStr  (String str,String dateformat,String toformat) throws  Exception{
        SimpleDateFormat sdf=new SimpleDateFormat(dateformat);//小写的mm表示的是分钟
        return format(sdf.parse(str),toformat);
    }

    /**
     * Datefomat  yyyy-MM-dd HH:mm:ss.SSS
     * yyyy-MM-dd HH:mm:ss.SSS dateFormat
     * @return
     */
    public static String format(String dateFormat){
        return ((DateFormat)new SimpleDateFormat(dateFormat).clone()).format(new Date());
    }

    public static int getSecondTimestamp(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime()/1000);
        return Integer.valueOf(timestamp);
    }
    public static int getSecondTimestamp(){

        String timestamp = String.valueOf(System.currentTimeMillis()/1000);
        return Integer.valueOf(timestamp);
    }

    /**
     * Calendar.YEAR,  Calendar.HOUR_OF_DAY
     * @param p
     * @return
     */
    public static int getDateTimePart(int p) {
        if(Calendar.MONTH==p){
            return Calendar.getInstance().get(p)+1;
        }else {
            return Calendar.getInstance().get(p);
        }
    }

    /**
     *
     * @param date HOUR_OF_DAY
     * @param p
     * @return
     */
    public static int getDateTimePart(Date date, int p) {

        // 创建 Calendar 对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if(Calendar.MONTH==p){
            return calendar.get(p)+1;
        }else {
            return calendar.get(p);
        }
    }


}

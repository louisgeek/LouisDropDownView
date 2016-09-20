package com.louisgeek.dropdownviewlib.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by louisgeek on 2016/6/19.
 */
public class DateTool {

    public static final String FORMAT_DATE="yyyy-MM-dd";
    public static final String FORMAT_DATE_TIME="yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_DATA="1970-01-01";
    /**
     * 取当前China日期
     * @return
     */
    public static String getChinaDate() {
        return parseDate2Str(new Date(),FORMAT_DATE);//取当前时间
    }
    /**
     * 取当前China时间
     * @return
     */
    public static String getChinaDateTime() {
        return parseDate2Str(new Date(),FORMAT_DATE_TIME);//取当前时间
    }

    /**
     * 多参数 Calendar 获取日期
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    public static String getChinaDateFromCalendar(int year,int monthOfYear,int dayOfMonth) {
        SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_DATE, Locale.CHINA);//Locale.SIMPLIFIED_CHINESE和Locale.CHINA一样
        //new Data(int,int,int)过时了
        GregorianCalendar calendar = new GregorianCalendar(year,monthOfYear,dayOfMonth);//初始具有指定年月日的公历类对象。
        Long timeInMillis = calendar.getTimeInMillis();
        return sdf.format(timeInMillis);//new Data()//取当前时间
    }
    /**
     * 多参数 Calendar 获取时间
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    public static String getChinaDateTimeFromCalendar(int year,int monthOfYear,int dayOfMonth) {
        SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_DATE_TIME, Locale.CHINA);//Locale.SIMPLIFIED_CHINESE和Locale.CHINA一样
        //new Data(int,int,int)过时了
        GregorianCalendar calendar = new GregorianCalendar(year,monthOfYear,dayOfMonth);//初始具有指定年月日的公历类对象。
        Long timeInMillis = calendar.getTimeInMillis();
        return sdf.format(timeInMillis);//new Data()//取当前时间
    }


    //不推荐  好用的
    @Deprecated
    public static String getZhongGuoTime(){
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE_TIME);
        String times = format.format(new Date());
        System.out.print("日期格式---->" + times);
        return times;
    }


    public static Date parseStr2Date(String dateStr,String formatStr){
        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr, Locale.CHINA);//Locale.SIMPLIFIED_CHINESE和Locale.CHINA一样
        try {
            date=sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  date;
    }

    public static String parseDate2Str(Date date,String formatStr) {
        SimpleDateFormat sdf=new SimpleDateFormat(formatStr, Locale.CHINA);//Locale.SIMPLIFIED_CHINESE和Locale.CHINA一样
        return sdf.format(date);
    }



    public static String parseCalendar2Str(Calendar calendar,String formatStr){
        Date date=parseCalendar2Date(calendar);
        return parseDate2Str(date,formatStr);
    }


    public static Calendar parseStr2Calendar(String dateStr,String formatStr){
        Date date=parseStr2Date(dateStr,formatStr);
        return  parseDate2Calendar(date);
    }

    public static Date parseCalendar2Date(Calendar calendar){
        if (calendar==null){
            calendar= Calendar.getInstance();
        }
        Date date=calendar.getTime();
        return  date;
    }

    public static Calendar parseDate2Calendar(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return  calendar;
    }

      public static boolean canParseToDate(String stringMayBeDate){
            if (stringMayBeDate==null){
                return false;
            }
            Date date;
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE, Locale.CHINA);//Locale.SIMPLIFIED_CHINESE和Locale.CHINA一样
            try {
                date=sdf.parse(stringMayBeDate);
            } catch (Exception e) {
                return false;
            }
            return date!=null;
        }
    public static boolean canParseToDateTime(String stringMayBeDateTime){
        if (stringMayBeDateTime==null){
            return false;
        }
        Date date;
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_TIME, Locale.CHINA);//Locale.SIMPLIFIED_CHINESE和Locale.CHINA一样
        try {
            date=sdf.parse(stringMayBeDateTime);
        } catch (Exception e) {
            return false;
        }
        return date!=null;
    }

    public static String dealMaybeCanParseDateOrDefaultDateOrJustBackSource(String stringDateMayBe){
        String temp;
        if (DateTool.canParseToDateTime(stringDateMayBe)||DateTool.canParseToDate(stringDateMayBe)){
            //格式化时间
            temp=DateTool.parseDate2Str(DateTool.parseStr2Date(stringDateMayBe,DateTool.FORMAT_DATE),DateTool.FORMAT_DATE);
            //默认时间
            if(temp.equals(DEFAULT_DATA)){
                temp="暂无";
            }
        }else {
            temp=stringDateMayBe;
        }
        return  temp;

    }
}

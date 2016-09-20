package com.louisgeek.dropdownviewlib.tools;

import android.graphics.Paint;

/**
 * Created by louisgeek on 2016/8/25.
 */
public class StringTool {

    /**
     * all
     * @param str
     * @return
     */
    public static boolean isNullOrNullStrOrBlankStr(String str) {
        return (isNullOrNullStr(str)||isNullOrBlankStr(str));
    }

    public static boolean isNullOrBlankStr(String str) {
        return (str == null || str.trim().length() == 0||str.trim().equals(""));
    }

    public static boolean isNullOrNullStr(String str) {
        return (str == null || "null".equals(str.toLowerCase()));
    }

    public static String getNotNullStr(String str,String defaultBackStr) {
        String strTemp="";
        if (defaultBackStr!=null){
            strTemp=defaultBackStr;
        }
        strTemp=isNullOrNullStrOrBlankStr(str)?strTemp:str;
        return strTemp;
    }
    public static String getNotNullStr(String str) {
        return getNotNullStr(str,null);
    }

    public static int getAllTextWidth(String text, float textSize) {
        if (null == text || "".equals(text)){
            return 0;
        }
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        int text_width = (int) paint.measureText(text);// 得到总体长度
        return text_width;
    }
    public static int getEveryCharWidthInAllText(String text, float textSize) {
        int width = getAllTextWidth(text,textSize)/text.length();//每一个字符的长度
        return width;
    }
}

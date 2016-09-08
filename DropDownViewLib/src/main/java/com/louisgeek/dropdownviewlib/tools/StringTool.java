package com.louisgeek.dropdownviewlib.tools;

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

}

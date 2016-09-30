package com.louisgeek.dropdownviewlib.tools;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.louisgeek.dropdownviewlib.R;
import com.louisgeek.dropdownviewlib.javabean.Area;
import com.louisgeek.dropdownviewlib.javabean.City;
import com.louisgeek.dropdownviewlib.javabean.ProCate;
import com.louisgeek.dropdownviewlib.javabean.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by louisgeek on 2016/6/16.
 */
public class MySSQTool {
    public static List<Province> parseJson(String ssq_json) {
        List<Province> provinceList = new ArrayList<>();
        try {
            //
            JSONObject jsonObject = new JSONObject(ssq_json);
            JSONObject o_provinces = jsonObject.getJSONObject("provinces");
            JSONArray a_province = o_provinces.getJSONArray("province");
            //===

            for (int i = 0; i < a_province.length(); i++) {
                JSONObject o_province = (JSONObject) a_province.get(i);
                String ssqid = o_province.getString("ssqid");
                String ssqname = o_province.getString("ssqname");
                String ssqename = o_province.getString("ssqename");
                JSONObject o_cities = o_province.getJSONObject("cities");
                JSONArray a_city = o_cities.getJSONArray("city");
                //===
                List<City> cityList = new ArrayList<>();
                for (int j = 0; j < a_city.length(); j++) {
                    //===
                    JSONObject o_city = (JSONObject) a_city.get(j);
                    String ssqid_C = o_city.getString("ssqid");
                    String ssqname_C = o_city.getString("ssqname");
                    String ssqename_C = o_city.getString("ssqename");
                    JSONObject o_areas_C = o_city.getJSONObject("areas");
                    JSONArray a_area = o_areas_C.getJSONArray("area");
                    //===
                    List<Area> areaList = new ArrayList<>();
                    for (int k = 0; k < a_area.length(); k++) {
                        JSONObject O_area = (JSONObject) a_area.get(k);
                        String ssqid_A = O_area.getString("ssqid");
                        String ssqname_A = O_area.getString("ssqname");
                        String ssqename_A = O_area.getString("ssqename");
                        //===
                        Area area = new Area(ssqid_A, ssqname_A, "简称", ssqename_A);
                        areaList.add(area);
                    }
                    //===
                    City city = new City(ssqid_C, ssqname_C, "简称", ssqename_C, areaList);
                    cityList.add(city);
                }
                //===
                Province province = new Province(ssqid, ssqname, "简称", ssqename, cityList);
                provinceList.add(province);
            }

            Log.d("XXX", "LOUIS" + provinceList.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("XXX", "LOUIS" + e.getMessage());
        }
        return provinceList;
    }

    /**
     * getStringFromRaw
     * @param rawID
     * @return
     */
    public static String getStringFromRaw(Context context,int rawID) {
        StringBuilder sb_result = new StringBuilder("");
        try {
            InputStream ssq_is = context.getResources().openRawResource(rawID);

            InputStreamReader inputReader = new InputStreamReader(ssq_is);
            //InputStreamReader inputReader = new InputStreamReader(ssq_is,"UTF-8");
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
            { sb_result.append(line);}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb_result.toString();
    }

    /**
     * 很多行 会卡死
     * @param context
     * @param rawID
     * @return
     */
    @Deprecated
    public static String getStringFromRawOld(Context context,int rawID) {
        String result = "";
        try {
            InputStream ssq_is = context.getResources().openRawResource(rawID);

            InputStreamReader inputReader = new InputStreamReader(ssq_is);
            //InputStreamReader inputReader = new InputStreamReader(ssq_is,"UTF-8");
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
            {result += line;}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getCateNameByCateID(Context context,String proCateID){
        String proCateName="";
    String pro_cate_json = getStringFromRaw(context, R.raw.pro_cate);
    ProCate proCate= JSON.parseObject(pro_cate_json,ProCate.class);
    List<ProCate.CatesBean>  proCateCatesBeanList=proCate.getCates();
    for (int i = 0; i <proCateCatesBeanList.size() ; i++) {
        ProCate.CatesBean pccb=proCateCatesBeanList.get(i);
        if (("0_"+proCateID).equals(pccb.getCateid())){
            proCateName=pccb.getCatename();
            break;
        }
        String[] nowProcateIDArray=pccb.getCateid().split("_");
        if (nowProcateIDArray!=null&&nowProcateIDArray.length>1)
        {
         String   nowParentID=nowProcateIDArray[1];
        List<ProCate.CatesBean.ChildrenBean> childrenBeans=pccb.getChildren();
        for (int j = 0; j < childrenBeans.size(); j++) {
            if ((nowParentID+"_"+proCateID).equals( childrenBeans.get(j).getCateid())){
                proCateName=pccb.getCatename();
                break;
            }
        }}
    }
        return  proCateName;

}

    public static String getProvinceCityAreaNameStrByOnlyAreaID(Context context,String areaKey){
        if (areaKey==null||areaKey.trim().equals("")||areaKey.trim().toLowerCase().equals("null")){
            return "";
        }
        String ssqJson = getStringFromRaw(context, R.raw.ssq);
        // String ssqJson = getStringFromRawOld(context, R.raw.ssq);
        List<Province> provinceList = parseJson(ssqJson);
        //
        String province = "";
        String city = "";
        String area = "";
        //
        String provinceID = areaKey.substring(0, 2) + "0000";//从0开始数，其中不包括endIndex位置的字符
        String cityID = areaKey.substring(0, 4) + "00";
        String areaID = areaKey;

        int nowProvinceIndex=0;
        for (int i = 0; i <provinceList.size() ; i++) {
            if (provinceID.equals(provinceList.get(i).getProvinceID())){
                province=provinceList.get(i).getProvinceName();
                nowProvinceIndex=i;
                break;
            }
        }
        int nowCityIndex=0;
        for (int i = 0; i <provinceList.get(nowProvinceIndex).getCites().size() ; i++) {
            if (cityID.equals(provinceList.get(nowProvinceIndex).getCites().get(i).getCityID())){
                city=provinceList.get(nowProvinceIndex).getCites().get(i).getCityName();
                nowCityIndex=i;
                break;
            }
        }
        for (int i = 0; i <provinceList.get(nowProvinceIndex).getCites().get(nowCityIndex).getAreas().size() ; i++) {
            if (areaID.equals(provinceList.get(nowProvinceIndex).getCites().get(nowCityIndex).getAreas().get(i).getAreaID())){
                area=provinceList.get(nowProvinceIndex).getCites().get(nowCityIndex).getAreas().get(i).getAreaName();
                break;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        if (province != null && !province.equals("")) {
            stringBuilder.append(province);
            stringBuilder.append("-");
        }
        if (city != null && !city.equals("")) {
            stringBuilder.append(city);
            stringBuilder.append("-");
        }
        if (area != null && !area.equals("")) {
            stringBuilder.append(area);
        }
        String temp=stringBuilder.toString();
        return temp.equals("--")?"":temp;
    }

    public static String getProvinceCityAreaNameStrByOnlyAreaIDWithOutFix(Context context,String areaKey){
        return  getProvinceCityAreaNameStrByOnlyAreaID(context,areaKey).replace("-","");
    }
}

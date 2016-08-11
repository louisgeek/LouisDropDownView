package com.louisgeek.dropdownviewlib.tools;

import android.content.Context;
import android.util.Log;

import com.louisgeek.dropdownviewlib.javabean.Area;
import com.louisgeek.dropdownviewlib.javabean.City;
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
        String result = "";
        try {
            InputStream ssq_is = context.getResources().openRawResource(rawID);

            InputStreamReader inputReader = new InputStreamReader(ssq_is);
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
                result += line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

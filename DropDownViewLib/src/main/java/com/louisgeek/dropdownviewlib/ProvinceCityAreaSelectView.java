package com.louisgeek.dropdownviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by louisgeek on 2016/6/12.
 */
public class ProvinceCityAreaSelectView extends LinearLayout{
    Context mContext;
    DropDownView id_ddv_province;
    DropDownView id_ddv_city;
    DropDownView id_ddv_area;
    List<Province> provinceList;
    int nowProvincePos=0;

    String nowProvinceText;
    String nowCityText;
    String nowAreaText;

    String ssq_json;//省市区json

    TextView  id_areas_all_in;
    private static final String TAG = "ProvinceCityArea";
    public ProvinceCityAreaSelectView(Context context) {
        super(context);
        init(context);
        Log.d(TAG, "DropDownView: Context context");
    }

    public ProvinceCityAreaSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.MyProvinceCityAreaSelectView);
        nowProvinceText = typedArray.getString(R.styleable.MyProvinceCityAreaSelectView_province);
        nowCityText = typedArray.getString(R.styleable.MyProvinceCityAreaSelectView_province);
        nowAreaText = typedArray.getString(R.styleable.MyProvinceCityAreaSelectView_province);

        typedArray.recycle();
        init(context);
        Log.d(TAG, "DropDownView: Context context, AttributeSet attrs");
    }

    public ProvinceCityAreaSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        Log.d(TAG, "DropDownView: Context context, AttributeSet attrs, int defStyleAttr");
    }




    private void init(Context context) {
        mContext=context;
        //View view=View.inflate(context, R.layout.layout_provincecityarea_selectview, this);
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_provincecityarea_selectview,this);
        id_ddv_province= (DropDownView) view.findViewById(R.id.id_province);
        id_ddv_city= (DropDownView) view.findViewById(R.id.id_city);
        id_ddv_area= (DropDownView) view.findViewById(R.id.id_area);
        id_areas_all_in=(TextView) view.findViewById(R.id.id_areas_all_in);

        id_ddv_province.setTextMy(nowProvinceText);
        id_ddv_city.setTextMy(nowCityText);
        id_ddv_area.setTextMy(nowAreaText);


        initData();
        initDropDownView();

    }

    private void  initDropDownView(){

        List<Map<String, Object>> nameStateList=new ArrayList<>();
        for (int i = 0; i <provinceList.size() ; i++) {
            Map<String, Object> map=new HashMap<>();
            map.put("name",provinceList.get(i).getProvinceName());
            map.put("index",i);
            map.put("ssqid",provinceList.get(i).getProvinceID());
            nameStateList.add(map);
        }
        id_ddv_province.setupNameStateList(nameStateList);
        id_ddv_province.setOnItemClickListener(new DropDownView.OnItemClickListener() {
            @Override
            public void onItemClick(Map<String, Object> map) {
                nowProvincePos= Integer.parseInt(map.get("index").toString());
                initInnerCity(nowProvincePos);
                initInnerArea(nowProvincePos,0);
            }
        });
        //##initInnerCity(0);
        //##initInnerArea(0,0);
    }
    private void initInnerCity(int province_pos) {
        id_ddv_city.setText(id_ddv_city.getDefaultText());
        List<Map<String, Object>> nameStateList_city=new ArrayList<>();
        for (int i = 0; i <provinceList.get(province_pos).getCites().size(); i++) {
            Map<String, Object> map=new HashMap<>();
            map.put("name",provinceList.get(province_pos).getCites().get(i).getCityName());
            map.put("index",i);
            map.put("ssqid",provinceList.get(province_pos).getCites().get(i).getCityID());
            nameStateList_city.add(map);
        }
        id_ddv_city.setupNameStateList(nameStateList_city);
        id_ddv_city.setOnItemClickListener(new DropDownView.OnItemClickListener() {
            @Override
            public void onItemClick(Map<String, Object> map) {
                initInnerArea(nowProvincePos,Integer.parseInt(map.get("index").toString()));
            }
        });
    }

    private void initInnerArea(int province_pos,int city_pos) {
        id_ddv_area.setText(id_ddv_city.getDefaultText());
        List<Map<String, Object>> nameStateList_area=new ArrayList<>();
        for (int i = 0; i <provinceList.get(province_pos).getCites().get(city_pos).getAreas().size(); i++) {
            Map<String, Object> map=new HashMap<>();
            map.put("name",provinceList.get(province_pos).getCites().get(city_pos).getAreas().get(i).getAreaName());
            map.put("index",i);
            map.put("ssqid",provinceList.get(province_pos).getCites().get(city_pos).getAreas().get(i).getAreaID());
            nameStateList_area.add(map);
        }
        id_ddv_area.setupNameStateList(nameStateList_area);
    }


    public String getStringFromRaw(int rawID) {
        String result = "";
        try {
            InputStream ssq_is = getResources().openRawResource(rawID);

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


    private void initData() {

        try {
            ssq_json=getStringFromRaw(R.raw.ssq);
            //
            JSONObject jsonObject = new JSONObject(ssq_json);
            JSONObject o_provinces = jsonObject.getJSONObject("provinces");
            JSONArray a_province = o_provinces.getJSONArray("province");
            //===
            provinceList = new ArrayList<>();
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
    }

    public void setupProvinceCityArea(String province,String city,String area){
        nowProvinceText=province;
        nowCityText=city;
        nowAreaText=area;

        id_areas_all_in.setVisibility(GONE);

        id_ddv_province.setVisibility(VISIBLE);
        id_ddv_city.setVisibility(VISIBLE);
        id_ddv_area.setVisibility(VISIBLE);

        id_ddv_province.setTextMy(nowProvinceText);
        id_ddv_city.setTextMy(nowProvinceText);
        id_ddv_area.setTextMy(nowProvinceText);
    }
    public void setupProvinceCityAreaAll(String provincecityare_all_in){

        id_areas_all_in.setVisibility(VISIBLE);
        id_areas_all_in.setText(provincecityare_all_in);

        id_ddv_province.setVisibility(GONE);
        id_ddv_city.setVisibility(GONE);
        id_ddv_area.setVisibility(GONE);
    }

    public String getProvinceCityArea(){
        String ssq_sheng_id= (String) id_ddv_province.getTag(R.id.hold_dropdown_id);
        String ssq_shi_id= (String)  id_ddv_province.getTag(R.id.hold_dropdown_id);
        String ssq_qu_id= (String) id_ddv_province.getTag(R.id.hold_dropdown_id);
        if (ssq_qu_id!=null&&!ssq_qu_id.equals(""))
        {
            return  ssq_qu_id;
        }else if (ssq_shi_id!=null&&!ssq_shi_id.equals("")){
            return ssq_shi_id;
        }else if (ssq_sheng_id!=null&&!ssq_sheng_id.equals("")){
            return ssq_sheng_id;
        }else{
            return "";
        }
    }
}

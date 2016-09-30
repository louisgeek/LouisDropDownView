package com.louisgeek.dropdownviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.louisgeek.dropdownviewlib.javabean.Province;
import com.louisgeek.dropdownviewlib.tools.MySSQTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by louisgeek on 2016/6/12.
 */
public class ProvinceCityAreaSelectView extends LinearLayout {
    private Context mContext;
    private DropDownView id_ddv_province;
    private DropDownView id_ddv_city;
    private DropDownView id_ddv_area;
    private List<Province> provinceList;
    // private int nowProvincePos=0;

    private final String ALL_AREA_KEY_DEFAULT = "000000"; // see in ssq.json
    private String allAreaKey;
//    String nowCityText;
//    String nowAreaText;

    private String ssq_json;//省市区json

    private TextView id_areas_all_in_textview;

    private int nowProvince_index_setup = 0;
    private int nowCity_index_setup = 0;
    private static final String TAG = "ProvinceCityArea";
    private boolean isShowArea;
    public ProvinceCityAreaSelectView(Context context) {
        super(context);
        init(context);
        Log.d(TAG, "DropDownView: Context context");
    }

    public ProvinceCityAreaSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ProvinceCityAreaSelectView);
        allAreaKey = typedArray.getString(R.styleable.ProvinceCityAreaSelectView_allAreaKey);//like "000000"  see in ssq.json
        isShowArea=typedArray.getBoolean(R.styleable.ProvinceCityAreaSelectView_isShowArea,true);
        if (allAreaKey == null) {
            allAreaKey = ALL_AREA_KEY_DEFAULT;
        }

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
//        if (this.isInEditMode()) { return; }
        mContext = context;
        //View view=View.inflate(context, R.layout.layout_provincecityarea_selectview, this);
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_provincecityarea_selectview, this);
        id_ddv_province = (DropDownView) view.findViewById(R.id.id_province);
        id_ddv_city = (DropDownView) view.findViewById(R.id.id_city);
        id_ddv_area = (DropDownView) view.findViewById(R.id.id_area);
        id_areas_all_in_textview = (TextView) view.findViewById(R.id.id_areas_all_in_textview);


        //
        initData();

        initDropDownView();

        if (!allAreaKey.equals(ALL_AREA_KEY_DEFAULT)) {
            setupProvinceCityAreaByKey(allAreaKey);
        }
    }

    private void initDropDownView() {

        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < provinceList.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", provinceList.get(i).getProvinceName());
            map.put("key", provinceList.get(i).getProvinceID());
            dataList.add(map);
        }
        id_ddv_province.setupDataList(dataList);
        id_ddv_province.setOnItemClickListener(new DropDownView.OnItemClickListener() {
            @Override
            public void onItemClick(Map<String, Object> map, int pos, int realPos) {

                initInnerCity(realPos);
            }
        });

        ///### initInnerCity(0);

    }

    private void initInnerCity(final int province_pos) {
        id_ddv_city.setText(id_ddv_city.getDefaultText());
        Log.i(TAG, "initInnerCity: province_pos:" + province_pos);
        List<Map<String, Object>> dataList_city = new ArrayList<>();
        if (province_pos >= 0) {
            for (int i = 0; i < provinceList.get(province_pos).getCites().size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", provinceList.get(province_pos).getCites().get(i).getCityName());
                map.put("key", provinceList.get(province_pos).getCites().get(i).getCityID());
                dataList_city.add(map);
            }
        } else {
            //省未选择时  清空区  附加  2016年9月10日16:07:10
            initInnerArea(province_pos, 0);
        }
        id_ddv_city.setupDataList(dataList_city);
        id_ddv_city.setOnItemClickListener(new DropDownView.OnItemClickListener() {
            @Override
            public void onItemClick(Map<String, Object> map, int pos, int realPos) {

                initInnerArea(province_pos, realPos);

            }
        });

        //### initInnerArea(province_pos, 0);
    }

    private void initInnerArea(int province_pos, int city_pos) {
        id_ddv_area.setText(id_ddv_area.getDefaultText());
        Log.i(TAG, "initInnerCity: province_pos:" + province_pos);
        Log.i(TAG, "initInnerArea: city_pos:" + city_pos);
        List<Map<String, Object>> dataList_area = new ArrayList<>();
        if (province_pos >= 0 && city_pos >= 0) {
            for (int i = 0; i < provinceList.get(province_pos).getCites().get(city_pos).getAreas().size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", provinceList.get(province_pos).getCites().get(city_pos).getAreas().get(i).getAreaName());
                map.put("key", provinceList.get(province_pos).getCites().get(city_pos).getAreas().get(i).getAreaID());
                dataList_area.add(map);
            }
        }
        id_ddv_area.setupDataList(dataList_area);
        if (isShowArea){
            id_ddv_area.setVisibility(VISIBLE);
        }else
        {
            id_ddv_area.setVisibility(GONE);
        }

    }


    private void initData() {
        ssq_json = MySSQTool.getStringFromRaw(getContext(), R.raw.ssq);
        provinceList = MySSQTool.parseJson(ssq_json);

    }



  /*  public void setupProvinceCityArea(String province,String city,String area){
        nowProvinceText=province;
        nowCityText=city;
        nowAreaText=area;

        id_areas_all_in_textview.setVisibility(GONE);

        id_ddv_province.setVisibility(VISIBLE);
        id_ddv_city.setVisibility(VISIBLE);
        id_ddv_area.setVisibility(VISIBLE);

        id_ddv_province.setSelectName(nowProvinceText);
        if (!("").equals(nowCityText)){
            id_ddv_city.setSelectName(nowCityText);
            //
            if (!("").equals(nowAreaText)){
                id_ddv_area.setSelectName(nowAreaText);
            }else{
                initInnerArea(nowProvince_index_setup,nowCity_index_setup);
            }
        }else{
            initInnerCity(nowProvince_index_setup);
        }
    }*/

    public void setupProvinceCityArea(String provinceName, String cityName, String areaName) {
        if (TextUtils.isEmpty(provinceName) || TextUtils.isEmpty(cityName) || TextUtils.isEmpty(areaName)) {
            return;
        }
        setupProvinceCityAreaByKeyInner(id_ddv_province.getKeyByName(provinceName), id_ddv_city.getKeyByName(cityName), id_ddv_area.getKeyByName(areaName));
    }

    public void setupProvinceCityAreaByKey(String onlyAreaid) {
        if (onlyAreaid == null || ("").equals(onlyAreaid)) {
            return;
        }
        String provinceID = onlyAreaid.substring(0, 2) + "0000";//从0开始数，其中不包括endIndex位置的字符
        String cityID = onlyAreaid.substring(0, 4) + "00";
        String areaID = onlyAreaid;

        setupProvinceCityAreaByKeyInner(provinceID, cityID, areaID);
    }

    private void setupProvinceCityAreaByKeyInner(String provinceid, String cityid, String areaid) {
        int nowProvincePos = id_ddv_province.getPositionByKey(provinceid);
        nowProvincePos = nowProvincePos == -1 ? 0 : nowProvincePos;
        initInnerCity(nowProvincePos);

        int nowCityPos = id_ddv_city.getPositionByKey(cityid);
        nowCityPos = nowCityPos == -1 ? 0 : nowCityPos;
        initInnerArea(nowProvincePos, nowCityPos);


        id_ddv_province.setSelectNameByKey(provinceid);
        id_ddv_city.setSelectNameByKey(cityid);
        id_ddv_area.setSelectNameByKey(areaid);
    }
  /*  public void setupProvinceCityAreaByID(String provinceid,String cityid,String areaid){
        String province="";
        String city="";
        String area="";



        for (int i = 0; i <provinceList.size() ; i++) {
            if (provinceid.equals(provinceList.get(i).getProvinceID())){
                province=provinceList.get(i).getProvinceName();
                nowProvince_index_setup=i;
                break;
            }
        }

        for (int i = 0; i <provinceList.get(nowProvince_index_setup).getCites().size() ; i++) {
            if (cityid.equals(provinceList.get(nowProvince_index_setup).getCites().get(i).getCityID())){
                city=provinceList.get(nowProvince_index_setup).getCites().get(i).getCityName();
                nowCity_index_setup=i;
                break;
            }
        }

        for (int i = 0; i <provinceList.get(nowProvince_index_setup).getCites().get(nowCity_index_setup).getAreas().size() ; i++) {
            if (areaid.equals(provinceList.get(nowProvince_index_setup).getCites().get(nowCity_index_setup).getAreas().get(i).getAreaID())){
                area=provinceList.get(nowProvince_index_setup).getCites().get(nowCity_index_setup).getAreas().get(i).getAreaName();
                break;
            }
        }

        setupProvinceCityArea(province,city,area);
    }*/

    /**
     * @param provincecityare_all_in_textview
     */
    public void setupProvinceCityAreaAllInTextView(String provincecityare_all_in_textview) {

        id_areas_all_in_textview.setVisibility(VISIBLE);
        id_areas_all_in_textview.setText(provincecityare_all_in_textview);

        id_ddv_province.setVisibility(GONE);
        id_ddv_city.setVisibility(GONE);
        id_ddv_area.setVisibility(GONE);
    }

    public String getProvinceCityAreaNameStr(){
        String province = id_ddv_province.getSelectName();
        String city = id_ddv_city.getSelectName();
        String area = id_ddv_area.getSelectName();
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
    public String getProvinceCityAreaNameStrWithOutFix(){
        return  getProvinceCityAreaNameStr().replace("-","");
    }

    public String getProvinceCityAreaKey() {
        String province_id = id_ddv_province.getSelectKey();
        String city_id = id_ddv_city.getSelectKey();
        String area_id = id_ddv_area.getSelectKey();
        if (area_id != null && !area_id.equals("") && !area_id.equals("-1")) {
            return area_id;
        } else if (city_id != null && !city_id.equals("") && !city_id.equals("-1")) {
            return city_id;
        } else if (province_id != null && !province_id.equals("") && !province_id.equals("-1")) {
            return province_id;
        } else {
            return ALL_AREA_KEY_DEFAULT;
        }
    }
 /*   public String getProvinceCityAreaKey(){
        String province_id= (String) id_ddv_province.getTag(R.id.hold_dropdown_key);
        String city_id= (String)  id_ddv_city.getTag(R.id.hold_dropdown_key);
        String area_id= (String) id_ddv_area.getTag(R.id.hold_dropdown_key);

        if (area_id!=null&&!area_id.equals(""))
        {
            return  area_id;
        }else if (city_id!=null&&!city_id.equals("")){
            return city_id;
        }else if (province_id!=null&&!province_id.equals("")){
            return province_id;
        }else{
            return ALL_AREA_KEY_DEFAULT;
        }
    }*/
}

package com.louisgeek.dropdownviewlib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.louisgeek.dropdownviewlib.javabean.ProCate;
import com.louisgeek.dropdownviewlib.tools.MySSQTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by louisgeek on 2016/6/28.
 */
public class ProCateSelectView extends LinearLayout{
    private  Context mContext;
    DropDownView id_parent_pro;
    DropDownView  id_child_pro;
    String pro_cate_json;
    List<ProCate.CatesBean> proCate_CatesBeanList;
    public ProCateSelectView(Context context) {
        this(context,null);
    }

    public ProCateSelectView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProCateSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        mContext=context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_procate_selectview, this);
         id_parent_pro = (DropDownView) view.findViewById(R.id.id_parent_pro);
          id_child_pro = (DropDownView) view.findViewById(R.id.id_child_pro);

        initData();
        initDropDownView();
    }

    private void initData() {
        pro_cate_json = MySSQTool.getStringFromRaw(getContext(), R.raw.pro_cate);
        ProCate proCate= JSON.parseObject(pro_cate_json,ProCate.class);

        proCate_CatesBeanList=proCate.getCates();
    }

    private void initDropDownView() {

        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < proCate_CatesBeanList.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", proCate_CatesBeanList.get(i).getCatename());
            map.put("key", proCate_CatesBeanList.get(i).getCateid());
            dataList.add(map);
        }
        id_parent_pro.setupDataList(dataList);
        id_parent_pro.setOnItemClickListener(new DropDownView.OnItemClickListener() {
            @Override
            public void onItemClick(Map<String, Object> map, int pos,int realPos) {

                initInnerChild(realPos);
            }
        });

        ///###initInnerChild(0);

    }

    private void initInnerChild(int parent_pos) {
        id_child_pro.setText(id_child_pro.getDefaultText());
        List<Map<String, Object>> dataList_area = new ArrayList<>();
        if (parent_pos>=0){
        for (int i = 0; i < proCate_CatesBeanList.get(parent_pos).getChildren().size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", proCate_CatesBeanList.get(parent_pos).getChildren().get(i).getCatename());
            map.put("key", proCate_CatesBeanList.get(parent_pos).getChildren().get(i).getCateid());
            dataList_area.add(map);
        }
        }
        id_child_pro.setupDataList(dataList_area);
    }

    @Deprecated
   public void setupProCateByKey(String key){
       String parentkey="";
       String childkey="";
       if(key!=null&&!key.equals("")) {
           if (key.startsWith("0_")) {//只有父类
               parentkey=key;
           } else {
               childkey=key;
               String[] Keys=key.split("_");
               if (Keys!=null&&Keys.length>0){
                   parentkey="0_"+Keys[0];
               }
           }
           id_parent_pro.setSelectNameByKey(parentkey);
           int parent_pos= id_parent_pro.getPositionByKey(parentkey);
           if (parent_pos>0) {
               initInnerChild(parent_pos);
             if (!childkey.equals("")) {
               id_child_pro.setSelectNameByKey(childkey);
           }
           }
       }
   }
    public void setupProCateByKey(String OneKey,String TwoKey){
        String parentkey="";
        String childkey="";
        if(OneKey!=null&&!OneKey.equals("")) {
                parentkey="0_"+OneKey;
        }
        if(TwoKey!=null&&!TwoKey.equals("")) {
            childkey=OneKey+"_"+TwoKey;
        }
            id_parent_pro.setSelectNameByKey(parentkey);
            int parent_pos= id_parent_pro.getPositionByKey(parentkey);
            if (parent_pos>-1) {
                initInnerChild(parent_pos);
                if (!childkey.equals("")) {
                    id_child_pro.setSelectNameByKey(childkey);
                }
            }
       //
    }

    public String  getProCateKey(){
        String parent_key = id_parent_pro.getSelectKey();
        String child_key = id_child_pro.getSelectKey();
        if (child_key != null && !child_key.equals("")&&!child_key.equals("-1")) {
            return child_key;
        } else if (parent_key != null && !parent_key.equals("")&&!parent_key.equals("-1")) {
            return parent_key;
        } else {
            return "0_0";
        }
    }
    public String  getProCateNameOnlyChild(){
      //  String parent_key = id_parent_pro.getSelectKey();
        String child_name = id_child_pro.getSelectName();
        if (child_name != null && !child_name.equals("")) {
            return child_name;
        } else {
            return "";
        }
    }
    public int  getProCateKeyOnlyChildId(){
        int childId=0;
        String proCateKey=getProCateKey();
        if (proCateKey!=null){
        if (proCateKey.contains("_")){
            String[] proCateKeys=proCateKey.split("_");
            if (proCateKeys!=null&&proCateKeys.length>0){
                if (proCateKeys.length>1&&proCateKeys[1]!=null){
                childId=Integer.valueOf(proCateKeys[1]);
                }
            }
        }
        }
        return  childId;
    }
}

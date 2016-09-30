package com.louisgeek.dropdownviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.louisgeek.dropdownviewlib.adapter.MultiSelectViewRecycleViewAdapter;
import com.louisgeek.dropdownviewlib.javabean.MultiSelectHasChildBean;
import com.louisgeek.dropdownviewlib.javabean.ProCate;
import com.louisgeek.dropdownviewlib.tools.MySSQTool;
import com.louisgeek.dropdownviewlib.tools.SizeTool;
import com.louisgeek.dropdownviewlib.ui.MutiSelectDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by louisgeek on 2016/6/20.
 */
public class MultiSelectView_HasChild extends LinearLayout implements View.OnClickListener {
    private  Context mContext;
    RecyclerView mRecyclerView;
    LinearLayout id_ll_MultiSelectView;
    MultiSelectViewRecycleViewAdapter mMultiSelectViewRecycleViewAdapter;
    public static final int  ONLY_SHOW_COLUMNS_DEFAULT=2;
    private  int  nowShowColumns;
    public static final int  ONLY_SHOW_ROWS=2;
    private static final String TAG = "MultiSelectView_Child";
    /**
     * 设置选项
     * @param multiSelectHasChildBeanList
     */
    public void setupMultiSelectHasChildBeanListOutter(List<MultiSelectHasChildBean> multiSelectHasChildBeanList) {
        //
        mMultiSelectHasChildBeanList=multiSelectHasChildBeanList;
        mMultiSelectMapListInAll=this.getMultiSelectMapListInAll(multiSelectHasChildBeanList);
        //
        mMultiSelectViewRecycleViewAdapter.updateDate(mMultiSelectMapListInAll);
        dealLieAndHeight();
    }
    //##
    /**
     * 取到所有（包括未选择）
     * @return
     */
    private List<Map<String, Object>> getMultiSelectMapListInAll( List<MultiSelectHasChildBean>  multiSelectMapListInnerGroupAndChildBeanListOutter){
        List<MultiSelectHasChildBean> multiSelectMapListInnerGroupAndChildBeanList=multiSelectMapListInnerGroupAndChildBeanListOutter;

        List<Map<String, Object>> selectMapListInAllTemp=new ArrayList<>();
        for (int j = 0; j < multiSelectMapListInnerGroupAndChildBeanList.size(); j++) {
            List<Map<String, Object>> multiSelectMapList=multiSelectMapListInnerGroupAndChildBeanList.get(j).getMultiSelectMapList();

            for (int i = 0; i < multiSelectMapList.size(); i++) {
                ////if(multiSelectMapList.get(i).get("checked")!=null){
                //if (Boolean.parseBoolean(multiSelectMapList.get(i).get("checked").toString())){
                selectMapListInAllTemp.add(multiSelectMapList.get(i));
                // }
                ////}
            }
        }

        return  selectMapListInAllTemp;
    }

    //###

    private void dealLieAndHeight() {
        //更新列
        ((GridLayoutManager)mRecyclerView.getLayoutManager()).setSpanCount(dealColumns());
        //更新recyclerview外面的id_ll_MultiSelectView高度
        ViewGroup.LayoutParams vlp=id_ll_MultiSelectView.getLayoutParams();
        if (mMultiSelectMapListInAll.size()<=ONLY_SHOW_ROWS*nowShowColumns) {
            Log.d(TAG, "dealLieAndHeight: WRAP_CONTENT");
            vlp.height=ViewGroup.LayoutParams.WRAP_CONTENT;
            id_ll_MultiSelectView.setLayoutParams(vlp);
        }else{
            Log.d(TAG, "dealLieAndHeight: XML");
            //layout_height="100dp"
            vlp.height= SizeTool.dp2px(mContext,100);
            id_ll_MultiSelectView.setLayoutParams(vlp);
        }
    }

    private List<Map<String,Object>> mMultiSelectMapListInAll=new ArrayList<>();
    List<MultiSelectHasChildBean> mMultiSelectHasChildBeanList=new ArrayList<>();
    //private List<MultiSelectHasChildBean> mMultiSelectMapListInnerGroupAndChildBeanListOutter=new ArrayList<>();
    public MultiSelectView_HasChild(Context context) {
        super(context);
        init(context);
    }

    public MultiSelectView_HasChild(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.MultiSelectView);
   /*     int itemArray_resID = typedArray.getResourceId(R.styleable.MultiSelectView_multItemArray,0);
        if (itemArray_resID!=0) {
            items_all = getResources().getStringArray(itemArray_resID);//R.array.select_dialog_items
        }
        if (items_all!=null&&items_all.length>0){
            items_key_list=new ArrayList<>();
            items_name_list=new ArrayList<>();

            for (int i = 0; i <items_all.length ; i++) {
                String[] items_key_and_name=items_all[i].split("_");//风格

                if (items_key_and_name!=null&&items_key_and_name.length>1) {//1  mean:至少2个
                    items_key_list.add(items_key_and_name[0]);
                    items_name_list.add(items_key_and_name[1]);
                }else{
                    items_key_list.add(items_all[i]);//没有_就直接都是一样的
                    items_name_list.add(items_all[i]);
                }
            }
        }*/
        //
        int showColumns = typedArray.getInt(R.styleable.MultiSelectView_showColumns,0);
        if (showColumns>0){
            nowShowColumns=showColumns;
        }else{
            nowShowColumns=ONLY_SHOW_COLUMNS_DEFAULT;
        }

        // dealParseList();
        typedArray.recycle();
        init(context);
    }

    /*   private void dealParseList() {
           if (mMultiSelectMapListOutter==null||mMultiSelectMapListOutter.size()<=0){
               //from  XML
               if (items_name_list!=null&&items_name_list.size()>0&&items_key_list!=null&&items_key_list.size()>0){
                   for (int i = 0; i < items_name_list.size(); i++) {
                       Map<String, Object> map=new HashMap<>();
                       map.put("key",items_key_list.get(i));
                       map.put("name",items_name_list.get(i));
                       map.put("checked",false);
                       mMultiSelectMapListOutter.add(map);
                   }
               }
           }

       }*/
    public MultiSelectView_HasChild(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    /**
     * 单纯用于分类展示功能
     */
    private void initHasChildData() {
        if (isInEditMode()){
            return;
        }
        //
        String pro_cate_json = MySSQTool.getStringFromRaw(mContext,R.raw.pro_cate);
        ProCate proCate= JSON.parseObject(pro_cate_json,ProCate.class);

        List<ProCate.CatesBean>  proCate_CatesBeanList=proCate.getCates();



        mMultiSelectHasChildBeanList=new ArrayList<>();
        for (int j = 0; j < proCate_CatesBeanList.size(); j++) {
            String p_key=this.getKeyLast(proCate_CatesBeanList.get(j).getCateid());
            String p_name=proCate_CatesBeanList.get(j).getCatename();
            List<ProCate.CatesBean.ChildrenBean> childrenBeanList=proCate_CatesBeanList.get(j).getChildren();
            //
            MultiSelectHasChildBean multiSelectMapListInnerGroupAndChildBean=new MultiSelectHasChildBean();
            List<Map<String, Object>> multiSelectMapList=new ArrayList<>();
            for (int i = 0; i < childrenBeanList.size(); i++) {
                Map<String,Object> map=new HashMap<>();
                map.put("name", childrenBeanList.get(i).getCatename());
                map.put("key", this.getKeyLast(childrenBeanList.get(i).getCateid()));
                map.put("checked", false);
                multiSelectMapList.add(map);
            }
            multiSelectMapListInnerGroupAndChildBean.setMultiSelectMapList(multiSelectMapList);
            multiSelectMapListInnerGroupAndChildBean.setKey(p_key);
            multiSelectMapListInnerGroupAndChildBean.setName(p_name);
//            multiSelectMapListInnerGroupAndChildBean.setID(j);
            mMultiSelectHasChildBeanList.add(multiSelectMapListInnerGroupAndChildBean);
        }


    }

    /**
     *
     * 单纯用于分类展示功能
     */
    /**取_后面的
     * @param cateID
     * @return
     */
    private  String getKeyLast(String cateID){
        String keyLast="";
        if (cateID.contains("_")){
            String[] cateIDs= cateID.split("_");
            if (cateIDs!=null&&cateIDs.length>0){
                keyLast=cateIDs[1];//第二个
            }
        }
        return  keyLast;
    }
    private void init(Context context) {
        mContext=context;


        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_mutiselect_enter, this);
        id_ll_MultiSelectView= (LinearLayout) view.findViewById(R.id.id_ll_MultiSelectView);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.id_rv);
        mMultiSelectViewRecycleViewAdapter=new MultiSelectViewRecycleViewAdapter(mMultiSelectMapListInAll,mContext,ONLY_SHOW_ROWS*nowShowColumns);
        mMultiSelectViewRecycleViewAdapter.setOnCheckboxSelectListener(new MultiSelectViewRecycleViewAdapter.OnCheckboxSelectListener() {
            @Override
            public void onCheckboxSelect(int pos, boolean isChecked) {
                mMultiSelectMapListInAll.get(pos).put("checked",isChecked);
                //mMultiSelectViewRecycleViewAdapter.updateDate(mMultiSelectMapListOutter);
                if (onCheckedChangedOrFinishListener!=null) {
                    onCheckedChangedOrFinishListener.onCheckedChanged(pos, isChecked);
                }
            }
        });


        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,nowShowColumns));
        // mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(nowShowColumns,5,true));
        mRecyclerView.setAdapter(mMultiSelectViewRecycleViewAdapter);

        //mRecyclerView.addItemDecoration(new SpacesItemDecoration(10));
        //mRecyclerView.addItemDecoration(new SpacesItemDecorationTwo(5));
        //  mRecyclerView.addItemDecoration(new ListSpacingDecoration(this));
        //
        dealLieAndHeight();
        //
        view.setOnClickListener(this);
        //mRecyclerView.setOnClickListener(this);
        /*if (this.getPaddingTop()==0&&this.getPaddingBottom()==0&&this.getPaddingLeft()==0&&this.getPaddingRight()==0) {
            int paddingLeft_Right = SizeTool.dp2px(mContext, 10);
            int paddingTop_Bottom = SizeTool.dp2px(mContext, 6);
            this.setPadding(paddingLeft_Right, paddingTop_Bottom, paddingLeft_Right, paddingTop_Bottom);
        }
        this.setOnClickListener(this);
        this.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_keyboard_arrow_down_blue_grey_400_18dp,0);
        this.setBackgroundResource(R.drawable.shape_list);*/

        /**
         * 这两句可以移到view外使用
         */
        //2016年7月26日09:09:07
        initHasChildData();
        //2016年7月26日09:09:09
        setupMultiSelectHasChildBeanListOutter(mMultiSelectHasChildBeanList);
    }


    private int dealColumns() {
        if (mMultiSelectMapListInAll.size()< nowShowColumns){
            if (mMultiSelectMapListInAll.size()>1) {
                return mMultiSelectMapListInAll.size();
            }
        }else{
            return nowShowColumns;
        }
        return ONLY_SHOW_COLUMNS_DEFAULT;//默认1
    }

    /*    public  void setupDateText(String text){
            if (text==null||text.equals("")||text.equals("null")||text.trim().equals("")) {
                this.setText("请选择");
            }else{
                this.setText(text);
            }
        }*/
    @Override
    public void onClick(View v) {
        MutiSelectDialogFragment myDialogFragment = MutiSelectDialogFragment.newInstance("",true);
        myDialogFragment.setCancelable(false);
        myDialogFragment.setupMultiSelectMapListOut(null,mMultiSelectHasChildBeanList);
        Log.d("tag", "louis:2 onClick: mMultiSelectHasChildBeanList:"+mMultiSelectHasChildBeanList);
        myDialogFragment.setOnBackHasChildDataListener(new MutiSelectDialogFragment.OnBackHasChildDataListener() {
            @Override
            public void onBackHasChildData(List<MultiSelectHasChildBean> multiSelectMapList, List<MultiSelectHasChildBean> selectMapList) {
                setupMultiSelectHasChildBeanListOutter(multiSelectMapList);
                Log.d("tag", "louis:15 onBackHasChildData: multiSelectMapList:"+multiSelectMapList);
                if (onCheckedChangedOrFinishListener!=null) {
                    onCheckedChangedOrFinishListener.onCheckedFinish();//完成回调
                }
            }
        });
        if (mContext instanceof AppCompatActivity){
            AppCompatActivity appCompatActivity= (AppCompatActivity) mContext;
            myDialogFragment.show(appCompatActivity.getSupportFragmentManager(), "myDialogFragment");
        }else if (mContext instanceof FragmentActivity){
            FragmentActivity fragmentActivity= (FragmentActivity) mContext;
            myDialogFragment.show(fragmentActivity.getSupportFragmentManager(), "myDialogFragment");
        }
    }

    /**
     *
     */
    List<Map<String, Object>> mSelectMapList;
    public List<Map<String, Object>> getSelectMapList() {
        return mSelectMapList;

    }
    public String  getSelectedKey() {
        String selectedKeys="";
        StringBuilder sb=new StringBuilder();
        if (mMultiSelectMapListInAll!=null&&mMultiSelectMapListInAll.size()>0){
            for (int i = 0; i <mMultiSelectMapListInAll.size() ; i++) {
                if (Boolean.parseBoolean(String.valueOf(mMultiSelectMapListInAll.get(i).get("checked")))) {
                    sb.append(String.valueOf(mMultiSelectMapListInAll.get(i).get("key")) + ",");
                }
            }
        }
        selectedKeys=sb.toString();
        //去掉最后一个","
        if(selectedKeys.length()>0) {
            selectedKeys = selectedKeys.substring(0, selectedKeys.length() - 1);//从beginIndex开始取，到endIndex结束，从0开始数，其中不包括endIndex位置的字符
        }
        return selectedKeys;

    }


    private void  setupSelectedKey(String[] selectedKeys) {
        List<String> strList= Arrays.asList(selectedKeys);
        List<Map<String,Object>> multiSelectMapListOutter_temp=new ArrayList<>();
        for (int j = 0; j < mMultiSelectMapListInAll.size(); j++) {
            String key=String.valueOf(mMultiSelectMapListInAll.get(j).get("key"));
            if (strList.contains(key)){
                mMultiSelectMapListInAll.get(j).put("checked",true);
            }
            multiSelectMapListOutter_temp.add(mMultiSelectMapListInAll.get(j));
        }
        mMultiSelectViewRecycleViewAdapter.updateDate(multiSelectMapListOutter_temp);
    }
    /**
     * 设置已选的key  如果有setupMultiSelectMapListOutter  在其后调用
     * @param selectedKeyStr
     */
    public void setupSelectedKeyStr(String selectedKeyStr){
        if (selectedKeyStr==null||selectedKeyStr.equals("") || selectedKeyStr.equals("null")){
            return;
        }
        String[] selectedKeys=selectedKeyStr.split(",");
        this.setupSelectedKey(selectedKeys);
    }
    public  interface  OnCheckedChangedOrFinishListener {
        void onCheckedChanged(int pos, boolean isChecked);
        void onCheckedFinish();
    }

    public void setOnCheckedChangedListener(OnCheckedChangedOrFinishListener onCheckedChangedOrFinishListener) {
        this.onCheckedChangedOrFinishListener = onCheckedChangedOrFinishListener;
    }

    OnCheckedChangedOrFinishListener onCheckedChangedOrFinishListener;
}

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

import com.louisgeek.dropdownviewlib.adapter.MultiSelectViewRecycleViewAdapter;
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
public class MultiSelectView extends LinearLayout implements View.OnClickListener {
    private  Context mContext;
    RecyclerView mRecyclerView;
    LinearLayout id_ll_MultiSelectView;
    String[] items_all;
    List<String>  items_key_list;
    List<String>  items_name_list;
    MultiSelectViewRecycleViewAdapter mMultiSelectViewRecycleViewAdapter;
    public static final int  ONLY_SHOW_COLUMNS_DEFAULT=2;
    private  int  nowShowColumns;
    public static final int  ONLY_SHOW_ROWS=2;
    private static final String TAG = "MultiSelectView";
    /**
     * 设置选项
     * @param multiSelectMapListOutter
     */
    public void setupMultiSelectMapListOutter(List<Map<String, Object>> multiSelectMapListOutter) {
        mMultiSelectViewRecycleViewAdapter.updateDate(multiSelectMapListOutter);
        dealLieAndHeight();
    }

    private void dealLieAndHeight() {
        //更新列
        ((GridLayoutManager)mRecyclerView.getLayoutManager()).setSpanCount(dealColumns());
        //更新recyclerview外面的id_ll_MultiSelectView高度
        ViewGroup.LayoutParams vlp=id_ll_MultiSelectView.getLayoutParams();
        if (mMultiSelectMapListOutter.size()<=ONLY_SHOW_ROWS*nowShowColumns) {
            Log.d(TAG, "dealLieAndHeight: WRAP_CONTENT ONLY_SHOW_ROWS："+ONLY_SHOW_ROWS+",nowShowColumns："+nowShowColumns);
            vlp.height=ViewGroup.LayoutParams.WRAP_CONTENT;
            id_ll_MultiSelectView.setLayoutParams(vlp);
        }else{
            Log.d(TAG, "dealLieAndHeight: XML ONLY_SHOW_ROWS："+ONLY_SHOW_ROWS+",nowShowColumns："+nowShowColumns);
            //layout_height="100dp"
            vlp.height= SizeTool.dp2px(mContext,100);
            id_ll_MultiSelectView.setLayoutParams(vlp);
        }
    }

    private List<Map<String,Object>> mMultiSelectMapListOutter=new ArrayList<>();
    public MultiSelectView(Context context) {
        super(context);
        init(context);
    }

    public MultiSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.MultiSelectView);
        int itemArray_resID = typedArray.getResourceId(R.styleable.MultiSelectView_multItemArray,0);
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
        }
        //
        int showColumns = typedArray.getInt(R.styleable.MultiSelectView_showColumns,0);
        if (showColumns>0){
            nowShowColumns=showColumns;
        }else{
            nowShowColumns=ONLY_SHOW_COLUMNS_DEFAULT;
        }

        dealParseList();
        typedArray.recycle();
        init(context);
    }

    private void dealParseList() {
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
    }
    public MultiSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext=context;

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_mutiselect_enter, this);

         id_ll_MultiSelectView= (LinearLayout) view.findViewById(R.id.id_ll_MultiSelectView);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.id_rv);
        mMultiSelectViewRecycleViewAdapter=new MultiSelectViewRecycleViewAdapter(mMultiSelectMapListOutter,mContext,ONLY_SHOW_ROWS*nowShowColumns);
        mMultiSelectViewRecycleViewAdapter.setOnCheckboxSelectListener(new MultiSelectViewRecycleViewAdapter.OnCheckboxSelectListener() {
            @Override
            public void onCheckboxSelect(int pos, boolean isChecked) {
                mMultiSelectMapListOutter.get(pos).put("checked",isChecked);
                Log.d(TAG, "onCheckboxSelect:pos:"+pos+"，isChecked:"+isChecked);
                // mMultiSelectViewRecycleViewAdapter.updateDate(mMultiSelectMapListOutter);
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
    }


    private int dealColumns() {
        if (mMultiSelectMapListOutter.size()< nowShowColumns){
            if (mMultiSelectMapListOutter.size()>1) {
                return mMultiSelectMapListOutter.size();
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
        MutiSelectDialogFragment myDialogFragment = MutiSelectDialogFragment.newInstance("",false);
        myDialogFragment.setCancelable(false);
        myDialogFragment.setupMultiSelectMapListOut(mMultiSelectMapListOutter,null);
        myDialogFragment.setOnBackDataListener(new MutiSelectDialogFragment.OnBackDataListener() {
            @Override
            public void onBackData(List<Map<String, Object>> multiSelectMapList, List<Map<String, Object>> selectMapList) {
                setupMultiSelectMapListOutter(multiSelectMapList);
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
        if (mMultiSelectMapListOutter!=null&&mMultiSelectMapListOutter.size()>0){
            for (int i = 0; i <mMultiSelectMapListOutter.size() ; i++) {
                if (Boolean.parseBoolean(String.valueOf(mMultiSelectMapListOutter.get(i).get("checked")))) {
                    sb.append(String.valueOf(mMultiSelectMapListOutter.get(i).get("key")) + ",");
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
        for (int j = 0; j < mMultiSelectMapListOutter.size(); j++) {
            String key=String.valueOf(mMultiSelectMapListOutter.get(j).get("key"));
            if (strList.contains(key)){
                mMultiSelectMapListOutter.get(j).put("checked",true);
            }
            multiSelectMapListOutter_temp.add(mMultiSelectMapListOutter.get(j));
        }
        mMultiSelectViewRecycleViewAdapter.updateDate(multiSelectMapListOutter_temp);
    }
    private void  setupSelectedKeySortAble(String[] selectedKeys) {
        List<String> strList= Arrays.asList(selectedKeys);
        List<Map<String,Object>> multiSelectMapListOutter_temp=new ArrayList<>();
        List<Map<String,Object>> multiSelectMapListOutter_selected_temp=new ArrayList<>();
        List<Map<String,Object>> multiSelectMapListOutter_unSelect_temp=new ArrayList<>();
        //存放选中的
        for (int j = 0; j < mMultiSelectMapListOutter.size(); j++) {
            String key=String.valueOf(mMultiSelectMapListOutter.get(j).get("key"));
            if (strList.contains(key)){
                mMultiSelectMapListOutter.get(j).put("checked",true);
                multiSelectMapListOutter_selected_temp.add(mMultiSelectMapListOutter.get(j));
            }else{
                multiSelectMapListOutter_unSelect_temp.add(mMultiSelectMapListOutter.get(j));
            }
        }
        multiSelectMapListOutter_temp.addAll(multiSelectMapListOutter_selected_temp);
        multiSelectMapListOutter_temp.addAll(multiSelectMapListOutter_unSelect_temp);

        mMultiSelectViewRecycleViewAdapter.updateDate(multiSelectMapListOutter_temp);
    }
    /**
     * 设置已选的key  如果有setupMultiSelectMapListOutter  在其后调用
     * @param selectedKeyStr
     */
    public void setupSelectedKeyStr(String selectedKeyStr,boolean sortAble){
        if (selectedKeyStr==null||selectedKeyStr.equals("") || selectedKeyStr.equals("null")){
            return;
        }
        String[] selectedKeys=selectedKeyStr.split(",");
        if (sortAble){
            this.setupSelectedKeySortAble(selectedKeys);
        }else{
            this.setupSelectedKey(selectedKeys);
        }
    }
    /**
     * 设置已选的key  如果有setupMultiSelectMapListOutter  在其后调用
     * @param selectedKeyStr
     */
    public void setupSelectedKeyStr(String selectedKeyStr){
        setupSelectedKeyStr(selectedKeyStr,false);
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

package com.louisgeek.dropdownviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.louisgeek.dropdownviewlib.tools.SizeTool;
import com.louisgeek.dropdownviewlib.tools.StringTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by louisgeek on 2016/6/5.
 */
public class DropDownView extends TextView implements View.OnClickListener {

private  Context mContext;
    private static final String TAG = "DropDownView";
    public static final String NUSELETED_SHOW_NAME = "请选择";
    String[] items_all;
    List<String>  items_key_list;
    List<String>  items_name_list;
    View nowClickView;
    public String getDefaultText() {
        return defaultText;
    }

    String defaultText;
    public void setupDataList(List<Map<String, Object>> dataList) {
        //if (dataList!=null&&dataList.size()>0) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            Log.d(TAG, "setNameStateList: " + this.dataList.size());
       // }
    }

    List<Map<String, Object>> dataList=new ArrayList<>();

    public DropDownView(Context context) {
        super(context);
        init(context);
        Log.d(TAG, "DropDownView: Context context");
    }

    public DropDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.DropDownView);
        int itemArray_resID = typedArray.getResourceId(R.styleable.DropDownView_itemArray,0);
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

        typedArray.recycle();
        init(context);
        Log.d(TAG, "DropDownView: Context context, AttributeSet attrs");
    }

    public DropDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        Log.d(TAG, "DropDownView: Context context, AttributeSet attrs, int defStyleAttr");
    }


    private void init(Context context) {
        mContext=context;
        if (this.getText()==null||StringTool.isNullOrNullStrOrBlankStr(this.getText().toString())) {
            this.setText(NUSELETED_SHOW_NAME);
        }
        defaultText=this.getText().toString();//
       // this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (this.getPaddingTop()==0&&this.getPaddingBottom()==0&&this.getPaddingLeft()==0&&this.getPaddingRight()==0) {
            int paddingLeft_Right = SizeTool.dp2px(mContext, 8);
            int paddingTop_Bottom = SizeTool.dp2px(mContext, 5);
            this.setPadding(paddingLeft_Right, paddingTop_Bottom, paddingLeft_Right, paddingTop_Bottom);
        }
        this.setOnClickListener(this);
        this.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_keyboard_arrow_down_blue_grey_400_18dp,0);
        this.setBackgroundResource(R.drawable.shape_list);
        this.setSingleLine();
        /**
         * setup min width
         */
       // int text_width= (int) this.getPaint().measureText("请选择");//当前画笔测量三个字的width
       // int text_ScaleX= (int) this.getTextScaleX();//字间距
        //
       // final int text_count=230;
        int  temp_min_width=SizeTool.dp2px(mContext,60);//三个字 大概
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (this.getMinWidth()!=-1&&this.getMinWidth()!=0){
                temp_min_width=this.getMinWidth();
            }
        }
        this.setMinWidth(temp_min_width);

        /**
         * setup max width
         */
       // final int text_count_MAX=10;
        int  temp_max_width=SizeTool.dp2px(mContext,130);//九个字 大概
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (this.getMaxWidth()!=-1&&this.getMaxWidth()!=0&&this.getMaxWidth()!=Integer.MAX_VALUE){
                temp_max_width=this.getMaxWidth();
            }
        }
        this.setMaxWidth(temp_max_width);
        this.setEllipsize(TextUtils.TruncateAt.END);


        dealParseList();//转换list
    }

    public int  dealTextMyW(int text_width,int text_ScaleX,int text_count){
       return text_width*text_count+text_ScaleX*(text_count-1);
    }


    @Override
    public void onClick(View v) {
        nowClickView=v;

        if (dataList==null||dataList.size()>0){
            dealParseList();//转换list
        }

        if (dataList!=null&&dataList.size()>0){
            DropDownPopupWindow myPopupwindow=new DropDownPopupWindow(mContext,dataList);
            myPopupwindow.showAsDropDownBelwBtnView(nowClickView);
            myPopupwindow.setOnItemSelectListener(new DropDownPopupWindow.OnItemSelectListener() {
                @Override
                public void onItemSelect(Map<String,Object> map,int pos,int realPos) {
                    if (map==null){
                        ((DropDownView) nowClickView).setText(NUSELETED_SHOW_NAME);
                    }else{
                        ////
                    if (map.get("name")!=null) {
                        ((DropDownView) nowClickView).setText(map.get("name").toString());
                    }
                    nowClickView.setTag(R.id.hold_dropdown_map,map);
                    //
                    if(map.get("key")!=null) {
                        nowClickView.setTag(R.id.hold_dropdown_key, map.get("key").toString());
                    }
                    //Log.d(TAG, "onItemSelect: ssqid:"+map.get("ssqid").toString());

                    }

                    if (onItemClickListener!=null) {
                        onItemClickListener.onItemClick(map,pos,realPos);
                    }
                }
            });
        }
        Log.d(TAG, "onClick: "+dataList.size());
    }

    private void dealParseList() {
        if (dataList==null||dataList.size()<=0){
            //from  XML
            if (items_name_list!=null&&items_name_list.size()>0&&items_key_list!=null&&items_key_list.size()>0){
                for (int i = 0; i < items_name_list.size(); i++) {
                    Map<String, Object> map=new HashMap<>();
                    map.put("key",items_key_list.get(i));
                    map.put("name",items_name_list.get(i));
                    dataList.add(map);
                }
            }
        }
    }


    public  interface OnItemClickListener{
        void  onItemClick(Map<String,Object> map,int pos,int realPos);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    OnItemClickListener onItemClickListener;

    /**
     * 通过key  设置name
     * @param key
     */
    public void setSelectNameByKey(String key) {
        if (key==null||key.equals("")||key.equals("null")||key.trim().equals("")) {
            this.setText(NUSELETED_SHOW_NAME);
        }else{
            this.setText(this.getNameByKey(key));
        }
    }
    /**
     * 直接设置name
     * @param text
     */
    public void setSelectName(String text) {
        if (text==null||text.equals("")||text.equals("null")||text.trim().equals("")) {
            this.setText(NUSELETED_SHOW_NAME);
        }else{
            this.setText(text);
        }
    }
    /**
     * 得到所选的key
     */
    public String  getSelectKey() {
        String key="";
        if (this.getText()==null||StringTool.isNullOrNullStrOrBlankStr(this.getText().toString()) ) {
           //
        }else{
            key=this.getKeyByName(this.getText().toString());
        }
        return  key;
    }

    /**
     * 得到所选的name
     */
    public String  getSelectName() {
        String name="";
        if (this.getText()==null||StringTool.isNullOrNullStrOrBlankStr(this.getText().toString())
                ||this.getText().toString().trim().equals(defaultText)) {
            name="";
        }else{
            name=this.getText().toString();
        }
        return  name;
    }

    /**
     * 通过name得到所选的index
     */
    public int getPositionByName(String name){
        int pos=-1;
        if (name!=null&&!name.equals("")){

            for (int i = 0; i <dataList.size(); i++) {
               if (name.equals(dataList.get(i).get("name"))){
                   pos=i;
                   break;
               }
            }
        }
        return pos;
    }

    /**
     * 通过key得到所选的index
     */
    public int getPositionByKey(String key){
        int pos=-1;
        if (key!=null&&!key.equals("")){

            for (int i = 0; i <dataList.size(); i++) {
                if (key.equals(dataList.get(i).get("key"))){
                    pos=i;
                    break;
                }
            }
        }
        return pos;
    }

    /**
     * 通过key得到所选的name
     */
    public String getNameByKey(String key){
        String defaultName=getDefaultText();
        int pos=getPositionByKey(key);
        defaultName=pos==-1?defaultName:String.valueOf(dataList.get(pos).get("name"));
        return defaultName;
    }
    /**
     * 通过name得到所选的key
     */
    public String getKeyByName(String name){
        String defaultKey="";
        int pos=getPositionByName(name);
        defaultKey=pos==-1?defaultKey:String.valueOf(dataList.get(pos).get("key"));
        return defaultKey;
    }
    /**
     * 通过index得到所选的key
     */
    public String getKeyByPosition(int position){
        return String.valueOf(dataList.get(position).get("key"));
    }
    /**
     * 通过name得到所选的key
     */
    public String getNameByPosition(int position){
        String name= String.valueOf(dataList.get(position).get("name"));
        if (name.trim().equals(defaultText)){
            name="";
        }
        return name;
    }
    /**
     * 通过position设置name
     */
    public void setNameByPosition(int position){
        String name=defaultText;
        if (dataList!=null&&dataList.size()>0&&position<dataList.size()){
            name=String.valueOf(dataList.get(position).get("name"));
        }
        this.setSelectName(name);
    }
}

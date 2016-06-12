package com.louisgeek.dropdownviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
    String[] items;
    View nowClickView;

    public String getDefaultText() {
        return defaultText;
    }

    String defaultText;
    public void setupNameStateList(List<Map<String, Object>> nameStateList) {
        this.nameStateList.clear();
        this.nameStateList.addAll(nameStateList);
        Log.d(TAG, "setNameStateList: "+this.nameStateList.size());

    }

    List<Map<String, Object>> nameStateList=new ArrayList<>();

    public DropDownView(Context context) {
        super(context);
        init(context);
        Log.d(TAG, "DropDownView: Context context");
    }

    public DropDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.MyDropDownView);
        int itemArray_resID = typedArray.getResourceId(R.styleable.MyDropDownView_itemArray,0);
        if (itemArray_resID!=0) {
            items = getResources().getStringArray(itemArray_resID);//R.array.select_dialog_items
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
        if (this.getText()==null||this.getText().equals("")||this.getText().equals("null")) {
            this.setText("请选择");
        }
        defaultText=this.getText().toString();//
        if (this.getPaddingTop()==0&&this.getPaddingBottom()==0&&this.getPaddingLeft()==0&&this.getPaddingRight()==0) {
            int paddingLeft_Right = dp2px(mContext, 8);
            int paddingTop_Bottom = dp2px(mContext, 5);
            this.setPadding(paddingLeft_Right, paddingTop_Bottom, paddingLeft_Right, paddingTop_Bottom);
        }
        this.setOnClickListener(this);
        this.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_keyboard_arrow_down_blue_grey_400_18dp,0);
        this.setBackgroundResource(R.drawable.shape_list);
        this.setSingleLine();


    }
    public int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    @Override
    public void onClick(View v) {
        nowClickView=v;

        if (nameStateList!=null&&nameStateList.size()>0){
            //code设置list
        }else{
            //code设置list
            if (items!=null&&items.length>0){
                for (int i = 0; i < items.length; i++) {
                    Map<String, Object> map=new HashMap<>();
                    map.put("name",items[i]);
                    nameStateList.add(map);
                }
            }
        }

        if (nameStateList!=null&&nameStateList.size()>0){
            DropDownPopupWindow myPopupwindow=new DropDownPopupWindow(mContext,nameStateList);
            myPopupwindow.showAsDropDownBelwBtnView(nowClickView);
            myPopupwindow.setOnItemSelectListener(new DropDownPopupWindow.OnItemSelectListener() {
                @Override
                public void onItemSelect(Map<String,Object> map) {
                    ((DropDownView)nowClickView).setText(map.get("name").toString());
                    ((DropDownView)nowClickView).setTag(R.id.hold_dropdown_id,map);
                    if (onItemClickListener!=null) {
                        onItemClickListener.onItemClick(map);
                    }
                }
            });
        }
        Log.d(TAG, "onClick: "+nameStateList.size());
    }


    public  interface OnItemClickListener{
        void  onItemClick(Map<String,Object> map);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    OnItemClickListener onItemClickListener;


    public void setTextMy(String text) {
        if (text==null||text.equals("")||text.equals("null")) {
            this.setText("请选择");
        }else{
            this.setText(text);
        }
    }
}

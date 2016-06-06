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
    public DropDownView(Context context) {
        super(context);
        init(context);
        Log.d(TAG, "DropDownView: Context context");
    }

    public DropDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.MyDropDownView);
        int itemArray_resID = typedArray.getResourceId(R.styleable.MyDropDownView_itemArray, 33);
        items = getResources().getStringArray(itemArray_resID);//R.array.select_dialog_items
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
        this.setText("请选择");
        int padding=dp2px(mContext,10);
        this.setPadding(padding,padding,padding,padding);
        this.setOnClickListener(this);
        this.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_keyboard_arrow_down_blue_grey_400_18dp,0);
        this.setBackgroundResource(R.drawable.shape_list);
    }
    public int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    @Override
    public void onClick(final View v) {
        List<Map<String, Object>> nameStateList=new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            Map<String, Object> map=new HashMap<>();
            map.put("name",items[i]);
            nameStateList.add(map);
        }

        DropDownPopupWindow myPopupwindow=new DropDownPopupWindow(mContext,nameStateList);
        myPopupwindow.showAsDropDownBelwBtnView(v);
        myPopupwindow.setOnItemSelectListener(new DropDownPopupWindow.OnItemSelectListener() {
            @Override
            public void onItemSelect(String text) {
                ((DropDownView)v).setText(text);
            }
        });
    }
    }

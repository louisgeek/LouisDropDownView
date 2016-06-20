package com.louisgeek.dropdownviewlib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.louisgeek.dropdownviewlib.tools.DateTool;
import com.louisgeek.dropdownviewlib.tools.SizeTool;

import java.util.Date;

/**
 * Created by louisgeek on 2016/6/5.
 */
public class DateSelectView extends TextView implements View.OnClickListener {
    private  Context mContext;
    private  String nowDateText;
    public DateSelectView(Context context) {
        super(context);
        init(context);
    }

    public DateSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DateSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext=context;

        initDate();//初始化

        if (this.getPaddingTop()==0&&this.getPaddingBottom()==0&&this.getPaddingLeft()==0&&this.getPaddingRight()==0) {
            int paddingLeft_Right = SizeTool.dp2px(mContext, 10);
            int paddingTop_Bottom = SizeTool.dp2px(mContext, 6);
            this.setPadding(paddingLeft_Right, paddingTop_Bottom, paddingLeft_Right, paddingTop_Bottom);
        }
        this.setOnClickListener(this);
        this.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_keyboard_arrow_down_blue_grey_400_18dp,0);
        this.setBackgroundResource(R.drawable.shape_list);
    }


    @Override
    public void onClick(final View v) {
        DateSelectPopupWindow myPopupwindow=new DateSelectPopupWindow(mContext,nowDateText);
        myPopupwindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,0,0);
        myPopupwindow.setOnDateSelectListener(new DateSelectPopupWindow.OnDateSelectListener() {
            @Override
            public void onDateSelect(int year, int monthOfYear, int dayOfMonth) {
               // SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                if (year==0&&monthOfYear==0&&dayOfMonth==0){
                    if (nowDateText.equals("")) {
                        nowDateText = DateTool.getChinaDate();
                    }
                }else{
                    nowDateText=DateTool.getChinaDateFromCalendar(year,monthOfYear,dayOfMonth);
                }
                ((DateSelectView)v).setText(nowDateText);
            }
        });
    }

    public  void setupDateText(String text){
        if (text==null||text.equals("")||text.equals("null"))
        {
            nowDateText=DateTool.getChinaDate();
        }else{
            //先转
            Date date=DateTool.parseStr2Data(text,DateTool.FORMAT_DATE);
            nowDateText=DateTool.parseDate2Str(date,DateTool.FORMAT_DATE);
        }
        this.setText(nowDateText);
    }

    private   void initDate(){
        setupDateText("");
    }
}

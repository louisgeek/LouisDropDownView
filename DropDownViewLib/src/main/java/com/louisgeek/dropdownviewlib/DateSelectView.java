package com.louisgeek.dropdownviewlib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by louisgeek on 2016/6/5.
 */
public class DateSelectView extends TextView implements View.OnClickListener {
    private  Context mContext;

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

        if (this.getText()==null||this.getText().equals("")||this.getText().equals("null"))
        {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String date=sdf.format(new Date());
            this.setText(date);
        }
        if (this.getPaddingTop()==0&&this.getPaddingBottom()==0&&this.getPaddingLeft()==0&&this.getPaddingRight()==0) {
            int paddingLeft_Right = dp2px(mContext, 10);
            int paddingTop_Bottom = dp2px(mContext, 6);
            this.setPadding(paddingLeft_Right, paddingTop_Bottom, paddingLeft_Right, paddingTop_Bottom);
        }
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
        DateSelectPopupWindow myPopupwindow=new DateSelectPopupWindow(mContext);
        myPopupwindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,0,0);
        myPopupwindow.setOnDateSelectListener(new DateSelectPopupWindow.OnDateSelectListener() {
            @Override
            public void onDateSelect(int year, int monthOfYear, int dayOfMonth) {
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                //new Data(int,int,int)过时了
                GregorianCalendar calendar = new GregorianCalendar(year,monthOfYear,dayOfMonth);//初始具有指定年月日的公历类对象。
                Long timeInMillis = calendar.getTimeInMillis();
                String dataStr=sdf.format(timeInMillis);//new Data()
                ((DateSelectView)v).setText(dataStr);
            }
        });
    }

    public  void setTextMy(String text){
        if (text==null||text.equals("")||text.equals("null"))
        {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String date=sdf.format(new Date());
            this.setText(date);
        }else{
            this.setText(text);
        }


    }
}

package com.louisgeek.dropdownviewlib;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.louisgeek.dropdownviewlib.tools.DateTool;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by louisgeek on 2016/6/5.
 */
public class DateSelectPopupWindow extends PopupWindow{

    private View view;
    private Context mContext;
    private TextView id_btn_date_ok;
    private TextView id_btn_date_cancel;
    private DateSelectPopupWindow dateSelectPopupWindow;
    private  DatePicker datePick1;

    private int mYear;
    private int mMonthOfYear;
    private int mDayOfMonth;
    private static final String TAG = "DateSelectPopupWindow";

    private  String mNowDateTextInner;
    private  String mStartDateTextInner;
    private  String mEndDateTextInner;

    public DateSelectPopupWindow(Context context,String nowDateTextInner,String startDateTextInner,String endDateTextInner) {
        super(context);
        mContext = context;
        mNowDateTextInner=nowDateTextInner;
        mStartDateTextInner=startDateTextInner;
        mEndDateTextInner=endDateTextInner;
        initView();
        dateSelectPopupWindow=this;
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view= inflater.inflate(R.layout.layout_popupwindow_datepick, null);

        datePick1= (DatePicker) view.findViewById(R.id.datePick1);

        initDatePicker();

        id_btn_date_ok= (TextView) view.findViewById(R.id.id_btn_date_ok);
        id_btn_date_cancel= (TextView) view.findViewById(R.id.id_btn_date_cancel);
        id_btn_date_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelectPopupWindow.dismiss();
                mOnDateSelectListener.onDateSelect(mYear,mMonthOfYear,mDayOfMonth);
            }
        });
        id_btn_date_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelectPopupWindow.dismiss();
            }
        });

        //设置PopupWindow的View
        this.setContentView(view);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new ColorDrawable(0xffffffff));//必须设置  ps:xml bg和这个不冲突
        this.setAnimationStyle(R.style.selectDataViewAnimation);
        this.setFocusable(true);//设置后  达到返回按钮先消失popupWindow
    }

    private void initDatePicker() {
        Calendar calendar;
        if(mNowDateTextInner!=null&&!mNowDateTextInner.equals("")&&!mNowDateTextInner.equals("null")
                &&!mNowDateTextInner.contains(DateSelectView.DEFAULT_DATA)&&!mNowDateTextInner.equals(DateSelectView.DEFAULT_STR)){
            //显示上一次选择数据
           Date date=DateTool.parseStr2Date(mNowDateTextInner,DateTool.FORMAT_DATE);
           calendar=DateTool.parseDate2Calendar(date);
        }else{
            calendar=Calendar.getInstance();//初始化时间
         }
        int year=calendar.get(Calendar.YEAR);
        int monthOfYear=calendar.get(Calendar.MONTH);
        int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);

        DatePicker.OnDateChangedListener dcl=new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mYear=year;
                mMonthOfYear=monthOfYear;
                mDayOfMonth=dayOfMonth;
            }
        };
        //
        datePick1.init(year,monthOfYear,dayOfMonth,dcl);
        //
        Log.i(TAG, "initDatePicker: mNowDateTextInner:"+mNowDateTextInner);
        Log.i(TAG, "initDatePicker: mStartDateTextInner:"+mStartDateTextInner);
        if (mStartDateTextInner!=null){
            Calendar calendar_s=DateTool.parseStr2Calendar(mStartDateTextInner,DateTool.FORMAT_DATE);
           long  time_s=calendar_s.getTimeInMillis();
            Log.i(TAG, "initDatePicker: time_s:"+time_s);
            datePick1.setMinDate(time_s);
        }
        Log.i(TAG, "initDatePicker: mEndDateTextInner:"+mEndDateTextInner);
        if (mEndDateTextInner!=null){
            Calendar calendar_e=DateTool.parseStr2Calendar(mEndDateTextInner,DateTool.FORMAT_DATE);
            long  time_e=calendar_e.getTimeInMillis();
            Log.i(TAG, "initDatePicker: calendar_e:"+calendar_e);
            datePick1.setMaxDate(time_e);
        }


    }


    public interface OnDateSelectListener {
        void onDateSelect(int year, int monthOfYear, int dayOfMonth);
    }

    public void setOnDateSelectListener(OnDateSelectListener onDateSelectListener) {
        mOnDateSelectListener = onDateSelectListener;
    }

    private OnDateSelectListener mOnDateSelectListener;

}

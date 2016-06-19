package com.louisgeek.dropdownviewlib;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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


    private  String mNowDateTextInner;

    public DateSelectPopupWindow(Context context,String nowDateTextInner) {
        super(context);
        mContext = context;
        mNowDateTextInner=nowDateTextInner;
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
        if(mNowDateTextInner!=null&&!mNowDateTextInner.equals("")&&!mNowDateTextInner.equals("null")){
            //显示上一次选择数据
           Date date=DateTool.parseStr2Data(mNowDateTextInner,DateTool.FORMAT_DATE);
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
        datePick1.init(year,monthOfYear,dayOfMonth,dcl);
    }


    public interface OnDateSelectListener {
        void onDateSelect(int year, int monthOfYear, int dayOfMonth);
    }

    public void setOnDateSelectListener(OnDateSelectListener onDateSelectListener) {
        mOnDateSelectListener = onDateSelectListener;
    }

    private OnDateSelectListener mOnDateSelectListener;

}

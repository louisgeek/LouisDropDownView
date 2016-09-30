package com.louisgeek.dropdownviewlib;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.louisgeek.dropdownviewlib.tools.DateTool;
import com.louisgeek.dropdownviewlib.tools.KeyBoardTool;
import com.louisgeek.dropdownviewlib.tools.SizeTool;

import java.util.Date;

/**
 * Created by louisgeek on 2016/6/5.
 */
public class DateSelectView extends TextView implements View.OnClickListener {
    private  Context mContext;
    private  String mNowDateText;
    private  String startDateText;
    private  String endDateText;
    private  final  String DEFAULT_DATA_TIME="1970-01-01 00:00:00";
    public static final  String DEFAULT_DATA="1970-01-01";
    public static final  String DEFAULT_STR="请选择";
    private static final String TAG = "DateSelectView";
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
        //
        KeyBoardTool.hideKeyboard(v);

        DateSelectPopupWindow myPopupwindow=new DateSelectPopupWindow(mContext,mNowDateText,startDateText,endDateText);
        myPopupwindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,0,0);
        myPopupwindow.setOnDateSelectListener(new DateSelectPopupWindow.OnDateSelectListener() {
            @Override
            public void onDateSelect(int year, int monthOfYear, int dayOfMonth) {
               // SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                if (year==0&&monthOfYear==0&&dayOfMonth==0){
                    if (mNowDateText.trim().equals("")||mNowDateText.trim().equals(DEFAULT_STR)) {
                        mNowDateText = DateTool.getChinaDate();
                    }
                }else{
                    mNowDateText=DateTool.getChinaDateFromCalendar(year,monthOfYear,dayOfMonth);
                }
                ((DateSelectView)v).setText(mNowDateText);
            }
        });
    }

    /**
     *
     * @param nowDataText
     */
    public  void setupDateText(String nowDataText){
        Log.i(TAG, "setupDateText  nowDataText Only: nowDataText:"+nowDataText);
        setupDateText(nowDataText,null,null);
    }

    /**
     *
     * @param nowDateText
     * @param startDateText
     * @param endDateText
     */
    public  void setupDateText(String nowDateText,String startDateText,String endDateText){
        Log.i(TAG, "setupDateText: nowDateText:"+nowDateText);
        Log.i(TAG, "setupDateText: startDateText:"+startDateText);
        Log.i(TAG, "setupDateText: endDateText:"+endDateText);
        if (nowDateText==null||nowDateText.trim().equals("")||nowDateText.trim().equals("null")
                ||nowDateText.contains(DEFAULT_DATA)||nowDateText.equals(DEFAULT_STR))
        {
            mNowDateText=DEFAULT_STR;
        }else{
            //先转
            Date date=DateTool.parseStr2Date(nowDateText,DateTool.FORMAT_DATE);
            if (date!=null) {
                mNowDateText = DateTool.parseDate2Str(date, DateTool.FORMAT_DATE);
            }else{
                mNowDateText=DEFAULT_STR;
            }
            if (mNowDateText.contains(DEFAULT_DATA)){
                mNowDateText=DEFAULT_STR;
            }
        }
        this.setText(mNowDateText);
        //
        this.startDateText=startDateText;
        this.endDateText=endDateText;
    }
    private   void initDate(){
        setupDateText(null);
    }

    @Deprecated
    public CharSequence getText() {
        return super.getText();
    }

    public String getNowSelectData(){
        String nowData="";
        if (this.getText() != null){
            nowData=this.getText().toString();
        }
       return  nowData.trim().equals("")||nowData.trim().equals(DEFAULT_STR)?DEFAULT_DATA:nowData;
    }

    /**
     * 未设置的返回当前时间
     * @return
     */
    public String getNowSelectDataFixedNowData(){
       return getNowSelectData()==null?DateTool.getChinaDate():getNowSelectData();
    }
}

package com.louisgeek.dropdownviewlib;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.louisgeek.dropdownviewlib.adapter.MultiSelectViewRecycleViewAdapter;
import com.louisgeek.dropdownviewlib.ui.MutiSelectDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by louisgeek on 2016/6/20.
 */
public class MultiSelectView extends LinearLayout implements View.OnClickListener {
    private  Context mContext;
    RecyclerView mRecyclerView;
    MultiSelectViewRecycleViewAdapter mMultiSelectViewRecycleViewAdapter;
    public static final int  ONLY_SHOW_COLUMNS=3;
    public static final int  ONLY_SHOW_ROWS=2;
    public void setMultiSelectMapListOutter(List<Map<String, Object>> multiSelectMapListOutter) {
        mMultiSelectViewRecycleViewAdapter.updateDate(multiSelectMapListOutter);
        //更新列
        ((GridLayoutManager)mRecyclerView.getLayoutManager()).setSpanCount(dealColumns());
        //更新高度
        ViewGroup.LayoutParams vlp=mRecyclerView.getLayoutParams();
        if (mMultiSelectMapListOutter.size()<=(MultiSelectView.ONLY_SHOW_ROWS-1)*MultiSelectView.ONLY_SHOW_COLUMNS) {
            vlp.height=ViewGroup.LayoutParams.WRAP_CONTENT;
            mRecyclerView.setLayoutParams(vlp);
        }else{
            //xml 中 android:layout_height="105dp"
        }
    }

    private List<Map<String,Object>> mMultiSelectMapListOutter=new ArrayList<>();
    public MultiSelectView(Context context) {
        super(context);
        init(context);
    }

    public MultiSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MultiSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext=context;

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_mutiselect_enter, this);


        mRecyclerView= (RecyclerView) view.findViewById(R.id.id_rv);
        mMultiSelectViewRecycleViewAdapter=new MultiSelectViewRecycleViewAdapter(mMultiSelectMapListOutter,mContext,ONLY_SHOW_ROWS*ONLY_SHOW_COLUMNS);
        mMultiSelectViewRecycleViewAdapter.setOnCheckboxSelectListener(new MultiSelectViewRecycleViewAdapter.OnCheckboxSelectListener() {
            @Override
            public void onCheckboxSelect(int pos, boolean isChecked) {
                mMultiSelectMapListOutter.get(pos).put("checked",isChecked);
                //mMultiSelectViewRecycleViewAdapter.updateDate(mMultiSelectMapListOutter);
            }
        });


        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,ONLY_SHOW_COLUMNS));
        mRecyclerView.setAdapter(mMultiSelectViewRecycleViewAdapter);


        //
        view.setOnClickListener(this);
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
        if (mMultiSelectMapListOutter.size()< MultiSelectView.ONLY_SHOW_COLUMNS){
            if (mMultiSelectMapListOutter.size()>1) {
               return mMultiSelectMapListOutter.size();
            }
        }else{
            return ONLY_SHOW_COLUMNS;
        }
        return 1;//默认1
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
        MutiSelectDialogFragment myDialogFragment = MutiSelectDialogFragment.newInstance("");
        myDialogFragment.setCancelable(false);
        myDialogFragment.setupMultiSelectMapListOut(mMultiSelectMapListOutter);
        myDialogFragment.setOnBackDataListener(new MutiSelectDialogFragment.OnBackDataListener() {
            @Override
            public void onBackData(List<Map<String, Object>> multiSelectMapList, List<Map<String, Object>> selectMapList) {
                setMultiSelectMapListOutter(multiSelectMapList);
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

}

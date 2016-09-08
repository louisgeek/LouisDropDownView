package com.louisgeek.dropdownviewlib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.louisgeek.dropdownviewlib.R;

import java.util.List;
import java.util.Map;

/**
 * 可见的view适配器
 * Created by louisgeek on 2016/6/20.
 */
public class MultiSelectViewRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private  int mShowCount;
  //  private final int NORMAL_ITEM=0;
  //  private final int MORE_ITEM=1;
    public MultiSelectViewRecycleViewAdapter(List<Map<String, Object>> dataList, Context context,int showCount) {
        mDataList = dataList;
        mContext = context;
        mShowCount=showCount;
    }

    private List<Map<String, Object>> mDataList;
    private Context mContext;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mutiselect_enter_list_item, parent,false);

        MyRecyclerViewHolder myRecyclerViewHolder = new MyRecyclerViewHolder(view);
        return myRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyRecyclerViewHolder myRecyclerViewHolder = (MyRecyclerViewHolder) holder;
      //  myRecyclerViewHolder.item_tv.setText(mDataList.get(position).get("name").toString());
        myRecyclerViewHolder.item_cb.setTag(R.id.hold_check_pos,position);
        myRecyclerViewHolder.item_cb.setText(mDataList.get(position).get("name").toString());
        myRecyclerViewHolder.item_cb.setChecked(Boolean.parseBoolean(String.valueOf(mDataList.get(position).get("checked"))));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
      //  return mDataList.size()<mShowCount?mDataList.size():mShowCount;//only  show  x item
    }


    class  MyRecyclerViewHolder extends RecyclerView.ViewHolder{
      //  TextView item_tv;
        CheckBox item_cb;
        public MyRecyclerViewHolder(View itemView) {
            super(itemView);
           // item_tv = (TextView) itemView.findViewById(R.id.item_tv);
            item_cb= (CheckBox) itemView.findViewById(R.id.item_cb);
            item_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   int pos= Integer.valueOf(buttonView.getTag(R.id.hold_check_pos).toString());
                    onCheckboxSelectListener.onCheckboxSelect(pos, isChecked);
                }
            });
        }
    }


    public void updateDate(List<Map<String,Object>> multiSelectMapList){
        mDataList.clear();
        mDataList.addAll(multiSelectMapList);

        this.notifyDataSetChanged();
    }

    public  interface  OnCheckboxSelectListener{
        void  onCheckboxSelect(int pos,boolean isChecked);
    }

    public void setOnCheckboxSelectListener(OnCheckboxSelectListener onCheckboxSelectListener) {
        this.onCheckboxSelectListener = onCheckboxSelectListener;
    }

    OnCheckboxSelectListener onCheckboxSelectListener;



}

package com.louisgeek.dropdownviewlib;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by louisgeek on 2016/6/3.
 */
public class DropDownViewRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  int itemWidth;

    private static final String TAG = "DropDownViewAdapter";

    public void updateBackground(boolean showAbove,RecyclerView recyclerView) {
        List<Map<String, Object>> mNameStateListTemp=new ArrayList<>();
        for (int i = 0; i < mNameStateList.size(); i++) {
            Map<String, Object> mapTemp= mNameStateList.get(i);
            if (showAbove){
                mapTemp.put("isShowAbove","true");
            }else{
                mapTemp.put("isShowAbove","false");
            }
            mNameStateListTemp.add(mapTemp);
        }
        mNameStateList.clear();
        mNameStateList.addAll(mNameStateListTemp);
        Log.d(TAG, "updateBackground:isShowAbove"+showAbove);
        Handler handler=new Handler();
        //线程报错
        // this.notifyDataSetChanged();
        handlerPostAndNotifyAdapterNotifyDataSetChanged(handler,recyclerView,this);
    }


    public DropDownViewRecycleViewAdapter(Context context, List<Map<String, Object>> mNameStateList, int itemWidth) {
        mContext = context;
        this.mNameStateList = mNameStateList;
        this.itemWidth=itemWidth;
    }

    private List<Map<String, Object>> mNameStateList;
    private Context mContext;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dropdown_item, parent,false);//不false  在popupwindow里报错

        //设置Item的宽
        view.setLayoutParams(new ViewGroup.LayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

        MyRecyclerViewHolder myRecyclerViewHolder = new MyRecyclerViewHolder(view);
        return myRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyRecyclerViewHolder myRecyclerViewHolder = (MyRecyclerViewHolder) holder;

        if (mNameStateList.get(position).get("isShowAbove")!=null){
        String isShowAbove=mNameStateList.get(position).get("isShowAbove").toString();
        Log.d(TAG, "onBindViewHolder updateBackground:isShowAbove"+isShowAbove);
        if("true".equals(isShowAbove)) {
            myRecyclerViewHolder.id_ll_item.setBackgroundResource(R.drawable.selector_shape_list_item_nobottom);
        }else{
            myRecyclerViewHolder.id_ll_item.setBackgroundResource(R.drawable.selector_shape_list_item_notop);
        }
        }

        myRecyclerViewHolder.mTextView.setText(mNameStateList.get(position).get("name").toString());
    }

    @Override
    public int getItemCount() {
        return mNameStateList.size();
    }

    class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        LinearLayout id_ll_item;
        public MyRecyclerViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.id_tv);
            id_ll_item= (LinearLayout) itemView.findViewById(R.id.id_ll_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemViewClickListener.onItemViewClick(v,getAdapterPosition());
                }
            });
        }




    }

    /**
     * 重复post message 直到recyclerView 完成显示
     * @param handler
     * @param recyclerView
     * @param adapter
     */
    protected void handlerPostAndNotifyAdapterNotifyDataSetChanged(final Handler handler, final RecyclerView recyclerView, final RecyclerView.Adapter adapter) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!recyclerView.isComputingLayout()) {
                    adapter.notifyDataSetChanged();
                } else {
                    handlerPostAndNotifyAdapterNotifyDataSetChanged(handler, recyclerView, adapter);
                }
            }
        });
    }

    public  interface  OnItemViewClickListener{
        void  onItemViewClick(View v, int Position);
    }

    public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
        mOnItemViewClickListener = onItemViewClickListener;
    }

    private  OnItemViewClickListener mOnItemViewClickListener;



}

package com.louisgeek.dropdownviewlib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.louisgeek.dropdownviewlib.R;
import com.louisgeek.dropdownviewlib.javabean.ClassfiyBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by louisgeek on 2016/8/9.
 */
public class MyRecylerViewRightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  final  int TYPE_VIEW_ALL=1;
    private  final  int TYPE_VIEW_NORMAL=2;

     private Map<Integer,Boolean> theAllSelectedStateMap=new HashMap<>();
    public MyRecylerViewRightAdapter(List<ClassfiyBean> classfiyBeanList, int parentPos) {
        mClassfiyBeanList = classfiyBeanList;
        this.parentPos = parentPos;
          //  dealThenSetTheAllSelectedState();
    }

    private void dealThenSetTheAllSelectedState() {
        for (int i = 0; i < mClassfiyBeanList.size(); i++) {
            boolean childNoOneHasSelected=true;
            for (int j = 0; j < mClassfiyBeanList.get(i).getChildClassfiyBeanList().size(); j++) {
                if (mClassfiyBeanList.get(i).getChildClassfiyBeanList().get(j).isSelected()){
                    childNoOneHasSelected=false;
                    break;
                }
            }
            if (childNoOneHasSelected){
                theAllSelectedStateMap.put(i,true);
            }
        }

    }

    private List<ClassfiyBean> mClassfiyBeanList;
    private int parentPos;
  //  int mChildSelectedPos;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder myRecyclerViewViewHolder = null;
        switch (viewType){
            case TYPE_VIEW_NORMAL:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.right_item_normal,parent,false);
                myRecyclerViewViewHolder=new MyRecyclerViewViewNormalHolder(view);
                break;
            case TYPE_VIEW_ALL:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.right_item_all,parent,false);
                myRecyclerViewViewHolder=new MyRecyclerViewViewAllHolder(view);
                break;
        }
        return myRecyclerViewViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TYPE_VIEW_ALL;
        }else {
            return TYPE_VIEW_NORMAL;
        }
        //return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        MyRecyclerViewViewNormalHolder myRecyclerViewViewNormalHolder;
        MyRecyclerViewViewAllHolder myRecyclerViewViewAllHolder;
        if (holder instanceof MyRecyclerViewViewNormalHolder){
            myRecyclerViewViewNormalHolder= (MyRecyclerViewViewNormalHolder) holder;
            final int realPos=position-1;
            myRecyclerViewViewNormalHolder.mTextViewName.setText(mClassfiyBeanList.get(parentPos).getChildClassfiyBeanList().get(realPos).getName());
            myRecyclerViewViewNormalHolder.mTextViewCount.setText(mClassfiyBeanList.get(parentPos).getChildClassfiyBeanList().get(realPos).getCount());

            //
            myRecyclerViewViewNormalHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setNormalSelectedState(realPos);
                    if (onItemClickListener!=null){
                    onItemClickListener.onItemClickNormal(v,mClassfiyBeanList,parentPos,realPos);
                    }
                }
            });
            myRecyclerViewViewNormalHolder.itemView.setSelected(getNormalSeletedState(realPos));
            if (myRecyclerViewViewNormalHolder.itemView.isSelected()){
                myRecyclerViewViewNormalHolder.id_iv_icon.setVisibility(View.VISIBLE);
            }else{
                myRecyclerViewViewNormalHolder.id_iv_icon.setVisibility(View.INVISIBLE);
            }

        }else if (holder instanceof MyRecyclerViewViewAllHolder){
            myRecyclerViewViewAllHolder= (MyRecyclerViewViewAllHolder) holder;
            myRecyclerViewViewAllHolder.mTextViewName.setText("全部");
            myRecyclerViewViewAllHolder.itemView.setTag(myRecyclerViewViewAllHolder.mTextViewName.getText());
            myRecyclerViewViewAllHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  // setSelectedState(position);
                    setTheAllSelectedState(parentPos);
                    onItemClickListener.onItemClickAll(v,mClassfiyBeanList,parentPos);
                }
            });
            myRecyclerViewViewAllHolder.itemView.setSelected(getTheAllSeletedState(parentPos));
            if (myRecyclerViewViewAllHolder.itemView.isSelected()){
                myRecyclerViewViewAllHolder.id_iv_icon.setVisibility(View.VISIBLE);
            }else{
                myRecyclerViewViewAllHolder.id_iv_icon.setVisibility(View.INVISIBLE);
            }
        }

    }

    public void updateParentPos(int parentPos){
        this.parentPos=parentPos;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mClassfiyBeanList.get(parentPos).getChildClassfiyBeanList().size()+1;
    }
    public void clearNormalSelectedState(){
        for (int i = 0; i < mClassfiyBeanList.get(parentPos).getChildClassfiyBeanList().size(); i++) {
            mClassfiyBeanList.get(parentPos).getChildClassfiyBeanList().get(i).setSelected(false);
        }
        this.notifyDataSetChanged();
        // this.notifyItemRangeChanged(0,getItemCount());//不包括pos==0
    }
    public void setNormalSelectedState(int position){
        clearTheAllAndNormalSelectedState();
        mClassfiyBeanList.get(parentPos).getChildClassfiyBeanList().get(position).setSelected(true);
        this.notifyDataSetChanged();
        // this.notifyItemRangeChanged(0,getItemCount());
    }
    private  boolean  getNormalSeletedState(int position){
        boolean isSelected=mClassfiyBeanList.get(parentPos).getChildClassfiyBeanList().get(position).isSelected();
        return isSelected;
    }
    public void clearTheAllSelectedState(){
        for (int key : theAllSelectedStateMap.keySet()) {
            // System.out.println("key= "+ key + " and value= " + map.get(key));
            theAllSelectedStateMap.put(key,false);
        }
        this.notifyDataSetChanged();
        // this.notifyItemRangeChanged(0,getItemCount());
    }
    public void clearTheAllAndNormalSelectedState(){
        clearNormalSelectedState();
        clearTheAllSelectedState();
    }
    public void setTheAllSelectedState(int  parentPos){
        clearTheAllAndNormalSelectedState();

        theAllSelectedStateMap.put(parentPos,true);
        this.notifyDataSetChanged();
        //this.notifyItemRangeChanged(0,getItemCount());
    }
    private  boolean  getTheAllSeletedState(int  parentPos){
        boolean isSelect=false;
        if (theAllSelectedStateMap.get(parentPos)!=null){
            isSelect=theAllSelectedStateMap.get(parentPos);
        }
        return  isSelect;
    }
    class MyRecyclerViewViewNormalHolder extends RecyclerView.ViewHolder{

        TextView mTextViewName;
        TextView mTextViewCount;
        ImageView id_iv_icon;
        public MyRecyclerViewViewNormalHolder(View itemView) {
            super(itemView);
            id_iv_icon= (ImageView) itemView.findViewById(R.id.id_iv_icon);
            mTextViewName= (TextView) itemView.findViewById(R.id.id_tv_name);
            mTextViewCount= (TextView) itemView.findViewById(R.id.id_tv_count);
        }
    }

    class MyRecyclerViewViewAllHolder extends RecyclerView.ViewHolder{

        TextView mTextViewName;
        ImageView id_iv_icon;
        public MyRecyclerViewViewAllHolder(View itemView) {
            super(itemView);
            id_iv_icon= (ImageView) itemView.findViewById(R.id.id_iv_icon);
            mTextViewName= (TextView) itemView.findViewById(R.id.id_tv_name);
        }
    }

    public interface OnItemClickListener{
        void  onItemClickNormal(View v, List<ClassfiyBean> classfiyBeanList, int parentPos, int childPos);
        void  onItemClickAll(View v, List<ClassfiyBean> classfiyBeanList, int parentPos);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public  OnItemClickListener onItemClickListener;
}

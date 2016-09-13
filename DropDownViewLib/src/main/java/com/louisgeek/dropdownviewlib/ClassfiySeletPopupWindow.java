package com.louisgeek.dropdownviewlib;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.louisgeek.dropdownviewlib.adapter.MyRecylerViewLeftAdapter;
import com.louisgeek.dropdownviewlib.adapter.MyRecylerViewOnlyOneAdapter;
import com.louisgeek.dropdownviewlib.adapter.MyRecylerViewRightAdapter;
import com.louisgeek.dropdownviewlib.javabean.ClassfiyBean;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

/**
 * Created by louisgeek on 2016/8/9.
 */
public class ClassfiySeletPopupWindow extends PopupWindow{

    private static final String TAG = "ClassfiySeletPopWin";
    public static final String CUT_TAG = "tuc";

    Context mContext;
    List<ClassfiyBean> mClassfiyBeanList;
    //List<ClassfiyBean.ChildClassfiyBean> mChildClassfiyBeanList;
    int parentPos=-1;
    int childPos=-1;
    String key_parent="";
    String key_child="";
    int mListMaxHeight;
    RecyclerView id_rv_left;
    RecyclerView id_rv_right;
    MyRecylerViewLeftAdapter myRecylerViewAdapter;
    MyRecylerViewRightAdapter myRecylerViewRightAdapter;
    MyRecylerViewOnlyOneAdapter myRecylerViewOnlyOneAdapter;
    String mDefaultKey="";
    boolean mOnlyOneList=false;
    public ClassfiySeletPopupWindow(Context context,List<ClassfiyBean> classfiyBeanList,String defaultKey,int listMaxHeight) {
        super(context);
        mContext=context;
        mClassfiyBeanList=classfiyBeanList;
        mDefaultKey=defaultKey;
        mListMaxHeight=listMaxHeight;
        Log.d(TAG, "dealDefaultKey: mDefaultKey:"+mDefaultKey);
        dealDefaultKey();
        initView();
    }

    private void dealDefaultKey() {
        if (mDefaultKey==null||mDefaultKey.equals("")){
            return;
        }

        if (mDefaultKey.contains(ClassfiySeletPopupWindow.CUT_TAG)){
            String[] keys=mDefaultKey.split(ClassfiySeletPopupWindow.CUT_TAG);
            if (keys!=null&&keys.length>0){
                key_parent=keys[0];
                if (keys.length>1){
                    key_child=keys[1];
                }
            }
        }else{
            //单个列表
            key_parent=mDefaultKey;
        }
        if (!key_parent.equals("")){
            for (int i = 0; i < mClassfiyBeanList.size(); i++) {
                if (key_parent.equals(mClassfiyBeanList.get(i).getBeanID())){
                    parentPos=i;
                    break;
                }
            }
        }
        if (!key_child.equals("")){
            if (parentPos>-1){
                List<ClassfiyBean.ChildClassfiyBean> cbccbs=mClassfiyBeanList.get(parentPos).getChildClassfiyBeanList();
                for (int j = 0; j < cbccbs.size(); j++) {
                    if (key_child.equals(cbccbs.get(j).getBeanID())){
                        childPos=j;
                        break;
                    }
                }
            }
        }

        Log.d(TAG, "dealDefaultKey: parentPos:"+parentPos);
        Log.d(TAG, "dealDefaultKey: childPos:"+childPos);
    }


    private void initView() {

        /**
         * //单个列表
         */
        if (mClassfiyBeanList!=null&&mClassfiyBeanList.size()>0){
            if(mClassfiyBeanList.get(0).getChildClassfiyBeanList()==null||mClassfiyBeanList.get(0).getChildClassfiyBeanList().size()==0){
                mOnlyOneList=true;
            }
        }

        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_popupwindow_selectview,null);
        id_rv_left=(RecyclerView) view.findViewById(R.id.id_rv_left);
        id_rv_left.setHasFixedSize(true);//位置固定大小 //2016年8月12日16:41:18  这里的用意：不用的话点击后面的item 然后马上会滚动一段，因为如果item的内容会改变view布局大小
        id_rv_left.setLayoutManager(new LinearLayoutManager(mContext));
        if (mOnlyOneList){
            id_rv_left.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).build());
            myRecylerViewOnlyOneAdapter=new MyRecylerViewOnlyOneAdapter(mClassfiyBeanList);
            myRecylerViewOnlyOneAdapter.setOnItemClickListener(new MyRecylerViewOnlyOneAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    onItemSelectedListener.onItemSelected(mClassfiyBeanList.get(pos).getBeanID(), mClassfiyBeanList.get(pos).getName());
                    ClassfiySeletPopupWindow.this.dismiss();
                }
            });
            id_rv_left.setAdapter(myRecylerViewOnlyOneAdapter);
            if (parentPos > -1) {
                myRecylerViewOnlyOneAdapter.setSelectedState(parentPos);
            } else {
                //什么都没选的时候  选中left第一个
                //### myRecylerViewOnlyOneAdapter.setSelectedState(0);          //单个列表不选中
            }
        }else {
            id_rv_left.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).build());
            myRecylerViewAdapter = new MyRecylerViewLeftAdapter(mClassfiyBeanList);
            myRecylerViewAdapter.setOnItemClickListener(new MyRecylerViewLeftAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    myRecylerViewRightAdapter.updateParentPos(pos);
                    //myRecylerViewRightAdapter.clearTheAllAndNormalSelectedState();
                    //每次点击选中右边的
                    // myRecylerViewRightAdapter.setTheAllSelectedState(pos);
                    Log.d(TAG, "QQQ onItemClick: pp pos:" + pos);
                }
            });
            id_rv_left.setAdapter(myRecylerViewAdapter);
            Log.d(TAG, "initView: parentPos:" + parentPos);
            if (parentPos > -1) {
                myRecylerViewAdapter.setSelectedState(parentPos);
            } else {
                //什么都没选的时候  选中left第一个
                myRecylerViewAdapter.setSelectedState(0);
            }
        }

        id_rv_right = (RecyclerView) view.findViewById(R.id.id_rv_right);
        if (mOnlyOneList){
            id_rv_right.setVisibility(View.GONE);
        }else{
            id_rv_right.setVisibility(View.VISIBLE);
            myRecylerViewRightAdapter = new MyRecylerViewRightAdapter(mClassfiyBeanList, parentPos < 0 ? 0 : parentPos);
            //  mChildClassfiyBeanList=new ArrayList<>(mClassfiyBeanList.get(0).getChildClassfiyBeanList());
            myRecylerViewRightAdapter.setOnItemClickListener(new MyRecylerViewRightAdapter.OnItemClickListener() {

                @Override
                public void onItemClickNormal(View v, List<ClassfiyBean> classfiyBeanList, int parentPos, int childPos) {
                    List<ClassfiyBean.ChildClassfiyBean> childClassfiyBeanList = classfiyBeanList.get(parentPos).getChildClassfiyBeanList();
                    //Toast.makeText(mContext, "xxx parentPos:"+parentPos+",child name:"+childClassfiyBeanList.get(childPos).getName(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "QQQ onItemClickNormal: parentPos:" + parentPos + ",childPos:" + childPos);
                    String key = classfiyBeanList.get(parentPos).getBeanID() + CUT_TAG + childClassfiyBeanList.get(childPos).getBeanID();
                   if (onItemSelectedListener!=null){
                    onItemSelectedListener.onItemSelected(key, childClassfiyBeanList.get(childPos).getName());
                   }
                    ClassfiySeletPopupWindow.this.dismiss();

                    Log.d(TAG, "fff onItemClickNormal: key:" + key);
                }


                @Override
                public void onItemClickAll(View v, List<ClassfiyBean> classfiyBeanList, int parentPos) {
                    //Toast.makeText(mContext, "xxx parentPos:"+parentPos+",parent name:"+classfiyBeanList.get(parentPos).getName(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "QQQ onItemClickAll:  parentPos:" + parentPos);
                    String key = classfiyBeanList.get(parentPos).getBeanID() + CUT_TAG + "-1";
                    onItemSelectedListener.onItemSelected(key, v.getTag() + classfiyBeanList.get(parentPos).getName());
                    ClassfiySeletPopupWindow.this.dismiss();

                    Log.d(TAG, "fff onItemClickAll: key:" + key);
                }
            });
            id_rv_right.setAdapter(myRecylerViewRightAdapter);
            id_rv_right.setLayoutManager(new LinearLayoutManager(mContext));
            if (childPos == -1) {
                myRecylerViewRightAdapter.setTheAllSelectedState(parentPos);
            } else if (childPos > -1) {
                myRecylerViewRightAdapter.setNormalSelectedState(childPos);
            }


        }
        if (mListMaxHeight>0) {
            //===============================
            ViewGroup.LayoutParams vlp_left = id_rv_left.getLayoutParams();
            ViewGroup.LayoutParams vlp_right = id_rv_right.getLayoutParams();
            /**
             * 手动测量
             */
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            id_rv_left.measure(widthMeasureSpec, heightMeasureSpec);
            id_rv_right.measure(widthMeasureSpec, heightMeasureSpec);
            // int width = id_rv_left.getMeasuredWidth();
            int left_height = id_rv_left.getMeasuredHeight();
            int right_height = id_rv_right.getMeasuredHeight();
            Log.d(TAG, "initView: left_height:" + left_height);
            Log.d(TAG, "initView: right_height:" + right_height);
            Log.d(TAG, "initView: mListMaxHeight:" + mListMaxHeight);
            int maxHeight = Math.max(left_height, right_height);
            if (maxHeight > mListMaxHeight) {
                maxHeight = mListMaxHeight;
            }
            Log.d(TAG, "initView: maxHeight:" + maxHeight);
            vlp_left.height = maxHeight;
            vlp_right.height = maxHeight;
            id_rv_left.setLayoutParams(vlp_left);
            id_rv_right.setLayoutParams(vlp_right);
            //===============================
        }

        //设置PopupWindow的View
        this.setContentView(view);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));//必须设置  ps:xml bg和这个不冲突
        this.setFocusable(true);//设置后  达到返回按钮先消失popupWindow
        //id_pop_tv.setOnClickListener(this);
    }


    public  interface  OnItemSelectedListener{
        void  onItemSelected(String key,String name);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    OnItemSelectedListener onItemSelectedListener;
}

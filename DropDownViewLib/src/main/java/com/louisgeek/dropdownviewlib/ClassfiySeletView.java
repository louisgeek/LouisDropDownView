package com.louisgeek.dropdownviewlib;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.louisgeek.dropdownviewlib.javabean.ClassfiyBean;
import com.louisgeek.dropdownviewlib.tools.SizeTool;
import com.louisgeek.dropdownviewlib.tools.StringTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louisgeek on 2016/8/9.
 */
public class ClassfiySeletView extends TextView implements View.OnClickListener{
    Context mContext;
    private static final String TAG = "ClassfiySeletView";
    public static final String FIX_KEY_DEFAULT = "key_";
    //private String fix_key=FIX_KEY_DEFAULT;
    List<ClassfiyBean> mClassfiyBeanList;
    int nowPPos=0;
    ClassfiySeletPopupWindow myPopupwindow;
    String mDefaultKey="";
    String mShowName="";
    int mListMaxHeight;
    public final String DEFAULT_NAME="全部分类";
    public ClassfiySeletView(Context context) {
        this(context,null,0);
    }

    public ClassfiySeletView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClassfiySeletView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initInnerData();
        initView();
    }

    /**
     * classfiyBeanList的子列表设置为null就单纯展示单个列表
     * @param classfiyBeanList
     */
    public void setupClassfiyBeanList(List<ClassfiyBean> classfiyBeanList) {
        //myPopupwindow.refreshData(classfiyBeanList);
        mClassfiyBeanList.clear();
        mClassfiyBeanList.addAll(classfiyBeanList);
    }

    public void setListMaxHeight(int listMaxHeight) {
        mListMaxHeight = listMaxHeight;
    }

    private void initInnerData() {
        mClassfiyBeanList=new ArrayList<>();
        for (int i = 0; i <15 ; i++) {
            ClassfiyBean classfiyBean=new ClassfiyBean();
            classfiyBean.setID(i);
            classfiyBean.setBeanID(FIX_KEY_DEFAULT+i);
            classfiyBean.setName("父分类"+i);
            classfiyBean.setSelected(false);

            List<ClassfiyBean.ChildClassfiyBean> cccs=new ArrayList<>();
            for (int j = 0; j < (int)(Math.random()*10+3); j++) {//Math.random():获取0~1随机数
                ClassfiyBean.ChildClassfiyBean ccc=new ClassfiyBean.ChildClassfiyBean();
                ccc.setID(j);
                ccc.setBeanID(FIX_KEY_DEFAULT+j);
                ccc.setName("父分类"+i+"下面的子项"+j);
                ccc.setCount(""+(int)(Math.random()*20+1));//Math.random():获取0~1随机数
                cccs.add(ccc);
                classfiyBean.setSelected(false);
            }
            classfiyBean.setChildClassfiyBeanList(cccs);
            mClassfiyBeanList.add(classfiyBean);
        }
    }
    private void initView() {
        if (this.getText()==null|| StringTool.isNullOrNullStrOrBlankStr(this.getText().toString())) {
            this.setText(DEFAULT_NAME);
        }
        if (this.getPaddingTop()==0&&this.getPaddingBottom()==0&&this.getPaddingLeft()==0&&this.getPaddingRight()==0) {
            int paddingLeft_Right = SizeTool.dp2px(mContext, 8);
            int paddingTop_Bottom = SizeTool.dp2px(mContext, 5);
            this.setPadding(paddingLeft_Right, paddingTop_Bottom, paddingLeft_Right, paddingTop_Bottom);
        }
        this.setOnClickListener(this);
        this.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_keyboard_arrow_down_blue_grey_400_18dp,0);
        //## this.setBackgroundResource(R.drawable.shape_list);
        this.setSingleLine();
    }

    @Override
    public void onClick(final View v) {
        // bgShow();

        if (onContentViewChangeListener!=null) {
            onContentViewChangeListener.onContentViewShow();
        }


        if (v.getTag()!=null){
            mDefaultKey= String.valueOf(v.getTag());
        }
        myPopupwindow=new ClassfiySeletPopupWindow(mContext,mClassfiyBeanList,mDefaultKey,mListMaxHeight);

        //  myPopupwindow.set

        //
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        // int height = metric.heightPixels;   // 屏幕高度（像素）

        myPopupwindow.setWidth(width);
        myPopupwindow.setOnItemSelectedListener(new ClassfiySeletPopupWindow.OnItemSelectedListener() {
            @Override
            public void onItemSelected(String key, String name) {
                ClassfiySeletView.this.setText(name);
                ClassfiySeletView.this.setTag(key);
                String keyWithOutFix=key;
                if (key!=null&&!key.equals("")&&key.contains(FIX_KEY_DEFAULT)){
                    keyWithOutFix=key.replace(FIX_KEY_DEFAULT,"");
                }
                if (onContentViewChangeListener!=null) {
                    onContentViewChangeListener.onContentViewItemSeleted(key, keyWithOutFix, name);
                }
            }
        });
        myPopupwindow.showAsDropDown(v);
        myPopupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // bgClear();
                if (onContentViewChangeListener!=null) {
                    onContentViewChangeListener.onContentViewDismiss();
                }
            }
        });

    }

    // 设置背景颜色变暗
    void  bgShow(){
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = 0.5f; // 0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    void  bgClear(){
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = 1.0f; // 0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    public  interface  OnContentViewChangeListener{
        void onContentViewShow();
        void onContentViewDismiss();
        void onContentViewItemSeleted(String key,String keyWithOutFix, String name);
    }

    public void setOnContentViewChangeListener(OnContentViewChangeListener onContentViewChangeListener) {
        this.onContentViewChangeListener = onContentViewChangeListener;
    }

    private  OnContentViewChangeListener onContentViewChangeListener;


    /**
     *
     * @param key
     */
    public void setupSelectedByKey(String key){
        mDefaultKey=key;
        //
        dealKeyForName();
        if (!mShowName.equals("")){
            this.setText(mShowName);
        }
    }
    private void dealKeyForName() {
        String key_parent="";
        String key_child="";
        String leftName="";
        String rightName="";
        if (mDefaultKey==null||mDefaultKey.equals("")){
            return;
        }
        String hasAllTag="全部";
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
            hasAllTag="";
            key_parent=mDefaultKey;
        }
        if (!key_parent.equals("")){
            for (int i = 0; i < mClassfiyBeanList.size(); i++) {
                if (key_parent.equals(mClassfiyBeanList.get(i).getBeanID())){
                    leftName=mClassfiyBeanList.get(i).getName();
                    nowPPos=i;
                    break;
                }
            }
        }
        if (!key_child.equals("")) {
            List<ClassfiyBean.ChildClassfiyBean> cbccbs = mClassfiyBeanList.get(nowPPos).getChildClassfiyBeanList();
            for (int j = 0; j < cbccbs.size(); j++) {
                if (key_child.equals(cbccbs.get(j).getBeanID())) {
                    rightName = cbccbs.get(j).getName();
                    break;
                }
            }
        }
        if (leftName.equals("")&&rightName.equals("")){
            mShowName="";
        }else if (rightName.equals("")){
            mShowName=hasAllTag+leftName;
        }else{
            mShowName=rightName;
        }

    }

    /**
     *
     * @param parentPos
     * @param childPos
     */
    @Deprecated
    public void setupClassfiyByPosition(int parentPos,int childPos){
        //childPos -1时代表 选中全部
        if (parentPos<0||childPos<-1){
            return;
        }
        //clear
        for (int i = 0; i < mClassfiyBeanList.size(); i++) {
            mClassfiyBeanList.get(i).setSelected(false);
            for (int j = 0; j <mClassfiyBeanList.get(i).getChildClassfiyBeanList().size() ; j++) {
                mClassfiyBeanList.get(i).getChildClassfiyBeanList().get(j).setSelected(false);
            }
        }
        //
        mClassfiyBeanList.get(parentPos).setSelected(true);
        if (childPos>=0) {
            mClassfiyBeanList.get(parentPos).getChildClassfiyBeanList().get(childPos).setSelected(true);
        }
    }

    /**
     *
     * @return
     */
    public String  getSelectedClassfiyKey(){
        String key="";
        if (this.getTag()!=null)
        {
            key=String.valueOf(this.getTag());
        }
        return  key;
    }
    /**
     *
     * @return
     */
    public String  getSelectedClassfiyKeyWithoutFix(){
        String key=getSelectedClassfiyKey();
        String tempKey=key;
        if (tempKey!=null&&!tempKey.equals("")&&tempKey.contains(FIX_KEY_DEFAULT)){
            key=tempKey.replace(FIX_KEY_DEFAULT,"");
        }
        return  key;
    }




}

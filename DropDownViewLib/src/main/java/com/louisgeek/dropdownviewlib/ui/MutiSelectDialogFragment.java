package com.louisgeek.dropdownviewlib.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.louisgeek.dropdownviewlib.MultiSelectLayout;
import com.louisgeek.dropdownviewlib.MultiSelectLayout_HasChild;
import com.louisgeek.dropdownviewlib.R;
import com.louisgeek.dropdownviewlib.javabean.MultiSelectHasChildBean;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by louisgeek on 2016/6/7.
 */
public class MutiSelectDialogFragment extends DialogFragment {

    private final static   String TITLE_KEY="TitleKey";
    private final static   String IS_HAS_CHILD_KEY="isHasChildKey";
    TextView id_tv_ok;
    TextView id_tv_cancel;
     MultiSelectLayout id_msl;
    MultiSelectLayout_HasChild id_msl_gac;
    Dialog dialog;
    boolean mIsHasChild;
  /*  public void setupMultiSelectMapList(List<Map<String, Object>> multiSelectMapList) {
        mMultiSelectMapList = multiSelectMapList;

    }*/

   private   List<Map<String, Object>>  mMultiSelectMapListOut;
    private List<MultiSelectHasChildBean> mMultiSelectHasChildBeanList;
    public static MutiSelectDialogFragment newInstance(String title,boolean isHasChild) {
        MutiSelectDialogFragment myDialogFragment = new MutiSelectDialogFragment();
        Bundle args = new Bundle();
        // 自定义的标题
        args.putString(TITLE_KEY, title);
        args.putBoolean(IS_HAS_CHILD_KEY,isHasChild);
        myDialogFragment.setArguments(args);
        return myDialogFragment;
    }

    /**
     * 在onCreateView中使用  getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);即可去掉
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String title = getArguments().getString(TITLE_KEY);
        mIsHasChild= getArguments().getBoolean(IS_HAS_CHILD_KEY);
        dialog=this.getDialog();
        if (title.equals("")) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//
        }else{
            dialog.setTitle(title);
        }
        View view;

        if (mIsHasChild){
            view=inflater.inflate(R.layout.layout_mutiselect_dialog_frag_has_child,container,false);
            id_msl_gac= (MultiSelectLayout_HasChild) view.findViewById(R.id.id_msl_gac);

            id_msl_gac.setOnBtnClickListener(new MultiSelectLayout_HasChild.OnBtnClickListener() {
                @Override
                public void onOkBtnClick(View v, List<MultiSelectHasChildBean> multiSelectMapList, List<MultiSelectHasChildBean> selectMapList) {
                    dialog.dismiss();
                    onBackHasChildDataListener.onBackHasChildData(multiSelectMapList,selectMapList);

                }

                @Override
                public void onCancelBtnClick(View v) {
                    dialog.dismiss();

                }
            });
        }else{
            view=inflater.inflate(R.layout.layout_mutiselect_dialog_frag,container,false);
            id_msl= (MultiSelectLayout) view.findViewById(R.id.id_msl);

        id_msl.setOnBtnClickListener(new MultiSelectLayout.OnBtnClickListener() {
            @Override
            public void onOkBtnClick(View v, List<Map<String, Object>> multiSelectMapList, List<Map<String, Object>> selectMapList) {
                dialog.dismiss();
                onBackDataListener.onBackData(multiSelectMapList,selectMapList);

              /*  StringBuilder sb=new StringBuilder("选中"+selectMapList.size()+"项,分别是:");
                for (int i = 0; i < selectMapList.size(); i++) {
                            String name=  selectMapList.get(i).get("name").toString();
                            sb.append(name);
                        }
                Toast.makeText(getActivity(), ""+sb.toString(), Toast.LENGTH_LONG).show();*/
            }

            @Override
            public void onCancelBtnClick(View v) {
                dialog.dismiss();
            }
        });
       // id_msv.setupMultiSelectMapListInner(mMultiSelectMapList);
        //return super.onCreateView(inflater, container, savedInstanceState);

        }
        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /**
         * 设置数据
         */
        if (mMultiSelectMapListOut!=null){
            id_msl.setupMultiSelectMapList(mMultiSelectMapListOut);//初始化view后  初始化数据
        }
        //
        Log.d("tag", "louis:3 onViewCreated: mMultiSelectHasChildBeanList:"+mMultiSelectHasChildBeanList);
        if (mMultiSelectHasChildBeanList!=null){
            id_msl_gac.setupMultiSelectHasChildBeanList(mMultiSelectHasChildBeanList);
        }
    }


    /**
     * set date  for  onViewCreated
     *
     * @param multiSelectMapListOut
     */
    public void setupMultiSelectMapListOut(List<Map<String,Object>> multiSelectMapListOut,List<MultiSelectHasChildBean> multiSelectMapList){
        mMultiSelectMapListOut=multiSelectMapListOut;
        Logger.d("setupMultiSelectMapListOut:mMultiSelectMapListOut:"+mMultiSelectMapListOut);
        mMultiSelectHasChildBeanList=multiSelectMapList;
        Logger.d("setupMultiSelectMapListOut:multiSelectMapList: "+multiSelectMapList);
    }

    public  interface  OnBackDataListener{
        void onBackData(List<Map<String, Object>> multiSelectMapList,List<Map<String, Object>> selectMapList);
    }

    public void setOnBackDataListener(OnBackDataListener onBackDataListener) {
        this.onBackDataListener = onBackDataListener;
    }

    OnBackDataListener onBackDataListener;

    public  interface  OnBackHasChildDataListener{
        void onBackHasChildData(List<MultiSelectHasChildBean> multiSelectMapList, List<MultiSelectHasChildBean> selectMapList);
    }

    public void setOnBackHasChildDataListener(OnBackHasChildDataListener onBackHasChildDataListener) {
        this.onBackHasChildDataListener = onBackHasChildDataListener;
    }

    OnBackHasChildDataListener onBackHasChildDataListener;
}

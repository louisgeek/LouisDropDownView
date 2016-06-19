package com.louisgeek.dropdownviewlib.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.louisgeek.dropdownviewlib.MultiSelectView;
import com.louisgeek.dropdownviewlib.R;

import java.util.List;
import java.util.Map;

/**
 * Created by louisgeek on 2016/6/7.
 */
public class MutiSelectDialogFragment extends DialogFragment {

    private final static   String TITLE_KEY="TitleKey";

    TextView id_tv_ok;
    TextView id_tv_cancel;
     MultiSelectView id_msv;
    Dialog dialog;
  /*  public void setupMultiSelectMapList(List<Map<String, Object>> multiSelectMapList) {
        mMultiSelectMapList = multiSelectMapList;

    }*/

   private   List<Map<String, Object>>  mMultiSelectMapListOut;
    public static MutiSelectDialogFragment newInstance(String title) {
        MutiSelectDialogFragment myDialogFragment = new MutiSelectDialogFragment();
        Bundle args = new Bundle();
        // 自定义的标题
        args.putString(TITLE_KEY, title);
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
        dialog=this.getDialog();
        if (title.equals("")) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//
        }else{
            dialog.setTitle(title);
        }
        View view=inflater.inflate(R.layout.mutiselect_dialog_frag_list_content,container,false);

        id_msv= (MultiSelectView) view.findViewById(R.id.id_msv);
        id_msv.setOnBtnClickListener(new MultiSelectView.OnBtnClickListener() {
            @Override
            public void onOkBtnClick(View v, List<Map<String, Object>> multiSelectMapList, List<Map<String, Object>> selectMapList) {
                dialog.dismiss();

                StringBuilder sb=new StringBuilder("选中"+selectMapList.size()+"项,分别是:");
                for (int i = 0; i < selectMapList.size(); i++) {
                            String name=  selectMapList.get(i).get("name").toString();
                            sb.append(name);
                        }
                Toast.makeText(getActivity(), ""+sb.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelBtnClick(View v) {
                dialog.dismiss();
            }
        });
       // id_msv.setupMultiSelectMapListInner(mMultiSelectMapList);
        //return super.onCreateView(inflater, container, savedInstanceState);


        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /**
         * 设置数据
         */
        if (mMultiSelectMapListOut!=null){
            id_msv.setupMultiSelectMapList(mMultiSelectMapListOut);//初始化view后  初始化数据
        }
    }


    /**
     * set date  for  onViewCreated
     *
     * @param multiSelectMapListOut
     */
    public void setupMultiSelectMapListOut(List<Map<String,Object>> multiSelectMapListOut){
        mMultiSelectMapListOut=multiSelectMapListOut;
    }

}

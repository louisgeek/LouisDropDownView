package com.louisgeek.dropdownviewlib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.louisgeek.dropdownviewlib.adapter.MultiSelectViewContentBaseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MultiSelectLayout extends RelativeLayout {

    private  Context mContext;

    ListView id_lv ;
    TextView id_tv_select_all ;
    TextView id_tv_back_select_all ;
    TextView id_tv_title ;

    MultiSelectViewContentBaseAdapter mMultiSelectViewAdapter;

    public MultiSelectLayout(Context context) {
        super(context);
        init(context);
    }
    public MultiSelectLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MultiSelectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext=context;

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_mutiselect_dialog_frag_content_list, this);
        ExpandableListView id_elv = (ExpandableListView) view.findViewById(R.id.id_elv);
        id_elv.setVisibility(GONE);
         id_lv = (ListView) view.findViewById(R.id.id_lv);
         id_tv_select_all = (TextView) view.findViewById(R.id.id_tv_select_all);
         id_tv_back_select_all = (TextView) view.findViewById(R.id.id_tv_back_select_all);
         id_tv_title = (TextView) view.findViewById(R.id.id_tv_title);
        id_tv_title.setText("选项(0)");


        initMultiSelectView();

        id_tv_select_all.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mMultiSelectViewAdapter.isAllSeleted()) {
                     mMultiSelectViewAdapter.seletAll();
                }else{
                     mMultiSelectViewAdapter.unSeletAll();
                }
                refreshTextData();
            }
        });
        id_tv_back_select_all.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mMultiSelectViewAdapter.backSelet();
                refreshTextData();
            }
        });



        TextView id_tv_ok= (TextView) view.findViewById(R.id.id_tv_ok);
        id_tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBtnClickListener.onOkBtnClick(v,getMultiSelectMapList(),getSelectedMapList());
            }
        });
        TextView id_tv_cancel= (TextView) view.findViewById(R.id.id_tv_cancel);
        id_tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnClickListener.onCancelBtnClick(v);

            }
        });

    }
    private void initMultiSelectView() {
        /**
         * 默认没有数据
         */
        List<Map<String, Object>> multiSelectMapList=new ArrayList<>();
        mMultiSelectViewAdapter=new MultiSelectViewContentBaseAdapter(mContext,multiSelectMapList);
        id_lv.setAdapter(mMultiSelectViewAdapter);
        id_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMultiSelectViewAdapter.seletOne(view,position);
                refreshTextData();
            }
        });
    }
    public void setupMultiSelectMapList(List<Map<String, Object>> multiSelectMapList) {
        mMultiSelectViewAdapter.updateMultiSelectMapListInner(multiSelectMapList);
        refreshTextData();
    }

    private void refreshTextData() {
        int count = mMultiSelectViewAdapter.getCheckNum();
        id_tv_title.setText("选项("+count+")");
    }

    /*  public void  setupSeletedItemMapList( List<Map<String, Object>> seletedItemMapList){
          mMapList.clear();
          mMapList.addAll(seletedItemMapList);
          mMultiSelectViewAdapter.notifyDataSetChanged();
      }*/

    /**
     * 取到所有的(包括未选中的)
     * @return
     */
    public List<Map<String, Object>>  getMultiSelectMapList(){
        return  mMultiSelectViewAdapter.getMultiSelectMapListInner();
    }
    /**
     * 取到所有的选中的
     * @return
     */
    public List<Map<String, Object>>  getSelectedMapList(){
        List<Map<String, Object>> multiSelectMapList=getMultiSelectMapList();
        List<Map<String, Object>> multiSelectMapListTemp=new ArrayList<>();
        for (int i = 0; i < multiSelectMapList.size(); i++) {
            if(multiSelectMapList.get(i).get("checked")!=null){
                if (Boolean.parseBoolean(multiSelectMapList.get(i).get("checked").toString())){
                    multiSelectMapListTemp.add(multiSelectMapList.get(i));
                }
            }
        }
        return  multiSelectMapListTemp;
    }

    public interface OnBtnClickListener{
        /**
         *
         * @param v
         * @param multiSelectMapList  总的list
         * @param selectMapList   选中的list
         */
        void   onOkBtnClick(View v, List<Map<String, Object>> multiSelectMapList,List<Map<String, Object>> selectMapList);
        void  onCancelBtnClick(View v);
    }

    public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
        this.onBtnClickListener = onBtnClickListener;
    }

    OnBtnClickListener onBtnClickListener;
}

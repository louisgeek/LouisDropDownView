package com.louisgeek.dropdownviewlib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.louisgeek.dropdownviewlib.adapter.MultiSelectViewContentBaseExpandableListAdapter;
import com.louisgeek.dropdownviewlib.javabean.MultiSelectHasChildBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by louisgeek on 2016/6/17.
 */
public class MultiSelectLayout_HasChild extends RelativeLayout {

    private  Context mContext;

    private ExpandableListView id_elv ;
    private TextView id_tv_select_all ;
    private TextView id_tv_back_select_all ;
    private TextView id_tv_title ;

    //MultiSelectViewContentBaseAdapter mMultiSelectViewAdapter;
    private MultiSelectViewContentBaseExpandableListAdapter mMultiSelectViewContentBaseExpandableListAdapter;

    public MultiSelectLayout_HasChild(Context context) {
        super(context);
        init(context);
    }
    public MultiSelectLayout_HasChild(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MultiSelectLayout_HasChild(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext=context;

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_mutiselect_dialog_frag_content_list, this);

        ListView id_lv = (ListView) view.findViewById(R.id.id_lv);
        id_lv.setVisibility(GONE);
        id_elv = (ExpandableListView) view.findViewById(R.id.id_elv);
        //设置 属性 GroupIndicator 去掉默认向下的箭头
        id_elv.setGroupIndicator(null);

        id_tv_select_all = (TextView) view.findViewById(R.id.id_tv_select_all);
         id_tv_back_select_all = (TextView) view.findViewById(R.id.id_tv_back_select_all);
         id_tv_title = (TextView) view.findViewById(R.id.id_tv_title);
        id_tv_title.setText("选项(0)");


        initMultiSelectView();

        id_tv_select_all.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mMultiSelectViewContentBaseExpandableListAdapter.isAllSeleted()) {
                    mMultiSelectViewContentBaseExpandableListAdapter.seletAll();
                }else{
                    mMultiSelectViewContentBaseExpandableListAdapter.unSeletAll();
                }
                refreshTextData();
            }
        });
        id_tv_back_select_all.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mMultiSelectViewContentBaseExpandableListAdapter.backSelet();
                refreshTextData();
            }
        });



        TextView id_tv_ok= (TextView) view.findViewById(R.id.id_tv_ok);
        id_tv_ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              onBtnClickListener.onOkBtnClick(v,getMultiSelectMapListInnerGroupAndChildBeanList(),getSelectedMapGroupAndChildBeanListOnly());
            }
        });
        TextView id_tv_cancel= (TextView) view.findViewById(R.id.id_tv_cancel);
        id_tv_cancel.setOnClickListener(new OnClickListener() {
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
        List<MultiSelectHasChildBean> multiSelectHasChildBeanList=new ArrayList<>();
        mMultiSelectViewContentBaseExpandableListAdapter=new MultiSelectViewContentBaseExpandableListAdapter(mContext,multiSelectHasChildBeanList);
        id_elv.setAdapter(mMultiSelectViewContentBaseExpandableListAdapter);
        /*id_elv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMultiSelectViewAdapter.seletOne(view,position);
                refreshTextData();
            }
        });*/
        id_elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
               // Toast.makeText(getContext(), "groupPosition:"+groupPosition, Toast.LENGTH_SHORT).show();
                return true;//
            }
        });
        id_elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                mMultiSelectViewContentBaseExpandableListAdapter.seletOne(v,groupPosition,childPosition);
                refreshTextData();
                return false;
            }
        });
    }
    public void setupMultiSelectHasChildBeanList(List<MultiSelectHasChildBean> multiSelectHasChildBeanList) {
        mMultiSelectViewContentBaseExpandableListAdapter.updateMultiSelectHasChildBeanList(multiSelectHasChildBeanList);
        //默认展开所有
        for(int i = 0; i < mMultiSelectViewContentBaseExpandableListAdapter.getGroupCount(); i++){
            id_elv.expandGroup(i);
        }
        refreshTextData();
    }

    private void refreshTextData() {
        int count = mMultiSelectViewContentBaseExpandableListAdapter.getCheckNum();
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
    public List<MultiSelectHasChildBean>   getMultiSelectMapListInnerGroupAndChildBeanList(){
        return  mMultiSelectViewContentBaseExpandableListAdapter.getMultiSelectMapListInnerGroupAndChildBeanList();
    }
    /**
     * 取到所有的选中的
     * @return
     */
    public List<MultiSelectHasChildBean> getSelectedMapGroupAndChildBeanListOnly(){
        List<MultiSelectHasChildBean> multiSelectMapListInnerGroupAndChildBeanList=getMultiSelectMapListInnerGroupAndChildBeanList();;

        List<MultiSelectHasChildBean> multiSelectMapListInnerGroupAndChildBeanListTemp=new ArrayList<>();
        for (int j = 0; j < multiSelectMapListInnerGroupAndChildBeanList.size(); j++) {
            List<Map<String, Object>> multiSelectMapList=multiSelectMapListInnerGroupAndChildBeanList.get(j).getMultiSelectMapList();
            MultiSelectHasChildBean multiSelectMapListInnerGroupAndChildBean=new MultiSelectHasChildBean();
            multiSelectMapListInnerGroupAndChildBean.setName(multiSelectMapListInnerGroupAndChildBeanList.get(j).getName());
            multiSelectMapListInnerGroupAndChildBean.setID(multiSelectMapListInnerGroupAndChildBeanList.get(j).getID());

            List<Map<String, Object>> multiSelectMapListTemp=new ArrayList<>();
            for (int i = 0; i < multiSelectMapList.size(); i++) {
                if(multiSelectMapList.get(i).get("checked")!=null){
                    if (Boolean.parseBoolean(multiSelectMapList.get(i).get("checked").toString())){
                        multiSelectMapListTemp.add(multiSelectMapList.get(i));
                    }
                }
            }
            multiSelectMapListInnerGroupAndChildBean.setMultiSelectMapList(multiSelectMapListTemp);
            multiSelectMapListInnerGroupAndChildBeanListTemp.add(multiSelectMapListInnerGroupAndChildBean);
        }

        return  multiSelectMapListInnerGroupAndChildBeanListTemp;
    }
    /**
     * 取到所有的选中的
     * @return
     */
    public List<Map<String, Object>> getSelectMapListInAll(){
        List<MultiSelectHasChildBean> multiSelectMapListInnerGroupAndChildBeanList=getSelectedMapGroupAndChildBeanListOnly();;

        List<Map<String, Object>> selectMapListInAllTemp=new ArrayList<>();
        for (int j = 0; j < multiSelectMapListInnerGroupAndChildBeanList.size(); j++) {
            List<Map<String, Object>> multiSelectMapList=multiSelectMapListInnerGroupAndChildBeanList.get(j).getMultiSelectMapList();

            for (int i = 0; i < multiSelectMapList.size(); i++) {
                if(multiSelectMapList.get(i).get("checked")!=null){
                    if (Boolean.parseBoolean(multiSelectMapList.get(i).get("checked").toString())){
                        selectMapListInAllTemp.add(multiSelectMapList.get(i));
                    }
                }
            }
        }

        return  selectMapListInAllTemp;
    }
    public interface OnBtnClickListener{
        /**
         *
         * @param v
         * @param multiSelectMapList  总的list
         * @param selectMapList   选中的list
         */
        void   onOkBtnClick(View v, List<MultiSelectHasChildBean> multiSelectMapList, List<MultiSelectHasChildBean> selectMapList);
        void  onCancelBtnClick(View v);
    }

    public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
        this.onBtnClickListener = onBtnClickListener;
    }

    OnBtnClickListener onBtnClickListener;
}

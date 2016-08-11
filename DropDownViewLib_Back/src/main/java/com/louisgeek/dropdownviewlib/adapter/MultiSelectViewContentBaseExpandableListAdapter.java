package com.louisgeek.dropdownviewlib.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.louisgeek.dropdownviewlib.R;
import com.louisgeek.dropdownviewlib.javabean.MultiSelectHasChildBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by louisgeek on 2016/7/25.
 */
public class MultiSelectViewContentBaseExpandableListAdapter extends BaseExpandableListAdapter{
    private List<MultiSelectHasChildBean> mMultiSelectHasChildBeanList;
 //   private List<Map<String, Object>> mMultiSelectMapListInner;
 public void updateMultiSelectHasChildBeanList(List<MultiSelectHasChildBean> multiSelectHasChildBeanList) {
     Log.d("tag", "louis:6 updateMultiSelectHasChildBeanList: multiSelectHasChildBeanList:"+multiSelectHasChildBeanList);
     mMultiSelectHasChildBeanList.clear();
     mMultiSelectHasChildBeanList.addAll(multiSelectHasChildBeanList);
     Log.d("tag", "louis:7 updateMultiSelectHasChildBeanList: mMultiSelectHasChildBeanList:"+mMultiSelectHasChildBeanList);
     initSelectedData();//重新初始化
     this.notifyDataSetChanged();

 }
    public int getCheckNum() {
        return checkNum<0?0:checkNum;
    }
    public MultiSelectViewContentBaseExpandableListAdapter(Context context, List<MultiSelectHasChildBean> multiSelectHasChildBeanList) {
        this.context = context;
        mMultiSelectHasChildBeanList = multiSelectHasChildBeanList;
        Log.d("tag", "louis:12 MultiSelectViewContentBaseExpandableListAdapter: mMultiSelectHasChildBeanList:"+mMultiSelectHasChildBeanList);
        inflater = LayoutInflater.from(context);
        // 初始化数据
        initSelectedData();
    }
    // 初始化isSelected的数据
    private void initSelectedData() {
        isGroupChildSelectedMap=new HashMap<>();
        for (int j = 0; j < mMultiSelectHasChildBeanList.size(); j++) {
            if(mMultiSelectHasChildBeanList.get(j).getMultiSelectMapList()!=null&&mMultiSelectHasChildBeanList.get(j).getMultiSelectMapList().size()>0){
                for (int i = 0; i < mMultiSelectHasChildBeanList.get(j).getMultiSelectMapList().size(); i++) {
                    if (Boolean.parseBoolean(String.valueOf(mMultiSelectHasChildBeanList.get(j).getMultiSelectMapList().get(i).get("checked")))) {
                        isGroupChildSelectedMap.put("group_"+j+"_child_"+i, true);//索引
                        checkNum++;
                    }else{
                        isGroupChildSelectedMap.put("group_"+j+"_child_"+i, false);//索引
                    }
                }}

        }

    }
    // 用来控制CheckBox的选中状况
    private Map<String, Boolean> isGroupChildSelectedMap;
    private Context context;
    // 用来导入布局
    private LayoutInflater inflater = null;
    int checkNum = 0;

    @Override
    public int getGroupCount() {
        Log.d("tag", "louis:10 getGroupCount: mMultiSelectHasChildBeanList.size():"+mMultiSelectHasChildBeanList.size());
        return mMultiSelectHasChildBeanList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.d("tag", "louis:11 getChildrenCount: mMultiSelectHasChildBeanList.get(groupPosition).getMultiSelectMapList().size():"+mMultiSelectHasChildBeanList.get(groupPosition).getMultiSelectMapList().size());
        return mMultiSelectHasChildBeanList.get(groupPosition).getMultiSelectMapList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        Log.d("tag", "louis:8 getGroup: mMultiSelectHasChildBeanList.get(groupPosition):"+mMultiSelectHasChildBeanList.get(groupPosition));
        return mMultiSelectHasChildBeanList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.d("tag", "louis:9 getChild: mMultiSelectHasChildBeanList.get(groupPosition).getMultiSelectMapList().get(childPosition):"+mMultiSelectHasChildBeanList.get(groupPosition).getMultiSelectMapList().get(childPosition));

        return mMultiSelectHasChildBeanList.get(groupPosition).getMultiSelectMapList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        Log.d("tag", "louis:13 groupPosition::"+groupPosition);
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        Log.d("tag", "louis:14 childPosition:"+childPosition);
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Log.d("tag", "louis:4 getGroupView: mMultiSelectHasChildBeanList:"+mMultiSelectHasChildBeanList);
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            // 获得ViewHolder对象
            groupViewHolder = new GroupViewHolder();
            // 导入布局并赋值给convertview
            convertView = inflater.inflate(R.layout.mutiselect_dialog_frag_list_group_item, parent, false);
            groupViewHolder.tv = (TextView) convertView.findViewById(R.id.item_tv);
            //groupViewHolder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);
            // 为view设置标签
            convertView.setTag(groupViewHolder);
        } else {
            // 取出holder
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.tv.setText(mMultiSelectHasChildBeanList.get(groupPosition).getName());

       // groupViewHolder.cb.setChecked(isSelectedMap.get(childPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Log.d("tag", "louis:5 getChildView: mMultiSelectHasChildBeanList:"+mMultiSelectHasChildBeanList);
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            // 获得ViewHolder对象
            childViewHolder = new ChildViewHolder();
            // 导入布局并赋值给convertview
            convertView = inflater.inflate(R.layout.mutiselect_dialog_frag_list_item, parent, false);
            childViewHolder.tv = (TextView) convertView.findViewById(R.id.item_tv);
            childViewHolder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);
            // 为view设置标签
            convertView.setTag(childViewHolder);
        } else {
            // 取出holder
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.tv.setText(mMultiSelectHasChildBeanList.get(groupPosition).getMultiSelectMapList().get(childPosition).get("name").toString());
        // 根据isSelected来设置checkbox的选中状况
        childViewHolder.cb.setChecked(isGroupChildSelectedMap.get("group_"+groupPosition+"_child_"+childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public static class GroupViewHolder {
        TextView tv;
        //CheckBox cb;
    }

    public static class ChildViewHolder {
        TextView tv;
        CheckBox cb;
    }


    public void seletOne(View view,int g_pos,int c_pos) {
        ChildViewHolder childViewHolder= (ChildViewHolder) view.getTag();
        childViewHolder.cb.toggle();//转变
        isGroupChildSelectedMap.put("group_"+g_pos+"_child_"+c_pos,childViewHolder.cb.isChecked());
        if (childViewHolder.cb.isChecked()){
            checkNum++;
        }else{
            checkNum--;
        }
        this.notifyDataSetChanged();
        checkNum=checkNum<0?0:checkNum;
    }
    public void seletAll() {
        for (int j = 0; j <mMultiSelectHasChildBeanList.size() ; j++) {
        // 遍历list，全部设为true
        for (int i = 0; i < mMultiSelectHasChildBeanList.get(j).getMultiSelectMapList().size(); i++) {
            if (!isGroupChildSelectedMap.get("group_"+j+"_child_"+i)) {
                isGroupChildSelectedMap.put("group_"+j+"_child_"+i, true);
                checkNum++;
            }
        }
        }
        this.notifyDataSetChanged();
        checkNum=checkNum<0?0:checkNum;
    }

    public void unSeletAll() {
        for (int j = 0; j <mMultiSelectHasChildBeanList.size() ; j++) {
        // 遍历list，全部设为false
        for (int i = 0; i < mMultiSelectHasChildBeanList.get(j).getMultiSelectMapList().size(); i++) {
            if (isGroupChildSelectedMap.get("group_"+j+"_child_"+i)) {
                isGroupChildSelectedMap.put("group_"+j+"_child_"+i, false);
                checkNum--;
            }
        }
        }
        this.notifyDataSetChanged();
        checkNum=checkNum<0?0:checkNum;
    }



    public void backSelet() {
        for (int j = 0; j <mMultiSelectHasChildBeanList.size() ; j++) {
        for (int i = 0; i < mMultiSelectHasChildBeanList.get(j).getMultiSelectMapList().size(); i++) {
            if (isGroupChildSelectedMap.get("group_"+j+"_child_"+i)) {
                isGroupChildSelectedMap.put("group_"+j+"_child_"+i, false);
                checkNum--;
            } else {
                isGroupChildSelectedMap.put("group_"+j+"_child_"+i, true);
                checkNum++;
            }
        }
        }
        this.notifyDataSetChanged();
        checkNum=checkNum<0?0:checkNum;
    }

    /*
        public List<Map<String, Object>> getSeletedItemDataList(){
            List<Map<String, Object>> seletedItemDataList=new ArrayList<>();
            for (int i = 0; i < mMultiSelectMapList.size();i++) {
                if (isSelectedMap.get(i)) {
                    seletedItemDataList.add(mMultiSelectMapList.get(i));
                }
            }
            return  seletedItemDataList;
        }
    */
    public List<MultiSelectHasChildBean> getMultiSelectMapListInnerGroupAndChildBeanList() {
        for (int j = 0; j <mMultiSelectHasChildBeanList.size() ; j++) {
            for (int i = 0; i < mMultiSelectHasChildBeanList.get(j).getMultiSelectMapList().size(); i++) {
                if (isGroupChildSelectedMap.get("group_"+j+"_child_"+i)) {
                    mMultiSelectHasChildBeanList.get(j).getMultiSelectMapList().get(i).put("checked", true);
                } else {
                    mMultiSelectHasChildBeanList.get(j).getMultiSelectMapList().get(i).put("checked", false);
                }
            }
        }
        return  mMultiSelectHasChildBeanList;
    }

    public  Boolean isAllSeleted(){
        boolean isAllSeleted=true;
        for (int j = 0; j < mMultiSelectHasChildBeanList.size(); j++) {
        for (int i = 0; i < mMultiSelectHasChildBeanList.get(j).getMultiSelectMapList().size(); i++) {
            if (!isGroupChildSelectedMap.get("group_"+j+"_child_"+i)){//有一个未选择  就算没全选
                isAllSeleted=false;//
                break;
            }
        }
        }
        return isAllSeleted;
    }

}

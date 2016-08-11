package com.louisgeek.dropdownviewlib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.louisgeek.dropdownviewlib.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by louisgeek on 2016/6/17.
 */
public class MultiSelectViewContentBaseAdapter extends BaseAdapter {
    public void updateMultiSelectMapListInner(List<Map<String, Object>> multiSelectMapListInner) {
        mMultiSelectMapListInner.clear();
        mMultiSelectMapListInner.addAll(multiSelectMapListInner);
        initSelectedData();//重新初始化
        this.notifyDataSetChanged();
    }

    private List<Map<String, Object>> mMultiSelectMapListInner;
    // 用来控制CheckBox的选中状况
    private Map<Integer, Boolean> isSelectedMap;
    private Context context;
    // 用来导入布局
    private LayoutInflater inflater = null;
    int checkNum = 0;

    public int getCheckNum() {
        return checkNum<0?0:checkNum;
    }

    // 构造器
    public MultiSelectViewContentBaseAdapter(Context context, List<Map<String, Object>> multiSelectMapListInner) {
        this.context = context;
        this.mMultiSelectMapListInner = multiSelectMapListInner;
        inflater = LayoutInflater.from(context);
        // 初始化数据
        initSelectedData();
    }

    // 初始化isSelected的数据
    private void initSelectedData() {
        isSelectedMap=new HashMap<>();
        if(mMultiSelectMapListInner!=null&&mMultiSelectMapListInner.size()>0){
        for (int i = 0; i < mMultiSelectMapListInner.size(); i++) {
            if (Boolean.parseBoolean(String.valueOf(mMultiSelectMapListInner.get(i).get("checked")))) {
                isSelectedMap.put(i, true);//索引
                checkNum++;
            }else{
                isSelectedMap.put(i, false);//索引
            }
        }}
    }

    @Override
    public int getCount() {
        return mMultiSelectMapListInner.size();
    }

    @Override
    public Object getItem(int position) {
        return mMultiSelectMapListInner.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            // 获得ViewHolder对象
            holder = new ViewHolder();
            // 导入布局并赋值给convertview
            convertView = inflater.inflate(R.layout.mutiselect_dialog_frag_list_item, parent, false);
            holder.tv = (TextView) convertView.findViewById(R.id.item_tv);
            holder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);
            // 为view设置标签
            convertView.setTag(holder);
        } else {
            // 取出holder
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(mMultiSelectMapListInner.get(position).get("name").toString());
        // 根据isSelected来设置checkbox的选中状况
        holder.cb.setChecked(isSelectedMap.get(position));
        return convertView;
    }


    public static class ViewHolder {
        TextView tv;
        CheckBox cb;
    }

    public void seletOne(View view,int pos) {
        ViewHolder viewHolder= (ViewHolder) view.getTag();
        viewHolder.cb.toggle();//转变
        isSelectedMap.put(pos,viewHolder.cb.isChecked());
        if (viewHolder.cb.isChecked()){
            checkNum++;
        }else{
            checkNum--;
        }
        this.notifyDataSetChanged();
        checkNum=checkNum<0?0:checkNum;
    }
    public void seletAll() {
        // 遍历list，全部设为true
        for (int i = 0; i < mMultiSelectMapListInner.size(); i++) {
            if (!isSelectedMap.get(i)) {
                isSelectedMap.put(i, true);
                checkNum++;
            }
        }
        this.notifyDataSetChanged();
        checkNum=checkNum<0?0:checkNum;
    }

    public void unSeletAll() {
        // 遍历list，全部设为false
        for (int i = 0; i < mMultiSelectMapListInner.size(); i++) {
            if (isSelectedMap.get(i)) {
                isSelectedMap.put(i, false);
                checkNum--;
            }
        }
        this.notifyDataSetChanged();
        checkNum=checkNum<0?0:checkNum;
    }



    public void backSelet() {
        for (int i = 0; i < mMultiSelectMapListInner.size(); i++) {
            if (isSelectedMap.get(i)) {
                isSelectedMap.put(i, false);
                checkNum--;
            } else {
                isSelectedMap.put(i, true);
                checkNum++;
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
public List<Map<String, Object>> getMultiSelectMapListInner() {
    for (int i = 0; i < mMultiSelectMapListInner.size(); i++) {
        if (isSelectedMap.get(i)){
            mMultiSelectMapListInner.get(i).put("checked",true);
        }else{
            mMultiSelectMapListInner.get(i).put("checked",false);
        }
    }
    return  mMultiSelectMapListInner;
}

    public  Boolean isAllSeleted(){
        boolean isAllSeleted=true;
        for (int i = 0; i < isSelectedMap.size(); i++) {
            if (!isSelectedMap.get(i)){//有一个未选择  就算没全选
                isAllSeleted=false;//
                break;
            }
        }
        return isAllSeleted;
    }

}
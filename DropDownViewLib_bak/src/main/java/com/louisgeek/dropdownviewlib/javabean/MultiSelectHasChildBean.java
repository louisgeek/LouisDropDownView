package com.louisgeek.dropdownviewlib.javabean;

import java.util.List;
import java.util.Map;

/**
 * Created by louisgeek on 2016/7/25.
 */
public class MultiSelectHasChildBean {
    private int ID ;
    private String key;
    private String name;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map<String, Object>> getMultiSelectMapList() {
        return mMultiSelectMapList;
    }

    public void setMultiSelectMapList(List<Map<String, Object>> multiSelectMapList) {
        mMultiSelectMapList = multiSelectMapList;
    }

    private List<Map<String, Object>> mMultiSelectMapList;

}

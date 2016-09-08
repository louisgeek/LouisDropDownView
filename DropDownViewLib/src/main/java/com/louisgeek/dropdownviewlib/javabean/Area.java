package com.louisgeek.dropdownviewlib.javabean;

/**
 * Created by louisgeek on 2016/5/30.
 */
public class Area {
    private String areaID;
    private String areaName;

    public Area(String areaID, String areaName, String areaSimpleName, String areaEnName) {
        this.areaID = areaID;
        this.areaName = areaName;
        this.areaSimpleName = areaSimpleName;
        this.areaEnName = areaEnName;
    }

    private String areaSimpleName;
    private String areaEnName;

    public String getAreaID() {
        return areaID;
    }

    public void setAreaID(String areaID) {
        this.areaID = areaID;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaSimpleName() {
        return areaSimpleName;
    }

    public void setAreaSimpleName(String areaSimpleName) {
        this.areaSimpleName = areaSimpleName;
    }

    public String getAreaEnName() {
        return areaEnName;
    }

    public void setAreaEnName(String areaEnName) {
        this.areaEnName = areaEnName;
    }
}

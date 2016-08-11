package com.louisgeek.dropdownviewlib.javabean;

import java.util.List;

/**
 * Created by louisgeek on 2016/5/30.
 */
public class City {
    private   String cityID;
    private   String cityName;

    public City(String cityID, String cityName, String citySimpleName, String cityEnName, List<Area> areas) {
        this.cityID = cityID;
        this.cityName = cityName;
        this.citySimpleName = citySimpleName;
        this.cityEnName = cityEnName;
        this.areas = areas;
    }

    private   String citySimpleName;
    private   String cityEnName;
    private   List<Area> areas;

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCitySimpleName() {
        return citySimpleName;
    }

    public void setCitySimpleName(String citySimpleName) {
        this.citySimpleName = citySimpleName;
    }

    public String getCityEnName() {
        return cityEnName;
    }

    public void setCityEnName(String cityEnName) {
        this.cityEnName = cityEnName;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
}

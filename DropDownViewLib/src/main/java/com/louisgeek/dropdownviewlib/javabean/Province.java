package com.louisgeek.dropdownviewlib.javabean;

import java.util.List;

/**
 * Created by louisgeek on 2016/5/30.
 */
public class Province {
    private String provinceID;
    private String provinceName;
    private String provinceSimpleName;
    private String provinceEnName;

    public Province(String provinceID, String provinceName, String provinceSimpleName, String provinceEnName, List<City> cites) {
        this.provinceID = provinceID;
        this.provinceName = provinceName;
        this.provinceSimpleName = provinceSimpleName;
        this.provinceEnName = provinceEnName;
        this.cites = cites;
    }

    private List<City> cites;

    public String getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(String provinceID) {
        this.provinceID = provinceID;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceSimpleName() {
        return provinceSimpleName;
    }

    public void setProvinceSimpleName(String provinceSimpleName) {
        this.provinceSimpleName = provinceSimpleName;
    }

    public String getProvinceEnName() {
        return provinceEnName;
    }

    public void setProvinceEnName(String provinceEnName) {
        this.provinceEnName = provinceEnName;
    }

    public List<City> getCites() {
        return cites;
    }

    public void setCites(List<City> cites) {
        this.cites = cites;
    }
}

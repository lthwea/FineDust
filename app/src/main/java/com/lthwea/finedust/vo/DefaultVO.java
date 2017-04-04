package com.lthwea.finedust.vo;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by LeeTaeHun on 2017. 4. 3..
 */

public class DefaultVO implements ClusterItem {

    private String cityName;
    private String cityvalue;
    private String dataTime;
    private Double lat;
    private Double lng;


    public DefaultVO(){

    }

    public DefaultVO(Double lat, Double lng, String cityName, String cityvalue) {
        this.cityName = cityName;
        this.cityvalue = cityvalue;
        this.lat = lat;
        this.lng = lng;
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityvalue() {
        return cityvalue;
    }

    public void setCityvalue(String cityvalue) {
        this.cityvalue = cityvalue;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public LatLng getPosition() {
        return null;
    }
}

package com.lthwea.finedust.vo;


import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by LeeTaeHun on 2017. 4. 4..
 */

public class MarkerVO implements ClusterItem {


    private LatLng mPosition;
    private String sidoName;
    private String cityName;
    private String pm10Value;
    private String pm25Value;
    private String so2Value;
    private String o3Value;
    private String no2Value;
    private String coValue;
    private String dataTime;



    public MarkerVO(){

    }


    public MarkerVO(String sidoName, String cityName, LatLng position) {
        this.sidoName = sidoName;
        this.cityName = cityName;
        mPosition = position;
    }

    public MarkerVO(LatLng position, String sidoName, String cityName, String pm10Value) {
            this.sidoName = sidoName;
            this.cityName = cityName;
            this.pm10Value = pm10Value;
            mPosition = position;
    }



    public String getSidoName() {
        return sidoName;
    }

    public void setSidoName(String sidoName) {
        this.sidoName = sidoName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
//
//    public String getCityNameEng() {
//        return cityNameEng;
//    }
//
//    public void setCityNameEng(String cityNameEng) {
//        this.cityNameEng = cityNameEng;
//    }

    public String getPm10Value() {
        return pm10Value;
    }

    public void setPm10Value(String pm10Value) {
        this.pm10Value = pm10Value;
    }

    public String getPm25Value() {
        return pm25Value;
    }

    public void setPm25Value(String pm25Value) {
        this.pm25Value = pm25Value;
    }

    public String getSo2Value() {
        return so2Value;
    }

    public void setSo2Value(String so2Value) {
        this.so2Value = so2Value;
    }

    public String getO3Value() {
        return o3Value;
    }

    public void setO3Value(String o3Value) {
        this.o3Value = o3Value;
    }

    public String getNo2Value() {
        return no2Value;
    }

    public void setNo2Value(String no2Value) {
        this.no2Value = no2Value;
    }

    public String getCoValue() {
        return coValue;
    }

    public void setCoValue(String coValue) {
        this.coValue = coValue;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }


    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public void setPosition(LatLng position){
        this.mPosition = position;
    }
}

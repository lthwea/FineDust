package com.lthwea.finedust.vo;

/**
 * Created by LeeTaeHun on 2017. 4. 10..
 */

public class AlarmVO {
    private int id;
    private String isUse;
    private String sidoName;
    private String cityName;
    private int hour;
    private int min;
    private String days;

    public AlarmVO(){
    }


    public AlarmVO(int id, String isUse, String sidoName, String cityName, int hour, int min, String days) {
        this.id = id;
        this.isUse = isUse;
        this.sidoName = sidoName;
        this.cityName = cityName;
        this.hour = hour;
        this.min = min;
        this.days = days;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getSidoName() {
        return sidoName;
    }

    public void setSidoName(String sidoName) {
        this.sidoName = sidoName;
    }

    public String isUse() {
        return isUse;
    }

    public void setUse(String use) {
        isUse = use;
    }



    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }


    @Override
    public String toString() {
        return "AlarmVO{" +
                "id=" + id +
                ", isUse='" + isUse + '\'' +
                ", cityName='" + cityName + '\'' +
                ", sidoName='" + sidoName + '\'' +
                ", days='" + days + '\'' +
                ", hour=" + hour +
                ", min=" + min +
                '}';
    }

}

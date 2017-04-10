package com.lthwea.finedust.vo;

/**
 * Created by LeeTaeHun on 2017. 4. 10..
 */

public class AlarmVO {
    private boolean isUse;
    private String cityName;
    private String sidoName;
    private boolean[] days;
    private String time;
    private int hour;
    private int min;


    public AlarmVO(){
        days =  new boolean[]{false,false,false,false,false,false,false};
                                //월    화   수    목    금     토     일
    }


    public AlarmVO(boolean isUse, String sidoName, String cityName, boolean[] days, String time, int hour, int min) {
        days =  new boolean[]{};
        this.isUse = isUse;
        this.sidoName = sidoName;
        this.cityName = cityName;
        this.days = days;
        this.time = time;
        this.hour = hour;
        this.min = min;
    }

    public boolean getIsUse() {
        return isUse;
    }

    public void setIsUse(boolean use) {
        isUse = use;
    }


    public boolean[] getDays() {
        return days;
    }

    public void setDays(boolean[] days) {
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

    public boolean isUse() {
        return isUse;
    }

    public void setUse(boolean use) {
        isUse = use;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}

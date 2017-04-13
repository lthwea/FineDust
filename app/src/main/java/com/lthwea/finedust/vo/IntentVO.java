package com.lthwea.finedust.vo;

import com.lthwea.finedust.cnst.MyConst;

/**
 * Created by LeeTaeHun on 2017. 4. 12..
 */

public class IntentVO {


    // true = make marker, false = none, map
    private boolean isAlarmMarker;
    // if isAlarmMarker true, this has a value;
    private String locName;
    private boolean[] checkedDays;
    private String alarmTime;


    // true = add, false = update, alarm for ui Update
    private boolean isAlarmAdd;

    //if isAlarmAdd false, this is a value;
    private int alarmVoId;

    private boolean needListViewUpdate;

    private boolean isAlarmMarking;       // 알람설정에서 메인 갔다왔는지 확인하기 위함, 백버튼에 씀



    public IntentVO(boolean isAlarmMarker, String locName, boolean isAlarmAdd, int alarmVoId, boolean needListViewUpdate, boolean[] checkedDays, boolean isAlarmMarking, String alarmTime) {
        this.isAlarmMarker = isAlarmMarker;
        this.locName = locName;
        this.isAlarmAdd = isAlarmAdd;
        this.alarmVoId = alarmVoId;
        this.needListViewUpdate = needListViewUpdate;
        this.checkedDays = checkedDays;
        this.isAlarmMarking = isAlarmMarking;
        this.alarmTime = alarmTime;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public boolean isAlarmMarking() {
        return isAlarmMarking;
    }

    public void setAlarmMarking(boolean alarmMarking) {
        isAlarmMarking = alarmMarking;
    }

    public boolean[] getCheckedDays() {
        return checkedDays;
    }

    public void setCheckedDays(boolean[] checkedDays) {
        this.checkedDays = checkedDays;
    }


    public boolean getNeedListViewUpdate() {
        return needListViewUpdate;
    }

    public void setNeedListViewUpdate(boolean needListViewUpdate) {
        this.needListViewUpdate = needListViewUpdate;
    }


    public boolean isAlarmMarker() {
        return isAlarmMarker;
    }

    public void setAlarmMarker(boolean alarmMarker) {
        isAlarmMarker = alarmMarker;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public boolean isAlarmAdd() {
        return isAlarmAdd;
    }

    public void setAlarmAdd(boolean alarmAdd) {
        isAlarmAdd = alarmAdd;
    }

    public int getAlarmVoId() {
        return alarmVoId;
    }

    public void setAlarmVoId(int alarmVoId) {
        this.alarmVoId = alarmVoId;
    }


    public void setInitData(){
        this.isAlarmMarker = false;
        this.locName = null;
        this.isAlarmAdd = false;
        this.alarmVoId = MyConst.INTENT_DEFAULT_ID;
        this.needListViewUpdate = false;
        this.checkedDays = null;
        this.isAlarmMarking = false;
        this.alarmTime = null;
    }
}

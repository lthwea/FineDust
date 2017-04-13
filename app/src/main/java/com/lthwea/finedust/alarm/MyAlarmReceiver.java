package com.lthwea.finedust.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;

import com.lthwea.finedust.cnst.MyConst;
import com.lthwea.finedust.controller.DataController;
import com.lthwea.finedust.util.Utils;

import java.util.Calendar;

public class MyAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Log.d("MyAlarmReceiver", "onReceive");

        String sidoName = intent.getStringExtra(MyConst.SIDO_ALARM_INTENT_TAG);
        String cityName = intent.getStringExtra(MyConst.CITY_ALARM_INTENT_TAG);
        boolean[] days = intent.getBooleanArrayExtra(MyConst.DAYS_ALARM_INTENT_TAG);

        int todayIndex = getTodayIndex();
        if (days[todayIndex] == true){

            DataController dc = new DataController();
            String value = dc.getAlarmData(sidoName, cityName);      // p1 시, p2 시도


            String msg = "";
            if(!"".equals(value)){
                String[] values = value.split(",");         //1 pm10, 2 pm25, 3 date time
                msg = sidoName + " " + cityName + " 상세정보\n";
                msg += "미세먼지 : " + values[0] +  "("+ Utils.getPm10ValueStatus(values[0]) + ")\n";
                msg +=  "초미세먼지 : " +values[1]+  "("+ Utils.getPm25ValueStatus(values[1]) + ")\n";
                msg += "기준 : " + values[2];
            }else{
                msg = "죄송합니다. 서버 연결이 원할하지 않아 데이터를 불러 올 수 없습니다.";
            }
            
            Intent clockIntent = new Intent(context, MyAlarmActivity.class);
            clockIntent.putExtra("msg", msg);
            clockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(clockIntent);

        }else{

            return;

        }
    }


    //"월요일", "화요일", "수요일", "목요일", "금요일", "토요알", "일요일"
    //   0,      1,      2,        3,     4,       5,      6,
    public int getTodayIndex(){
        Calendar cal = Calendar.getInstance();
        int today = cal.get(Calendar.DAY_OF_WEEK);
        switch (today){
            case 1:         //일
                return 6;
            case 2:         //월
                return 0;
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
                return 3;
            case 6:
                return 4;
            case 7:
                return 5;
            default:        //ERROR
                return 8;
        }

    }


}

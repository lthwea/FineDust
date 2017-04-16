package com.lthwea.finedust.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;

import com.lthwea.finedust.cnst.MyConst;
import com.lthwea.finedust.controller.DataController;
import com.lthwea.finedust.util.Utils;
import com.lthwea.finedust.view.DetailActivity;

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
            String sidoInfo = "";
            String msg = "";

            if( Utils.isConnectNetwork(context)){


                DataController dc = new DataController();
                sidoInfo = dc.getSidoAlarmData(sidoName, cityName);      // p1 시, p2 시도


                if(!"".equals(sidoInfo)){
                    String[] values = sidoInfo.split(",");         //1 pm10, 2 pm25, 3 date time
                    dc.getForecastData();

                    Intent detailIntent = new Intent(context, DetailActivity.class);
                    detailIntent.putExtra("isAlarm", true);
                    detailIntent.putExtra(MyConst.DATE_TIME_DETAIL_INTENT_TAG, values[2]);
                    detailIntent.putExtra(MyConst.SIDO_DETAIL_INTENT_TAG, sidoName);
                    detailIntent.putExtra(MyConst.CITY_DETAIL_INTENT_TAG, cityName);
                    detailIntent.putExtra(MyConst.PM10_DETAIL_INTENT_TAG, values[0]);
                    detailIntent.putExtra(MyConst.PM25_DETAIL_INTENT_TAG, values[1]);
                    detailIntent.putExtra(MyConst.TODAY_VAL_DETAIL_INTENT_TAG, MyConst.PM10_FORECAST_SIDO_MAP.get(sidoName));
                    detailIntent.putExtra(MyConst.TODAY_FORE_DETAIL_INTENT_TAG, MyConst.TODAY_FORECAST_VO.getInformCause());
                    detailIntent.putExtra(MyConst.TOMW_FORE_DETAIL_INTENT_TAG, MyConst.TOMORROW_FORECAST_VO.getInformOverall());
                    context.startActivity(detailIntent);

                }else{
                    //msg = "죄송합니다. 서버 연결이 원할하지 않아 데이터를 불러 올 수 없습니다.";
                    Log.e("FineDustAlarm", "죄송합니다. 서버 연결이 원할하지 않아 데이터를 불러 올 수 없습니다.");
                    return;
                }



            }else{
                //Utils.showPositiveAlertDialog(context, "알림", "인터넷을 사용할 수 없습니다. 확인 후 다시 이용해주세요.");
                //msg = "인터넷이 연결되지 않아 미세먼지 실시간 정보를 받아올 수 없습니다.";
                Log.e("FineDustAlarm", "인터넷이 연결되지 않아 미세먼지 실시간 정보를 받아올 수 없습니다.");
                return;
            }


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

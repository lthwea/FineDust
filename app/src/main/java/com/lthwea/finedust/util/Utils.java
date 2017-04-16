package com.lthwea.finedust.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.lthwea.finedust.R;
import com.lthwea.finedust.alarm.MyAlarmReceiver;
import com.lthwea.finedust.cnst.MyConst;
import com.lthwea.finedust.controller.AlarmDataController;
import com.lthwea.finedust.vo.AlarmVO;
import com.lthwea.finedust.vo.MarkerVO;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by LeeTaeHun on 2017. 4. 7..
 */

public class Utils {


    /**
     * 두 지점간의 거리 계산
     */
    public static String getNearDistanceLocation(double lat1, double lon1) {
        String nearDistanceLocation = "";
        double minDistance = 99999999999999999999.0;

        Iterator<String> keys = com.lthwea.finedust.cnst.MyConst.markerMap.keySet().iterator();
        while ( keys.hasNext() ) {

            String key = keys.next();
            MarkerVO vo = MyConst.markerMap.get(key);
            LatLng l = vo.getPosition();
            Double lat2 = l.latitude;
            Double lon2 = l.longitude;
            double theta = lon1 - lon2;
            double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1609.344;

            if( minDistance > dist){
                nearDistanceLocation = vo.getSidoName() + " " + vo.getCityName();
                minDistance = dist;
            }
        }

        return nearDistanceLocation;
    }


    public static LatLng getNearDistanceLatLng(double lat1, double lon1) {
        LatLng nearDistanceLatLng = null;
        double minDistance = 99999999999999999999.0;

        Iterator<String> keys = com.lthwea.finedust.cnst.MyConst.markerMap.keySet().iterator();

        while ( keys.hasNext() ) {

            String key = keys.next();
            MarkerVO vo = com.lthwea.finedust.cnst.MyConst.markerMap.get(key);

            Log.d("getNearDistanceLatLng", key + "\t" + vo.getPm10Value() + " ");

            if(vo.getPm10Value() == null || "".equals(vo.getPm10Value())){
                continue;
            }

            LatLng l = vo.getPosition();
            Double lat2 = l.latitude;
            Double lon2 = l.longitude;
            double theta = lon1 - lon2;
            double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1609.344;

            if( minDistance > dist){
                nearDistanceLatLng = l;
                minDistance = dist;
            }
        }

        return nearDistanceLatLng;
    }

    public static String getNearDistanceInfo(double lat1, double lon1) {
        String msg = "";
        MarkerVO infoVO = null;

        double minDistance = 99999999999999999999.0;

        Iterator<String> keys = com.lthwea.finedust.cnst.MyConst.markerMap.keySet().iterator();

        while ( keys.hasNext() ) {

            String key = keys.next();
            MarkerVO vo = com.lthwea.finedust.cnst.MyConst.markerMap.get(key);

            //Log.d("getNearDistanceLatLng", key + "\t" + vo.getPm10Value() + " ");

            if(vo.getPm10Value() == null || "".equals(vo.getPm10Value())){
                continue;
            }

            LatLng l = vo.getPosition();
            Double lat2 = l.latitude;
            Double lon2 = l.longitude;
            double theta = lon1 - lon2;
            double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1609.344;

            if( minDistance > dist){
                infoVO = vo;
                minDistance = dist;
            }
        }

        if (infoVO != null){
            msg = infoVO.getSidoName() + " " + infoVO.getCityName() + " 상세정보\n";
            msg += "미세먼지 : " +infoVO.getPm10Value() +  "("+ Utils.getPm10ValueStatus(infoVO.getPm10Value()) + ")\n";
            msg +=  "초미세먼지 : " +infoVO.getPm25Value() +  "("+ Utils.getPm25ValueStatus(infoVO.getPm25Value()) + ")\n";
            msg += "기준 : " + infoVO.getDataTime();
        }

        return msg;
    }




    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }




    //  for alarm setting
    public static long getTriggerAtMillis(int hourOfDay, int minute) {
        GregorianCalendar currentCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int currentHourOfDay = currentCalendar.get(GregorianCalendar.HOUR_OF_DAY);
        int currentMinute = currentCalendar.get(GregorianCalendar.MINUTE);


        //현재시간보다 설정한 시간이 과거시간일 경우 1을 더해줘야 한다
        if ( currentHourOfDay < hourOfDay || ( currentHourOfDay == hourOfDay && currentMinute < minute ) )
            return getTimeInMillis(false, hourOfDay, minute);
        else
            return getTimeInMillis(true, hourOfDay, minute);

    }

    //  for alarm setting
    public static long getTimeInMillis(boolean tomorrow, int hourOfDay, int minute) {
        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();

        if ( tomorrow )
            calendar.add(GregorianCalendar.DAY_OF_YEAR, 1);

        calendar.set(GregorianCalendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(GregorianCalendar.MINUTE, minute);
        calendar.set(GregorianCalendar.SECOND, 0);
        calendar.set(GregorianCalendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }


    public static String getPm10ValueStatus(String v){
        try {
            int val = Integer.parseInt(v);

            if(val<=30 && val >= 0){
                return "좋음";
            }else if(val <= 80 && val >= 31){
                return "보통";
            }else if(val <= 150 && val >= 81){
                return "나쁨";
            }else if(val >= 151){
                return "매우나쁨";
            }else{
                return "데이터없음";
            }

        }catch (Exception e){
            return "";
        }finally {

        }
    };

    public static String getPm25ValueStatus(String v){
        try {
            int val = Integer.parseInt(v);

            if(val<=15 && val >= 0){
                return "좋음";
            }else if(val <= 50 && val >= 16){
                return "보통";
            }else if(val <= 100 && val >= 51){
                return "나쁨";
            }else if(val >= 101){
                return "매우나쁨";
            }else{
                return "데이터없음";
            }

        }catch (Exception e){
            return "";
        }finally {

        }
    };




    public static String getTimeStringFormat(int hour, int min){
        String time = "";

        if(hour <= 11){
            time += "오전 ";
        }else{
            time += "오후 ";
        }

        if(hour >= 13){
            String h = Integer.toString(hour-12);
            if (h.length() == 1) time += "0"+h;
            else                 time += h;
        }else{
            String h = Integer.toString(hour);
            if (h.length() == 1) time += "0"+h;
            else                 time += h;
        }

        String a = "";
        if( Integer.toString(min).length() == 1)
            time += "시 " + 0 + min + "분";
        else
            time += "시 " + min + "분";


        return time;
    }


    public static void printDBData(AlarmDataController db){
        List<AlarmVO> list = db.selectAllData();
        for(AlarmVO vo : list){
            Log.d("printDBData", vo.toString());
        }
    }



    public static boolean[] getBooleanArrayDays(String str){
        boolean[] ALARM_DAYS = new boolean[]{
                false,false,false,false,false,false,false
        };

        if(str != null){
            String[] tmp = str.split(" ");
            for(int i = 0 ; i < tmp.length ; i++){
                String s = tmp[i];
                if("월".equals(s)) ALARM_DAYS[0] = true;
                else if("화".equals(s)) ALARM_DAYS[1] = true;
                else if("수".equals(s)) ALARM_DAYS[2] = true;
                else if("목".equals(s)) ALARM_DAYS[3] = true;
                else if("금".equals(s)) ALARM_DAYS[4] = true;
                else if("토".equals(s)) ALARM_DAYS[5] = true;
                else if("일".equals(s)) ALARM_DAYS[6] = true;
            }
        }

        return ALARM_DAYS;
    }



    // sqlite
    public static void addAlarm(Context context, AlarmVO vo){


        Intent alarmIntent = new Intent(context, MyAlarmReceiver.class);
        alarmIntent.putExtra(MyConst.ID_ALARM_INTENT_TAG, vo.getId());
        alarmIntent.putExtra(MyConst.SIDO_ALARM_INTENT_TAG, vo.getSidoName());
        alarmIntent.putExtra(MyConst.CITY_ALARM_INTENT_TAG, vo.getCityName());
        alarmIntent.putExtra(MyConst.DAYS_ALARM_INTENT_TAG, getBooleanArrayDays(vo.getDays()));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, vo.getHour());
        calendar.set(Calendar.MINUTE, vo.getMin());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        PendingIntent pi = PendingIntent.getBroadcast(context, vo.getId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, Utils.getTriggerAtMillis(vo.getHour(), vo.getMin()), pi);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, Utils.getTriggerAtMillis(vo.getHour(), vo.getMin()), pi);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, Utils.getTriggerAtMillis(vo.getHour(), vo.getMin()), pi);
        }

        Log.d("AlarmManager", "add\t" + vo.toString());

    }


    public static void deleteAlarm(Context context, int id){

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MyAlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (sender != null) {
            am.cancel(sender);
            sender.cancel();
        }

        Log.d("AlarmManager", "delete\t" + id);

    }

    public static void updateAlarm(Context context, AlarmVO vo){
        Log.d("AlarmManager", "update\t" + vo);

        deleteAlarm(context, vo.getId());
        addAlarm(context, vo);
    }





    // 인터넷 연결 확인
    public static boolean isConnectNetwork(Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }




    // 데이터 수치에 의한 색깔
    public static int getPm10MarkerColor(Context context, int val) {
        if (val >= 0 && val <= 30) {
            return ContextCompat.getColor(context, R.color.color_dust_1);
        } else if (val >= 31 && val <= 80) {
            return ContextCompat.getColor(context, R.color.color_dust_2);
        } else if (val >= 81 && val <= 150) {
            return ContextCompat.getColor(context, R.color.color_dust_3);
        } else if (val >= 151) {
            return ContextCompat.getColor(context, R.color.color_dust_4);
        } else {
            return Color.BLACK;
        }
    }



    public static int getPm25MarkerColor(Context context, int val) {

        if (val >= 0 && val <= 15) {
            return ContextCompat.getColor(context, R.color.color_dust_1);
        } else if (val >= 16 && val <= 50) {
            return ContextCompat.getColor(context, R.color.color_dust_2);
        } else if (val >= 51 && val <= 100) {
            return ContextCompat.getColor(context, R.color.color_dust_3);
        } else if (val >= 101) {
            return ContextCompat.getColor(context, R.color.color_dust_4);
        } else {
            return Color.BLACK;
        }

    }



    public static int convertTodayStatusStringToInt(String val){
        if("좋음".equals(val)){
            return 25;
        }if("보통".equals(val)){
            return 75;
        }if("나쁨".equals(val)){
            return 112;
        }if("매우나쁨".equals(val)){
            return 150;
        }else{
            return 0;
        }
    }

}

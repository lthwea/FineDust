package com.lthwea.finedust.util;

import com.google.android.gms.maps.model.LatLng;
import com.lthwea.finedust.cnst.MapConst;
import com.lthwea.finedust.vo.MarkerVO;

import java.util.GregorianCalendar;
import java.util.Iterator;

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

        Iterator<String> keys = MapConst.markerMap.keySet().iterator();
        while ( keys.hasNext() ) {

            String key = keys.next();
            MarkerVO vo = MapConst.markerMap.get(key);
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

            if(val<=30){
                return "좋음";
            }else if(val <= 80 && val >= 31){
                return "보통";
            }else if(val <= 150 && val >= 81){
                return "나쁨";
            }else if(val >= 151){
                return "매우나쁨";
            }else{
                return "";
            }

        }catch (Exception e){
            return "";
        }finally {

        }
    };

    public static String getPm25ValueStatus(String v){
        try {
            int val = Integer.parseInt(v);

            if(val<=15){
                return "좋음";
            }else if(val <= 50 && val >= 16){
                return "보통";
            }else if(val <= 100 && val >= 51){
                return "나쁨";
            }else if(val >= 101){
                return "매우나쁨";
            }else{
                return "";
            }

        }catch (Exception e){
            return "";
        }finally {

        }
    };



}

package com.lthwea.finedust.util;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.lthwea.finedust.cnst.MapConst;
import com.lthwea.finedust.vo.MarkerVO;

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


}

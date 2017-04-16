package com.lthwea.finedust.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.lthwea.finedust.R;

/**
 * Created by LeeTaeHun on 2017. 4. 6..
 *
 *  앱 실행시 맵의 시작위치를 관리하는 클래스
 */
public class PrefController {

    public static final String PREFS_NAME = "INIT_MARKER";

    private SharedPreferences sharedPref;
    private Context context;

    //대한민국
    public static final double DEFAULT_LAT = 35.9077570;
    public static final double DEFAULT_LNG = 127.7669220;
    public static final double DEFAULT_ZOOM = 6;


    public PrefController(Context context){
        this.context = context;
        sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setPrefInitMarker(Double lat, Double lng, Double zoom){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.pref_init_location_lat), Double.toHexString(lat));
        editor.putString(context.getString(R.string.pref_init_location_lng), Double.toHexString(lng));
        editor.putString(context.getString(R.string.pref_init_location_zoom), Double.toHexString(zoom));
        editor.commit();
    }

    public void setIsFirstInitMarker(boolean b){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(context.getString(R.string.pref_init_is_first), b);
        editor.commit();
    }

    public boolean isFirstInitMarker(){
        boolean isFirst = sharedPref.getBoolean(context.getString(R.string.pref_init_is_first), true);
        return isFirst;
    }

    public double getPrefInitMarkerLat(){
        String lat = sharedPref.getString(context.getString(R.string.pref_init_location_lat), Double.toHexString(DEFAULT_LAT));
        return Double.parseDouble(lat);
    }

    public double getPrefInitMarkerLng(){
        String lng = sharedPref.getString(context.getString(R.string.pref_init_location_lng), Double.toHexString(DEFAULT_LNG));
        return Double.parseDouble(lng);
    }

    public double getPrefInitMarkerZoom(){
        String zoom = sharedPref.getString(context.getString(R.string.pref_init_location_zoom), Double.toHexString(DEFAULT_ZOOM));
        return Double.parseDouble(zoom);
    }

}

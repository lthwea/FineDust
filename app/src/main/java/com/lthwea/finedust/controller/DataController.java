package com.lthwea.finedust.controller;

import android.util.Log;

import com.lthwea.finedust.cnst.MyConst;
import com.lthwea.finedust.vo.MarkerVO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static com.lthwea.finedust.cnst.MyConst.markerMap;

/**
 * Created by LeeTaeHun on 2017. 3. 30..
 */

public class DataController {

    String TAG = "DataController";

    private static final String KEY = "1234";
    private static final String URL = "http://dopewealth.com/finedust?key="+KEY;


    public void getSidoData() {
        Log.d("DataController", "getSidoData 시작");

        HttpURLConnection urlConnection = null;
        String ACTION = "&action=getSidoData";

        try {
            URL url = new URL( URL+ACTION );
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            JSONArray jar = new JSONArray(sb.toString());


            for(int i = 0 ; i < jar.length() ; i++){
                JSONArray jsonArray = (JSONArray) jar.get(i);

                for(int j = 0 ; j < jsonArray.length() ; j++){
                    JSONObject tmp = (JSONObject) jsonArray.get(j);
                    String key = tmp.get("sidoName")+ "" +tmp.get("cityName");
                    MarkerVO vo = markerMap.get(key);
                    vo.setPm10Value((String) tmp.get("pm10Value"));
                    vo.setPm25Value((String) tmp.get("pm25Value"));
                    vo.setSo2Value((String) tmp.get("so2Value"));
                    vo.setO3Value((String) tmp.get("o3Value"));
                    vo.setNo2Value((String) tmp.get("no2Value"));
                    vo.setCoValue((String) tmp.get("coValue"));
                    vo.setDataTime((String) tmp.get("dataTime"));
                    markerMap.put(key, vo);
                }
            }

        }catch( Exception e) {
            e.printStackTrace();
        }
        finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }
        Log.d("DataController", "getSidoData end");
    }

    public void getForecastData() {

        HttpURLConnection urlConnection = null;
        String ACTION = "&action=getForecastData";

        try {
            URL url = new URL( URL+ACTION );
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            JSONArray jar = new JSONArray(sb.toString());


            for(int i = 0 ; i < jar.length() ; i++){
                JSONArray jsonArray = (JSONArray) jar.get(i);

                for(int j = 0 ; j < 2 ; j++){
                    JSONObject tmp = (JSONObject) jsonArray.get(j);
                    String informCode = (String) tmp.get("informCode");
                    String informData = (String) tmp.get("informData");


                    if(j == 0 && "PM10".equals(informCode)){
                        String informGrade = (String) tmp.get("informGrade");
                        //informGrade = informGrade.trim();
                        String[] grades = informGrade.split(",");

                        Log.d("getForecastData", informGrade);

                        for (int k = 0 ; k < grades.length ; k++){
                            String[] sidoGrade = grades[k].split(":");
                            if("영동 ".equals(sidoGrade[0])){
                                MyConst.PM10_FORECAST_SIDO_MAP.put("강원", sidoGrade[1].trim());
                            }else if("경기남부 ".equals(sidoGrade[0]) || "경기북부 ".equals(sidoGrade[0])){
                                MyConst.PM10_FORECAST_SIDO_MAP.put("경기", sidoGrade[1].trim());
                            }
                            else{
                                MyConst.PM10_FORECAST_SIDO_MAP.put(sidoGrade[0].trim(), sidoGrade[1].trim());
                            }

                        }

                        MyConst.TODAY_FORECAST_VO.setInformCode(informCode);
                        MyConst.TODAY_FORECAST_VO.setInformData(informData);
                        MyConst.TODAY_FORECAST_VO.setDataTime((String)tmp.get("dataTime"));
                        MyConst.TODAY_FORECAST_VO.setInformCause( ((String)tmp.get("informCause")).trim() );
                        MyConst.TODAY_FORECAST_VO.setInformOverall( ((String)tmp.get("informOverall")).trim() );

                    }else if(j == 1 && "PM10".equals(informCode)){
                        MyConst.TOMORROW_FORECAST_VO.setInformCode(informCode);
                        MyConst.TOMORROW_FORECAST_VO.setInformData(informData);
                        MyConst.TOMORROW_FORECAST_VO.setDataTime((String)tmp.get("dataTime"));
                        MyConst.TOMORROW_FORECAST_VO.setInformCause(((String)tmp.get("informCause")).trim());
                        MyConst.TOMORROW_FORECAST_VO.setInformOverall(((String)tmp.get("informOverall")).trim());
                    }

                    Log.d("getForecastData", tmp.toString());
                }
            }

        }catch( Exception e) {
            e.printStackTrace();
        }
        finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }

    }


    public String getSidoAlarmData(String sido, String city) {
        Log.d("getSidoAlarmData", "call");

        String ACTION = "&action=getSidoAlarmData";
        String PARAMETERS = "&value=";

        try {
            PARAMETERS += URLEncoder.encode(sido, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpURLConnection urlConnection = null;
        String cityName = "", pm10Value= "", dateTime="";
        String pm25Value = "";
        String returnStr = "";


        try {
            URL url = new URL( URL + ACTION + PARAMETERS );
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            Log.i("Response Code" , urlConnection.getResponseCode() + " ");


            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jArray = (JSONArray) jsonObject.get("list");


            boolean isExistData = false;
            for(int i = 0 ; i < jArray.length() ; i++){
                JSONObject tmp = (JSONObject) jArray.get(i);
                Log.d("JSON", tmp.toString());
                cityName = (String) tmp.get("cityName");
                if(city.equals(cityName)){
                    pm10Value = (String) tmp.get("pm10Value");
                    pm25Value = (String) tmp.get("pm25Value");
                    dateTime = (String) tmp.get("dataTime");
                    Log.d("getSidoAlarmData", cityName +","+ pm10Value + "," + pm25Value + "," + dateTime);
                    isExistData = true;
                    break;
                }else{
                    isExistData = false;
                }
            }

            if(isExistData){
                returnStr = pm10Value + "," + pm25Value + "," + dateTime;
            }else{
                returnStr = "";
            }

        }catch( Exception e) {
            e.printStackTrace();
            returnStr = "";
        }
        finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }

            return returnStr;
        }

    }


    /*public String getForecastAlarmData() {
        Log.d("getSidoAlarmData", "call");

        String ACTION = "&action=getForecastAlarmData";

        HttpURLConnection urlConnection = null;



        try {
            URL url = new URL( URL + ACTION );
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            Log.i("Response Code" , urlConnection.getResponseCode() + " ");


            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jArray = (JSONArray) jsonObject.get("list");


            boolean isExistData = false;
            for(int i = 0 ; i < 2 ; i++){
                JSONObject tmp = (JSONObject) jArray.get(i);
                Log.d("JSON", tmp.toString());

                String todayValue;
                String todayForecast;
                String tommorwForecast;

                if( i == 0 ){

                }

//
//                cityName = (String) tmp.get("cityName");
//                if(city.equals(cityName)){
//                    pm10Value = (String) tmp.get("pm10Value");
//                    pm25Value = (String) tmp.get("pm25Value");
//                    dateTime = (String) tmp.get("dataTime");
//                    Log.d("getSidoAlarmData", cityName +","+ pm10Value + "," + pm25Value + "," + dateTime);
//                    isExistData = true;
//                    break;
//                }else{
//                    isExistData = false;
//                }


            }

            if(isExistData){
                returnStr = pm10Value + "," + pm25Value + "," + dateTime;
            }else{
                returnStr = "";
            }

        }catch( Exception e) {
            e.printStackTrace();
            returnStr = "";
        }
        finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }

            return returnStr;
        }

    }*/




}

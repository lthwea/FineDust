package com.lthwea.finedust.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.lthwea.finedust.vo.DefaultVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by LeeTaeHun on 2017. 3. 30..
 */

public class DataController {

    HttpURLConnection urlConnection;
    public static List JSON_DEFAULT_LIST;

    public void getDefaultData() {

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL("http://dopewealth.com/finedust?key=1234&value=default");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d("getDefaultData", result.toString());

            JSONObject jsonObject = new JSONObject(result.toString());
            Log.d("jsonObject", jsonObject.get("list")+",");


            JSONArray jArray = (JSONArray) jsonObject.get("list");
            JSONObject jo = (JSONObject) jArray.get(0);
            JSON_DEFAULT_LIST = new ArrayList();
            Iterator<?> keys = jo.keys();
            while( keys.hasNext() ) {
                String key = (String)keys.next();
                String value = (String) jo.get(key);
                Log.d("JSON_DEFAULT_MAP", key + "\t" + value);



                DefaultVO vo = new DefaultVO();
                if ("chungbuk".equals(key)){
                    vo.setCityName("충청북도");
                    vo.setCityvalue(value);
                    vo.setLat(36.8);
                    vo.setLng(127.7);
                }else if ("chungnam".equals(key)){
                    vo.setCityName("충청남도");
                    vo.setCityvalue(value);
                    vo.setLat(36.5184);
                    vo.setLng(126.8);
                }else if ("daegu".equals(key)){
                    vo.setCityName("대구");
                    vo.setCityvalue(value);
                    vo.setLat(35.8714354);
                    vo.setLng(128.601445);
                }else if ("daejeon".equals(key)){
                    vo.setCityName("대전");
                    vo.setCityvalue(value);
                    vo.setLat(36.3504119);
                    vo.setLng(127.3845475);
                }else if ("gangwon".equals(key)){
                    vo.setCityName("강원도");
                    vo.setCityvalue(value);
                    vo.setLat(37.8228);
                    vo.setLng(128.1555);
                }else if ("gwangju".equals(key)){
                    vo.setCityName("광주");
                    vo.setCityvalue(value);
                    vo.setLat(35.1595454);
                    vo.setLng(126.8526012);
                }else if ("gyeongbuk".equals(key)){
                    vo.setCityName("경상북도");
                    vo.setCityvalue(value);
                    vo.setLat(36.8);
                    vo.setLng(127.7);
                }else if ("gyeonggi".equals(key)){
                    vo.setCityName("경기도");
                    vo.setCityvalue(value);
                    vo.setLat(37.4138);
                    vo.setLng(127.5183);
                }else if ("incheon".equals(key)){
                    vo.setCityName("인천");
                    vo.setCityvalue(value);
                    vo.setLat(37.4562557);
                    vo.setLng(126.7052062);
                }else if ("jeju".equals(key)){
                    vo.setCityName("제주도");
                    vo.setCityvalue(value);
                    vo.setLat(33.4996213);
                    vo.setLng(126.5311884);
                }else if ("jeonbuk".equals(key)){
                    vo.setCityName("전라북도");
                    vo.setCityvalue(value);
                    vo.setLat(35.7175);
                    vo.setLng(127.153);
                }else if ("jeonnam".equals(key)){
                    vo.setCityName("전라남도");
                    vo.setCityvalue(value);
                    vo.setLat(34.8679);
                    vo.setLng(126.991);
                }else if ("sejong".equals(key)){
                    vo.setCityName("세종");
                    vo.setCityvalue(value);
                    vo.setLat(36.5579988);
                    vo.setLng(127.2573209);
                }else if ("seoul".equals(key)){
                    vo.setCityName("서울");
                    vo.setCityvalue(value);
                    vo.setLat(37.566535);
                    vo.setLng(126.9779692);
                }else if ("ulsan".equals(key)){
                    vo.setCityName("울산");
                    vo.setCityvalue(value);
                    vo.setLat(35.5383773);
                    vo.setLng(129.3113596);
                }else if ("busan".equals(key)){
                    vo.setCityName("부산");
                    vo.setCityvalue(value);
                    vo.setLat(35.1795543);
                    vo.setLng(129.0756416);
                }else{
                    continue;
                }


                JSON_DEFAULT_LIST.add(vo);

            }


           /* Iterator<?> keys = jo.keys();
            while( keys.hasNext() ) {
                String key = (String)keys.next();
                String value = (String) jo.get(key);
                JSON_DEFAULT_MAP.put(key, value);
                Log.d("JSON_DEFAULT_MAP", key + "\t" + value);
            }*/


        }catch( Exception e) {
            e.printStackTrace();
        }
        finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }


    }


}

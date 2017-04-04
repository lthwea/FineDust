package com.lthwea.finedust.controller;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.lthwea.finedust.vo.MarkerVO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by LeeTaeHun on 2017. 3. 30..
 */

public class DataController {

    HttpURLConnection urlConnection;
    public static List JSON_DEFAULT_LIST;

    public String getDefaultData() {

        StringBuilder result = new StringBuilder();
        JSONObject jo = null;

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
            JSONArray jArray = (JSONArray) jsonObject.get("list");
            jo = (JSONObject) jArray.get(0);
            
            Log.d("jsonObject", jsonObject.get("list")+",");

            JSON_DEFAULT_LIST = new ArrayList();
            Iterator<?> keys = jo.keys();
            while( keys.hasNext() ) {
                String key = (String)keys.next();
                String value = (String) jo.get(key);
                Log.d("JSON_DEFAULT_MAP", key + "\t" + value);


                MarkerVO vo = new MarkerVO();
                if ("chungbuk".equals(key)){
                    vo.setCityName("충청북도");
                    vo.setPm10Value(value);
                    vo.setPosition(new LatLng(36.8, 127.7));
                }else if ("chungnam".equals(key)){
                    vo.setCityName("충청남도");
                    vo.setPm10Value(value);
                    vo.setPosition(new LatLng(36.5184, 126.8));
                }else if ("daegu".equals(key)){
                    vo.setCityName("대구");
                    vo.setPm10Value(value);
                    vo.setPosition(new LatLng(35.8714354, 128.601445));
                }else if ("daejeon".equals(key)){
                    vo.setCityName("대전");
                    vo.setPm10Value(value);
                    vo.setPosition(new LatLng(36.3504119, 127.3845475));
                }else if ("gangwon".equals(key)){
                    vo.setCityName("강원도");
                    vo.setPm10Value(value);
                    vo.setPosition(new LatLng(37.8228, 128.1555));
                }else if ("gwangju".equals(key)){
                    vo.setCityName("광주");
                    vo.setPm10Value(value);
                    vo.setPosition(new LatLng(35.1595454, 126.8526012));
                }else if ("gyeongbuk".equals(key)){
                    vo.setCityName("경상북도");
                    vo.setPm10Value(value);
                    vo.setPosition(new LatLng(36.4919, 128.8889));
                }else if ("gyeongnam".equals(key)){
                    vo.setCityName("경상남도");
                    vo.setPm10Value(value);
                    vo.setPosition(new LatLng(35.4606, 128.2132));
                }else if ("gyeonggi".equals(key)){
                    vo.setCityName("경기도");
                    vo.setPm10Value(value);
                    vo.setPosition(new LatLng(37.4138, 127.5183));
                }else if ("incheon".equals(key)){
                    vo.setCityName("인천");
                    vo.setPm10Value(value);
                    vo.setPosition(new LatLng(37.4562557, 126.7052062));
                }else if ("jeju".equals(key)){
                    vo.setCityName("제주도");
                    vo.setPm10Value(value);
                    vo.setPosition(new LatLng(33.4996213, 126.5311884));
                }else if ("jeonbuk".equals(key)){
                    vo.setCityName("전라북도");
                    vo.setPm10Value(value);
                    vo.setPosition(new LatLng(35.7175, 127.153));
                }else if ("jeonnam".equals(key)){
                    vo.setCityName("전라남도");
                    vo.setPm10Value(value);
                    vo.setPosition(new LatLng(34.8679, 126.991));
                }else if ("sejong".equals(key)){
                    vo.setCityName("세종");
                    vo.setPm10Value(value);
                    vo.setPosition(new LatLng(36.5579988, 127.2573209));
                }else if ("seoul".equals(key)){
                    vo.setCityName("서울");
                    vo.setPm10Value(value);
                    vo.setPosition(new LatLng(37.566535, 126.9779692));
                }else if ("ulsan".equals(key)){
                    vo.setCityName("울산");
                    vo.setPm10Value(value);
                    vo.setPosition(new LatLng(35.5383773, 129.3113596));
                }else if ("busan".equals(key)){
                    vo.setCityName("부산");
                    vo.setPm10Value(value);
                    vo.setPosition(new LatLng(35.1795543, 129.0756416));
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

        return jo.toString();

    }


    public void getSeoulData() {

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL("http://dopewealth.com/finedust?key=1234&value=sido");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d("getSeoulData", result.toString());

            JSONObject jsonObject = new JSONObject(result.toString());
            JSONArray jArray = (JSONArray) jsonObject.get("list");
            JSONObject jo = (JSONObject) jArray.get(0);

            Log.d("jsonObject", jsonObject.get("list")+",");

            JSON_DEFAULT_LIST = new ArrayList();
            Iterator<?> keys = jo.keys();
            while( keys.hasNext() ) {
                String key = (String)keys.next();
                String value = (String) jo.get(key);
                Log.d("JSON_SEOUL_MAP", key + "\t" + value);

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



/*

    public void getDefaultData2() throws IOException {

        String url = "http://dopewealth.com/finedust?";
        JSONObject jsonInput = new JSONObject();

        try {
            jsonInput.put("key", "1234");
            jsonInput.put("value", "default");
        } catch (JSONException e) {
        }

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonInput.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final JSONObject json;
            String myResponse = response.body().string();
            Log.d("onResponse myResponse", myResponse);
            try {
                json = new JSONObject(myResponse);
                //txtString.setText(json.getJSONObject("data").getString("first_name")+ " "+json.getJSONObject("data").getString("last_name"));
                Log.d("onResponse", json.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    });
    }
*/


}

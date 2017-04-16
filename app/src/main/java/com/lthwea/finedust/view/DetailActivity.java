package com.lthwea.finedust.view;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lthwea.finedust.R;
import com.lthwea.finedust.cnst.MyConst;
import com.lthwea.finedust.util.CircleDisplay;
import com.lthwea.finedust.util.Utils;

/**
 * Created by LeeTaeHun on 2017. 4. 16..
 */

public class DetailActivity extends Activity implements View.OnClickListener{

    private static String TAG = "DetailActivity";

    private String dateTime;
    private String sidoName;
    private String cityName;
    private String pm10Value;
    private String pm25Value;
    private String todayValue;
    private String todayForecast;
    private String tommorwForecast;
    private String basedData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);


        Intent intent = getIntent();

        boolean isAlarm = intent.getBooleanExtra("isAlarm", false);
        if(isAlarm){
            Vibrator vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(1000);

            TextView textView = (TextView) findViewById(R.id.tv_detail_title);
            textView.setText("스케쥴 알람");
        }

        dateTime = intent.getStringExtra(MyConst.DATE_TIME_DETAIL_INTENT_TAG);
        sidoName = intent.getStringExtra(MyConst.SIDO_DETAIL_INTENT_TAG);
        cityName = intent.getStringExtra(MyConst.CITY_DETAIL_INTENT_TAG);
        pm10Value = intent.getStringExtra(MyConst.PM10_DETAIL_INTENT_TAG);
        pm25Value = intent.getStringExtra(MyConst.PM25_DETAIL_INTENT_TAG);
        todayValue = intent.getStringExtra(MyConst.TODAY_VAL_DETAIL_INTENT_TAG);
        todayForecast = intent.getStringExtra(MyConst.TODAY_FORE_DETAIL_INTENT_TAG);
        tommorwForecast = intent.getStringExtra(MyConst.TOMW_FORE_DETAIL_INTENT_TAG);

        int todayValue = -200;
        if( MyConst.PM10_FORECAST_SIDO_MAP.get(sidoName) != null){
            todayValue = Utils.convertTodayStatusStringToInt(MyConst.PM10_FORECAST_SIDO_MAP.get(sidoName));
        }

        Log.d(TAG, sidoName + cityName + "\t" + MyConst.PM10_FORECAST_SIDO_MAP.get(sidoName));

        if("".equals(pm10Value)){
                pm10Value = "-200";
            }
            if("".equals(pm25Value)){
                pm25Value = "-200";
        }


        TextView tv_detail_rt_info = (TextView) findViewById(R.id.tv_detail_rt_info);
        TextView tv_detail_today_forecast = (TextView) findViewById(R.id.tv_detail_today_forecast);
        TextView tv_detail_tomorrow_forecast = (TextView) findViewById(R.id.tv_detail_tomorrow_forecast);

        tv_detail_rt_info.setText(sidoName + " " + cityName + "(" + dateTime + ")");

        tv_detail_today_forecast.setText(todayForecast);
        tv_detail_tomorrow_forecast.setText(tommorwForecast);


        CircleDisplay cd_dust = (CircleDisplay) findViewById(R.id.circle_dust);
        cd_dust.setTYPE(0);
        cd_dust.setVALUE(pm10Value);
        cd_dust.setAnimDuration(1000);
        cd_dust.setValueWidthPercent(25f);       // 원 굵기
        cd_dust.setTextSize(13f);
        cd_dust.setColor(Utils.getPm10MarkerColor(this, Integer.parseInt(pm10Value)));
        cd_dust.setDrawText(true);
        cd_dust.setDrawInnerCircle(true);
        cd_dust.setFormatDigits(0);
        cd_dust.setUnit("");
        cd_dust.setStepSize(0.5f);
        cd_dust.showValue(Integer.parseInt(pm10Value), 200, true);


        CircleDisplay circle_su_dust = (CircleDisplay) findViewById(R.id.circle_su_dust);
        circle_su_dust.setTYPE(1);
        circle_su_dust.setVALUE(pm25Value);
        circle_su_dust.setAnimDuration(1000);
        circle_su_dust.setValueWidthPercent(25f);       // 원 굵기
        circle_su_dust.setTextSize(13f);
        circle_su_dust.setColor(Utils.getPm25MarkerColor(this, Integer.parseInt(pm25Value)));
        circle_su_dust.setDrawText(true);
        circle_su_dust.setDrawInnerCircle(true);
        circle_su_dust.setFormatDigits(0);
        circle_su_dust.setUnit("");
        circle_su_dust.setStepSize(0.5f);
        circle_su_dust.showValue(Integer.parseInt(pm25Value), 150, true);

        CircleDisplay circle_today = (CircleDisplay) findViewById(R.id.circle_today);
        circle_today.setTYPE(2);
        circle_today.setSIDO(sidoName);
        circle_today.setVALUE(Integer.toString(todayValue));
        circle_today.setAnimDuration(1000);
        circle_today.setValueWidthPercent(25f);       // 원 굵기
        circle_today.setTextSize(13f);
        circle_today.setColor(Utils.getPm10MarkerColor(this, todayValue));
        circle_today.setDrawText(true);
        circle_today.setDrawInnerCircle(true);
        circle_today.setFormatDigits(0);
        circle_today.setUnit("");
        circle_today.setStepSize(0.5f);
        circle_today.showValue(todayValue, 150, true);

        ImageButton ibtn_detail_back = (ImageButton) findViewById(R.id.ibtn_detail_back);
        ibtn_detail_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.ibtn_detail_back){
            finish();
        }else{

        }

    }




}

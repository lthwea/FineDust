package com.lthwea.finedust.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lthwea.finedust.R;
import com.lthwea.finedust.util.Utils;
import com.lthwea.finedust.vo.AlarmVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LeeTaeHun on 2017. 4. 10..
 */

public class AlarmListActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_list_activity);
        getSupportActionBar().hide();

        // back button
      /*  android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("미세먼지 알림 설정");*/



        List<AlarmVO> voList = new ArrayList<>();
        boolean[] test = new boolean[]{ false, false, false, false, false, false, false};



        // test data
        AlarmVO vo = new AlarmVO(0, "Y", "서울", "강남구", 15, 30, "월 일");
        AlarmVO vo2 = new AlarmVO(1, "Y", "서울", "도봉구", 13, 30, "월 화 수 목 금 토 일");
        AlarmVO vo3 = new AlarmVO(2, "N", "서울", "서초구구", 01, 30, "월 화 수 목 금 토 일");
        AlarmVO vo4 = new AlarmVO(2, "N", "서울", "서초구구", 01, 30, "월 화 수 목 금 토 일");
        AlarmVO vo5 = new AlarmVO(2, "N", "서울", "서초구구", 01, 30, "월 화 수 목 금 토 일");
        AlarmVO vo6 = new AlarmVO(2, "N", "서울", "서초구구", 01, 30, "월 화 수 목 금 토 일");
        AlarmVO vo7 = new AlarmVO(2, "N", "서울", "서초구구", 01, 30, "월 화 수 목 금 토 일");
        AlarmVO vo8 = new AlarmVO(2, "N", "서울", "서초구구", 01, 30, "월 화 수 목 금 토 일");
        AlarmVO vo9 = new AlarmVO(2, "N", "서울", "서초구구", 01, 30, "월 화 수 목 금 토 일");
        AlarmVO vo10 = new AlarmVO(2, "N", "서울", "서초구구", 01, 30, "월 화 수 목 금 토 일");
        AlarmVO vo11 = new AlarmVO(2, "N", "서울", "서초구구", 01, 30, "월 화 수 목 금 토 일");
        AlarmVO vo12 = new AlarmVO(2, "N", "서울", "서초구구", 01, 30, "월 화 수 목 금 토 일");
        AlarmVO vo13 = new AlarmVO(2, "N", "서울", "서초구구", 01, 30, "월 화 수 목 금 토 일");
        AlarmVO vo14 = new AlarmVO(2, "N", "서울", "서초구구", 01, 30, "월 화 수 목 금 토 일");
        AlarmVO vo15 = new AlarmVO(2, "N", "서울", "서초구구", 01, 30, "월 화 수 목 금 토 일");
        AlarmVO vo16 = new AlarmVO(2, "N", "서울", "서초구구", 01, 30, "월 화 수 목 금 토 일");
        AlarmVO vo17 = new AlarmVO(2, "N", "서울", "서초구구", 01, 30, "월 화 수 목 금 토 일");
        voList.add(vo);
        voList.add(vo2);
        voList.add(vo3);
        voList.add(vo4);
        voList.add(vo5);
        voList.add(vo6);
        voList.add(vo7);
        voList.add(vo8);
        voList.add(vo9);
        voList.add(vo10);
        voList.add(vo11);
        voList.add(vo12);
        voList.add(vo13);
        voList.add(vo14);
        voList.add(vo15);
        voList.add(vo16);
        voList.add(vo17);




        ListView listView = (ListView)findViewById(R.id.listView);
        AlarmVoAdapter voAdapter = new AlarmVoAdapter(this, R.layout.alarm_list_item, voList);
        listView.setAdapter(voAdapter);

    }







    private class AlarmVoAdapter extends ArrayAdapter<AlarmVO> {

        private int layoutResource;


        public AlarmVoAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<AlarmVO> objects) {
            super(context, resource, objects);
            this.layoutResource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);

            View view = convertView;

            if (view == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                view = layoutInflater.inflate(layoutResource, null);
            }

            AlarmVO vo = getItem(position);
            if (vo != null) {
                TextView tv_alarm_item_loc = (TextView) view.findViewById(R.id.tv_alarm_item_loc);
                TextView tv_alarm_item_time = (TextView) view.findViewById(R.id.tv_alarm_item_time);
                TextView tv_alarm_item_day = (TextView) view.findViewById(R.id.tv_alarm_item_day);
                Switch sw = (Switch) view.findViewById(R.id.sw_alarm_item_isuse);

                if (tv_alarm_item_loc != null) {
                    tv_alarm_item_loc.setText(vo.getSidoName() + " " + vo.getCityName());
                }

                if (tv_alarm_item_time != null) {
                    tv_alarm_item_time.setText(Utils.getTimeStringFormat(vo.getHour(), vo.getMin()));
                }

                if (tv_alarm_item_day != null) {
                    tv_alarm_item_day.setText(vo.getDays());
                }


                if (sw != null) {
                    if("Y".equals(vo.getIsUse())){
                        sw.setChecked(true);
                    }else{
                        sw.setChecked(false);
                    }

                }
            }

            return view;
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            case R.id.menu_item_new_quote:
                Log.d("onOptionsItemSelected", "menu_item_new_quote");
                Intent i = new Intent(this, AlarmActivity.class);
                startActivityForResult(i, MainActivity.INTENT_ALARM_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alarm_plus_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Log.d("onActivityResult", "-> " + requestCode + ", " + requestCode + ", " + data.getStringExtra("isSetLocation"));

            if (requestCode == MainActivity.INTENT_ALARM_CODE) {
                if ("Y".equals(data.getStringExtra("isSetLocation"))) {
                    Intent intent = getIntent();
                    intent.putExtra("isSetLocation", "Y");
                    setResult(MainActivity.INTENT_ALARM_CODE, intent);
                    finish();
                }
            }
        }else{
            Log.e("onActivityResult", resultCode + " " + data);
        }

    }




}



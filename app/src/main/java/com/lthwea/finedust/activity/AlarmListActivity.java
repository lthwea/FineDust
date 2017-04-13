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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.lthwea.finedust.R;
import com.lthwea.finedust.cnst.MapConst;
import com.lthwea.finedust.controller.AlarmDataController;
import com.lthwea.finedust.util.Utils;
import com.lthwea.finedust.vo.AlarmVO;

import java.util.List;

/**
 * Created by LeeTaeHun on 2017. 4. 10..
 */

public class AlarmListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {



    private static String TAG = "AlarmListActivity";


    ImageButton ibtn_alarm_list_back;
    ImageButton ibtn_alarm_list_add;

    private AlarmDataController db;

    AlarmVoAdapter voAdapter;
    ListView listView;
    List<AlarmVO> voList;

    //boolean[] test = new boolean[]{ false, false, false, false, false, false, false};

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


        ibtn_alarm_list_back = (ImageButton) findViewById(R.id.ibtn_alarm_list_back);
        ibtn_alarm_list_add = (ImageButton) findViewById(R.id.ibtn_alarm_list_add);
        ibtn_alarm_list_back.setOnClickListener(this);
        ibtn_alarm_list_add.setOnClickListener(this);



        db = new AlarmDataController(this);

        voList = db.selectAllData();
        listView = (ListView)findViewById(R.id.listView);

        voAdapter = new AlarmVoAdapter(this, R.layout.alarm_list_item, voList);

        listView.setAdapter(voAdapter);
        listView.setOnItemClickListener(this);

        //db.insertAlarmData(vo);
        Utils.printDBData(db);
        Log.d("getDataCount", db.getDataCount() + " ");


        /*AlarmVO vo = new AlarmVO(0, "Y", "서울", "강남구", 15, 30, "월 일");
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
        voList.add(vo17);*/





//        checkIntentData();


    }

    @Override
    public void onClick(View v) {

        Log.d("onClick", "??");

        if(v.getId() == R.id.ibtn_alarm_list_back){

            finish();

        }else if(v.getId() == R.id.ibtn_alarm_list_add){

            MapConst.intentVO.setAlarmAdd(true);
            Intent intent = new Intent(this, AlarmActivity.class);
            startActivity(intent);

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AlarmVO vo = voAdapter.getItem(position);

        MapConst.intentVO.setAlarmAdd(false);
        MapConst.intentVO.setAlarmVoId(vo.getId());

        Intent intent = new Intent(this, AlarmActivity.class);
        startActivity(intent);

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


/*

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
*/

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alarm_plus_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
*/

/*

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "LIST " + requestCode + " " +  resultCode);

        //2, 4
        if(requestCode == MapConst.I_LIST_TO_ALARM_REQ_CODE && resultCode == MapConst.I_ALARM_TO_MAIN_RES_CODE){      // pass value

            if(data != null){
                if ("Y".equals(data.getStringExtra(MapConst.ALARM_IS_SET_LOCATION_TAG))) {
                    Intent intent = getIntent();
                    intent.putExtra(MapConst.ALARM_IS_SET_LOCATION_TAG, "Y");
                    setResult(MapConst.I_LIST_TO_MAIN_RES_CODE, intent);
                    finish();
                }
            }

    }else if(resultCode == MapConst.I_ALARM_TO_LIST_RES_CODE){
        Log.d(this.getLocalClassName(), "onActivityResult 알람 설정이 변경된 경우 listview를 업데이트 해야한다.");
    }


    }
*/

/*

    public void checkIntentData(){
        Intent i = getIntent();
        String isChagned = i.getStringExtra(MapConst.ALARM_IS_CHANGE_TAG);
        if ( "Y".equals(isChagned)){
            Log.d(this.getLocalClassName(), "onActivityResult 알람 설정이 변경된 경우 listview를 업데이트 해야한다.");
        }
    }

*/

    @Override
    protected void onResume() {
        super.onResume();


        if(MapConst.intentVO.isAlarmMarker()){
            this.finish();
        }else if(MapConst.intentVO.isAlarmMarking()){
            Intent intent = new Intent(this, AlarmActivity.class);
            startActivity(intent);
        }else {

            Log.d("onResume", "isUpdatedOrDeleted");
            MapConst.intentVO.setUpdatedOrDeleted(false);
            voAdapter.clear();
            voList = db.selectAllData();
            voAdapter = new AlarmVoAdapter(this, R.layout.alarm_list_item, voList);
            listView.setAdapter(voAdapter);
            listView.setOnItemClickListener(this);

        }


    }
}



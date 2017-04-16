package com.lthwea.finedust.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.lthwea.finedust.cnst.MyConst;
import com.lthwea.finedust.controller.AlarmDataController;
import com.lthwea.finedust.util.Utils;
import com.lthwea.finedust.vo.AlarmVO;

import java.util.List;

;

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
        //getSupportActionBar().hide();

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

    }

    @Override
    public void onClick(View v) {

        Log.d("onClick", "??");

        if(v.getId() == R.id.ibtn_alarm_list_back){

            finish();

        }else if(v.getId() == R.id.ibtn_alarm_list_add){
            MyConst.intentVO.setInitData();
            MyConst.intentVO.setAlarmAdd(true);
            Intent intent = new Intent(this, AlarmActivity.class);
            startActivity(intent);

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("onItemClick", id + " ");

        AlarmVO vo = voAdapter.getItem(position);

        MyConst.intentVO.setAlarmAdd(false);
        MyConst.intentVO.setAlarmVoId(vo.getId());

        Intent intent = new Intent(this, AlarmActivity.class);
        startActivity(intent);

    }


    private class AlarmVoAdapter extends ArrayAdapter<AlarmVO> {

        private int layoutResource;



        public AlarmVoAdapter(Context context, int resource, List<AlarmVO> objects) {
            super(context, resource, objects);
            this.layoutResource = resource;


        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //return super.getView(position, convertView, parent);
            //Log.d("getView", position + " ");

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
                final Switch sw = (Switch) view.findViewById(R.id.sw_alarm_item_isuse);


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
                    sw.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            AlarmVO vo = getItem(position);
                            if(sw.isChecked()){
                                vo.setIsUse("Y");
                                db.updateData(vo);
                                Utils.addAlarm(getApplicationContext(), vo);
                            }else{
                                vo.setIsUse("N");
                                db.updateData(vo);
                                Utils.deleteAlarm(getApplicationContext(), vo.getId());
                            }
                        }
                    });

                }

            }




            return view;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(MyConst.intentVO.isAlarmMarker()){
            this.finish();
        }else if(MyConst.intentVO.isAlarmMarking()){
            Intent intent = new Intent(this, AlarmActivity.class);
            startActivity(intent);
        }else if(MyConst.intentVO.getNeedListViewUpdate()){
            Log.d("onResume", "isUpdatedOrDeleted");
            MyConst.intentVO.setNeedListViewUpdate(false);
            voAdapter.clear();
            voList = db.selectAllData();
            for(AlarmVO vo : voList){
                voAdapter.add(vo);
            }
            voAdapter.notifyDataSetChanged();;
        }


    }
}



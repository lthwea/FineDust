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
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
;

import com.lthwea.finedust.R;
import com.lthwea.finedust.cnst.MyConst;
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
        TextView tv_alarm_item_loc;
        TextView tv_alarm_item_time;
        TextView tv_alarm_item_day;
        Switch sw;

        boolean flag = false;


        public AlarmVoAdapter(Context context, int resource, List<AlarmVO> objects) {
            super(context, resource, objects);
            this.layoutResource = resource;


        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //return super.getView(position, convertView, parent);
            Log.d("getView", position + " ");

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


                    sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            /*AlarmVO vo = getItem(position);
                            Log.d("TESTTEST", vo.getId() + " " +  isChecked + "");

                            if(isChecked){
                                vo.setIsUse("Y");
                                db.updateData(vo);
                                Utils.addAlarm(getApplicationContext(), vo);
                            }else{
                                vo.setIsUse("N");
                                db.updateData(vo);
                                Utils.deleteAlarm(getApplicationContext(), vo.getId());
                            }*/

                        }
                    });


                }

            }




            return view;
        }
    }

    /*public void test(final boolean isChecked, final AlarmVO vo){
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                String msg = "";
                if(isChecked == false){
                    msg = "미세먼지 알람을 해제하시겠습니까?";
                }else{
                    msg = "미세먼지 알람을 받으시겠습니까?";
                }

                Log.d("CheckedChangeListener", msg + " " + isChecked);

                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("미세먼지 알람");
                builder.setCancelable(true);
                builder.setMessage(msg);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isChecked == false) {
                            Utils.deleteAlarm(getApplicationContext(), vo.getId());
                        } else {
                            Utils.addAlarm(getApplicationContext(), vo);
                        }

                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isChecked == false) {
                            sw.setChecked(true);
                        } else {
                            sw.setChecked(false);
                        }
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }*/


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
        if(requestCode == MyConst.I_LIST_TO_ALARM_REQ_CODE && resultCode == MyConst.I_ALARM_TO_MAIN_RES_CODE){      // pass value

            if(data != null){
                if ("Y".equals(data.getStringExtra(MyConst.ALARM_IS_SET_LOCATION_TAG))) {
                    Intent intent = getIntent();
                    intent.putExtra(MyConst.ALARM_IS_SET_LOCATION_TAG, "Y");
                    setResult(MyConst.I_LIST_TO_MAIN_RES_CODE, intent);
                    finish();
                }
            }

    }else if(resultCode == MyConst.I_ALARM_TO_LIST_RES_CODE){
        Log.d(this.getLocalClassName(), "onActivityResult 알람 설정이 변경된 경우 listview를 업데이트 해야한다.");
    }


    }
*/

/*

    public void checkIntentData(){
        Intent i = getIntent();
        String isChagned = i.getStringExtra(MyConst.ALARM_IS_CHANGE_TAG);
        if ( "Y".equals(isChagned)){
            Log.d(this.getLocalClassName(), "onActivityResult 알람 설정이 변경된 경우 listview를 업데이트 해야한다.");
        }
    }

*/

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TESTTEST", "onResume" + MyConst.intentVO.getNeedListViewUpdate());

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
            voAdapter.notifyDataSetChanged();
            //listView.setAdapter(voAdapter);
            //listView.setOnItemClickListener(this);
        }






    }
}



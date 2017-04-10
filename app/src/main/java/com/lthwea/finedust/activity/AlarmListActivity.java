package com.lthwea.finedust.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.lthwea.finedust.vo.AlarmVO;

import java.util.ArrayList;
import java.util.List;

import com.lthwea.finedust.R;

/**
 * Created by LeeTaeHun on 2017. 4. 10..
 */

public class AlarmListActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.alarm_list_activity);
        setContentView(R.layout.activity_alarm2);

        // back button
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("미세먼지 알림 설정");


/*
        List<AlarmVO> voList = new ArrayList<>();
        boolean[] test = new boolean[]{ false, false, false, false, false, false, false};
        //AlarmVO(boolean isUse, String cityName, String sidoName, boolean[] days, String time, int hour, int min)


        AlarmVO vo = new AlarmVO(true, "서울", "강남구", test, "07시30분", 07, 30);
        voList.add(vo);

        ListView listView = (ListView)findViewById(R.id.listView);
        AlarmVoAdapter voAdapter = new AlarmVoAdapter(this, R.layout.alarm_list_item, voList);
        listView.setAdapter(voAdapter);*/

    }


/*



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
                TextView tv1 = (TextView) view.findViewById(R.id.tv_alarm_item_loc);
                TextView tv2 = (TextView) view.findViewById(R.id.tv_alarm_item_time);
                Switch sw = (Switch) view.findViewById(R.id.sw_alarm_item_isuse);

                if (tv1 != null) {
                    tv1.setText(vo.getSidoName() + "" + vo.getCityName());
                }

                if (tv2 != null) {
                    tv2.setText(vo.getTime());
                }

                if (sw != null) {
                    sw.setText(vo.getIsUse() + " ");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alarm_plus_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



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

*/


}



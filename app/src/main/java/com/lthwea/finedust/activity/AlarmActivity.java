package com.lthwea.finedust.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.lthwea.finedust.R;
import com.lthwea.finedust.alarm.MyAlarmReceiver;
import com.lthwea.finedust.controller.PrefController;
import com.lthwea.finedust.util.Utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by LeeTaeHun on 2017. 4. 7..
 */

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener
{

    private String ALARM_LOCATION = "";
    private String ALARM_TIME = "";         // NN:NN
    private int ALARM_HOUR = 0;
    private int ALARM_MIN = 0;
    private String ALARM_DAY = "";




    private PrefController pref;



    RelativeLayout rl_loc, rl_time;

    Button btn_alarm_completed;

    TextView tv_alarm_loc, tv_alarm_time;

    ToggleButton tbtn_0, tbtn_1, tbtn_2, tbtn_3, tbtn_4, tbtn_5, tbtn_6, tbtn_7;


    // String array for alert dialog multi choice items
    final String[] days = new String[]{
            "월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"
            //0,       1,       2,       3,       4,      5,     6,
    };

    // Boolean array for initial selected items
    final boolean[] checkedDays = new boolean[]{
            false,false,false,false,false,false,false
    };

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Log.d("AlarmActivity", "onCreate call");

        // back button
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("미세먼지 알림 설정");

        tv_alarm_loc = (TextView) findViewById(R.id.tv_alarm_loc);
        tv_alarm_loc.setOnClickListener(this);

        tv_alarm_time = (TextView) findViewById(R.id.tv_alarm_time);
        tv_alarm_time.setOnClickListener(this);

        Calendar cal = Calendar.getInstance();
        ALARM_HOUR = cal.get(Calendar.HOUR_OF_DAY);
        ALARM_MIN = cal.get(Calendar.MINUTE);

        tv_alarm_time.setText( Utils.getTimeStringFormat(ALARM_HOUR, ALARM_MIN) );

        rl_loc = (RelativeLayout) findViewById(R.id.rl_loc);
        //rl_loc.setOnClickListener(this);

        rl_time = (RelativeLayout) findViewById(R.id.rl_time);
        //rl_time.setOnClickListener(this);

        btn_alarm_completed = (Button) findViewById(R.id.btn_alarm_completed) ;
        btn_alarm_completed.setOnClickListener(this);



/*

        btn_alarm_setting = (Button) findViewById(R.id.btn_alarm_setting);
        btn_alarm_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pref.isAlarmUse()){
                    cancelAlarm();      //삭제
                }else{
                    setAlarm();         //등록
                }
            }
        });
*/


        Intent i = getIntent();
        ALARM_LOCATION = i.getStringExtra("ALARM_LOCATION");
        if(ALARM_LOCATION == null || "".equals(ALARM_LOCATION)){

        }else{
            tv_alarm_loc.setText(ALARM_LOCATION);
        }



        pref = new PrefController(this);
        if( !pref.isAlarmUse()){
            rl_loc.setClickable(true);
            rl_time.setClickable(true);

//            findViewById(R.id.tv_alarm_arrow1).setVisibility(View.VISIBLE);
//            findViewById(R.id.tv_alarm_arrow2).setVisibility(View.VISIBLE);
//            findViewById(R.id.tv_alarm_arrow3).setVisibility(View.VISIBLE);


            Log.d("ALARM_LOCATION", ALARM_LOCATION+ "   !!!   !!");

        }else{
            rl_loc.setClickable(false);
            rl_time.setClickable(false);

//            findViewById(R.id.tv_alarm_arrow1).setVisibility(View.INVISIBLE);
//            findViewById(R.id.tv_alarm_arrow2).setVisibility(View.INVISIBLE);
//            findViewById(R.id.tv_alarm_arrow3).setVisibility(View.INVISIBLE);

            ALARM_LOCATION = pref.getPrefIAlarmLocation();
            ALARM_TIME = pref.getPrefAlarmTime();
            ALARM_DAY = pref.getPrefIAlarmDay();

        }



        //checkAlarmData();

    }

    @Override
    public void onClick(View v) {
        Log.d("onClick", v.getId() + "asdasdadads");

        if( v.getId() == R.id.tv_alarm_loc ){
            Intent i = getIntent();
            i.putExtra("isSetLocation", "Y");
            setResult(MainActivity.INTENT_ALARM_CODE, i);
            finish();

        }else if( v.getId() == R.id.tv_alarm_time ){

            int hour, minute;
            GregorianCalendar calendar = new GregorianCalendar();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            new TimePickerDialog(AlarmActivity.this, timeSetListener, hour, minute, false).show();

        }else if ( v.getId() == R.id.btn_alarm_completed ){

        }
    }


/*

    public void setAlarm(){

        //유효성체크
        boolean isValid = checkAlarmData();
        if(isValid) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("미세먼지 알림");
            builder.setCancelable(true);
            builder.setMessage("알림을 등록하시겠습니까?");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    Intent i = new Intent(getApplicationContext(), MyAlarmReceiver.class);
                    i.putExtra("location", ALARM_LOCATION);
                    i.putExtra("week", checkedDays);


                    PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);


                    */
/*Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, ALARM_HOUR);
                    calendar.set(Calendar.MINUTE, ALARM_MIN);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);*//*

                    //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);

                    if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
                        Log.d("ExactAndAllowWhileIdle", "등록");
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, Utils.getTriggerAtMillis(ALARM_HOUR, ALARM_MIN), pi);
                    }
                    else if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ){
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, Utils.getTriggerAtMillis(ALARM_HOUR, ALARM_MIN), pi);
                    }
                    else{
                        alarmManager.set(AlarmManager.RTC_WAKEUP, Utils.getTriggerAtMillis(ALARM_HOUR, ALARM_MIN), pi);
                    }

                    Log.d("setAlarm" ,ALARM_LOCATION + "," + ALARM_HOUR + "," + ALARM_MIN + "," + ALARM_DAY + " 알림 설정 ");

                    ALARM_TIME = Integer.toString(ALARM_HOUR) + ":" + Integer.toString(ALARM_MIN);
                    pref.setPrefAlarm(ALARM_LOCATION, ALARM_TIME, ALARM_DAY);
                    pref.setIsAlarmUse(true);
                    Toast.makeText(getApplicationContext(), "알림 설정이 완료되었습니다. ", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();

        }else{
            Toast.makeText(this, "데이터를 선택하세요.", Toast.LENGTH_LONG).show();
        }

    }
*/


/*
    public boolean checkAlarmData(){

        boolean isValid = true;

        if(ALARM_LOCATION == null || "".equals(ALARM_LOCATION) || "-".equals(ALARM_LOCATION)){
            tv_alarm_loc_value.setText("");
            isValid = false;
        }else{
            tv_alarm_loc_value.setText(ALARM_LOCATION);
        }

        if(ALARM_TIME == null || "".equals(ALARM_TIME) || "-".equals(ALARM_TIME)){
            tv_alarm_time_value.setText("");
            isValid = false;
        }else{
            tv_alarm_time_value.setText(ALARM_TIME);
        }


        if(ALARM_DAY == null || "".equals(ALARM_DAY) || "-".equals(ALARM_DAY)){
            tv_alarm_day_value.setText("");
            isValid = false;
        }else{
            tv_alarm_day_value.setText(ALARM_DAY);
        }

        Log.d("checkAlarmData", "isValid" + isValid);
        return isValid;

    }
*/




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            ALARM_HOUR = hourOfDay;
            ALARM_MIN = minute;
            ALARM_TIME = Utils.getTimeStringFormat(ALARM_HOUR, ALARM_MIN);
            tv_alarm_time.setText(ALARM_TIME);
            //checkAlarmData();
            //Toast.makeText(AlarmActivity.this, msg, Toast.LENGTH_SHORT).show();
        }

    };






    /*
    public void popDayDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Convert the color array to list
        final List<String> daysList = Arrays.asList(days);

        builder.setMultiChoiceItems(days, checkedDays, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // Update the current focused item's checked status
                checkedDays[which] = isChecked;
                // Get the current focused item
                //String currentItem = daysList.get(which);
                // Notify the current action
                //Toast.makeText(getApplicationContext(),currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();

            }
        });

        // Specify the dialog is not cancelable
        builder.setCancelable(false);
        // Set a title for alert dialog
        builder.setTitle("반복할 요일을 선택해주세요.");
        // Set the positive/yes button click listener
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ALARM_DAY = "";

                int tmp = 0;

                for (int i = 0; i<checkedDays.length; i++) {
                    boolean checked = checkedDays[i];
                    if (checked) {
                        ALARM_DAY += daysList.get(i).substring(0, 1) + " ";

                    }
                }

                checkAlarmData();
               // Toast.makeText(getApplicationContext(), tmp, Toast.LENGTH_SHORT).show();
            }
        });

        // Set the negative/no button click listener
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the negative button
            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }
    */






    public void cancelAlarm() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("미세먼지 알림");
        builder.setCancelable(true);
        builder.setMessage("알림을 삭제하시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AlarmManager am = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
                PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                if (sender != null) {
                    am.cancel(sender);
                    sender.cancel();
                }

                ALARM_LOCATION = "";
                ALARM_DAY = "";
                ALARM_TIME = "";
                pref.setIsAlarmUse(false);
                pref.setPrefAlarm("", "", "");
                Toast.makeText(getApplicationContext(), "알림 삭제가 완료되었습니다. ", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();

    }



    public void printLog(String msg){
        Log.d("AlarmAcitivity", msg);
    }


}


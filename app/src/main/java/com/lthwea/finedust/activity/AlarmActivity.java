package com.lthwea.finedust.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.lthwea.finedust.R;
import com.lthwea.finedust.alarm.AlarmManagerUtil;
import com.lthwea.finedust.cnst.MapConst;
import com.lthwea.finedust.vo.MarkerVO;

/**
 * Created by LeeTaeHun on 2017. 4. 7..
 */

public class AlarmActivity extends AppCompatActivity
{

    TextView tv_alarm_loc_value, tv_alarm_time_value, tv_alarm_day_value;

    private RelativeLayout rl_loc, rl_time, rl_day;

    private String ALARM_LOCATION = "";
    private int ALARM_HOUR = 0;
    private int ALARM_MIN = 0;
    private String ALARM_DAY = "";
    private String ALARM_PM10VALUE = "";

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        // back button
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("미세먼지 알림 설정");

       /* 상태바, 타이틀바 */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_default_color));
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.color_default_color));
        }


        tv_alarm_loc_value = (TextView) findViewById(R.id.tv_alarm_loc_value);
        tv_alarm_time_value = (TextView) findViewById(R.id.tv_alarm_time_value);
        tv_alarm_day_value = (TextView) findViewById(R.id.tv_alarm_day_value);



        Intent i = getIntent();
        ALARM_LOCATION= i.getStringExtra("ALARM_LOCATION");
        Log.d("ALARM_LOCATION", ALARM_LOCATION+ "   !!!   !!");


        checkAlarmData();




        findViewById(R.id.rl_loc).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("isSetLocation", "Y");
                setResult(MainActivity.INTENT_ALARM_CODE, i);
                finish();
            }
        });

        findViewById(R.id.rl_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour, minute;
                GregorianCalendar calendar = new GregorianCalendar();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);
                new TimePickerDialog(AlarmActivity.this, timeSetListener, hour, minute, false).show();

            }

        });

        findViewById(R.id.rl_day).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                popDayDialog();
            }
        });


        findViewById(R.id.btn_alarm_setting).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                alarmSetting();
            }
        });



    }


    public boolean checkAlarmData(){

        boolean isValid = true;

        if(ALARM_LOCATION == null || "".equals(ALARM_LOCATION) || "-".equals(ALARM_LOCATION)){
            tv_alarm_loc_value.setText("-");
            isValid = false;
        }else{
            tv_alarm_loc_value.setText(ALARM_LOCATION);
        }

        if(ALARM_HOUR == 0 && ALARM_MIN == 0){
            isValid = false;
            tv_alarm_time_value.setText("-");
        }else{
            tv_alarm_time_value.setText( ALARM_HOUR + " : " + ALARM_MIN);
        }

        if(ALARM_DAY == null || "".equals(ALARM_DAY) || "-".equals(ALARM_DAY)){
            tv_alarm_day_value.setText("-");
            isValid = false;
        }else{
            tv_alarm_day_value.setText(ALARM_DAY);
        }

        Log.d("checkAlarmData", "isValid" + isValid);
        return isValid;

    }


    public void alarmSetting(){

        setClock();

        //유효성체크
        boolean isValid = checkAlarmData();
        if(isValid) {

            //알림설정



        }






    }


    /**
     * @param flag            신고주기적인 시간 간격,flag = 0 그것은 한 번 알람을 나타냅니다, flag = 1 매일 알람 시계의 알림을 나타냅니다(일일 간격),
     *                        flag = 2 표현 알람 시계 주에 주를 상기시켜（일주의주기적인 간격）
     * @param hour            시
     * @param minute          분
     * @param id              알람 시계 ID
     * @param week            week=0 일 경보에 또는 주기적 따라 시간 알람을 나타냅니다，0이 아니면 매주 정기 간행물에 대한주의 경보를 대신 몇 가지 경우
     * @param tips            알람 메시지
     * @param soundOrVibrator 도 2는 음성을 나타내는 진동을 실행한다，1은 단지 벨소리를 생각 나게，0은 진동을 의미
    setAlarm(Context context, int flag, int hour, int minute, int id, int week, String tips, int soundOrVibrator)
     */
    private void setClock() {

        AlarmManagerUtil.setAlarm(this, 1, 18, 20, 0, 0, "알람 시계 울렸다", 2);
        Toast.makeText(this, "알람 설정 성공", Toast.LENGTH_LONG).show();

        /*if (time != null && time.length() > 0) {

            String[] times = time.split(":");

            if (cycle == 0) {//알람 시계는 매일인가
                AlarmManagerUtil.setAlarm(this, 0, Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0, 0, "알람 시계 울렸다", ring);
            }

            if(cycle == -1){//알람 시계는 한 번만 울렸다
                AlarmManagerUtil.setAlarm(this, 1, Integer.parseInt(times[0]), Integer.parseInt (times[1]), 0, 0, "알람 시계 울렸다", ring);
            }else {//객관식, 알람 시계 몇 주

                String weeksStr = parseRepeat(cycle, 1);
                String[] weeks = weeksStr.split(",");
                for (int i = 0; i < weeks.length; i++) {
                    AlarmManagerUtil.setAlarm(this, 2,
                            Integer.parseInt(times[0]), Integer.parseInt(times[1]), i, Integer.parseInt(weeks[i]), "알람 시계 울렸다", ring);
                }

            }

        }*/

    }

/*

    */
/**
     * @param repeat 진 클럭 사이클을 구문 분석
     * @param flag   flag=중국 문자 0을 반환 월요일，周二cycle等，flag=1,반환weeks(1,2,3)
     * @return
     *//*

    public static String parseRepeat(int repeat, int flag) {
        String cycle = "";
        String weeks = "";
        if (repeat == 0) {
            repeat = 127;
        }
        if (repeat % 2 == 1) {
            cycle = "월요일";
            weeks = "1";
        }
        if (repeat % 4 >= 2) {
            if ("".equals(cycle)) {
                cycle = "화요일";
                weeks = "2";
            } else {
                cycle = cycle + "," + "화요일";
                weeks = weeks + "," + "2";
            }
        }
        if (repeat % 8 >= 4) {
            if ("".equals(cycle)) {
                cycle = "수요일";
                weeks = "3";
            } else {
                cycle = cycle + "," + "수요일";
                weeks = weeks + "," + "3";
            }
        }
        if (repeat % 16 >= 8) {
            if ("".equals(cycle)) {
                cycle = "목요일";
                weeks = "4";
            } else {
                cycle = cycle + "," + "목요일";
                weeks = weeks + "," + "4";
            }
        }
        if (repeat % 32 >= 16) {
            if ("".equals(cycle)) {
                cycle = "금요일";
                weeks = "5";
            } else {
                cycle = cycle + "," + "금요일";
                weeks = weeks + "," + "5";
            }
        }
        if (repeat % 64 >= 32) {
            if ("".equals(cycle)) {
                cycle = "토요일";
                weeks = "6";
            } else {
                cycle = cycle + "," + "토요일";
                weeks = weeks + "," + "6";
            }
        }
        if (repeat / 64 == 1) {
            if ("".equals(cycle)) {
                cycle = "일요일";
                weeks = "7";
            } else {
                cycle = cycle + "," + "일요일";
                weeks = weeks + "," + "7";
            }
        }

        return flag == 0 ? cycle : weeks;
    }

*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
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
            checkAlarmData();
            //Toast.makeText(AlarmActivity.this, msg, Toast.LENGTH_SHORT).show();
        }

    };



    // String array for alert dialog multi choice items
    final String[] days = new String[]{
            "월요일", "화요일", "수요일", "목요일", "금요일", "토요알", "일요일"
            //0,    1,   2,   3,    4,    5,   6,
    };

    // Boolean array for initial selected items
    final boolean[] checkedDays = new boolean[]{
            false,false,false,false,false,false,false
    };


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








}


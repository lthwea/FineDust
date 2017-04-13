package com.lthwea.finedust.activity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.lthwea.finedust.R;
import com.lthwea.finedust.cnst.MapConst;
import com.lthwea.finedust.controller.AlarmDataController;
import com.lthwea.finedust.util.Utils;
import com.lthwea.finedust.vo.AlarmVO;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by LeeTaeHun on 2017. 4. 7..
 */

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener
{

    // 이전 액티비티에서 인텐트로 가져옴
    private boolean IS_UPDATE = false;       // update or add
    private int ALARM_ID = 99;       // 99 is default value




    private String ALARM_LOCATION = "";      // get intent value From MainActivity
    private String ALARM_TIME = "";
    private int ALARM_HOUR = 0;
    private int ALARM_MIN = 0;
    private boolean[] ALARM_DAYS = new boolean[]{
            false,false,false,false,false,false,false
    };

   /* String[] days = new String[]{
            "월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"
            //0,       1,       2,       3,       4,      5,     6,
    };*/

    private boolean isVaildLocation = false;
    private boolean isVaildTime = true;
    private boolean isVaildDay = false;


    /* SQLite */
    private AlarmDataController db;



    //RelativeLayout rl_loc, rl_time;

    Button btn_alarm_completed;
    TextView tv_alarm_loc, tv_alarm_time;
    ToggleButton tbtn_0, tbtn_1, tbtn_2, tbtn_3, tbtn_4, tbtn_5, tbtn_6;
    ImageButton ibtn_alarm_back, ibtn_alarm_delete;

    // String array for alert dialog multi choice items




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        getSupportActionBar().hide();

        Log.d("AlarmActivity", "onCreate call");

        tv_alarm_loc = (TextView) findViewById(R.id.tv_alarm_loc);
        tv_alarm_loc.setOnClickListener(this);

        tv_alarm_time = (TextView) findViewById(R.id.tv_alarm_time);
        tv_alarm_time.setOnClickListener(this);

        btn_alarm_completed = (Button) findViewById(R.id.btn_alarm_completed);
        btn_alarm_completed.setOnClickListener(this);

        ibtn_alarm_back = (ImageButton) findViewById(R.id.ibtn_alarm_back);
        ibtn_alarm_delete = (ImageButton) findViewById(R.id.ibtn_alarm_delete);
        ibtn_alarm_back.setOnClickListener(this);
        ibtn_alarm_delete.setOnClickListener(this);


        Calendar cal = Calendar.getInstance();
        ALARM_HOUR = cal.get(Calendar.HOUR_OF_DAY);
        ALARM_MIN = cal.get(Calendar.MINUTE);
        tv_alarm_time.setText( Utils.getTimeStringFormat(ALARM_HOUR, ALARM_MIN) );

        tbtn_0 = (ToggleButton) findViewById(R.id.tbtn_mon);
        tbtn_1 = (ToggleButton) findViewById(R.id.tbtn_tue);
        tbtn_2 = (ToggleButton) findViewById(R.id.tbtn_wen);
        tbtn_3 = (ToggleButton) findViewById(R.id.tbtn_thu);
        tbtn_4 = (ToggleButton) findViewById(R.id.tbtn_fri);
        tbtn_5 = (ToggleButton) findViewById(R.id.tbtn_sat);
        tbtn_6 = (ToggleButton) findViewById(R.id.tbtn_sun);
        tbtn_0.setOnClickListener(this);
        tbtn_1.setOnClickListener(this);
        tbtn_2.setOnClickListener(this);
        tbtn_3.setOnClickListener(this);
        tbtn_4.setOnClickListener(this);
        tbtn_5.setOnClickListener(this);
        tbtn_6.setOnClickListener(this);


        db = new AlarmDataController(this);



        checkIntentData();
        changeUpdateUI();
        isVaildAlarmData();


        AlarmVO vo = db.selectData(ALARM_ID);
        Log.d("AlarmActivityIntent", "vo : " + vo.toString());

        if(ALARM_ID != 9999 && vo != null){

            Log.d("ALARM_ID", ALARM_ID + " ");

            ALARM_LOCATION = vo.getSidoName() + " " + vo.getCityName();
            ALARM_HOUR = vo.getHour();
            ALARM_MIN = vo.getMin();
            ALARM_TIME = Utils.getTimeStringFormat(ALARM_HOUR, ALARM_MIN);

            isVaildLocation = true;
            isVaildDay = true;

            tv_alarm_loc.setText(ALARM_LOCATION);
            tv_alarm_time.setText(ALARM_TIME);
            convertStringToBooleanDays(vo.getDays());   // including UI Update
            changeUpdateUI();
            isVaildAlarmData();

            MapConst.intentVO.setAlarmVoId(MapConst.INTENT_DEFAULT_ID);
        }



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


/*
        pref = new PrefController(this);
        if( !pref.isAlarmUse()){
//            rl_loc.setClickable(true);
//            rl_time.setClickable(true);

//            findViewById(R.id.tv_alarm_arrow1).setVisibility(View.VISIBLE);
//            findViewById(R.id.tv_alarm_arrow2).setVisibility(View.VISIBLE);
//            findViewById(R.id.tv_alarm_arrow3).setVisibility(View.VISIBLE);


            Log.d("ALARM_LOCATION", ALARM_LOCATION+ "   !!!   !!");

        }else{
//            rl_loc.setClickable(false);
//            rl_time.setClickable(false);

//            findViewById(R.id.tv_alarm_arrow1).setVisibility(View.INVISIBLE);
//            findViewById(R.id.tv_alarm_arrow2).setVisibility(View.INVISIBLE);
//            findViewById(R.id.tv_alarm_arrow3).setVisibility(View.INVISIBLE);

//            ALARM_LOCATION = pref.getPrefIAlarmLocation();
//            ALARM_TIME = pref.getPrefAlarmTime();
//            ALARM_DAY = pref.getPrefIAlarmDay();*/

    }



        //checkAlarmData();



    @Override
    public void onClick(View v) {
        Log.d("onClick", v.getId() + "  ");

        int id = v.getId();

        if( id == R.id.tv_alarm_loc ){

            MapConst.intentVO.setAlarmMarker(true);
            MapConst.intentVO.setCheckedDays(ALARM_DAYS);
            MapConst.intentVO.setAlarmMarking(true);
            finish();

        }else if( id == R.id.tv_alarm_time ){

            int hour, minute;

            GregorianCalendar calendar = new GregorianCalendar();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);

            new TimePickerDialog(AlarmActivity.this, timeSetListener, hour, minute, false).show();

        }else if ( id == R.id.btn_alarm_completed ){

            boolean isVaild = isVaildAlarmData();
            if(isVaild){

                if(!IS_UPDATE){

                    //신규는 인서트

                    String msg = "지역 : " + ALARM_LOCATION +
                                 "\n시간 : " + Utils.getTimeStringFormat(ALARM_HOUR, ALARM_MIN) +
                                 "\n반복 요일 : " + getStringDaysFormat() +
                                 "\n위 기준으로 미세먼지/초미세먼지 알림을 받으시겠습니까?";

                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("미세먼지 알람 설정");
                    builder.setCancelable(true);
                    builder.setMessage(msg);
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String[] sidoCity = ALARM_LOCATION.split(" ");
                            int id = db.getDataCount();
                            AlarmVO vo = new AlarmVO(id+1, "Y", sidoCity[0], sidoCity[1], ALARM_HOUR, ALARM_MIN, getStringDaysFormat());
                            Log.d("insertDB", vo.toString());
                            Log.d("insertDB", sidoCity[0]);
                            Log.d("insertDB", sidoCity[1]);

                            db.insertAlarmData(vo);
                            Toast.makeText(AlarmActivity.this, "알람 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            //printDBData();


                            MapConst.intentVO.setLocName(null);
                            ALARM_LOCATION = null;


                            /*Intent intent = new Intent(getApplicationContext(), AlarmListActivity.class);
                            startActivity(intent);*/
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


                    //수정은 업데이트


                }

            }else{
                Toast.makeText(this, "데이터를 선택하세요", Toast.LENGTH_SHORT).show();
            }

        }else if(  id == R.id.ibtn_alarm_back ){

            /*if(MapConst.intentVO.isAlarmMarking()){
                MapConst.intentVO.setAlarmMarking(false);
                Intent intent = new Intent(getApplicationContext(), AlarmListActivity.class);
                startActivity(intent);
                this.finish();
            }else{
                this.finish();
            }
            */

            this.finish();

            /*Intent intent = new Intent(getApplicationContext(), AlarmListActivity.class);
            startActivity(intent);
            */




        }else if(  id == R.id.ibtn_alarm_delete ){
            //삭제

            String msg = "해당 알람을 삭제하시겠습니까?";

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("미세먼지 알람 설정");
            builder.setCancelable(true);
            builder.setMessage(msg);
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.deleteData(ALARM_ID);
                    Toast.makeText(AlarmActivity.this, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();


                    MapConst.intentVO.setUpdatedOrDeleted(true);
//                    Intent intent = new Intent(getApplicationContext(), AlarmListActivity.class);
//                    startActivity(intent);
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

        /* Toggle Button Event */
        else if(id == R.id.tbtn_mon){
            if( ALARM_DAYS[0] == false) ALARM_DAYS[0] = true;
            else                         ALARM_DAYS[0] = false;
            isVaildAlarmData();
        }else if(id == R.id.tbtn_tue){
            if( ALARM_DAYS[1] == false) ALARM_DAYS[1] = true;
            else                         ALARM_DAYS[1] = false;
            isVaildAlarmData();
        }else if(id == R.id.tbtn_wen){
            if( ALARM_DAYS[2] == false) ALARM_DAYS[2] = true;
            else                         ALARM_DAYS[2] = false;
            isVaildAlarmData();
        }else if(id == R.id.tbtn_thu){
            if( ALARM_DAYS[3] == false) ALARM_DAYS[3] = true;
            else                         ALARM_DAYS[3] = false;
            isVaildAlarmData();
        }else if(id == R.id.tbtn_fri){
            if( ALARM_DAYS[4] == false) ALARM_DAYS[4] = true;
            else                         ALARM_DAYS[4] = false;
            isVaildAlarmData();
        }else if(id == R.id.tbtn_sat){
            if( ALARM_DAYS[5] == false) ALARM_DAYS[5] = true;
            else                         ALARM_DAYS[5] = false;
            isVaildAlarmData();
        }else if(id == R.id.tbtn_sun){
            if( ALARM_DAYS[6] == false) ALARM_DAYS[6] = true;
            else                         ALARM_DAYS[6] = false;
            isVaildAlarmData();
        }



    }



    public void changeUpdateUI(){

        if(ALARM_LOCATION == null || "".equals(ALARM_LOCATION)){
            isVaildLocation = false;
        }else{
            tv_alarm_loc.setText(ALARM_LOCATION);
            isVaildLocation = true;
        }

        if(IS_UPDATE == false){
            ibtn_alarm_delete.setVisibility(View.INVISIBLE);
            btn_alarm_completed.setText("완료");
        }else{
            ibtn_alarm_delete.setVisibility(View.VISIBLE);
            btn_alarm_completed.setText("수정");
        }

        //UI Update
        if (ALARM_DAYS[0] == true) tbtn_0.setChecked(true);
        else                       tbtn_0.setChecked(false);

        if (ALARM_DAYS[1] == true) tbtn_1.setChecked(true);
        else                       tbtn_1.setChecked(false);

        if (ALARM_DAYS[2] == true) tbtn_2.setChecked(true);
        else                       tbtn_2.setChecked(false);

        if (ALARM_DAYS[3] == true) tbtn_3.setChecked(true);
        else                       tbtn_3.setChecked(false);

        if (ALARM_DAYS[4] == true) tbtn_4.setChecked(true);
        else                       tbtn_4.setChecked(false);

        if (ALARM_DAYS[5] == true) tbtn_5.setChecked(true);
        else                       tbtn_5.setChecked(false);

        if (ALARM_DAYS[6] == true) tbtn_6.setChecked(true);
        else                       tbtn_6.setChecked(false);

    }


    // 데이터 유효성을 검사함. 액티비티 실행, 버튼 입력 등 매번 발생함. 가장 중요함.
    public boolean isVaildAlarmData(){

        isVaildDay = isVaildDaysData();

        if (isVaildLocation && isVaildDay && isVaildTime){
            btn_alarm_completed.setBackgroundColor(ContextCompat.getColor(this, R.color.color_widget_green));
            return true;
        }
        else{
            btn_alarm_completed.setBackgroundColor(ContextCompat.getColor(this, R.color.line_and_outline_grey));
            return false;
        }

    }

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            ALARM_HOUR = hourOfDay;
            ALARM_MIN = minute;
            ALARM_TIME = Utils.getTimeStringFormat(ALARM_HOUR, ALARM_MIN);
            tv_alarm_time.setText(ALARM_TIME);
            isVaildAlarmData();
            //Toast.makeText(AlarmActivity.this, msg, Toast.LENGTH_SHORT).show();
        }

    };


    public String getStringDaysFormat(){
        String str = "";
        if (ALARM_DAYS[0] == true) str += "월 ";
        if (ALARM_DAYS[1] == true) str += "화 ";
        if (ALARM_DAYS[2] == true) str += "수 ";
        if (ALARM_DAYS[3] == true) str += "목 ";
        if (ALARM_DAYS[4] == true) str += "금 ";
        if (ALARM_DAYS[5] == true) str += "토 ";
        if (ALARM_DAYS[6] == true) str += "일 ";
        return str;
    }

    public void convertStringToBooleanDays(String str){

        if(str != null){
            String[] tmp = str.split(" ");
            for(int i = 0 ; i < tmp.length ; i++){
                String s = tmp[i];
                if("월".equals(s)) ALARM_DAYS[0] = true;
                else if("화".equals(s)) ALARM_DAYS[1] = true;
                else if("수".equals(s)) ALARM_DAYS[2] = true;
                else if("목".equals(s)) ALARM_DAYS[3] = true;
                else if("금".equals(s)) ALARM_DAYS[4] = true;
                else if("토".equals(s)) ALARM_DAYS[5] = true;
                else if("일".equals(s)) ALARM_DAYS[6] = true;
            }
        }


        //UI Update
//        if (ALARM_DAYS[0] == true) tbtn_0.setChecked(true);
//        else                       tbtn_0.setChecked(false);
//
//        if (ALARM_DAYS[1] == true) tbtn_1.setChecked(true);
//        else                       tbtn_1.setChecked(false);
//
//        if (ALARM_DAYS[2] == true) tbtn_2.setChecked(true);
//        else                       tbtn_2.setChecked(false);
//
//        if (ALARM_DAYS[3] == true) tbtn_3.setChecked(true);
//        else                       tbtn_3.setChecked(false);
//
//        if (ALARM_DAYS[4] == true) tbtn_4.setChecked(true);
//        else                       tbtn_4.setChecked(false);
//
//        if (ALARM_DAYS[5] == true) tbtn_5.setChecked(true);
//        else                       tbtn_5.setChecked(false);
//
//        if (ALARM_DAYS[6] == true) tbtn_6.setChecked(true);
//        else                       tbtn_6.setChecked(false);

    }

    public boolean isVaildDaysData(){
        for(int i = 0 ; i < ALARM_DAYS.length ; i++){
            if (ALARM_DAYS[i] == true) return true;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "Alarm " + requestCode + " " + requestCode);

        /*// this -> main 으로 위치 선택하고 넘어온 값을 받아와서 텍스트에 뿌림.
        if(requestCode == MapConst.ALARM_TO_MAIN_REQ_CODE_INTENT){
            if( data != null ){
                Intent i = getIntent();
                ALARM_LOCATION = i.getStringExtra(MapConst.ALARM_LOCATION_TAG);
                if(ALARM_LOCATION == null || "".equals(ALARM_LOCATION)){            //첫실행일경우
                    isVaildLocation = false;
                }else{
                    tv_alarm_loc.setText(ALARM_LOCATION);
                    isVaildLocation = true;
                }
            }
        }*/

    }

    public void checkIntentData(){
        IS_UPDATE = !MapConst.intentVO.isAlarmAdd();
        ALARM_ID = MapConst.intentVO.getAlarmVoId();
        ALARM_LOCATION = MapConst.intentVO.getLocName();



        if(  MapConst.intentVO.isAlarmMarking() == true){
            MapConst.intentVO.setAlarmMarker(false);
            ALARM_DAYS = MapConst.intentVO.getCheckedDays();
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





/*

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
*/



    public void printLog(String msg){
        Log.d("AlarmAcitivity", msg);
    }


}


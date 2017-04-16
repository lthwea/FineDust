package com.lthwea.finedust.view;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.lthwea.finedust.R;
import com.lthwea.finedust.cnst.MyConst;
import com.lthwea.finedust.controller.AlarmDataController;
import com.lthwea.finedust.util.Utils;
import com.lthwea.finedust.vo.AlarmVO;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.lthwea.finedust.util.Utils.addAlarm;
import static com.lthwea.finedust.util.Utils.deleteAlarm;
import static com.lthwea.finedust.util.Utils.updateAlarm;

/**
 * Created by LeeTaeHun on 2017. 4. 7..
 */

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    /* View */
    Button btn_alarm_completed;
    TextView tv_alarm_loc, tv_alarm_time;
    ToggleButton tbtn_0, tbtn_1, tbtn_2, tbtn_3, tbtn_4, tbtn_5, tbtn_6;
    ImageButton ibtn_alarm_back, ibtn_alarm_delete;

    /* Intent Flag */
    private boolean IS_UPDATE = false;
    private int ALARM_ID = MyConst.INTENT_DEFAULT_ID;

    /* Data Value var*/
    private String ALARM_LOCATION = "";      // get intent value From MainActivity
    private String ALARM_TIME = "";
    private int ALARM_HOUR = 0;
    private int ALARM_MIN = 0;
    private boolean[] ALARM_DAYS = new boolean[]{
            false,false,false,false,false,false,false
    };

    /* Data Vaild Flag*/
    private boolean isVaildLocation = false;
    private boolean isVaildTime = true;
    private boolean isVaildDay = false;

    /* SQLite */
    private AlarmDataController db;


    InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_setting_activity);
        //getSupportActionBar().hide();

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


        Calendar cal = Calendar.getInstance();
        ALARM_HOUR = cal.get(Calendar.HOUR_OF_DAY);
        ALARM_MIN = cal.get(Calendar.MINUTE);
        tv_alarm_time.setText( Utils.getTimeStringFormat(ALARM_HOUR, ALARM_MIN) );

        // get Connection
        db = new AlarmDataController(this);

        checkIntentData();

        checkNewOrModify();

        changeUpdateUI();

        isVaildAlarmData();


        //admob
        setFullAd();


    }

    public void checkIntentData(){

        IS_UPDATE = !MyConst.intentVO.isAlarmAdd();
        ALARM_ID = MyConst.intentVO.getAlarmVoId();
        Log.d("ALARM_ID", ALARM_ID + " ");

        ALARM_LOCATION = MyConst.intentVO.getLocName();

        if(  MyConst.intentVO.isAlarmMarking() == true){
            ALARM_DAYS = MyConst.intentVO.getCheckedDays();
        }
    }


    public void checkNewOrModify(){

        // get All data for list
        if(ALARM_ID != MyConst.INTENT_DEFAULT_ID){
            AlarmVO vo = db.selectData(ALARM_ID);
            Log.d("AlarmActivityIntent", "vo : " + vo.toString());

            Log.d("ALARM_ID", ALARM_ID + " ");

            if(MyConst.intentVO.isAlarmMarking()){
                ALARM_LOCATION = MyConst.intentVO.getLocName();
                String tmp = MyConst.intentVO.getAlarmTime();
                ALARM_TIME = tmp;
                ALARM_HOUR = Integer.parseInt(tmp.substring(3, 5));
                ALARM_MIN = Integer.parseInt(tmp.substring(7, 8));
            }else{
                ALARM_LOCATION = vo.getSidoName() + " " + vo.getCityName();
                ALARM_HOUR = vo.getHour();
                ALARM_MIN = vo.getMin();
                ALARM_TIME = Utils.getTimeStringFormat(ALARM_HOUR, ALARM_MIN);
            }

            MyConst.intentVO.setAlarmMarking(false);

            isVaildLocation = true;
            isVaildDay = true;
            tv_alarm_loc.setText(ALARM_LOCATION);
            tv_alarm_time.setText(ALARM_TIME);
            convertStringToBooleanDays(vo.getDays());

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


    @Override
    public void onClick(View v) {
        Log.d("onClick", v.getId() + "  ");

        int id = v.getId();

        if( id == R.id.tv_alarm_loc ){                           // location marker

            MyConst.intentVO.setAlarmMarker(true);
            MyConst.intentVO.setCheckedDays(ALARM_DAYS);
            MyConst.intentVO.setAlarmTime(ALARM_TIME);
            MyConst.intentVO.setAlarmMarking(true);
            MyConst.intentVO.setNeedListViewUpdate(false);
            finish();

        }else if( id == R.id.tv_alarm_time ){                   // set time

            int hour, minute;

            GregorianCalendar calendar = new GregorianCalendar();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);

            new TimePickerDialog(AlarmActivity.this, timeSetListener, hour, minute, false).show();

        }else if ( id == R.id.btn_alarm_completed ){            // submit

            boolean isVaild = isVaildAlarmData();
            if(isVaild){

                if(!IS_UPDATE){ // New

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
                            addAlarm(getApplicationContext(), vo);
                            Toast.makeText(AlarmActivity.this, "알람 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            //printDBData();
                            ALARM_LOCATION = null;
                            MyConst.intentVO.setInitData();
                            MyConst.intentVO.setNeedListViewUpdate(true);
                            displayAD();
                            finish();

                        }
                    });
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }else{      //modify

                    //수정은 업데이트
                    String msg = "지역 : " + ALARM_LOCATION +
                            "\n시간 : " + Utils.getTimeStringFormat(ALARM_HOUR, ALARM_MIN) +
                            "\n반복 요일 : " + getStringDaysFormat() +
                            "\n위 기준으로 미세먼지/초미세먼지 알림을 수정하시겠습니까?";

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("미세먼지 알람 설정");
                    builder.setCancelable(true);
                    builder.setMessage(msg);
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String[] sidoCity = ALARM_LOCATION.split(" ");
                            AlarmVO vo = new AlarmVO(ALARM_ID, "Y", sidoCity[0], sidoCity[1], ALARM_HOUR, ALARM_MIN, getStringDaysFormat());
                            Log.d("insertDB", vo.toString());
                            Log.d("insertDB", sidoCity[0]);
                            Log.d("insertDB", sidoCity[1]);

                            db.updateData(vo);
                            updateAlarm(getApplicationContext(), vo);

                            Toast.makeText(AlarmActivity.this, "알람 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                            ALARM_LOCATION = null;
                            MyConst.intentVO.setInitData();
                            MyConst.intentVO.setNeedListViewUpdate(true);
                            displayAD();
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

            }else{
                Toast.makeText(this, "데이터를 선택하세요", Toast.LENGTH_SHORT).show();
            }




        }else if(  id == R.id.ibtn_alarm_back ){            //back

            this.finish();


        }else if(  id == R.id.ibtn_alarm_delete ){          //delete

            String msg = "해당 알람을 삭제하시겠습니까?";

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("미세먼지 알람 설정");
            builder.setCancelable(true);
            builder.setMessage(msg);
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.deleteData(ALARM_ID);
                    deleteAlarm(getApplicationContext(), ALARM_ID);
                    Toast.makeText(AlarmActivity.this, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    MyConst.intentVO.setInitData();
                    MyConst.intentVO.setNeedListViewUpdate(true);
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

    }

    public boolean isVaildDaysData(){
        for(int i = 0 ; i < ALARM_DAYS.length ; i++){
            if (ALARM_DAYS[i] == true) return true;
        }
        return false;
    }



    private void setFullAd(){
        interstitialAd = new InterstitialAd(this); //새 광고를 만듭니다.
        interstitialAd.setAdUnitId(getResources().getString(R.string.full_ad_unit_id)); //이전에 String에 저장해 두었던 광고 ID를 전면 광고에 설정합니다.
        AdRequest adRequest1 = new AdRequest.Builder().build(); //새 광고요청
        interstitialAd.loadAd(adRequest1); //요청한 광고를 load 합니다.
        interstitialAd.setAdListener(new AdListener() { //전면 광고의 상태를 확인하는 리스너 등록

            @Override
            public void onAdClosed() { //전면 광고가 열린 뒤에 닫혔을 때
                AdRequest adRequest1 = new AdRequest.Builder().build();  //새 광고요청
                interstitialAd.loadAd(adRequest1); //요청한 광고를 load 합니다.
            }
        });
    }


    public void displayAD() {
        if (interstitialAd.isLoaded()) { //광고가 로드 되었을 시
            interstitialAd.show(); //보여준다

        }
    }


}














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




package com.lthwea.finedust.alarm;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import com.lthwea.finedust.R;


public class MyAlarmActivity extends Activity {

    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity_clock_alarm);

        String message = this.getIntent().getStringExtra("msg");
        showAlarm( message );

    }

    private void showAlarm(String message) {


        vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
        //vibrator.vibrate(new long[]{100, 10, 100, 100}, 0);

     /*   AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("미세먼지 알림");
        builder.setCancelable(true);
        builder.setMessage(message);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vibrator.cancel();
                dialog.dismiss();
                finish();
            }
        });
        builder.show();*/

        final MyAlarmDialog dialog = new MyAlarmDialog(this, R.style.Theme_dialog);
        dialog.show();
        dialog.setTitle("미세먼지 알림");
        dialog.setMessage(message);
        dialog.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.bt_confirm == v ) {
                    vibrator.cancel();
                    dialog.dismiss();
                    finish();
                }
            }
        });


       /* final MyAlarmDialog dialog = new MyAlarmDialog(this, R.style.Theme_dialog);
        dialog.show();
        dialog.setTitle("미세먼지 알림");
        dialog.setMessage(message);
        dialog.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MyAlarmActivity dialog", "setClickListener");

                    *//*if (flag == 1 || flag == 2) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                    if (flag == 0 || flag == 2) {

                    }*//*
                    vibrator.cancel();
                    dialog.dismiss();
                    finish();
                }
        });*/


       /* if (flag == 1 || flag == 2) {
            mediaPlayer = MediaPlayer.create(this, R.raw.in_call_alarm);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
        //배열 매개 변수의 의미：지정된 시간 동안 대기 한 후 첫 번째 매개 변수는 흔들 리기 시작，두번째 매개 변수로 진동 시간。매개 변수는 시간 대기 충격 및 진동의 배후
        //두번째 매개 변수는 반복 횟수이며，-1 반복하지 않는다，0为一直震动
        if (flag == 0 || flag == 2) {
            vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(new long[]{100, 10, 100, 600}, 0);
        }

        final MyAlarmDialog dialog = new MyAlarmDialog(this, R.style.Theme_dialog);
        dialog.show();
        dialog.setTitle("미세먼지 알림");
        dialog.setMessage(message);
        dialog.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.bt_confirm == v || dialog.bt_cancel == v) {
                    if (flag == 1 || flag == 2) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                    if (flag == 0 || flag == 2) {
                        vibrator.cancel();
                    }
                    dialog.dismiss();
                    finish();
                }
            }
        });
*/

    }

}

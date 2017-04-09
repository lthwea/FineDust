package com.lthwea.finedust.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import com.lthwea.finedust.R;
import com.lthwea.finedust.controller.PrefController;
import com.lthwea.finedust.util.Utils;

/**
 * Created by LeeTaeHun on 2017. 4. 9..
 */

public class BootReceiver extends BroadcastReceiver {
    SharedPreferences pref;


    public BootReceiver() {
        Log.d("BootReceiver", "BootReceiver()");

    }

    @Override
    public void onReceive(Context context, Intent intent) {


        pref = context.getSharedPreferences(PrefController.PREFS_NAME, context.MODE_PRIVATE);
        boolean isAlarmUse = pref.getBoolean(context.getString(R.string.pref_alarm_use), true);

        Log.d("BootReceiver", "check " + isAlarmUse);

        if(isAlarmUse){
            String location = pref.getString(context.getString(R.string.pref_alarm_location), "");
            String time = pref.getString(context.getString(R.string.pref_alarm_time), "");
            String[] times = time.split(":");
            int ALARM_HOUR = Integer.parseInt(times[0]);
            int ALARM_MIN = Integer.parseInt(times[1]);
            String day = pref.getString(context.getString(R.string.pref_alarm_day), "");

            boolean[] checkedDays = new boolean[]{
                    false,false,false,false,false,false,false
            };
            String[] days = day.split(" ");
            for(int i = 0 ; i < days.length ; i++){
                if("월".equals(days[i])){
                    checkedDays[0] = true;
                }else if("화".equals(days[i])){
                    checkedDays[1] = true;
                }else if("수".equals(days[i])){
                    checkedDays[2] = true;
                }else if("목".equals(days[i])){
                    checkedDays[3] = true;
                }else if("금".equals(days[i])){
                    checkedDays[4] = true;
                }else if("토".equals(days[i])){
                    checkedDays[5] = true;
                }else if("일".equals(days[i])){
                    checkedDays[6] = true;
                }
            }


            Intent i = new Intent(context, MyAlarmReceiver.class);
            i.putExtra("location", location);
            i.putExtra("week", checkedDays);

            PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

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

            Log.d("BootReceiver", "re set alarm " );
        }else{

            //재부팅시 알람 사용하지 않으면 pass

        }

    }


}

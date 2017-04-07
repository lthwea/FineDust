package com.lthwea.finedust.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

/**
 * Created by loonggg on 2016/3/21.
 */
public class AlarmManagerUtil {

    public static final String ALARM_ACTION = "com.loonggg.alarm.clock";

    public static void setAlarmTime(Context context, long timeInMillis, Intent intent) {

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(context, intent.getIntExtra("id", 0), intent, PendingIntent.FLAG_CANCEL_CURRENT);

        int interval = (int) intent.getLongExtra("intervalMillis", 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setWindow(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);
        }
    }

    public static void cancelAlarm(Context context, String action, int id) {
        Intent intent = new Intent(action);
        PendingIntent pi = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
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
     */
    public static void setAlarm(Context context, int flag, int hour, int minute, int id, int week, String tips, int soundOrVibrator) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();

        long intervalMillis = 0;
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get
                (Calendar.DAY_OF_MONTH), hour, minute, 10);

        if (flag == 0) {
            intervalMillis = 0;
        } else if (flag == 1) {
            intervalMillis = 24 * 3600 * 1000;
        } else if (flag == 2) {
            intervalMillis = 24 * 3600 * 1000 * 7;
        }

        Intent intent = new Intent(ALARM_ACTION);
        intent.putExtra("intervalMillis", intervalMillis);
        intent.putExtra("msg", tips);
        intent.putExtra("id", id);
        intent.putExtra("soundOrVibrator", soundOrVibrator);
        PendingIntent sender = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setWindow(AlarmManager.RTC_WAKEUP, calMethod(week, calendar.getTimeInMillis()),
                    intervalMillis, sender);
        } else {

            if (flag == 0) {
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
            } else {
                am.setRepeating(AlarmManager.RTC_WAKEUP, calMethod(week, calendar.getTimeInMillis()), intervalMillis, sender);
            }
        }
    }


    /**
     * @param weekflag 일주일의 수신
     * @param dateTime 수신 타임 스탬프입니다（설정 데이 데이트+선택 상자에서 분, 초를 가져왔다）
     * @return 위로 알람 시간 스탬프를 시작합니다
     */
    private static long calMethod(int weekflag, long dateTime) {
        long time = 0;
        //weekflag == 0表示是按天为周期性的时间间隔或者是一次行的，weekfalg非0时表示每周几的闹钟并以周为时间间隔
        if (weekflag != 0) {
            Calendar c = Calendar.getInstance();
            int week = c.get(Calendar.DAY_OF_WEEK);
            if (1 == week) {
                week = 7;
            } else if (2 == week) {
                week = 1;
            } else if (3 == week) {
                week = 2;
            } else if (4 == week) {
                week = 3;
            } else if (5 == week) {
                week = 4;
            } else if (6 == week) {
                week = 5;
            } else if (7 == week) {
                week = 6;
            }

            if (weekflag == week) {

                if (dateTime > System.currentTimeMillis()) {
                    time = dateTime;
                } else {
                    time = dateTime + 7 * 24 * 3600 * 1000;
                }

            } else if (weekflag > week) {

                time = dateTime + (weekflag - week) * 24 * 3600 * 1000;

            } else if (weekflag < week) {

                time = dateTime + (weekflag - week + 7) * 24 * 3600 * 1000;
            }
        } else {

            if (dateTime > System.currentTimeMillis()) {
                time = dateTime;
            } else {
                time = dateTime + 24 * 3600 * 1000;
            }

        }
        return time;
    }


}

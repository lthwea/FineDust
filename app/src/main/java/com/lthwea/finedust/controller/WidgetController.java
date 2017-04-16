package com.lthwea.finedust.controller;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.lthwea.finedust.R;
import com.lthwea.finedust.cnst.MyConst;
import com.lthwea.finedust.util.Utils;
import com.lthwea.finedust.view.MainActivity;

/**
 * Created by LeeTaeHun on 2017. 4. 15..
 */

public class WidgetController extends AppWidgetProvider{

    private static final String TAG = "WidgetController";
    private static final String ACTION_UPDATE_CLICK = "com.lthwea.finedust.action.UPDATE_CLICK";
    private static boolean isSetLocation;
    private static String sidoName;
    private static String cityName;


    private long FINSH_INTERVAL_TIME = 60000;
    private long inputButtonTime = 0;
    /**
     * 브로드캐스트를 수신할때, Override된 콜백 메소드가 호출되기 직전에 호출됨
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Log.d(TAG, "onReceive CALL\t" + intent.getAction() + " " +  intent.getStringExtra("LOCATION"));

        String loc = intent.getStringExtra("LOCATION");
        if(loc != null){
            String[] tmp = loc.split(" ");
            sidoName = tmp[0];
            cityName = tmp[1];
            isSetLocation = true;
            this.onUpdate(context);
        }else if(ACTION_UPDATE_CLICK.equals(intent.getAction())){
            //시간 제한
            long tempTime = System.currentTimeMillis();
            long intervalTime = tempTime - inputButtonTime;

            if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
                this.onUpdate(context);
            } else {
                inputButtonTime = tempTime;
                Toast.makeText(context, "" + inputButtonTime, Toast.LENGTH_SHORT).show();
            }

        }




    }

    /**
     * 위젯을 갱신할때 호출됨
     *
     * 주의 : Configure Activity를 정의했을때는 위젯 등록시 처음 한번은 호출이 되지 않습니다
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(TAG, "onUpdate CALL");


        appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
        for (int i = 0; i < appWidgetIds.length; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }


    }

    /**
     * 위젯이 처음 생성될때 호출됨
     *
     * 동일한 위젯이 생성되도 최초 생성때만 호출됨
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);

    }

    /**
     * 위젯의 마지막 인스턴스가 제거될때 호출됨
     *
     * onEnabled()에서 정의한 리소스 정리할때
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    /**
     * 위젯이 사용자에 의해 제거될때 호출됨
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }



    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.d(TAG, "updateAppWidget CALL");


        /**
         * RemoteViews를 이용해 Text설정
         */
        RemoteViews updateViews = new RemoteViews(context.getPackageName(),R.layout.widget_main);

        if(isSetLocation){

            DataController dc = new DataController();
            String sidoInfo = dc.getSidoAlarmData(sidoName, cityName);
            String[] values = sidoInfo.split(",");         //1 pm10, 2 pm25, 3 date time

            updateViews.setTextViewText(R.id.tv_main_widget_loc, sidoName + " " + cityName);
            updateViews.setTextViewText(R.id.tv_main_widget_date, values[2]);
            updateViews.setTextViewText(R.id.tv_main_widget_value, values[0]);
            updateViews.setTextViewText(R.id.tv_main_widget_stats, Utils.getPm10ValueStatus(values[0]));
            updateViews.setTextColor(R.id.tv_main_widget_value, Utils.getPm10MarkerColor(context, Integer.parseInt(values[0])));
            updateViews.setTextColor(R.id.tv_main_widget_stats, Utils.getPm10MarkerColor(context, Integer.parseInt(values[0])));

        }else{
            isSetLocation = false;

            updateViews.setTextViewText(R.id.tv_main_widget_loc, "설정버튼을 눌러 지역을 선택하세요.");
            updateViews.setTextViewText(R.id.tv_main_widget_date, "");
            updateViews.setTextViewText(R.id.tv_main_widget_value, "");
            updateViews.setTextViewText(R.id.tv_main_widget_stats, "");
        }




        /**
         * 레이아웃을 클릭하면 이벤트
         */
        // settings
        Intent settingI = new Intent(Intent.ACTION_MAIN);
        settingI.addCategory(Intent.CATEGORY_LAUNCHER);
        settingI.setComponent(new ComponentName(context, MainActivity.class));
        settingI.setAction(MyConst.WIDGET_TO_MAIN_INTENT_ACTION);
        PendingIntent settingPi = PendingIntent.getActivity(context, 0, settingI, 0);
        updateViews.setOnClickPendingIntent(R.id.ibtn_widget_setting, settingPi);

        // refresh
        Intent updateI = new Intent(context, WidgetController.class);
        updateI.setAction(ACTION_UPDATE_CLICK);
        PendingIntent updatePi = PendingIntent.getBroadcast(context, 0, updateI, 0);
        updateViews.setOnClickPendingIntent(R.id.ibtn_widget_update, updatePi);


        /**
         * 위젯 업데이트
         */
        appWidgetManager.updateAppWidget(appWidgetId, updateViews);
    }


    /**
     * A general technique for calling the onUpdate method,
     * requiring only the context parameter.
     *
     * @author John Bentley, based on Android-er code.
     * @see <a href="http://android-er.blogspot.com
     * .au/2010/10/update-widget-in-onreceive-method.html">
     * Android-er > 2010-10-19 > Update Widget in onReceive() method</a>
     */
    private void onUpdate(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        // Uses getClass().getName() rather than MyWidget.class.getName() for
        // portability into any App Widget Provider Class
        ComponentName thisAppWidgetComponentName = new ComponentName(context.getPackageName(), getClass().getName() );
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName);
        onUpdate(context, appWidgetManager, appWidgetIds);
    }





}

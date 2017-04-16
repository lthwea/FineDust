package com.lthwea.finedust.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lthwea.finedust.R;
import com.lthwea.finedust.controller.WidgetController;
import com.lthwea.finedust.view.MainActivity;


public class WidgetSettingActivity extends Activity implements View.OnClickListener{

    String LOCATION = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_setting_activity);

        //String message = this.getIntent().getStringExtra("msg");
        Button btn = (Button) findViewById(R.id.btn_widget_setting_complete);
        btn.setOnClickListener(this);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl_widget_loc);
        rl.setOnClickListener(this);


        Intent intent = getIntent();
        LOCATION = intent.getStringExtra("LOCATION");


        TextView tv = (TextView)findViewById(R.id.tv_setting_widget_loc);

        if(LOCATION != null){
            btn.setBackgroundColor(ContextCompat.getColor(this, R.color.color_widget_green));
            tv.setText(LOCATION);
        }else{
            btn.setBackgroundColor(ContextCompat.getColor(this, R.color.line_and_outline_grey));
        }

    }


    public void goWidget(){

        /*Context context = this;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        RemoteViews updateViews = new RemoteViews(context.getPackageName(),R.layout.widget_main);
        updateViews.setTextViewText(R.id.tv_main_widget_loc, LOCATION);

        ComponentName thisWidget = new ComponentName(context, WidgetController.class);

        appWidgetManager.updateAppWidget(thisWidget, updateViews);*/

        Intent intent = new Intent(this, WidgetController.class);
        intent.putExtra("LOCATION", LOCATION);
        getApplicationContext().sendBroadcast(intent);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if( id == R.id.btn_widget_setting_complete){

            if(LOCATION != null){
                goWidget();
                Toast.makeText(this, "위젯 지역 설정을 완료하였습니다.", Toast.LENGTH_SHORT).show();
                MainActivity.isEndWidgetMarker = true;
                finish();
            }else{
                Toast.makeText(this, "위젯 지역을 선택해주세요.", Toast.LENGTH_SHORT).show();
            }

        }else if(id == R.id.rl_widget_loc){
            MainActivity.isSettingWidgetMarker = true;
            finish();
        }
    }
}

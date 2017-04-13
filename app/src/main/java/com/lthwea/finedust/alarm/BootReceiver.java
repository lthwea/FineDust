package com.lthwea.finedust.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lthwea.finedust.controller.AlarmDataController;
import com.lthwea.finedust.util.Utils;
import com.lthwea.finedust.vo.AlarmVO;

import java.util.List;

/**
 * Created by LeeTaeHun on 2017. 4. 9..
 */

public class BootReceiver extends BroadcastReceiver {
    /* SQLite */
    private AlarmDataController db;


    public BootReceiver() {
        Log.d("BootReceiver", "BootReceiver()");

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        db = new AlarmDataController(context);
        List<AlarmVO> voList = db.selectAllData();

        for (AlarmVO vo : voList) {
            if ("Y".equals(vo.getIsUse())) {
                Utils.addAlarm(context, vo);
            }
        }
        Log.d("BootReceiver", "RE-SET Alarm");

    }


}

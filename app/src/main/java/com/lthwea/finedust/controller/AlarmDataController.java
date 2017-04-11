package com.lthwea.finedust.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lthwea.finedust.vo.AlarmVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LeeTaeHun on 2017. 4. 11..
 */

public class AlarmDataController extends SQLiteOpenHelper{

    // All Static variables Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "AlarmData.db";

    // Contacts table name
    private static final String TABLE_NAME = "AlarmData";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";                          // 0
    private static final String KEY_IS_USE = "isUse";                    // 1
    private static final String KEY_CITY_NAME = "cityName";               // 2
    private static final String KEY_SIDO_NAME = "sidoName";               // 3
    private static final String KEY_HOUR = "hour";                    // 4
    private static final String KEY_MIN  = "min";                    // 5
    private static final String KEY_DAYS = "days";                    // 6


    // Query
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY," +
                    KEY_IS_USE + " TEXT," +
                    KEY_CITY_NAME + " TEXT," +
                    KEY_SIDO_NAME + " TEXT," +
                    KEY_HOUR + " INTEGER," +
                    KEY_MIN + " INTEGER," +
                    KEY_DAYS + " TEXT" +
                    ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public AlarmDataController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("AlarmDataController", SQL_CREATE_ENTRIES);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("AlarmDataController", "onCreate");
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        /*db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);*/
    }

    public long insertAlarmData(AlarmVO vo){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_ID, vo.getId());
        values.put(KEY_IS_USE, vo.getIsUse());
        values.put(KEY_SIDO_NAME, vo.getSidoName());
        values.put(KEY_CITY_NAME, vo.getCityName());
        values.put(KEY_HOUR, vo.getHour());
        values.put(KEY_MIN, vo.getMin());
        values.put(KEY_DAYS, vo.getDays());
        long newRowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return newRowId;

    }


    public List selectAllData(){
        ArrayList<AlarmVO> list = new ArrayList<AlarmVO>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AlarmVO vo = new AlarmVO();
                vo.setId(Integer.parseInt(cursor.getString(0)));
                vo.setIsUse(cursor.getString(1));
                vo.setSidoName(cursor.getString(2));
                vo.setCityName(cursor.getString(3));
                vo.setHour(Integer.parseInt(cursor.getString(4)));
                vo.setMin(Integer.parseInt(cursor.getString(5)));
                vo.setDays(cursor.getString(6));

                // Adding to list


                list.add(vo);
            } while (cursor.moveToNext());
        }

        db.close();
        // return  list
        return list;

    }



    public AlarmVO selectData(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] returnValue = {
                KEY_ID,
                KEY_IS_USE,
                KEY_CITY_NAME,
                KEY_SIDO_NAME,
                KEY_HOUR,
                KEY_MIN,
                KEY_DAYS
        };

        Cursor cursor = db.query(
                TABLE_NAME
                , returnValue
                , KEY_ID + "=?"
                , new String[] { String.valueOf(id) }           //where value
                , null
                , null
                , null
                , null);

        if (cursor != null)
            cursor.moveToFirst();

        AlarmVO vo = new AlarmVO();

        if (cursor.moveToFirst()) {
            do {
                vo.setId(Integer.parseInt(cursor.getString(0)));
                vo.setIsUse(cursor.getString(1));
                vo.setSidoName(cursor.getString(2));
                vo.setCityName(cursor.getString(3));
                vo.setHour(Integer.parseInt(cursor.getString(4)));
                vo.setMin(Integer.parseInt(cursor.getString(5)));
                vo.setDays(cursor.getString(6));
            } while (cursor.moveToNext());
        }



        db.close();
        // return contact
        return vo;
    }


    public int updateData(AlarmVO vo){

        SQLiteDatabase db = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(KEY_IS_USE, vo.getIsUse());
        values.put(KEY_SIDO_NAME, vo.getSidoName());
        values.put(KEY_CITY_NAME, vo.getCityName());
        values.put(KEY_HOUR, vo.getHour());
        values.put(KEY_MIN, vo.getMin());
        values.put(KEY_DAYS, vo.getDays());


        int count = db.update(
                TABLE_NAME
                , values
                , KEY_ID + "=?"
                , new String[] { String.valueOf(vo.getId()) }           //where value
                );

        db.close();
        return count;
    }


    public void deleteData(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        // Issue SQL statement.
        db.delete(
                TABLE_NAME
                , KEY_ID + "=?"  // selection
                , new String[] { String.valueOf(id) } //selectionArgs
        );

        db.close();

    }


    public void deleteAllData(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        // Issue SQL statement.
        db.execSQL("delete from "+ TABLE_NAME);

    }

    public int getDataCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();

        // return count
        return cnt;
    }


    public void deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();

        // Issue SQL statement.
        db.execSQL(SQL_DELETE_ENTRIES);

        db.close();
    }


}

package com.examples.android.evento.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseController extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "HasgeekAPIData";
    private static final String TABLE_NAME_SceduleAndEventsData = "SceduleAndEventsData";
    private static final String Key_EventName = "EventName";
    private static final String Key_EventData = "EventData";

    public static DataBaseController instance;

    private DataBaseController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DataBaseController getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseController(context);
            return instance;
        } else {
            return instance;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_Schedule_Event_DataTable = "CREATE TABLE " + TABLE_NAME_SceduleAndEventsData + "("
                + Key_EventName + " String PRIMARY KEY," + Key_EventData + " TEXT"
                + ")";
        db.execSQL(CREATE_Schedule_Event_DataTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SceduleAndEventsData);


    }

    public void addScheduleAndEventData(String scheduleAndEventData, String EventName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(Key_EventName, EventName);
        values.put(Key_EventData, scheduleAndEventData);
        long response = db.insert(TABLE_NAME_SceduleAndEventsData, null, values);
        db.close();


    }


    public String getScheduleAndEventData(String EventName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_SceduleAndEventsData, new String[]{Key_EventName,
                        Key_EventData}, Key_EventName + " = ?",
                new String[]{String.valueOf(EventName)}, null, null, null, null);
        if (cursor != null)

            cursor.moveToFirst();


        String EventsnameScheduleandData = cursor.getString(1);
        cursor.close();
        db.close();
        return EventsnameScheduleandData;


    }

    public int updateScheduleAndEventData(String scheduleAndEventData, String EventName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Key_EventName, EventName);
        values.put(Key_EventData, scheduleAndEventData);

        int updatecount = db.update(TABLE_NAME_SceduleAndEventsData, values, Key_EventName + " = ?",
                new String[]{String.valueOf(EventName)});
        db.close();
        return updatecount;
    }


    public int getCount(String EventName) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_SceduleAndEventsData, new String[]{Key_EventName,
                        Key_EventData}, Key_EventName + " = ?",
                new String[]{String.valueOf(EventName)}, null, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;


    }


    // Getting All Contacts
    public int getAllContacts() {

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_SceduleAndEventsData;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }


}

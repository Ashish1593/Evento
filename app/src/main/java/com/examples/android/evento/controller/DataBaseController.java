package com.examples.android.evento.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseController extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME= "HasgeekAPIData";
    private static final String TABLE_NAME_SceduleAndEventsData ="SceduleAndEventsData";
    private static final String Key_EventName ="EventName" ;
    private static final String Key_EventData = "EventData";


    public DataBaseController(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_Schedule_Event_DataTable = "CREATE TABLE " + TABLE_NAME_SceduleAndEventsData + "("
                + Key_EventName + " String PRIMARY KEY," + Key_EventData + " TEXT"
                + ")";
        db.execSQL(CREATE_Schedule_Event_DataTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db,int OldVersion,int newVersion){

    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SceduleAndEventsData);


}

    public void addScheduleAndEventData(String scheduleAndEventData , String EventName){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();


    values.put(Key_EventName,EventName);
    values.put(Key_EventData,scheduleAndEventData);
    db.insert(TABLE_NAME_SceduleAndEventsData,null ,values);
    db.close();


}


    public String getScheduleAndEventData(String EventName){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_SceduleAndEventsData, new String[] { Key_EventName,
                        Key_EventData}, Key_EventName + "=?",
                new String[] { String.valueOf(EventName) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        String EventsnameScheduleandData = cursor.getString(1);

        return EventsnameScheduleandData;


    }

    public  int updateScheduleAndEventData(String scheduleAndEventData , String EventName){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Key_EventName,EventName);
        values.put(Key_EventData,scheduleAndEventData);

        return db.update(TABLE_NAME_SceduleAndEventsData, values, Key_EventName+ " = ?",
                new String[] { String.valueOf(EventName) });
    }

}

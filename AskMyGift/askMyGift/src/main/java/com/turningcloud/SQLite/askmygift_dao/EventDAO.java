package com.turningcloud.SQLite.askmygift_dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.turningcloud.SQLite.model_classes.Diary;
import com.turningcloud.SQLite.model_classes.Event;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAHIRAJ on 8/12/2015.
 */
public class EventDAO {
    public static final String TAG = "EventDAO";
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDatabaseHelper;
    private Context mContext;


    private String[] mAllColumns = {DatabaseHelper.EVENT_ID,DatabaseHelper.DIARY_ID,
            DatabaseHelper.EVENT_NAME,DatabaseHelper.EVENT_DESC,DatabaseHelper.EVENT_START_DATE,
            DatabaseHelper.EVENT_END_DATE,
            DatabaseHelper.CREATION_TIME,DatabaseHelper.UPDATION_TIME
    };

    public EventDAO(Context context){
        this.mContext = context;
        mDatabaseHelper = new DatabaseHelper(context);
        //open the database
        try{
            open();
        }catch (SQLException e){
            Log.e(TAG, "SQLException on opening database" + e.getMessage());
        }

    }

    public void open() throws SQLException {
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }
    public void close(){
        mDatabaseHelper.close();
    }



    public void createEvent(ArrayList<Event> event) {

        for (int i = 0; i < event.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.EVENT_ID, event.get(i).eventId);
            values.put(DatabaseHelper.DIARY_ID, event.get(i).diaryId);
            values.put(DatabaseHelper.EVENT_NAME, event.get(i).eventName);
            values.put(DatabaseHelper.EVENT_DESC, event.get(i).eventDesc);
            values.put(DatabaseHelper.EVENT_START_DATE, event.get(i).eventStartDate);
            values.put(DatabaseHelper.EVENT_END_DATE, event.get(i).eventEndDate);
            long insertId = mDatabase
                    .insert(DatabaseHelper.TABLE_EVENT, null, values);
        }
    }


    public void deleteEvent(Event event) {
        int id = event.getEventId();
        // delete all employees of this company
       /* EmployeeDAO employeeDao = new EmployeeDAO(mContext);
        List<Employee> listEmployees = employeeDao.getEmployeesOfCompoany(id);
        if (listEmployees != null && !listEmployees.isEmpty()) {
            for (Employee e : listEmployees) {
                employeeDao.deleteEmployee(e);
            }
        } */

        System.out.println("the deleted Event has the id: " + id);
        mDatabase.delete(mDatabaseHelper.TABLE_EVENT, DatabaseHelper.EVENT_ID
                + " = " + id, null);
    }

    public List<Event> getAllEvent() {
        List<Event> listEvent = new ArrayList<Event>();

        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_EVENT, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Event event = cursorToEvent(cursor);
                listEvent.add(event);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listEvent;
    }

    public Event getEventById(String id) {
        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_EVENT, mAllColumns,
                DatabaseHelper.EVENT_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Event event = cursorToEvent(cursor);
        return event;
    }

    protected Event cursorToEvent(Cursor cursor) {
        Event event = new Event();
        event.setEventId(cursor.getInt(0));
        event.setDiaryId(cursor.getInt(1));
        event.setEventName(cursor.getString(2));
        event.setEventDesc(cursor.getString(3));
        event.setEventStartDate(cursor.getString(4));
        event.setEventEndDate(cursor.getString(5));
        event.setCreationTime(Timestamp.valueOf(cursor.getString(6)));
        event.setUpdationTime(Timestamp.valueOf(cursor.getString(7)));
        return event;
    }
}

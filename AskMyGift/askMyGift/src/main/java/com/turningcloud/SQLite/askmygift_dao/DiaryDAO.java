package com.turningcloud.SQLite.askmygift_dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.turningcloud.SQLite.model_classes.Diary;
import com.turningcloud.SQLite.model_classes.Preferences;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAHIRAJ on 8/12/2015.
 */
public class DiaryDAO {
    public static final String TAG = "DiaryDAO";
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDatabaseHelper;
    private Context mContext;

    private String[] mAllColumns = {DatabaseHelper.DIARY_ID,DatabaseHelper.USER_ID,
            DatabaseHelper.DIARY_NAME,DatabaseHelper.DIARY_DESC,DatabaseHelper.DIARY_TYPE,
            DatabaseHelper.CREATION_TIME,DatabaseHelper.UPDATION_TIME
    };

    public DiaryDAO(Context context){
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



    public void createDiary(ArrayList<Diary> diary) {

        for (int i = 0; i < diary.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.DIARY_ID, diary.get(i).diaryId);
            values.put(DatabaseHelper.USER_ID, diary.get(i).userId);
            values.put(DatabaseHelper.DIARY_NAME, diary.get(i).diaryName);
            values.put(DatabaseHelper.DIARY_DESC, diary.get(i).diaryDesc);
            values.put(DatabaseHelper.DIARY_TYPE, diary.get(i).diaryType);
            long insertId = mDatabase
                    .insert(DatabaseHelper.TABLE_DIARY, null, values);
        }
    }


    public void deleteDiary(Diary diary) {
        int id = diary.getDiaryId();
        // delete all employees of this company
       /* EmployeeDAO employeeDao = new EmployeeDAO(mContext);
        List<Employee> listEmployees = employeeDao.getEmployeesOfCompoany(id);
        if (listEmployees != null && !listEmployees.isEmpty()) {
            for (Employee e : listEmployees) {
                employeeDao.deleteEmployee(e);
            }
        } */

        System.out.println("the deleted Diary has the id: " + id);
        mDatabase.delete(mDatabaseHelper.TABLE_DIARY, DatabaseHelper.DIARY_ID
                + " = " + id, null);
    }

    public List<Diary> getAllDiary() {
        List<Diary> listDiary = new ArrayList<Diary>();

        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_DIARY, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Diary diary = cursorToDiary(cursor);
                listDiary.add(diary);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listDiary;
    }

    public Diary getDiaryById(String id) {
        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_DIARY, mAllColumns,
                DatabaseHelper.DIARY_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Diary diary = cursorToDiary(cursor);
        return diary;
    }

    protected Diary cursorToDiary(Cursor cursor) {
        Diary diary = new Diary();
        diary.setDiaryId(cursor.getInt(0));
        diary.setUserId(cursor.getString(1));
        diary.setDiaryName(cursor.getString(2));
        diary.setDiaryDesc(cursor.getString(3));
        diary.setDiaryType(cursor.getString(4));
        diary.setCreationTime(Timestamp.valueOf(cursor.getString(5)));
        diary.setUpdationTime(Timestamp.valueOf(cursor.getString(6)));
        return diary;
    }
}

package com.turningcloud.SQLite.askmygift_dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.turningcloud.SQLite.model_classes.Preferences;
import com.turningcloud.SQLite.model_classes.Verify;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAHIRAJ on 8/12/2015.
 */
public class PreferencesDAO {
    public static final String TAG = "PreferencesDAO";
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDatabaseHelper;
    private Context mContext;

    private String[] mAllColumns = {DatabaseHelper.PREF_ID,DatabaseHelper.SHOE_SIZE,
            DatabaseHelper.CASUAL_SHIRT_SIZE,DatabaseHelper.DRESS_SHIRT_SIZE,DatabaseHelper.WAIST_SIZE,
            DatabaseHelper.JACKET_SIZE,DatabaseHelper.DRESS_SIZE,DatabaseHelper.HAT_SIZE,
            DatabaseHelper.LIKES,DatabaseHelper.DISLIKES,DatabaseHelper.CHOICE_A,
            DatabaseHelper.CHOICE_B, DatabaseHelper.CHOICE_C,DatabaseHelper.CHOICE_D,
            DatabaseHelper.CREATION_TIME,DatabaseHelper.UPDATION_TIME};

    public PreferencesDAO(Context context){
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
    public void createPreferences(ArrayList<Preferences> preferences) {

        for (int i = 0; i < preferences.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.PREF_ID, preferences.get(i).prefId);
            values.put(DatabaseHelper.SHOE_SIZE, preferences.get(i).shoeSize);
            values.put(DatabaseHelper.CASUAL_SHIRT_SIZE, preferences.get(i).casualShirtSize);
            values.put(DatabaseHelper.DRESS_SHIRT_SIZE, preferences.get(i).dressShirtSize);
            values.put(DatabaseHelper.WAIST_SIZE, preferences.get(i).waistSize);
            values.put(DatabaseHelper.JACKET_SIZE, preferences.get(i).jacketSize);
            values.put(DatabaseHelper.DRESS_SIZE, preferences.get(i).dressSize);
            values.put(DatabaseHelper.HAT_SIZE, preferences.get(i).hatSize);
            values.put(DatabaseHelper.LIKES, preferences.get(i).likes);
            values.put(DatabaseHelper.DISLIKES, preferences.get(i).dislikes);
            values.put(DatabaseHelper.CHOICE_A, preferences.get(i).choicea);
            values.put(DatabaseHelper.CHOICE_B, preferences.get(i).choiceb);
            values.put(DatabaseHelper.CHOICE_C, preferences.get(i).choicec);
            values.put(DatabaseHelper.CHOICE_D, preferences.get(i).choiced);

            long insertId = mDatabase
                    .insert(DatabaseHelper.TABLE_PREFERENCES, null, values);
        }
    }


    public void deletePreferences(Preferences preferences) {
        String id = preferences.getPrefId();
        // delete all employees of this company
       /* EmployeeDAO employeeDao = new EmployeeDAO(mContext);
        List<Employee> listEmployees = employeeDao.getEmployeesOfCompoany(id);
        if (listEmployees != null && !listEmployees.isEmpty()) {
            for (Employee e : listEmployees) {
                employeeDao.deleteEmployee(e);
            }
        } */

        System.out.println("the deleted preferences has the id: " + id);
        mDatabase.delete(mDatabaseHelper.TABLE_PREFERENCES, DatabaseHelper.PREF_ID
                + " = " + id, null);
    }

    public List<Preferences> getAllPreferences() {
        List<Preferences> listPreferences = new ArrayList<Preferences>();

        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_PREFERENCES, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Preferences preferences = cursorToPreferences(cursor);
                listPreferences.add(preferences);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listPreferences;
    }

    public Preferences getPreferencesById(String id) {
        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_PREFERENCES, mAllColumns,
                DatabaseHelper.PREF_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Preferences preferences = cursorToPreferences(cursor);
        return preferences;
    }

    protected Preferences cursorToPreferences(Cursor cursor) {
        Preferences preferences = new Preferences();
        preferences.setPrefId(cursor.getString(0));
        preferences.setShoeSize(cursor.getString(1));
        preferences.setCasualShirtSize(cursor.getString(2));
        preferences.setDressShirtSize(cursor.getString(3));
        preferences.setWaistSize(cursor.getString(4));
        preferences.setJacketSize(cursor.getString(5));
        preferences.setDressSize(cursor.getString(6));
        preferences.setHatSize(cursor.getString(7));
        preferences.setLikes(cursor.getString(8));
        preferences.setDislikes(cursor.getString(9));
        preferences.setChoicea(cursor.getString(10));
        preferences.setChoiceb(cursor.getString(11));
        preferences.setChoicec(cursor.getString(12));
        preferences.setChoiced(cursor.getString(13));
        preferences.setCreationTime(Timestamp.valueOf(cursor.getString(14)));
        preferences.setUpdationTime(Timestamp.valueOf(cursor.getString(15)));
        return preferences;
    }
}

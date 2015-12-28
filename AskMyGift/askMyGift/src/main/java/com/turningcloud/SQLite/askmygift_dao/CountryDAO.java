package com.turningcloud.SQLite.askmygift_dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.turningcloud.SQLite.askmygift_dao.DatabaseHelper;
import com.turningcloud.SQLite.model_classes.Country;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by MAHIRAJ on 8/11/2015.
 */
public class CountryDAO {
    public static final String TAG = "CountryDAO";
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDatabaseHelper;
    private Context mContext;
    private String[] mAllColumns = {DatabaseHelper.COUNTY_ID,DatabaseHelper.COUNTRY_CODE,
            DatabaseHelper.COUNTRY, DatabaseHelper.UPDATION_TIME,
            DatabaseHelper.UPDATION_TIME};

    public CountryDAO(Context context){
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

    public void createCountry(ArrayList<Country> country) {

        for (int i = 0; i < country.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COUNTY_ID, country.get(i).counrtyId);
            values.put(DatabaseHelper.COUNTRY_CODE, country.get(i).countryCode);
            values.put(DatabaseHelper.COUNTRY, country.get(i).country);
            long insertId = mDatabase
                    .insert(DatabaseHelper.TABLE_COUNTRY, null, values);
        }
    }


    public void deleteCountry(Country country) {
        int id = country.getCounrtyId();
        // delete all employees of this company
       /* EmployeeDAO employeeDao = new EmployeeDAO(mContext);
        List<Employee> listEmployees = employeeDao.getEmployeesOfCompoany(id);
        if (listEmployees != null && !listEmployees.isEmpty()) {
            for (Employee e : listEmployees) {
                employeeDao.deleteEmployee(e);
            }
        } */

        System.out.println("the deleted country has the id: " + id);
        mDatabase.delete(mDatabaseHelper.TABLE_COUNTRY, DatabaseHelper.COUNTY_ID
                + " = " + id, null);
    }

    public List<Country> getAllCountry() {
        List<Country> listCountry = new ArrayList<Country>();

        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_COUNTRY, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Country country = cursorToCountry(cursor);
                listCountry.add(country);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listCountry;
    }

    public Country getCountryById(String id) {
        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_COUNTRY, mAllColumns,
                DatabaseHelper.TABLE_COUNTRY + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Country country = cursorToCountry(cursor);
        return country;
    }
    protected Country cursorToCountry(Cursor cursor) {
        Country country = new Country();
        country.setCounrtyId(cursor.getInt(0));
        country.setCountryCode(cursor.getString(1));
        country.setCountry(cursor.getString(2));
        country.setUpdationTime(Timestamp.valueOf(cursor.getString(3)));
        return country;
    }
}

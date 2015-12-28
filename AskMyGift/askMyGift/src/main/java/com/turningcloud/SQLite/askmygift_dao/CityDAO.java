package com.turningcloud.SQLite.askmygift_dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.turningcloud.SQLite.model_classes.City;
import com.turningcloud.SQLite.model_classes.Country;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAHIRAJ on 8/12/2015.
 */
public class CityDAO {
    public static final String TAG = "CityDAO";
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDatabaseHelper;
    private Context mContext;
    private String[] mAllColumns = {DatabaseHelper.CITY_ID,DatabaseHelper.CITY,
            DatabaseHelper.COUNTY_ID, DatabaseHelper.UPDATION_TIME,
            DatabaseHelper.UPDATION_TIME};

    public CityDAO(Context context){
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

    public void createCity(ArrayList<City> city) {

        for (int i = 0; i < city.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COUNTY_ID, city.get(i).cityId);
            values.put(DatabaseHelper.COUNTRY_CODE, city.get(i).city);
            values.put(DatabaseHelper.COUNTRY, city.get(i).countryId);
            long insertId = mDatabase
                    .insert(DatabaseHelper.TABLE_CITY, null, values);
        }
    }


    public void deleteCountry(City city) {
        int id = city.getCityId();
        // delete all employees of this company
       /* EmployeeDAO employeeDao = new EmployeeDAO(mContext);
        List<Employee> listEmployees = employeeDao.getEmployeesOfCompoany(id);
        if (listEmployees != null && !listEmployees.isEmpty()) {
            for (Employee e : listEmployees) {
                employeeDao.deleteEmployee(e);
            }
        } */

        System.out.println("the deleted city has the id: " + id);
        mDatabase.delete(mDatabaseHelper.TABLE_CITY, DatabaseHelper.CITY_ID
                + " = " + id, null);
    }

    public List<City> getAllCity() {
        List<City> listCity = new ArrayList<City>();

        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_CITY, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                City city = cursorToCity(cursor);
                listCity.add(city);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listCity;
    }

    public City getCityById(String id) {
        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_CITY, mAllColumns,
                DatabaseHelper.CITY_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        City city = cursorToCity(cursor);
        return city;
    }
    protected City cursorToCity(Cursor cursor) {
        City city = new City();
        city.setCityId(cursor.getInt(0));
        city.setCity(cursor.getString(1));
        city.setCountryId(cursor.getInt(2));
        city.setUpdationTime(Timestamp.valueOf(cursor.getString(3)));
        return city;
    }
}

package com.turningcloud.SQLite.askmygift_dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.turningcloud.SQLite.model_classes.City;
import com.turningcloud.SQLite.model_classes.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAHIRAJ on 8/12/2015.
 */
public class UserDAO {
    public static final String TAG = "UserDAO";
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDatabaseHelper;
    private Context mContext;
    private String[] mAllColumns = {DatabaseHelper.USER_ID,DatabaseHelper.FULL_NAME,
            DatabaseHelper.EMAIL_ID, DatabaseHelper.MOBILE_NUMBER,DatabaseHelper.DATE_OF_BIRTH,
            DatabaseHelper.GENDER,DatabaseHelper.HOBBY,DatabaseHelper.USER_TYPE,DatabaseHelper.GCM_ID,
            DatabaseHelper.ADDRESS_ID,DatabaseHelper.PREF_ID,DatabaseHelper.ACTIVE,
            DatabaseHelper.CREATION_TIME,DatabaseHelper.UPDATION_TIME};

    public UserDAO(Context context){
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

    public void createUser(ArrayList<User> user) {

        for (int i = 0; i < user.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.USER_ID, user.get(i).userId);
            values.put(DatabaseHelper.FULL_NAME, user.get(i).fullName);
            values.put(DatabaseHelper.EMAIL_ID, user.get(i).emailId);
            values.put(DatabaseHelper.MOBILE_NUMBER, user.get(i).mobileNumber);
            values.put(DatabaseHelper.DATE_OF_BIRTH, user.get(i).dateOfBirth);
            values.put(DatabaseHelper.GENDER, user.get(i).gender);
            values.put(DatabaseHelper.HOBBY, user.get(i).hobby);
            values.put(DatabaseHelper.USER_TYPE, user.get(i).userId);
            values.put(DatabaseHelper.GCM_ID, user.get(i).gcmId);
            values.put(DatabaseHelper.ADDRESS_ID, user.get(i).addressId);
            values.put(DatabaseHelper.PREF_ID, user.get(i).prefId);
            values.put(DatabaseHelper.ACTIVE, user.get(i).active);
            
            long insertId = mDatabase
                    .insert(DatabaseHelper.TABLE_USER, null, values);
        }
    }


    public void deleteUser(User user) {
        String id = user.getUserId();
        // delete all employees of this company
       /* EmployeeDAO employeeDao = new EmployeeDAO(mContext);
        List<Employee> listEmployees = employeeDao.getEmployeesOfCompoany(id);
        if (listEmployees != null && !listEmployees.isEmpty()) {
            for (Employee e : listEmployees) {
                employeeDao.deleteEmployee(e);
            }
        } */

        System.out.println("the deleted User has the id: " + id);
        mDatabase.delete(mDatabaseHelper.TABLE_USER, DatabaseHelper.USER_ID
                + " = " + id, null);
    }

    public List<User> getAllUser() {
        List<User> listUser = new ArrayList<User>();

        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_USER, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                User user = cursorToUser(cursor);
                listUser.add(user);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listUser;
    }

    public User getUserById(String id) {
        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_USER, mAllColumns,
                DatabaseHelper.USER_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        User user = cursorToUser(cursor);
        return user;
    }



    protected User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setUserId(cursor.getString(0));
        user.setFullName(cursor.getString(1));
        user.setEmailId(cursor.getString(2));
        user.setMobileNumber(cursor.getString(3));
        user.setDateOfBirth(cursor.getString(4));
        user.setGender(cursor.getString(5));
        user.setHobby(cursor.getString(6));
        user.setUserId(cursor.getString(7));
        user.setGcmId(cursor.getString(8));
        user.setAddressId(cursor.getString(9));
        user.setPrefId(cursor.getString(10));
        user.setActive(cursor.getInt(11));
        user.setCreationTime(Timestamp.valueOf(cursor.getString(12)));
        user.setUpdationTime(Timestamp.valueOf(cursor.getString(13)));
        return user;
    }
}

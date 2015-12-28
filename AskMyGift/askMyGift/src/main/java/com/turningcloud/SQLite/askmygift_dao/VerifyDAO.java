package com.turningcloud.SQLite.askmygift_dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.turningcloud.SQLite.model_classes.User;
import com.turningcloud.SQLite.model_classes.Verify;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAHIRAJ on 8/12/2015.
 */
public class VerifyDAO {
    public static final String TAG = "VerifyDAO";
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDatabaseHelper;
    private Context mContext;

    private String[] mAllColumns = {DatabaseHelper.VERIFY_ID,DatabaseHelper.USER_ID,
            DatabaseHelper.CHANNEL, DatabaseHelper.VALUE,DatabaseHelper.VERIFIED
            };

    public VerifyDAO(Context context){
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

    public void createVerify(ArrayList<Verify> verify) {

        for (int i = 0; i < verify.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.VERIFY_ID, verify.get(i).verifyId);
            values.put(DatabaseHelper.USER_ID, verify.get(i).userId);
            values.put(DatabaseHelper.CHANNEL, verify.get(i).channel);
            values.put(DatabaseHelper.VALUE, verify.get(i).value);
            values.put(DatabaseHelper.VERIFIED, verify.get(i).verified);

            long insertId = mDatabase
                    .insert(DatabaseHelper.TABLE_VERIFY, null, values);
        }
    }


    public void deleteVerify(Verify verify) {
        String id = verify.getUserId();
        // delete all employees of this company
       /* EmployeeDAO employeeDao = new EmployeeDAO(mContext);
        List<Employee> listEmployees = employeeDao.getEmployeesOfCompoany(id);
        if (listEmployees != null && !listEmployees.isEmpty()) {
            for (Employee e : listEmployees) {
                employeeDao.deleteEmployee(e);
            }
        } */

        System.out.println("the deleted Verify has the id: " + id);
        mDatabase.delete(mDatabaseHelper.TABLE_VERIFY, DatabaseHelper.VERIFY_ID
                + " = " + id, null);
    }

    public List<Verify> getAllVerify() {
        List<Verify> listVerify = new ArrayList<Verify>();

        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_VERIFY, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Verify verify = cursorToVerify(cursor);
                listVerify.add(verify);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listVerify;
    }

    public Verify getVerifyById(String id) {
        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_VERIFY, mAllColumns,
                DatabaseHelper.VERIFY_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Verify verify = cursorToVerify(cursor);
        return verify;
    }

    protected Verify cursorToVerify(Cursor cursor) {
        Verify verify = new Verify();
        verify.setVerifyId(cursor.getInt(0));
        verify.setUserId(cursor.getString(1));
        verify.setChannel(cursor.getString(2));
        verify.setValue(cursor.getString(3));
        verify.setVerified(cursor.getInt(4));
        verify.setCreationTime(Timestamp.valueOf(cursor.getString(5)));
        verify.setUpdationTime(Timestamp.valueOf(cursor.getString(6)));
        return verify;
    }
}

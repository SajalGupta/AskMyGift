package com.turningcloud.SQLite.askmygift_dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.turningcloud.SQLite.model_classes.Event;
import com.turningcloud.SQLite.model_classes.Wish;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAHIRAJ on 8/12/2015.
 */
public class WishDAO {
    public static final String TAG = "WishDAO";
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDatabaseHelper;
    private Context mContext;

    private String[] mAllColumns = {DatabaseHelper.ID,DatabaseHelper.KEYWORD,
            DatabaseHelper.URL,DatabaseHelper.DESCRIPTION,DatabaseHelper.MESSAGE,
            DatabaseHelper.CREATION_TIME,DatabaseHelper.UPDATION_TIME
    };

    public WishDAO(Context context){
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

    public void createWish(ArrayList<Wish> wish) {

        for (int i = 0; i < wish.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.ID, wish.get(i).id);
            values.put(DatabaseHelper.KEYWORD, wish.get(i).keyword);
            values.put(DatabaseHelper.URL, wish.get(i).url);
            values.put(DatabaseHelper.DESCRIPTION, wish.get(i).description);
            values.put(DatabaseHelper.MESSAGE, wish.get(i).message);
            long insertId = mDatabase
                    .insert(DatabaseHelper.TABLE_WISH, null, values);
        }
    }


    public void deleteWish(Wish wish) {
        int id = wish.getId();
        // delete all employees of this company
       /* EmployeeDAO employeeDao = new EmployeeDAO(mContext);
        List<Employee> listEmployees = employeeDao.getEmployeesOfCompoany(id);
        if (listEmployees != null && !listEmployees.isEmpty()) {
            for (Employee e : listEmployees) {
                employeeDao.deleteEmployee(e);
            }
        } */

        System.out.println("the deleted Wish has the id: " + id);
        mDatabase.delete(mDatabaseHelper.TABLE_WISH, DatabaseHelper.ID
                + " = " + id, null);
    }

    public List<Wish> getAllWish() {
        List<Wish> listWish = new ArrayList<Wish>();

        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_WISH, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Wish wish = cursorToWish(cursor);
                listWish.add(wish);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listWish;
    }

    public Wish getWishById(String id) {
        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_WISH, mAllColumns,
                DatabaseHelper.ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Wish wish = cursorToWish(cursor);
        return wish;
    }

    protected Wish cursorToWish(Cursor cursor) {
        Wish wish = new Wish();
        wish.setId(cursor.getInt(0));
        wish.setKeyword(cursor.getString(1));
        wish.setUrl(cursor.getString(2));
        wish.setDescription(cursor.getString(3));
        wish.setMessage(cursor.getString(4));
        wish.setCreationTime(Timestamp.valueOf(cursor.getString(5)));
        wish.setUpdationTime(Timestamp.valueOf(cursor.getString(6)));
        return wish;
    }
}

package com.turningcloud.SQLite.askmygift_dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.turningcloud.SQLite.model_classes.Address;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAHIRAJ on 8/11/2015.
 */
public class AddressDAO {
    public static final String TAG = "AddressDAO";
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDatabaseHelper;
    private Context mContext;
    private String[] mAllColumns = {DatabaseHelper.ADDRESS_ID,DatabaseHelper.ADDRESS_LINE1,
            DatabaseHelper.ADDRESSLINE2,
            DatabaseHelper.DISTRICT_ID,DatabaseHelper.CITY_ID,DatabaseHelper.STATE,
            DatabaseHelper.PINCODE,DatabaseHelper.CREATION_TIME,DatabaseHelper.UPDATION_TIME};

    public AddressDAO(Context context){
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

    public void createAddress(ArrayList<Address> address) {

        for (int i = 0; i < address.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.ADDRESS_ID, address.get(i).addressId);
            values.put(DatabaseHelper.ADDRESS_LINE1, address.get(i).addressLine1);
            values.put(DatabaseHelper.ADDRESSLINE2, address.get(i).addressLine2);
            values.put(DatabaseHelper.DISTRICT_ID, address.get(i).district);
            values.put(DatabaseHelper.CITY_ID, address.get(i).cityId);
            values.put(DatabaseHelper.STATE, address.get(i).state);
            values.put(DatabaseHelper.PINCODE, address.get(i).postalCode);

            long insertId = mDatabase
                    .insert(DatabaseHelper.TABLE_ADDRESS, null, values);
        }
    }


    public void deleteAddress(Address address) {
        String id = address.getAddressId();
        // delete all employees of this company
       /* EmployeeDAO employeeDao = new EmployeeDAO(mContext);
        List<Employee> listEmployees = employeeDao.getEmployeesOfCompoany(id);
        if (listEmployees != null && !listEmployees.isEmpty()) {
            for (Employee e : listEmployees) {
                employeeDao.deleteEmployee(e);
            }
        } */

        System.out.println("the deleted company has the id: " + id);
        mDatabase.delete(mDatabaseHelper.TABLE_ADDRESS, DatabaseHelper.ADDRESS_ID
                + " = " + id, null);
    }

    public List<Address> getAllAddress() {
        List<Address> listAddress = new ArrayList<Address>();

        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_ADDRESS, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Address address = cursorToAddress(cursor);
                listAddress.add(address);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listAddress;
    }

    public Address getAddressById(String id) {
        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_ADDRESS, mAllColumns,
                DatabaseHelper.ADDRESS_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Address address = cursorToAddress(cursor);
        return address;
    }
    protected Address cursorToAddress(Cursor cursor) {
        Address address = new Address();
        address.setAddressId(cursor.getString(0));
        address.setAddressLine1(cursor.getString(1));
        address.setAddressLine2(cursor.getString(2));
        address.setDistrict(cursor.getString(3));
        address.setCityId(cursor.getString(4));
        address.setState(cursor.getString(5));
        address.setPostalCode(cursor.getString(6));
        address.setCreationTime(Timestamp.valueOf(cursor.getString(7)));
        address.setUpdationTime(Timestamp.valueOf(cursor.getString(8)));
        return address;
    }
}

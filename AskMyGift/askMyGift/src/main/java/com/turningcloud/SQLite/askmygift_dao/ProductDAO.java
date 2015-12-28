package com.turningcloud.SQLite.askmygift_dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.turningcloud.SQLite.model_classes.Product;
import com.turningcloud.SQLite.model_classes.Wish;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAHIRAJ on 8/12/2015.
 */
public class ProductDAO {
    public static final String TAG = "ProductDAO";
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDatabaseHelper;
    private Context mContext;


    private String[] mAllColumns = {DatabaseHelper.PRODUCT_ID,DatabaseHelper.DIARY_ID,
            DatabaseHelper.PRODUCT_NAME,DatabaseHelper.PRODUCT_DESC,DatabaseHelper.PRODUCT_URL,
            DatabaseHelper.PRODUCT_IMAGE_URL,DatabaseHelper.PRODUCT_UPC,DatabaseHelper.PRODUCT_COST,
            DatabaseHelper.CREATION_TIME,DatabaseHelper.UPDATION_TIME
    };

    public ProductDAO(Context context){
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


    public void createProduct(ArrayList<Product> product) {

        for (int i = 0; i < product.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.PRODUCT_ID, product.get(i).productId);
            values.put(DatabaseHelper.DIARY_ID, product.get(i).diaryId);
            values.put(DatabaseHelper.PRODUCT_NAME, product.get(i).productName);
            values.put(DatabaseHelper.PRODUCT_DESC, product.get(i).productDesc);
            values.put(DatabaseHelper.PRODUCT_URL, product.get(i).productUrl);
            values.put(DatabaseHelper.PRODUCT_IMAGE_URL, product.get(i).productImageUrl);
            values.put(DatabaseHelper.PRODUCT_UPC, product.get(i).productUPC);
            values.put(DatabaseHelper.PRODUCT_COST, product.get(i).productCost);
            long insertId = mDatabase
                    .insert(DatabaseHelper.TABLE_PRODUCT, null, values);
        }
    }


    public void deleteProduct(Product product) {
        int id = product.getProductId();
        // delete all employees of this company
       /* EmployeeDAO employeeDao = new EmployeeDAO(mContext);
        List<Employee> listEmployees = employeeDao.getEmployeesOfCompoany(id);
        if (listEmployees != null && !listEmployees.isEmpty()) {
            for (Employee e : listEmployees) {
                employeeDao.deleteEmployee(e);
            }
        } */

        System.out.println("the deleted product has the id: " + id);
        mDatabase.delete(mDatabaseHelper.TABLE_PRODUCT, DatabaseHelper.PRODUCT_ID
                + " = " + id, null);
    }

    public List<Product> getAllProduct() {
        List<Product> listProduct = new ArrayList<Product>();

        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_PRODUCT, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Product product = cursorToProduct(cursor);
                listProduct.add(product);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listProduct;
    }

    public Product getProductById(String id) {
        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_PRODUCT, mAllColumns,
                DatabaseHelper.PRODUCT_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Product product = cursorToProduct(cursor);
        return product;
    }

    protected Product cursorToProduct(Cursor cursor) {
        Product product = new Product();
        product.setProductId(cursor.getInt(0));
        product.setDiaryId(cursor.getInt(1));
        product.setProductName(cursor.getString(2));
        product.setProductDesc(cursor.getString(3));
        product.setProductUrl(cursor.getString(4));
        product.setProductImageUrl(cursor.getString(5));
        product.setProductUPC(cursor.getString(6));
        product.setProductCost(cursor.getString(7));
        product.setCreationTime(Timestamp.valueOf(cursor.getString(8)));
        product.setUpdationTime(Timestamp.valueOf(cursor.getString(9)));
        return product;
    }
}

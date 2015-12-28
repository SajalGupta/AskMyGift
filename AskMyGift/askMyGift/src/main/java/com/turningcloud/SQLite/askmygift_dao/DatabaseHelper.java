package com.turningcloud.SQLite.askmygift_dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by MAHIRAJ on 8/11/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "askmygift.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_ADDRESS = "address";
    public static final String TABLE_CITY = "city";
    public static final String TABLE_COUNTRY = "country";
    public static final String TABLE_USER = "user";
    public static final String TABLE_VERIFY = "verify";
    public static final String TABLE_PREFERENCES = "preferences";
    public static final String TABLE_DIARY = "diary";
    public static final String TABLE_EVENT = "event";
    public static final String TABLE_WISH = "wish";
    public static final String TABLE_PRODUCT = "product";

    //COMMON COLUMN NAMES
    public static final String COUNTY_ID = "countryId";
    public static final String ADDRESS_ID = "addressId";
    public static final String CREATION_TIME ="creationTime";
    public static final String UPDATION_TIME ="updationTime";
    public static final String CITY_ID = "cityId";
    public static final String  COUNTRY_CODE="countryCode";

    //columns for address table
    public static final String ADDRESS_LINE1="addressLine1";
    public static final String ADDRESSLINE2="addressLine2";
    public static final String DISTRICT_ID="district";
    public static final String STATE="state";
    public static final String PINCODE="postalCode";
    private static final String CREATE_TABLE_ADDRESS = "CREATE TABLE `address` (\n" +
            "  `addressId` varchar(20) NOT NULL,\n" +
            "  `addressLine1` varchar(50) DEFAULT NULL,\n" +
            "  `addressLine2` varchar(50) DEFAULT NULL,\n" +
            "  `district` varchar(20) DEFAULT NULL,\n" +
            "  `cityId` INTEGER NOT NULL,\n" +
            "  `state` varchar(100) DEFAULT NULL,\n" +
            "  `postalCode` varchar(10) DEFAULT NULL,\n" +
            "  `creationTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',\n" +
            "  `updationTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "  PRIMARY KEY (`addressId`),\n" +

            "   FOREIGN KEY (`cityId`) REFERENCES `city` (`cityId`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
            ");";
    //columns for country tables

    public static final String COUNTRY ="country";
    private static final String CREATE_TABLE_COUNTRY ="CREATE TABLE `country`(\n" +
            "  `countryId` INTEGER PRIMARY KEY,\n" +
            "  `countryCode` varchar(5) NOT NULL,\n" +
            "  `country` varchar(50) NOT NULL,\n" +
            "  `updationTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP\n" +

            ");";
    //columns for city table
    public static final String CITY ="city";
    private static final String CREATE_TABLE_CITY ="CREATE TABLE `city` (\n" +
            "  `cityId` INTEGER PRIMARY KEY,\n" +
            "  `city` varchar(50) NOT NULL,\n" +
            "  `countryId` INTEGER NOT NULL,\n" +
            "  `updationTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +


            "  FOREIGN KEY (`countryId`) REFERENCES `country` (`countryId`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
            ") ;";
    //user table names
    public static final  String USER_ID = "userId";
    public static final  String FULL_NAME = "fullName";
    public static final  String EMAIL_ID = "emailId";
    public static final  String MOBILE_NUMBER = "mobileNumber";
    public static final  String DATE_OF_BIRTH = "dateOfBirth";
    public static final  String GENDER = "gender";
    public static final  String HOBBY = "hobby";
    public static final  String USER_TYPE = "userType";
    public static final  String GCM_ID = "gcmId";
    public static final  String PREF_ID = "prefId";
    public static final  String ACTIVE = "prefId";
    private static final String CREATE_TABLE_USER ="CREATE TABLE `user` (\n" +
            "  `userId` varchar(50) NOT NULL,\n" +
            "  `fullName` varchar(100) NOT NULL,\n" +
            "  `emailId` varchar(100) DEFAULT NULL,\n" +
            "  `mobileNumber` varchar(20) DEFAULT NULL,\n" +
            "  `dateOfBirth` varchar(20) NOT NULL,\n" +
            "  `gender` varchar(45) DEFAULT NULL,\n" +
            "  `hobby` varchar(45) DEFAULT NULL,\n" +
            "  `userType` varchar(45) DEFAULT 'user',\n" +
            "  `gcmId` varchar(100) DEFAULT NULL,\n" +
            "  `addressId` varchar(20) NOT NULL,\n" +
            "  `prefId` varchar(20) DEFAULT NULL,\n" +
            "  `active` tinyint(1) NOT NULL DEFAULT '1',\n" +
            "  `creationTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',\n" +
            "  `updationTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "  PRIMARY KEY (`userId`),\n" +

            "  FOREIGN KEY (`addressId`) REFERENCES `address` (`addressId`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
            "  FOREIGN KEY (`prefId`) REFERENCES `preferences` (`prefId`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
            ") ;";
    //columns for varify table
    public static final  String VERIFY_ID ="verifyId";
    public static final  String CHANNEL ="channel";
    public static final  String VALUE ="value";
    public static final  String VERIFIED ="verified";
    private static final String CREATE_TABLE_VERIFY ="CREATE TABLE `verify` (\n" +
            "  `verifyId` INTEGER PRIMARY KEY ,\n" +
            "  `userId` varchar(50) NOT NULL,\n" +
            "  `channel` varchar(20) NOT NULL,\n" +
            "  `value` varchar(100) NOT NULL,\n" +
            "  `verified` tinyint(4) NOT NULL DEFAULT '1',\n" +
            "  `creationTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',\n" +
            "  `updationTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n" +


            "  FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
            ") ;";
    //COLUMNS FOR PREFERENCES TABLE

    public static final  String SHOE_SIZE ="shoeSize";
    public static final  String CASUAL_SHIRT_SIZE ="casualShirtSize";
    public static final  String DRESS_SHIRT_SIZE ="dressShirtSize";
    public static final  String WAIST_SIZE ="waistSize";
    public static final  String JACKET_SIZE ="jacketSize";
    public static final  String DRESS_SIZE ="dressSize";
    public static final  String HAT_SIZE ="hatSize";
    public static final  String LIKES ="likes";
    public static final  String DISLIKES ="dislikes";
    public static final  String CHOICE_A ="choicea";
    public static final  String CHOICE_B ="choiceb";
    public static final  String CHOICE_C ="choicec";
    public static final  String CHOICE_D ="choiced";

    private static final String CREATE_TABLE_PREFERENCES ="CREATE TABLE `preferences` (\n" +
            "  `prefId` varchar(20) NOT NULL,\n" +
            "  `shoeSize` varchar(50) DEFAULT NULL,\n" +
            "  `casualShirtSize` varchar(50) DEFAULT NULL,\n" +
            "  `dressShirtSize` varchar(50) DEFAULT NULL,\n" +
            "  `waistSize` varchar(50) DEFAULT NULL,\n" +
            "  `jacketSize` varchar(50) DEFAULT NULL,\n" +
            "  `dressSize` varchar(50) DEFAULT NULL,\n" +
            "  `hatSize` varchar(50) DEFAULT NULL,\n" +
            "  `likes` varchar(500) DEFAULT NULL,\n" +
            "  `dislikes` varchar(500) DEFAULT NULL,\n" +
            "  `choicea` varchar(250) DEFAULT NULL,\n" +
            "  `choiceb` varchar(250) DEFAULT NULL,\n" +
            "  `choicec` varchar(250) DEFAULT NULL,\n" +
            "  `choiced` varchar(250) DEFAULT NULL,\n" +
            "  `creationTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',\n" +
            "  `updationTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "  PRIMARY KEY (`prefId`)\n" +
            ") ;";
    //columns for diary table
    public static final  String DIARY_ID ="diaryId";
    public static final  String DIARY_NAME ="diaryName";
    public static final  String DIARY_DESC ="diaryDesc";
    public static final  String DIARY_TYPE ="diaryType";
    private static final String CREATE_TABLE_DIARY ="CREATE TABLE `diary` (\n" +
            "  `diaryId` INTEGER PRIMARY KEY ,\n" +
            "  `userId` varchar(50) NOT NULL,\n" +
            "  `diaryName` varchar(45) NOT NULL,\n" +
            "  `diaryDesc` varchar(100) DEFAULT NULL,\n" +
            "  `diaryType` varchar(45) DEFAULT NULL,\n" +
            "  `creationTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',\n" +
            "  `updationTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n" +


            "  FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE CASCADE\n" +
            ") ;";
    //colums for event
    public static final  String EVENT_ID ="eventId";
    public static final  String EVENT_NAME ="eventName";
    public static final  String EVENT_DESC ="eventDesc";
    public static final  String EVENT_START_DATE ="eventStartDate";
    public static final  String EVENT_END_DATE ="eventEndDate";
    private static final String CREATE_TABLE_EVENT ="CREATE TABLE `event` (\n" +
            "  `eventId` INTEGER PRIMARY KEY ,\n" +
            "  `diaryId` int(11) NOT NULL,\n" +
            "  `eventName` varchar(45) NOT NULL,\n" +
            "  `eventDesc` varchar(100) DEFAULT NULL,\n" +
            "  `eventStartDate` varchar(20) DEFAULT NULL,\n" +
            "  `eventEndDate` varchar(20) DEFAULT NULL,\n" +
            "  `creationTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',\n" +
            "  `updationTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n" +


            "  FOREIGN KEY (`diaryId`) REFERENCES `diary` (`diaryId`) ON DELETE NO ACTION ON UPDATE CASCADE\n" +
            ") ;";
    //columns for column wish
    public static final  String ID ="id";
    public static final  String KEYWORD ="keyword";
    public static final  String URL ="url";
    public static final  String DESCRIPTION ="description";
    public static final  String MESSAGE ="message";
    private static final String CREATE_TABLE_WISH ="CREATE TABLE `wish` (\n" +
            "  `id` INTEGER PRIMARY KEY ,\n" +
            "  `keyword` varchar(255) NOT NULL,\n" +
            "  `url` varchar(2000) NOT NULL,\n" +
            "  `description` varchar(1000) NOT NULL,\n" +
            "  `message` varchar(1000) DEFAULT NULL,\n" +
            "  `creationTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',\n" +
            "  `updationTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP\n" +

            ");";
    //columns for product table
    public static final  String PRODUCT_ID ="productId";
    public static final  String PRODUCT_NAME ="productName";
    public static final  String PRODUCT_DESC ="productDesc";
    public static final  String PRODUCT_URL ="productUrl";
    public static final  String PRODUCT_IMAGE_URL ="productImageUrl";
    public static final  String PRODUCT_UPC ="productUPC";
    public static final  String PRODUCT_COST ="productCost";
    private static final String CREATE_TABLE_PRODUCT ="CREATE TABLE `product` (\n" +
            "  `productId` INTEGER PRIMARY KEY ,\n" +
            "  `diaryId` int(11) NOT NULL,\n" +
            "  `productName` varchar(45) NOT NULL,\n" +
            "  `productDesc` varchar(100) DEFAULT NULL,\n" +
            "  `productUrl` varchar(100) DEFAULT NULL,\n" +
            "  `productImageUrl` varchar(100) DEFAULT NULL,\n" +
            "  `productUPC` varchar(45) DEFAULT NULL,\n" +
            "  `productCost` varchar(20) DEFAULT NULL,\n" +
            "  `creationTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',\n" +
            "  `updationTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n" +

            "   FOREIGN KEY (`diaryId`) REFERENCES `diary` (`diaryId`) ON DELETE NO ACTION ON UPDATE CASCADE\n" +
            ") ;";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("DB", "Construct");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("Query",CREATE_TABLE_COUNTRY);
        db.execSQL(CREATE_TABLE_COUNTRY);
        db.execSQL(CREATE_TABLE_CITY);
        db.execSQL(CREATE_TABLE_ADDRESS);
        db.execSQL(CREATE_TABLE_PREFERENCES);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_VERIFY);
        db.execSQL(CREATE_TABLE_DIARY);
        db.execSQL(CREATE_TABLE_EVENT);
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_TABLE_WISH);
        Log.i("DB","Create");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_COUNTRY);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_CITY);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_ADDRESS);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_PREFERENCES);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_VERIFY);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_DIARY);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_EVENT);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_WISH);
        onCreate(db);
    }
}

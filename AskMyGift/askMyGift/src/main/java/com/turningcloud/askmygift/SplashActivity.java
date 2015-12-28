package com.turningcloud.askmygift;

import com.facebook.FacebookSdk;
import com.pushbots.push.Pushbots;
import com.turningcloud.SQLite.askmygift_dao.AddressDAO;
import com.turningcloud.SQLite.askmygift_dao.DatabaseHelper;
import com.turningcloud.SQLite.askmygift_dao.PreferencesDAO;
import com.turningcloud.SQLite.askmygift_dao.ProductDAO;
import com.turningcloud.SQLite.model_classes.Address;
import com.turningcloud.SQLite.model_classes.Preferences;
import com.turningcloud.SQLite.model_classes.Product;
import com.turningcloud.drawer.MainActivity;
import com.turningcloud.login.LoginActivityFacebookGoogle;
import com.turningcloud.login.NewFacebookLogin;
import com.turningcloud.login.RegisterActivity;
import com.turningcloud.push_notifications.customHandler;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class SplashActivity extends Activity {
	
	
	/** Duration of wait **/
    // SQLite Sync. Getting started for the first time.
    private final int SPLASH_DISPLAY_TIME = 3000;
    DatabaseHelper mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        FacebookSdk.sdkInitialize(getApplicationContext());
        //code that displays the content in full screen mode  
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        Pushbots.sharedInstance().init(this);
        mydb = new DatabaseHelper(this);
     /*   ArrayList<Product> pList = new ArrayList<>();
        ProductDAO p = new ProductDAO(this);
        Product temp = new Product();
        temp.setDiaryId(1);
        temp.setProductName("Iphone");
        temp.setProductId(2);
        pList.add(temp);
        p.createProduct(pList); */
        //Create AddressRow
        ArrayList<Address> pList = new ArrayList<>();
        AddressDAO p = new AddressDAO(this);
        Address temp = new Address();
        temp.setAddressId("ADDR" + System.currentTimeMillis());
        temp.setCityId("1");

        pList.add(temp);
       p.createAddress(pList);

        //Create Preference Row
        ArrayList<Preferences> preList = new ArrayList<>();
        PreferencesDAO pref = new PreferencesDAO(this);
        Preferences temp_pref = new Preferences();
        temp_pref.setPrefId("PREF");

        pList.add(temp);
        p.createAddress(pList);








        Pushbots.sharedInstance().setCustomHandler(customHandler.class);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.turningcloud.askmygift",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        
        
        new Handler().postDelayed(new Runnable() { 
            @Override
            public void run() {
               Intent i = new Intent(SplashActivity.this, TutorialActivity.class);
              /* if(check shared preference for registered usertrue){
            	   i = new Intent(SplashActivity.this, MainActivity.class);
               } else  if(check shared preference for first timetrue){
            	   i = new Intent(SplashActivity.this, MainActivity.class);
               } */
               startActivity(i);
                // close this activity
                finish();
            }
        }, SPLASH_DISPLAY_TIME); // wait for 3 seconds
        
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.turningcloud.drawer;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;

import com.turningcloud.askmygift.R;


public class MainActivity extends AppCompatActivity implements NavigationDrawerCallbacks {

    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    Fragment fragment;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_topdrawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        try{
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }catch (Exception e){
            Log.i("SupportActionBar",e.getMessage());
        }


      
        

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
    	
    //    Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();
        
       
        
        switch(position) {
        case 0:
        	FirstFragment fragmenttab = new FirstFragment();
            getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, fragmenttab).addToBackStack("fragBack").commit();
break;
        
              case 1:
          FirstFragment2 firstFragment2 = new FirstFragment2();
            getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, firstFragment2).addToBackStack("fragBack").commit();
break;
        case 2:
        	MyPreferences myPreferences = new MyPreferences();
            getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, myPreferences).addToBackStack("fragBack").commit();
break;
     /* case 3:
        	Contacts contacts = new Contacts();
            getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, contacts).addToBackStack("fragBack").commit();
break;
        case 4:
        	ManageDiary manageDiary = new ManageDiary();
            getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, manageDiary).addToBackStack("fragBack").commit();
break;*/
        case 3:
        	OrganizeEvents organizeEvents = new OrganizeEvents();
            getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, organizeEvents).addToBackStack("fragBack").commit();
break;
        case 4:
        	GiftLists giftLists = new GiftLists();
            getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, giftLists).addToBackStack("fragBack").commit();
break;
        case 5:
        	GiftSomeone giftSomeone = new GiftSomeone();
            getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, giftSomeone).addToBackStack("fragBack").commit();
break;
    
        
        }
    }
        

    @Override
    public void onBackPressed() {
    	FrameLayout fl = (FrameLayout) findViewById(R.id.container);
        if (mNavigationDrawerFragment.isDrawerOpen())
        	

        	
        	
            mNavigationDrawerFragment.closeDrawer();
        
        if (fl.getChildCount() == 1) {
            super.onBackPressed();
            
            if (fl.getChildCount() <=0) {
                new AlertDialog.Builder(this)
                        .setTitle("Ask My Gift").setCancelable(false)
                        .setMessage("Do you really want to close this beautiful app?")
                        .setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                            int which) {
                                        finish();
                                    }
                                })
                        .setNegativeButton("NO",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                            int which) {
                                    	
                                    }
                                }).show();
                // load your first Fragment here
            }
        } else if (fl.getChildCount() == 0) {
        	
        } else {
            super.onBackPressed();
        }
    }
     
     
        
          
    
    
    public void addPersonalEvent(View v){
    	
    	PersonalAddEvents personalAddEvents=new PersonalAddEvents();
    	getSupportFragmentManager().beginTransaction().replace(R.id.container, personalAddEvents).addToBackStack("fragBack").commit();
    }
    
    
    
public void addGenericEvent(View v){
    	
	GenericAddEvents genericAddEvents=new GenericAddEvents();
	getSupportFragmentManager().beginTransaction().replace(R.id.container, genericAddEvents).addToBackStack("fragBack").commit();
    }
    

    public void eventAdd(View v)
    {
    	AddEvents addEvents=new AddEvents();
    	getSupportFragmentManager().beginTransaction().replace(R.id.container, addEvents).addToBackStack("fragBack").commit();
    }
    
    public void updateDetails(View v)
    {
    	
    	
    	
    	 if (mNavigationDrawerFragment.isDrawerOpen())
             mNavigationDrawerFragment.closeDrawer();
    	UpdateAccount updateAccount=new UpdateAccount();
    	getSupportFragmentManager().beginTransaction().replace(R.id.container, updateAccount).addToBackStack("fragBack").commit();
    }
    
    public void giftSomeone(View v) {
    	AddWishListGift addWishListGift=new AddWishListGift();
    	getSupportFragmentManager().beginTransaction().replace(R.id.container, addWishListGift).addToBackStack("fragBack").commit();
    	
    }
    
    
}

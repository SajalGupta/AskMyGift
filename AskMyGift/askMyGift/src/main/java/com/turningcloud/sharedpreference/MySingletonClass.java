package com.turningcloud.sharedpreference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class MySingletonClass {

	
	private static MySingletonClass instance;
	Activity activity;
	private SharedPreferences sharedPreferences;
	
	private  MySingletonClass(Activity activity) {
		this.activity = activity;
	}
	
	public static MySingletonClass getSingleInstance(Activity activity){
		if (instance == null) {
			instance = new MySingletonClass(activity);
		}
		return instance;
	}
	
	public SharedPreferences getPreferences(){
		
		if (sharedPreferences == null) {
			
			sharedPreferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
		}
		
		return sharedPreferences;
	}
	
	
	
}

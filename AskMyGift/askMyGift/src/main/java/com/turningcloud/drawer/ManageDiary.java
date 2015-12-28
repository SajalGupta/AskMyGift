package com.turningcloud.drawer;

import com.turningcloud.askmygift.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ManageDiary extends Fragment {  

	   
	   public ManageDiary() {  
	   }  

	   @Override  
	   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
	       View rootView = inflater.inflate(R.layout.manage_diary, container, false);  
	       
	       TextView txt=(TextView)rootView.findViewById(R.id.textView1);
	       return rootView;  
	   }  

	  
	}  
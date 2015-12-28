package com.turningcloud.drawer;

import com.turningcloud.askmygift.R;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FirstFragment2 extends Fragment {  

	   
	   public FirstFragment2() {  
	   }  

	   @Override  
	   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
	       View rootView = inflater.inflate(R.layout.fragment_two, container, false);  
	       
	       TextView txt=(TextView)rootView.findViewById(R.id.textView1);
	       return rootView;  
	   }  

	  
	}  
package com.turningcloud.drawer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.turningcloud.askmygift.R;

public class OrganizeEvents extends Fragment {  

	
	Toolbar toolbar;
    ViewPager pager;
    OrganizeEventsAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Generic Events","Personal Events"};
    int Numboftabs =2;
    
    
	   public OrganizeEvents() {  
	   }  

	   @Override  
	   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
		   View rootView = inflater.inflate(R.layout.organize_events,container, false);
		   
		   adapter =  new OrganizeEventsAdapter(getChildFragmentManager(),Titles,Numboftabs);
		// Assigning ViewPager View and setting the adapter
	        pager = (ViewPager)rootView.findViewById(R.id.pager);
	        pager.setAdapter(adapter);

	        // Assiging the Sliding Tab Layout View
	        tabs = (SlidingTabLayout)rootView.findViewById(R.id.tabs);
	        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

	        // Setting Custom Color for the Scroll bar indicator of the Tab View
	        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
	            @Override
	            public int getIndicatorColor(int position) {
	                return getResources().getColor(R.color.tabsScrollColor);
	            }
	        });

	        // Setting the ViewPager For the SlidingTabsLayout
	        tabs.setViewPager(pager);
		   
return rootView;
	   }
	   
	 
	   
	   
	   
}

package com.turningcloud.drawer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.turningcloud.askmygift.R;

public class Contacts extends Fragment {  
	
	ListView list;
	 String[] itemname ={
	 "Ashish",
	 "Sumeet",
	 "Alka",
	 "Nadeem",
	 "Manoj",
	 "Nitesh",
	 "Parth",
	 "Ajit"
	 
	 };
	 
	 Integer[] imgid={
	 R.drawable.audi,
	 R.drawable.ferrari_enzo,
	 R.drawable.ford_fiesta,
	 R.drawable.honda_accord,
	 R.drawable.hyundai_i30,
	 R.drawable.lamborghini_gallardo,
	 R.drawable.opel,
	 R.drawable.porshe_cayenne,
	 };

	   
	   public Contacts() {  
	   }  

	   @Override  
	   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
	       View rootView = inflater.inflate(R.layout.contacts, container, false);  
	       ContactListAdapter adapter=new ContactListAdapter(getActivity(), itemname, imgid);
	       list=(ListView)rootView.findViewById(R.id.list);
	       list.setAdapter(adapter);
	       
	       list.setOnItemClickListener(new OnItemClickListener() {
	       
	       @Override
	       public void onItemClick(AdapterView<?> parent, View view,
	       int position, long id) {
	       // TODO Auto-generated method stub
	       String Slecteditem= itemname[+position];
	       Toast.makeText(getActivity(), Slecteditem, Toast.LENGTH_SHORT).show();
	       
	       }
	       });
	       
	      
	       return rootView;  
	   }  

	  
	}  
package com.turningcloud.drawer;

import java.util.ArrayList;

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

public class GiftLists extends Fragment implements OnItemClickListener{  

	ArrayList<Car> arrayCars;
	ListView listViewCars;
	
	   public GiftLists() {  
	   }  

	   @Override  
	   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
	       View rootView = inflater.inflate(R.layout.gift_list, container, false);  
	       arrayCars=new ArrayList<Car>();
	       
	       Car audi=new Car(R.drawable.audi, "Audi A4", "Gray", 18000);
			Car opel=new Car(R.drawable.opel, "Opel Insigna", "Black", 14000);
			Car mercedes=new Car(R.drawable.mercedes, "mercedes CLS 320","Black", 16000);
			Car ferrari=new Car(R.drawable.ferrari_enzo, "Ferrari Enzo","White", 93000);
			Car fiesta=new Car(R.drawable.ford_fiesta, "Ford Fiesta","Green", 18000);
			Car porshe=new Car(R.drawable.porshe_cayenne,"porshe_cayenne","Dark Gray", 101000);
			Car lambo=new Car(R.drawable.lamborghini_gallardo, "Lamborghini gallardo","orange", 100000);
			Car hyundai=new Car(R.drawable.hyundai_i30, "Hyundai i30","blue", 20000);
			Car honda=new Car(R.drawable.honda_accord, "Honda accord","red", 19000);	
			
			arrayCars.add(audi);
			arrayCars.add(opel);
			arrayCars.add(mercedes);
			arrayCars.add(ferrari);
			arrayCars.add(fiesta);
			arrayCars.add(porshe);
			arrayCars.add(lambo);
			arrayCars.add(hyundai);
			arrayCars.add(honda);
	       
			listViewCars = (ListView)rootView.findViewById(R.id.list_cars);
			ListCarsAdapter adapter = new ListCarsAdapter(getActivity(), arrayCars);
			listViewCars.setAdapter(adapter);
			listViewCars.setOnItemClickListener(this);
	       
	       return rootView;  
	   }  
	   @Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Car selectedCar = arrayCars.get(position);
			Toast.makeText(getActivity(),"You've selected " + selectedCar.getModel()+
					" "+selectedCar.getColor()
					,Toast.LENGTH_SHORT).show();

		}
	  
	}  
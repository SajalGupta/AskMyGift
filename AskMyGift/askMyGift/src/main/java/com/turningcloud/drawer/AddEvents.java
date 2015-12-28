package com.turningcloud.drawer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.askmygift.giftdairy.constant.QueryMetadata;
import com.askmygift.giftdairy.dao.entity.Device;
import com.askmygift.giftdairy.dao.entity.Event;
import com.askmygift.giftdairy.dao.entity.User;
import com.google.gson.Gson;
import com.turningcloud.askmygift.R;
import com.turningcloud.login.RegisterActivity;




public class AddEvents extends Fragment {  
	
	JSONObject jsonObject,jsonObject2;
	Event event=null;
	EditText StartdateOfEvent,titleEvent,descriptionEvents,EndateOfEvent;
	 private RadioGroup radionEvents;
	    private RadioButton radioGeneric,radioPersonal;
	    Button addEvent;
	    SharedPreferences shared;
		 String userId;
	     public  static String url="http://4-dot-askmygiftweb.appspot.com/rest/DairyWebservice/DairyServices/";
	   public AddEvents() {  
		   
	   }  

	   @Override  
	   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
	       View rootView = inflater.inflate(R.layout.add_events, container, false);  
	       
	       shared = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
	        userId=shared.getString("Userid", "");
	       
	        StartdateOfEvent = (EditText)rootView. findViewById(R.id.StartdateOfEvent);
	        StartdateOfEvent.setInputType(InputType.TYPE_NULL);
	        StartdateOfEvent.requestFocus();
	        
	        EndateOfEvent = (EditText)rootView. findViewById(R.id.EndateOfEvent);
	        EndateOfEvent.setInputType(InputType.TYPE_NULL);
	        EndateOfEvent.requestFocus();
	        
		      
		      titleEvent=(EditText) rootView.findViewById(R.id.titleEvent);
		      descriptionEvents=(EditText) rootView.findViewById(R.id.descriptionEvets);
		      radioGeneric=(RadioButton) rootView.findViewById(R.id.radioGeneric);
		      radioPersonal=(RadioButton) rootView.findViewById(R.id.radioPersonal);
		      
	       
	     
	        StartdateOfEvent.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 DialogFragment newFragment = new SelectDateFragment();
		            newFragment.show(getActivity().getFragmentManager(), "DatePicker");
				
			}
		});
	        
	        EndateOfEvent.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					 DialogFragment newFragment = new SelectDateEndFragment();
			            newFragment.show(getActivity().getFragmentManager(), "DatePicker");
					
				}
			});
	      
	      addEvent=(Button) rootView.findViewById(R.id.addEvents);
	      addEvent.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(radioGeneric.isChecked()){
					
				//	new GenericAsyntask().execute(url);
					
					
					
					
				}
				else{
					
				//	new PersonalAsyntask().execute(url);
					
				
				}
				
			}
		});
	      
	      
	      
	      
	       return rootView;  
	   }  
	   
	   public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	        @Override
	        public Dialog onCreateDialog(Bundle savedInstanceState) {
	        final Calendar calendar = Calendar.getInstance();
	        int yy = calendar.get(Calendar.YEAR);
	        int mm = calendar.get(Calendar.MONTH);
	        int dd = calendar.get(Calendar.DAY_OF_MONTH);
	        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
	        }

	        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
	            populateSetDate(yy, mm+1, dd);
	        }
	        public void populateSetDate(int year, int month, int day) {
	        	StartdateOfEvent.setText(month+"/"+day+"/"+year);
	        	
	        	EndateOfEvent.setText(month+"/"+day+"/"+year);
	            }

	    }
	   
	   public class SelectDateEndFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	        @Override
	        public Dialog onCreateDialog(Bundle savedInstanceState) {
	        final Calendar calendar = Calendar.getInstance();
	        int yy = calendar.get(Calendar.YEAR);
	        int mm = calendar.get(Calendar.MONTH);
	        int dd = calendar.get(Calendar.DAY_OF_MONTH);
	        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
	        }

	        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
	            populateSetDate(yy, mm+1, dd);
	        }
	        public void populateSetDate(int year, int month, int day) {
	        	EndateOfEvent.setText(month+"/"+day+"/"+year);
	            }

	    }
	   
	   
	   private class GenericAsyntask extends AsyncTask<String, Void, String>{
	    	
	    	Context context;
	    	private ProgressDialog dialog = new ProgressDialog(getActivity());
	    	@Override
	    	protected void onPreExecute() {

	    		this.dialog.setMessage("Please wait");
	            this.dialog.show();
	    	}

	    	
			@Override
	    	protected String doInBackground(String... urls) {
				 try 
				 {
					 
	    		User user=new User();
	    		user.setUserId(userId);
	    		user.setMethodName(String.valueOf(QueryMetadata.dairyWebServiceType.ADD_EVENT));
	       			Gson gson = new Gson();
	       			
	       	 String userData = gson.toJson(user);
			
				jsonObject = new JSONObject(userData);

			event = new Event();
			event.setEventName(titleEvent.getText().toString());
			event.setEventDesc(descriptionEvents.getText().toString());
			event.setEventStartDate(StartdateOfEvent.getText().toString());
			event.setEventEndDate(EndateOfEvent.getText().toString());
	        event.setEventType(String.valueOf(QueryMetadata.eventType.GENERIC));
			 
			 String eventsData = gson.toJson(event);
		
				
					 
					jsonObject2 = new JSONObject(eventsData);
					
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
		
			 JSONArray arr = new JSONArray();

	         arr.put(jsonObject);

	         arr.put(jsonObject2);
	         
	         return POST(urls[0],arr);
	    		
	    	}

			private String POST(String string, JSONArray arr)  {
				InputStream inputStream = null;
		        String result = "";
		        try {
		        	
		            HttpClient httpclient = new DefaultHttpClient();
		            HttpPost httpPost = new HttpPost(url);
		 
		            String json = "";
		 
		            json = arr.toString();
		            StringEntity se = new StringEntity(json);
		            httpPost.setEntity(se);
		            httpPost.setHeader("Accept", "application/json");
		            httpPost.setHeader("Content-type", "application/json");
		         	 HttpResponse httpResponse = httpclient.execute(httpPost);
		            inputStream = httpResponse.getEntity().getContent();
		 
		            if(inputStream != null){
		                result = convertInputStreamToString(inputStream);
		                
		                Log.d("Generic response", result);
		                
		            }
		            
		            else
		                result = "Did not work!";
		            
		          
		        } catch (Exception e) {
		            Log.d("InputStream", e.getLocalizedMessage());
		        }
		 
		      
		        
		        
		        // 11. return result
		        return result;
		    }

			private  String convertInputStreamToString(InputStream inputStream) throws IOException{
		        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		        String line = "";
		        String result = "";
		        while((line = bufferedReader.readLine()) != null)
		            result += line;
		 
		        inputStream.close();
		        return result;
		 
		    }   


			@Override
	    	protected void onPostExecute(String result) {
				
			
				
				if (dialog.isShowing()) {
				
		            dialog.dismiss();
		        }
	    	
	    	}

	    }
 
	   private class PersonalAsyntask extends AsyncTask<String, Void, String>{
	    	
	    	Context context;
	    	private ProgressDialog dialog = new ProgressDialog(getActivity());
	    	@Override
	    	protected void onPreExecute() {

	    		this.dialog.setMessage("Please wait");
	            this.dialog.show();
	    	}

	    	
			@Override
	    	protected String doInBackground(String... urls) {
				 try 
				 {
					 
	    		User user=new User();
	    		user.setUserId(userId);
	       		
	       			Gson gson = new Gson();
	       			
	       	 String userData = gson.toJson(user);
			
				jsonObject = new JSONObject(userData);

			event = new Event();
			event.setEventName(titleEvent.getText().toString());
			event.setEventDesc(descriptionEvents.getText().toString());
			event.setEventStartDate(StartdateOfEvent.getText().toString());
			event.setEventEndDate(EndateOfEvent.getText().toString());
	        event.setEventType(String.valueOf(QueryMetadata.eventType.PERSONAL));
			 
			 String eventsData = gson.toJson(event);
		
				
					 
					jsonObject2 = new JSONObject(eventsData);
					
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
		
			 JSONArray arr = new JSONArray();

	         arr.put(jsonObject);

	         arr.put(jsonObject2);
	         
	         return POST(urls[0],arr);
	    		
	    	}

			private String POST(String string, JSONArray arr)  {
				InputStream inputStream = null;
		        String result = "";
		        try {
		        	
		            HttpClient httpclient = new DefaultHttpClient();
		            HttpPost httpPost = new HttpPost(url);
		 
		            String json = "";
		 
		            json = arr.toString();
		            StringEntity se = new StringEntity(json);
		            httpPost.setEntity(se);
		            httpPost.setHeader("Accept", "application/json");
		            httpPost.setHeader("Content-type", "application/json");
		         	 HttpResponse httpResponse = httpclient.execute(httpPost);
		            inputStream = httpResponse.getEntity().getContent();
		 
		            if(inputStream != null){
		                result = convertInputStreamToString(inputStream);
		                
		                Log.d("Generic response", result);
		                
		            }
		            
		            else
		                result = "Did not work!";
		            
		          
		        } catch (Exception e) {
		            Log.d("InputStream", e.getLocalizedMessage());
		        }
		 
		      
		        
		        
		        // 11. return result
		        return result;
		    }

			private  String convertInputStreamToString(InputStream inputStream) throws IOException{
		        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		        String line = "";
		        String result = "";
		        while((line = bufferedReader.readLine()) != null)
		            result += line;
		 
		        inputStream.close();
		        return result;
		 
		    }   


			@Override
	    	protected void onPostExecute(String result) {
				
			
				Toast.makeText(getActivity(), "Event has been successfully added", Toast.LENGTH_SHORT).show();
				if (dialog.isShowing()) {
				
		            dialog.dismiss();
		        }
	    	
	    	}

	    }

	   
	   
	   
}
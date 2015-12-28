package com.turningcloud.drawer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.askmygift.giftdairy.constant.QueryMetadata;
import com.askmygift.giftdairy.dao.entity.Address;
import com.askmygift.giftdairy.dao.entity.Preferences;
import com.askmygift.giftdairy.dao.entity.User;
import com.google.gson.Gson;
import com.turningcloud.askmygift.R;
import com.turningcloud.login.RegisterActivity;

public class MyPreferences extends Fragment {  

	public static String url="http://4-dot-askmygiftweb.appspot.com/rest/UserWebservice/UserServices/";
	 SharedPreferences shared;
	 String userid,addressId,preferenceId;
	 EditText shoe,casual,dressShirt,pant,jacket,dresssizes,hat,like,dislike,add1,add2,add3,add4;
	 Button preferenceSave;
	 JSONObject jsonObject,jsonObject2,jsonObject3;
	 SharedPreferences.Editor editor;
	   public MyPreferences() {  
	   }  

	   @Override  
	   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
	       View rootView = inflater.inflate(R.layout.preference, container, false);  
	       
	       shared = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
	       
	       userid=shared.getString("Userid", "");
	       addressId=shared.getString("Address", "");
	       preferenceId=shared.getString("Preference", "");
	      
	       shoe=(EditText) rootView.findViewById(R.id.shoeSizes);
	       casual=(EditText) rootView.findViewById(R.id.casualSizes);
	       dressShirt=(EditText) rootView.findViewById(R.id.dressShirtSizes);
	       pant=(EditText) rootView.findViewById(R.id.pantSizes);
	       jacket=(EditText) rootView.findViewById(R.id.jacketSizes);
	       dresssizes=(EditText) rootView.findViewById(R.id.dressSizes);
	       hat=(EditText) rootView.findViewById(R.id.hatSizes);
	       like=(EditText) rootView.findViewById(R.id.likes);
	       dislike=(EditText) rootView.findViewById(R.id.dislikes);
	       add1=(EditText) rootView.findViewById(R.id.ayoc1);
	       add2=(EditText) rootView.findViewById(R.id.ayoc2);
	       add3=(EditText) rootView.findViewById(R.id.ayoc3);
	       add4=(EditText) rootView.findViewById(R.id.ayoc4);
	       preferenceSave=(Button) rootView.findViewById(R.id.prefSave);
	       
	       
	       shoe.setText(shared.getString("shoe", ""));
  	       casual.setText(shared.getString("casual", ""));
  	       dressShirt.setText(shared.getString("dressShirt", ""));
  	       pant.setText(shared.getString("pant", ""));
  	       jacket.setText(shared.getString("jacket", ""));
  	       dresssizes.setText(shared.getString("dresssizes", ""));
  	       hat.setText(shared.getString("hat", ""));
  	       like.setText(shared.getString("like", ""));
  	       dislike.setText(shared.getString("dislike", ""));
  	       add1.setText(shared.getString("add1", ""));
  	       add2.setText(shared.getString("add2", ""));
  	       add3.setText(shared.getString("add3", ""));
  	       add4.setText(shared.getString("add4", ""));
	       
	       preferenceSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
	        	
				
				if(isConnected()){
					
					
						
					new UpdateAsyntask().execute(url);
					
					}
					 else
				        {
				            Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT).show();
				        }
				
				
				
				
			}

			private   boolean isConnected() {
				
				
				
				
				
    		ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) 
                return true;
            else
                return false;  
	}
		});
	    		   
	       
	       return rootView;  
	   }  

	   
	   private class UpdateAsyntask extends AsyncTask<String, Void, String>{

	    	
	    	Context context;
	    	private ProgressDialog dialog = new ProgressDialog(getActivity());
	    	@Override
	    	protected void onPreExecute() {

	    		this.dialog.setMessage("Please wait....");
	            this.dialog.show();
	            
	            editor=shared.edit();
				editor.putString("shoe", shoe.getText().toString());
				editor.putString("casual", casual.getText().toString());
				editor.putString("dressShirt", dressShirt.getText().toString());
				editor.putString("pant", pant.getText().toString());
				editor.putString("jacket", jacket.getText().toString());
				editor.putString("dresssizes", dresssizes.getText().toString());
				editor.putString("hat", hat.getText().toString());
				editor.putString("like", like.getText().toString());
				editor.putString("dislike", dislike.getText().toString());
				editor.putString("add1", add1.getText().toString());
				editor.putString("add2", add2.getText().toString());
				editor.putString("add3", add3.getText().toString());
				editor.putString("add4",add4.getText().toString());
					editor.commit();
	            
	    	}

	    	
			@Override
	    	protected String doInBackground(String... urls) {
	    		
	    		User user=new User();
	    		
	       		user.setMethodName(String.valueOf(QueryMetadata.userWebServiceType.UPDATE_USER_ACCOUNT));
	       		
	       			Gson gson = new Gson();
	       			
	       	 String userData = gson.toJson(user);
			try 
			{
				jsonObject = new JSONObject(userData);
				
			} catch (JSONException e) {
				
				e.printStackTrace();
			}

			Address address=new Address();
			
			 String addressData = gson.toJson(address);
		
			
			 
				 try {
					 
					jsonObject2 = new JSONObject(addressData);
					
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
			//	 EditText shoe,casual,dressShirt,pant,jacket,dresssizes,hat,like,dislike,add1,add2,add3,add4;	 
				 Preferences preferences=new Preferences();
				preferences.setPrefId(preferenceId);
				preferences.setShoeSize(shoe.getText().toString());
				preferences.setCasualShirtSize(casual.getText().toString());
				preferences.setDressShirtSize(dressShirt.getText().toString());
				preferences.setWaistSize(pant.getText().toString());
				preferences.setJacketSize(jacket.getText().toString());
				preferences.setDressSize(dresssizes.getText().toString());
				preferences.setHatSize(hat.getText().toString());
				preferences.setLikes(like.getText().toString());
				preferences.setDislikes(dislike.getText().toString());
				preferences.setChoicea(add1.getText().toString());
				preferences.setChoiceb(add2.getText().toString());
				preferences.setChoicec(add3.getText().toString());
				preferences.setChoiced(add4.getText().toString());
				
					
					 String preferenceData = gson.toJson(preferences);
				
					
					 
						 try {
							 
							jsonObject3 = new JSONObject(preferenceData);
							
						} catch (JSONException e) {
							
							e.printStackTrace();
						}
				
		
			 JSONArray arr = new JSONArray();

	         arr.put(jsonObject);
	         arr.put(jsonObject2);
	         arr.put(jsonObject3);
	         return POST(urls[0],arr);
	    		
	    	}
	    	
	    	

			private String POST(String string, JSONArray arr)  {
				InputStream inputStream = null;
		        String result = "";
		        try {
		 
		            // 1. create HttpClient
		            HttpClient httpclient = new DefaultHttpClient();
		 
		            // 2. make POST request to the given URL
		            HttpPost httpPost = new HttpPost(url);
		 
		            String json = "";
		 
		          
		            json = arr.toString();
		 
		          
		            StringEntity se = new StringEntity(json);
		 
		           
		            httpPost.setEntity(se);
		 
		          
		            httpPost.setHeader("Accept", "application/json");
		            httpPost.setHeader("Content-type", "application/json");
		 
		           
		            HttpResponse httpResponse = httpclient.execute(httpPost);
		 
		         
		            inputStream = httpResponse.getEntity().getContent();
		 
		            // 10. convert inputstream to string
		            if(inputStream != null){
		                result = convertInputStreamToString(inputStream);
		                
		                Log.d("Resposnes cskkk", result);
		               
		            }
		            
		            else
		                result = "Did not work!";
		            
		          
		        } catch (Exception e) {
		            Log.d("InputStream", e.getLocalizedMessage());
		        }
		        
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
				
				Toast.makeText(getActivity(), "Preferences has been successfully updated", Toast.LENGTH_SHORT).show();	
				
				if (dialog.isShowing()) {
					
					
				
				
					
		            dialog.dismiss();
		        }
	    	
	    	}

	    
		}
		
	   
	   
		
	  
	}  
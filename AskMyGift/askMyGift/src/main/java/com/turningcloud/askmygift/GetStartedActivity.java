package com.turningcloud.askmygift;

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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.askmygift.giftdairy.constant.QueryMetadata;
import com.askmygift.giftdairy.dao.entity.User;
import com.google.gson.Gson;
import com.turningcloud.events.GenericEventsActivity;

public class GetStartedActivity extends Activity {

	Button getstarted;
	Intent getStartedLogin;
	JSONObject jsonObject;
	SharedPreferences shared;
	 String userId;
	 public  static String url="http://4-dot-askmygiftweb.appspot.com/rest/UserWebservice/UserServices/";
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			 requestWindowFeature(Window.FEATURE_NO_TITLE);
			 
			setContentView(R.layout.activity_get_started);
			
			
			getstarted=(Button) findViewById(R.id.getstartedbutton);
			
			GetStartedPageAdapter adapter = new GetStartedPageAdapter();
		       
	        ViewPager risk = (ViewPager) findViewById(R.id.viewpager);
	        risk.setAdapter(adapter);
	        risk.setCurrentItem(0);
	       CirclePageIndicator cri=
	   	        (CirclePageIndicator)findViewById( R.id.indicator);
	    cri.setViewPager(risk);
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.get_started, menu);
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

		
		public void onClick(View v) {
			getStartedLogin=new Intent(GetStartedActivity.this, GenericEventsActivity.class);

			if(isConnected()){
				 new MobileCodeAsyntask().execute(url); 
				 
			startActivity(getStartedLogin);
			}
			 else
		        {
		            Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
		        }
			
		}

		private   boolean isConnected() {
    		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) 
                return true;
            else
                return false;  
	}
		private class MobileCodeAsyntask extends AsyncTask<String, Void, String>{
			
			Context context;
			private ProgressDialog dialog = new ProgressDialog(GetStartedActivity.this);
			@Override
			protected void onPreExecute() {

				this.dialog.setMessage("Please wait");
		        this.dialog.show();
			}
			
			@Override
			protected String doInBackground(String... urls) {
				
				User user=new User();
				user.setUserId(userId);
				user.setMethodName(String.valueOf(QueryMetadata.userWebServiceType.GET_FIRST_TIME_DATA));
			
				Gson gson = new Gson();
					
		      	 String userData = gson.toJson(user);
				
					try {
						jsonObject = new JSONObject(userData);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				
				 JSONArray arr = new JSONArray();

		         arr.put(jsonObject);
		         
			
		return POST(urls[0],arr);
			
				
			}
			
			private String POST(String string, JSONArray arr)  {
				InputStream inputStream = null;
		        String result = "";
		        try {
		 
		          
		            HttpClient httpclient = new DefaultHttpClient();
		 
		            // 2. make POST request to the given URL
		            HttpPost httpPost = new HttpPost(url);
		 
		            String json = "";
		 
		            
		            json = arr.toString();
		 
		            
		            StringEntity se = new StringEntity(json);
		 
		            // 6. set httpPost Entity
		            httpPost.setEntity(se);
		 
		            // 7. Set some headers to inform server about the type of the content   
		            httpPost.setHeader("Accept", "application/json");
		            httpPost.setHeader("Content-type", "application/json");
		 
		            // 8. Execute POST request to the given URL
		            HttpResponse httpResponse = httpclient.execute(httpPost);
		 
		            // 9. receive response as inputStream
		            inputStream = httpResponse.getEntity().getContent();
		 
		         
		            if(inputStream != null){
		                result = convertInputStreamToString(inputStream);
		                
		            
		            }
		            
		            else
		                result = "Did not work!";
		            
		          
		        } catch (Exception e) {
		           e.printStackTrace();
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
				
				if (dialog.isShowing()) {
				
		            dialog.dismiss();
		        }
			
			}

			

		}

	}

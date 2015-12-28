package com.turningcloud.events;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.askmygift.giftdairy.constant.QueryMetadata;
import com.askmygift.giftdairy.dao.entity.User;
import com.google.gson.Gson;

import com.turningcloud.askmygift.R;




public class GenericEventsActivity extends Activity implements OnClickListener{
	
	SharedPreferences shared;
	JSONObject jsonObject;
	public static  String url="http://4-dot-askmygiftweb.appspot.com/rest/UserWebservice/UserServices/";
	
	String[] genEvent = new String[] {
            
	};
	Button genbutton;
	ListView listView;
	ArrayAdapter< String> adapter;
	String getSharedgeneric,userId,generic;
	

	ArrayList<String> eventListPopulator = new ArrayList<String>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_generic_events);

       shared = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId=shared.getString("Userid", "");
      
        Button addButton = (Button) findViewById(R.id.addButton);
		final EditText eventName = (EditText)findViewById(R.id.eventName);

		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = eventName.getText().toString();

			}
		});
		Button getData = (Button) findViewById(R.id.getData);


        listView = ( ListView ) findViewById(R.id.listview);
        genbutton=(Button) findViewById(R.id.genButton);
		getData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		Button deleteEvent = (Button) findViewById(R.id.deleteEvent);
		deleteEvent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = eventName.getText().toString();
				int id = Integer.parseInt(name);

			}
		});
        
        if(isConnected()){
			// new MobileCodeAsyntask().execute(url);
			 
		
		}

		 else
	        {
	            Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
	        }
        
       genbutton.setOnClickListener(this);
    
        
    }
   

	private   boolean isConnected() {
    		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) 
                return true;
            else
                return false;  
	}


	@Override
	public void onClick(View v) {
		SparseBooleanArray checked = listView.getCheckedItemPositions();
		ArrayList<String> selectedItems = new ArrayList<String>();
		for (int i = 0; i < checked.size(); i++) {
			// Item position in adapter
			int position = checked.keyAt(i);
			// Add sport if it is checked i.e.) == TRUE!
			if (checked.valueAt(i))selectedItems.add(adapter.getItem(position));
		}

		String[] genricStrArr = new String[selectedItems.size()];

		for (int i = 0; i < selectedItems.size(); i++) {
			genricStrArr[i] = selectedItems.get(i);
		}

		Intent intent = new Intent(GenericEventsActivity.this,PersonalEventsActivity.class);

		
		Bundle b= new Bundle();
		b.putStringArray("GenericItems", genricStrArr);

	
		intent.putExtras(b);

	
		startActivity(intent);
	}
	 
	
	
	private class MobileCodeAsyntask extends AsyncTask<String, Void, String>{
		
		Context context;
		private ProgressDialog dialog = new ProgressDialog(GenericEventsActivity.this);
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
					// TODO Auto-generated catch block
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
	                
	               JSONArray object;
	                object = new JSONArray(result);
	              
	                generic= (String) object.get(0);
	               
	               
	            
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
			
			genEvent=generic.split(",");
			
			 adapter = new ArrayAdapter<String>(GenericEventsActivity.this, android.R.layout.simple_list_item_multiple_choice, genEvent);
		       
		       listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		       listView.setAdapter(adapter);
			
			if (dialog.isShowing()) {
			
	            dialog.dismiss();
	        }
		
		}

		

	}

	
}
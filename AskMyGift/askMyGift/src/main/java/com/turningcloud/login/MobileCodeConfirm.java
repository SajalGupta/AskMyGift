package com.turningcloud.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

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
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.askmygift.giftdairy.constant.QueryMetadata;
import com.askmygift.giftdairy.dao.entity.User;
import com.google.gson.Gson;
import com.turningcloud.askmygift.GetStartedActivity;
import com.turningcloud.askmygift.R;
import com.turningcloud.events.GenericEventsActivity;

public class MobileCodeConfirm extends Activity {
	int count=0;
	boolean check=false;
	 TextView msgalert;
	 EditText codeText;
	 private CountDownTimer countDownTimer;
	 private boolean timerStarted = false;
	 Button submitButton;
	 JSONObject jsonObject;
	 public static String url="http://4-dot-askmygiftweb.appspot.com/rest/UserWebservice/UserServices/";
	 String generic;
	 String personal;
	 String gifts;
	 String verCode,userId;
	 SharedPreferences shared;
	    public TextView textView;
	    private final long startTime = 300 * 1000;
	    private final long interval = 1 * 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mobile_code_confirm);
        
        shared = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId=shared.getString("Userid", "");
        
                verCode=getIntent().getStringExtra("VerCode");
        
        Toast.makeText(getApplicationContext(), "Verification"+verCode, Toast.LENGTH_LONG).show();

        textView = (TextView) this.findViewById(R.id.textView);
        countDownTimer = new CountDownTimerActivity(startTime, interval);
        textView.setText(textView.getText() + String.valueOf(startTime/1000));
        if (!timerStarted) {
            countDownTimer.start();
            timerStarted = true;
        }
      codeText = (EditText) findViewById(R.id.mobileCode);
        codeText.addTextChangedListener(textWatcher);
        msgalert=(TextView) findViewById(R.id.msg);
        submitButton = (Button) findViewById(R.id.submitCode);
        submitButton.setEnabled(false);
        
    }
    
    public void confirmCodeOpenRegister(View view) {
        
    	if(check)
    	{
    		
        Intent intent = new Intent(MobileCodeConfirm.this, GenericEventsActivity.class);
        if(isConnected()){
       
        new UseridAsyntask().execute(url);
     
        startActivity(intent);
       // finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }
    	
    	else if(count==3)
    	{
    		
    		Intent i=new Intent(this, MobileCodeFailedActivity.class);
    		startActivity(i);
    		
    	}
    	else
    	{
    		check=false;
    		Toast.makeText(getApplicationContext(), "Please enter correct verification code", Toast.LENGTH_LONG).show();
    		codeText.setText("");
    		submitButton.setEnabled(false);
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


	private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkRequiredFields();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private  void checkRequiredFields() {
    	
         submitButton = (Button) findViewById(R.id.submitCode);
         codeText = (EditText) findViewById(R.id.mobileCode);
       
        if(codeText.getText().toString().equals(verCode)) {
        	check=true;
            submitButton.setEnabled(true);
            
        }
        else if(codeText.length()!=6 || codeText.equals(""))
        	{
        		submitButton.setEnabled(false);
            }
        
        	else
        	{
        		check=false;
        		count++;   
        		submitButton.setEnabled(true);
        		 
        	}
        }
        	
    public class CountDownTimerActivity extends CountDownTimer {
        public CountDownTimerActivity(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            textView.setText("Time's up!");
            Intent i=new Intent(MobileCodeConfirm.this, MobileCodeFailedActivity.class);
            startActivity(i);
        }

        @Override
        public void onTick(long millisUntilFinished) {
        	textView.setText(""+String.format("%d : %02d ",  TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -  TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
        	
        }
    }
    @Override
    public void onBackPressed() {
    	countDownTimer.cancel();
    	super.onBackPressed();
    }
@Override
protected void onPause() {
	count=0;
	countDownTimer.cancel();
    timerStarted = false;
    codeText.setText("");
	super.onPause();
}
@Override
protected void onStop() {
	countDownTimer.cancel();
    timerStarted = false;
	super.onStop();
}

private class UseridAsyntask extends AsyncTask<String, Void, String>{
	
	Context context;
	private ProgressDialog dialog = new ProgressDialog(MobileCodeConfirm.this);
	@Override
	protected void onPreExecute() {

		this.dialog.setMessage("Please wait");
        this.dialog.show();
	}
	
	@Override
	protected String doInBackground(String... urls) {
		
		User user=new User();
		user.setUserId(userId);
		user.setMethodName(String.valueOf(QueryMetadata.userWebServiceType.CONFIRM_USER_ACCOUNT));
	
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

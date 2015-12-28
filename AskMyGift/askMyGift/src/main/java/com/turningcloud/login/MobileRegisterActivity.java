package com.turningcloud.login;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.turningcloud.askmygift.R;

public class MobileRegisterActivity extends Activity implements AdapterView.OnItemSelectedListener {
	 EditText mobileText;
	 String stringExtra,number;
	 Button submitButton; 
	 String MobilePattern = "[0-9]{10}";
	 
	
  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.activity_mobile_register);

       mobileText = (EditText) findViewById(R.id.mobileNumber);
       number=mobileText.getText().toString();
      
     submitButton = (Button) findViewById(R.id.submitMobile);
      
      // Spinner element
      Spinner spinner = (Spinner) findViewById(R.id.countrySpinner);

      // Spinner click listener
      spinner.setOnItemSelectedListener(this);

      // Spinner Drop down elements
      List<String> categories = Arrays.asList(getResources().getStringArray(R.array.country_arrays));

      // Creating adapter for spinner
      ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

      // Drop down layout style - list view with radio button
      dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

      // attaching data adapter to spinner
      spinner.setAdapter(dataAdapter);
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      // On selecting a spinner item
      String item = parent.getItemAtPosition(position).toString();
      int pos = parent.getSelectedItemPosition();

      List<String> categories = Arrays.asList(getResources().getStringArray(R.array.country_codes));
      String code = categories.get(pos);
      EditText countryCode = (EditText) findViewById(R.id.countryCode);
      countryCode.setText(code);

      // Showing selected spinner item
   //   Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

  }

  @Override
  public void onNothingSelected(AdapterView<?> arg0) {
      // TODO Auto-generated method stub

  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      int id = item.getItemId();

      //noinspection SimplifiableIfStatement
      if (id == R.id.action_settings) {
          return true;
      }

      return super.onOptionsItemSelected(item);
  }

  public boolean sendSmsVerifyCode(View view)  {
	  
	  if(mobileText.getText().toString().length()==0){
		  Toast.makeText(getApplicationContext(), "Please enter your phone number", Toast.LENGTH_SHORT).show();
		  return false;
	  }
	  
	    if(!mobileText.getText().toString().matches(MobilePattern))
	    {
	        Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();


	        return false;
	    }

	  else
	  {
		  
      stringExtra = mobileText.getText().toString();

      Intent intent = new Intent(this, MobileCodeConfirm.class);
      
      //Create a bundle object
      intent.putExtra("string", stringExtra);
      //start the DisplayActivity
      startActivity(intent);
      return true;
	  }
	
  }
}
 

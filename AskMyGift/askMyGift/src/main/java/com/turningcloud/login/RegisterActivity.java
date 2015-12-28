package com.turningcloud.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.askmygift.giftdairy.constant.QueryMetadata;
import com.askmygift.giftdairy.dao.entity.Device;
import com.askmygift.giftdairy.dao.entity.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.pushbots.push.Pushbots;
import com.turningcloud.Dashboard.NewDashBoardActivity;
import com.turningcloud.askmygift.GetStartedActivity;
import com.turningcloud.askmygift.R;
import com.turningcloud.askmygift.TutorialActivity;
import com.turningcloud.drawer.GenericEvents;
import com.turningcloud.drawer.MainActivity;

public class RegisterActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener {

    EditText mEdit, userName, dob, email, mobileNo;
    Button registerButton;
    String mobilePattern = "[0-9]{10}", facebookId, gender, imageUrl, name, getEmail, googleID, country_id, code, android_id, imei_no, macAddress;
    String resp_code = null, addr = null, userid = null, pref = null, verficationcode = null, secureCode = null, number, countrySortId, CountryID = "";
    ;
    private RadioGroup radioSexGroup;
    private RadioButton radioButton, radioMale, radioFemale;
    public static String url = "http://4-dot-askmygiftweb.appspot.com/rest/UserWebservice/UserServices/";
    User user = null;
    int codePosition;
    Intent intent;
    TelephonyManager telephonyManager;
    Device device = null;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String CODE = "MyPrefs";
    SharedPreferences sharedpreferences, shared;
    SharedPreferences.Editor editor, editor2;
    List<String> list5;
    List<String> categories1;
    CallbackManager callbackManager;
    LoginButton loginButton;

    JSONObject jsonObject1, jsonObject, response = null;
    HttpResponse httpResponse;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        android_id = Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID);
        imei_no = telephonyManager.getDeviceId();
        number = telephonyManager.getLine1Number();
        countrySortId = telephonyManager.getSimCountryIso().toUpperCase();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        //Check if user is already logged in.
        AccessToken mAccessToken = AccessToken.getCurrentAccessToken();
        //Log.i("AccessToken",mAccessToken.getToken());
        if (mAccessToken != null) {
            Intent i = new Intent(this, NewDashBoardActivity.class);
            startActivity(i);
            Pushbots.sharedInstance().setAlias(Profile.getCurrentProfile().getId());
            Toast.makeText(getApplicationContext(), "Already Logged in :" + Profile.getCurrentProfile().getName(), Toast.LENGTH_SHORT).show();
            finish();
        }
        //Reference the login button
        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setBackgroundResource(R.drawable.facebook);
       // loginButton.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.com_facebook_button_icon), getDrawable(R.drawable.com_facebook_button_icon), getDrawable(R.drawable.com_facebook_button_icon));
        loginButton.setPadding(10, 20, 20, 20);
        ImageView facebookIcon = (ImageView) findViewById(R.id.facebook_icon);
        facebookIcon.bringToFront();


        //Set permissions
        loginButton.setReadPermissions("user_friends", "user_likes", "email", "user_birthday");


        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), "Issue" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("LoginError", exception.getMessage());
            }
        });
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                // App code
               /* loginButton.setVisibility(View.INVISIBLE);
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                    Toast.makeText(getApplicationContext(), profile.getName(), Toast.LENGTH_SHORT).show();
                    userName = (EditText) findViewById(R.id.fullName);
                    userName.setText(profile.getName());
                    // make the API call
                    new GraphRequest(
                            AccessToken.getCurrentAccessToken(),
                            "/" + accessToken.getUserId(),
                            null,
                            HttpMethod.GET,
                            new GraphRequest.Callback() {
                                public void onCompleted(GraphResponse response) {
            // handle the result
                                    Log.i("UserDetails", response.toString());
                                    try {
                                        Log.i("crap", response.getJSONObject().getString("email"));
                                        email = (EditText) findViewById(R.id.emailAddress);
                                        email.setText(response.getJSONObject().getString("email"));
                                        mEdit = (EditText) findViewById(R.id.dateOfBirth);
                                        mEdit.setText(response.getJSONObject().getString("birthday"));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }
                    ).executeAsync();


                } */

                Intent i = new Intent(RegisterActivity.this, NewDashBoardActivity.class);

                startActivity(i);
                finish();
            }

            @Override
            public void onCancel() {
                Toast.makeText(RegisterActivity.this, "Login Manager Cancelled", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(RegisterActivity.this, "Login Manager Error", Toast.LENGTH_SHORT).show();

            }
        });

       /* String[] rl = this.getResources().getStringArray(R.array.country_sortId);
        for (int i = 0; i < rl.length; i++) {

            if (rl[i].equalsIgnoreCase(countrySortId)) {
                codePosition = i;
            }

        }

        getSecureCode();

        if (getIntent() != null) {

            facebookId = getIntent().getStringExtra("ID");
            googleID = getIntent().getStringExtra("GoogleID");
            imageUrl = getIntent().getStringExtra("GProfilePic");
            imageUrl = getIntent().getStringExtra("FbimageUrl");
            getEmail = getIntent().getStringExtra("EMAIL");
            name = getIntent().getStringExtra("FullName");
            gender = getIntent().getStringExtra("GENDER");
        }

      //  mobileNo = (EditText) findViewById(R.id.MobileNo);

        if (number != null) {
            mobileNo.setText(number);
        }


      //  userName = (EditText) findViewById(R.id.fullName);
        userName.setText(name);


        dob = (EditText) findViewById(R.id.dateOfBirth);
        dob.setInputType(InputType.TYPE_NULL);
        dob.requestFocus();

       // email = (EditText) findViewById(R.id.emailAddress);
        email.setText(getEmail);

        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        radioMale = (RadioButton) findViewById(R.id.radioMale);
        radioFemale = (RadioButton) findViewById(R.id.radioFemale);

        if (gender != null) {
            if (gender.equalsIgnoreCase("male")) {
                radioMale.setChecked(true);
                radioFemale.setChecked(false);
            } else {
                radioFemale.setChecked(true);
                radioMale.setChecked(false);
            }
        }

        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);


       // registerButton = (Button) findViewById(R.id.registerButton);

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

        spinner.setSelection(codePosition);

        categories1 = Arrays.asList(getResources().getStringArray(R.array.country_codes));

        code = categories1.get(codePosition);


        list5 = Arrays.asList(getResources().getStringArray(R.array.country_id));

        country_id = list5.get(codePosition);


        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        macAddress = wInfo.getMacAddress(); */

    }

    public void getSecureCode() {

        Random rnd = new Random();

        int code = 100000 + rnd.nextInt(900000);

        secureCode = Integer.toString(code);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        String item = parent.getItemAtPosition(position).toString();
        int pos = parent.getSelectedItemPosition();

        code = categories1.get(pos);

        country_id = list5.get(pos);


        EditText countryCode = (EditText) findViewById(R.id.countryCode);

        countryCode.setText("+" + code);


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openDairyActivity(View view) {

        if (validate()) {
            sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();
            editor.putString("FullName", userName.getText().toString());
            editor.putString("EMAIL", email.getText().toString());
            editor.putString("DOB", dob.getText().toString());
            editor.putString("GENDER", radioButton.getText().toString());
            editor.commit();
            intent = new Intent(RegisterActivity.this, MobileCodeConfirm.class);
            intent.putExtra("VerCode", secureCode);
            startActivity(intent);
           /* if (LoginActivityFacebookGoogle.flag) {
                intent = new Intent(RegisterActivity.this, GenericEvents.class);
            } else {
                intent = new Intent(RegisterActivity.this, MobileCodeConfirm.class);
                intent.putExtra("VerCode", secureCode);


            }
            if (isConnected()) {
                new RegisterAsyntask().execute(url);

                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            } */
        }


    }

    private boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private boolean validate() {
        if (userName.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dob.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Please click to enter your date of birth", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mobileNo.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Please enter your mobile number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!mobileNo.getText().toString().matches(mobilePattern)) {
            Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.getText().toString().matches("")) {
            return true;
        }

        if (!email.getText().toString().matches("")) {
            if (EMAIL_PATTERN.matcher(email.getText().toString()).matches()) {
                return true;
            } else {

                Toast.makeText(getApplicationContext(), "Please enter your correct email id", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            return true;
        }

    }


    public void selectDate(View view) {

        DialogFragment newFragment = new SelectDateFragment();
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    public void populateSetDate(int year, int month, int day) {
        mEdit = (EditText) findViewById(R.id.dateOfBirth);

        Date currentDate = Calendar.getInstance().getTime();
        currentDate.setYear(year - 1900);
        currentDate.setMonth(month - 1);
        currentDate.setDate(day);
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("MMM dd, yyyy");
        String formattedCurrentDate = simpleDateFormat.format(currentDate);

        mEdit.setText(formattedCurrentDate);
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
            populateSetDate(yy, mm + 1, dd);
        }
    }


    private class RegisterAsyntask extends AsyncTask<String, Void, String> {

        Context context;
        private ProgressDialog dialog = new ProgressDialog(RegisterActivity.this);

        @Override
        protected void onPreExecute() {

            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }


        @Override
        protected String doInBackground(String... urls) {

            user = new User();
            user.setFullName(userName.getText().toString());
            user.setDateOfBirth(dob.getText().toString());
            user.setEmailId(email.getText().toString());
            user.setMobileNumber(mobileNo.getText().toString());
            user.setGender(radioButton.getText().toString());
            user.setImageUrl(imageUrl);
            user.setFacebookId(facebookId);
            user.setGooglelId(googleID);
            user.setMethodName(String.valueOf(QueryMetadata.userWebServiceType.CREATE_USER_ACCOUNT));

            Gson gson = new Gson();

            String userData = gson.toJson(user);
            try {
                jsonObject = new JSONObject(userData);

            } catch (JSONException e) {

                e.printStackTrace();
            }

            device = new Device();
            device.setDeviceCountryId(country_id);
            device.setDeviceCountryCode(code);
            device.setDeviceImeiId(imei_no);
            device.setDeviceMacId(macAddress);
            device.setDeviceNumber(android_id);
            device.setDeviceId(secureCode);

            String deviceData = gson.toJson(device);


            try {

                jsonObject1 = new JSONObject(deviceData);

            } catch (JSONException e) {

                e.printStackTrace();
            }

            JSONArray arr = new JSONArray();

            arr.put(jsonObject);

            arr.put(jsonObject1);

            return POST(urls[0], arr);

        }


        private String POST(String string, JSONArray arr) {
            InputStream inputStream = null;
            String result = "";
            try {

                // 1. create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // 2. make POST request to the given URL
                HttpPost httpPost = new HttpPost(url);

                String json = "";

                // 3. build jsonObject

                // jsonObject.accumulate("name", user.getFullName());
                //   JSONArray arr1 = new JSONArray();

	           /* arr.put(jsonObject);

	            arr.put(jsonObject1);*/

                // 4. convert JSONObject to JSON to String
                json = arr.toString();

                // ** Alternative way to convert Person object to JSON string usin Jackson Lib
                // ObjectMapper mapper = new ObjectMapper();
                // json = mapper.writeValueAsString(person);

                // 5. set json to StringEntity
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

                // 10. convert inputstream to string
                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);

                    Log.d("Resposnes cskkk", result);


                } else
                    result = "Did not work!";


            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            return result;
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }


        @Override
        protected void onPostExecute(String result) {


            try {
                response = new JSONObject(result);
                resp_code = response.get("resp_code").toString();
                addr = response.get("addr").toString();
                userid = response.get("user").toString();
                pref = response.get("pref").toString();
                verficationcode = response.get("code").toString();

                shared = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                editor2 = shared.edit();
                editor2.putString("Verification", verficationcode);
                editor2.putString("Preference", pref);
                editor2.putString("Userid", userid);
                editor2.putString("Address", addr);
                editor2.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (dialog.isShowing()) {

                dialog.dismiss();
            }

        }

    }


}

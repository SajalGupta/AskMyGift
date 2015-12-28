package com.turningcloud.login;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

/*import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;

import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton; */
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.turningcloud.askmygift.R;

public class LoginActivityFacebookGoogle extends Activity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
   // private LoginButton loginBtn;
    private static final List<String> PERMISSIONS = new ArrayList<String>() {
        {
            add("user_friends");
            add("public_profile");
            add("email");
            add("user_birthday");

        }
    };
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    //private UiLifecycleHelper uiHelper;
    URL image_value = null;
    Bitmap profPict;

    public static boolean flag = false;

    private static String message = "Sample status posted from android app", imageUrl;

    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "MainActivity";

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    private boolean mIntentInProgress;

    private boolean mSignInClicked;

    private ConnectionResult mConnectionResult;

    private SignInButton btnSignIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
      //  uiHelper = new UiLifecycleHelper(this, statusCallback);
       // uiHelper.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_activity_facebook_google);

        //loginBtn = (LoginButton) findViewById(R.id.fb_login_button);

       /* loginBtn.setReadPermissions(PERMISSIONS);
        loginBtn.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                if (user != null) {
                    Log.d("FacebookSampleActivity", user.toString() + "genfder" + user.asMap().get("gender").toString());
                    Intent intent = new Intent(LoginActivityFacebookGoogle.this, RegisterActivity.class);
                    intent.putExtra("FullName", user.getFirstName() + " " + user.getLastName());
                    intent.putExtra("ID", user.getId());
                    intent.putExtra("EMAIL", user.asMap().get("email").toString());
                    intent.putExtra("ORIGIN", "FACEBOOK");
                    intent.putExtra("GENDER", user.asMap().get("gender").toString());

                    try {
                        image_value = new URL("https://graph.facebook.com/" + user.getId() + "/picture?type=large");
                        imageUrl = image_value.toString();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("FbimageUrl", imageUrl);


                    // shared preference
                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    editor = sharedpreferences.edit();
                    //editor.putString("FullName",user.getFirstName()+" "+user.getLastName());
                    //editor.putString("EMAIL",user.asMap().get("email").toString());
                    //editor.putString("FbID", user.getId());
                    //editor.putString("FbimageUrl", imageUrl);
                    editor.commit();

                    //ending shared prefrences
                    flag = true;
                    startActivity(intent);
                    finish();
                } else {
                    //userName.setText("You are not logged");
                }
            }


        }); */

        btnSignIn = (SignInButton) findViewById(R.id.gplus_login_button);
        btnSignIn.setSize(SignInButton.SIZE_WIDE);

// Button click listeners
        btnSignIn.setOnClickListener(this);

// Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }

    /*private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            if (state.isOpened()) {
                Log.d("FacebookSampleActivity", "Facebook session opened");
                Request.executeMyFriendsRequestAsync(session,
                        new Request.GraphUserListCallback() {
                            @Override
                            public void onCompleted(List<GraphUser> users,
                                                    Response response) {
                                Log.i("Response JSON", response.toString());
                                String[] names = new String[users.size()];
                                String[] id = new String[users.size()];
                                for (int i = 0; i < users.size(); i++) {
                                    names[i] = users.get(i).getName();
                                    id[i] = users.get(i).getId();
                                    Log.i("Friend " + i, names[i] + "--" + id[i]);
                                }
                            }
                        });
            } else if (state.isClosed()) {
                Log.d("FacebookSampleActivity", "Facebook session closed");
            }
        }
    }; */

    @Override
    public void onResume() {
        super.onResume();
        //uiHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
     //   uiHelper.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        uiHelper.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
  //      uiHelper.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
//        uiHelper.onSaveInstanceState(savedState);
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Button on click listener
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gplus_login_button:
                // Signin button clicked
                signInWithGplus();
                break;

        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }

    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

// Get user's information
        getProfileInformation();

// Update the UI after signin
        updateUI(true);

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        updateUI(false);
    }

    /**
     * Updating the UI, showing/hiding buttons and profile layout
     */
    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE);
    /*btnSignOut.setVisibility(View.VISIBLE);
    btnRevokeAccess.setVisibility(View.VISIBLE);
    llProfileLayout.setVisibility(View.VISIBLE);*/
        } else {
            btnSignIn.setVisibility(View.VISIBLE);
    /*btnSignOut.setVisibility(View.GONE);
    btnRevokeAccess.setVisibility(View.GONE);
    llProfileLayout.setVisibility(View.GONE);*/
        }
    }

    /**
     * Sign-in into google
     */
    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    /**
     * Method to resolve any signin errors
     */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    /**
     * Fetching user's information name, email, profile pic
     */
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String googleId = currentPerson.getId();
                String personGooglePlusProfile = currentPerson.getUrl();
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);


                Log.e(TAG, "Name: " + personName + ", plusProfile: " + personGooglePlusProfile + ", email: " + email + ", Image: " + personPhotoUrl);

                Intent intent = new Intent(LoginActivityFacebookGoogle.this, RegisterActivity.class);
                intent.putExtra("FullName", personName);
                intent.putExtra("EMAIL", email);
                intent.putExtra("GoogleID", googleId);
                intent.putExtra("ORIGIN", "GOOGLE");


                //Shared preference
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                editor = sharedpreferences.edit();
                //  	 editor.putString("FullName",personName);
                //	 editor.putString("EMAIL",email);
                editor.putString("GProfilePic", personPhotoUrl);
                editor.putString("GoogID", googleId);
                editor.commit();


                flag = true;

                startActivity(intent);


                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;

                finish();


            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Background Async task to load user profile picture from url
     */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    /**
     * Sign-out from google
     */
    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            updateUI(false);
        }
    }

    /**
     * Revoking access from google
     */
    private void revokeGplusAccess() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.e(TAG, "User access revoked!");
                            mGoogleApiClient.connect();
                            updateUI(false);
                        }

                    });
        }
    }

    public void openMobileRegisterActivity(View view) {

        Intent intent = new Intent(LoginActivityFacebookGoogle.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }


}	
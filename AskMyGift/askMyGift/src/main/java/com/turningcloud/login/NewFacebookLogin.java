package com.turningcloud.login;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.turningcloud.Contacts.contactStore;
import com.turningcloud.askmygift.R;
import com.turningcloud.drawer.MainActivity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;


public class NewFacebookLogin extends ActionBarActivity implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    CallbackManager callbackManager;
    private ArrayList<contactStore> mContactStore = new ArrayList<contactStore>();
    private ArrayList<contactStore> tempContactStore = new ArrayList<contactStore>();
    private contactStore tempContact = new contactStore();
    Button fbInviteButton;
    Button contactsButton,publishButton,btnUserLikes;
    // Google+ SignIn Requirements

    private static final int RC_SIGN_IN = 0;
    private static final int PROFILE_PIC_SIZE = 400;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private SignInButton btnSignIn;
    private Button btnSignOut, btnRevokeAccess;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail;
    private ConnectionResult mConnectionResult;

    //End of Google+ requirements
    Cursor cur, cursor;
    int[][] mArray = new int [10][10];
    int lastKnownContactLoc=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_new_facebook_login);
        fbInviteButton = (Button) findViewById(R.id.fbInviteButton);
        contactsButton = (Button) findViewById(R.id.contactsButton);
        publishButton = (Button) findViewById(R.id.publishPerm);
        btnUserLikes = (Button) findViewById(R.id.btnLikes);

        //Google+ crap
        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        btnSignOut = (Button) findViewById(R.id.btn_sign_out);
        btnRevokeAccess = (Button) findViewById(R.id.btn_revoke_access);
        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        //End of Google+ crap

        //Retrieve Contacts Button
        contactsButton.setOnClickListener(this);
        //Facebook Buttons
        fbInviteButton.setOnClickListener(this);
        publishButton.setOnClickListener(this);
        btnUserLikes.setOnClickListener(this);
        //Google+ Buttons
        btnSignIn.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
        btnRevokeAccess.setOnClickListener(this);
        // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();


        //Check if user is already logged in.
        AccessToken mAccessToken = AccessToken.getCurrentAccessToken();
        //Log.i("AccessToken",mAccessToken.getToken());
        if (mAccessToken != null) {

            Toast.makeText(getApplicationContext(), "Already Logged in abcc :" + Profile.getCurrentProfile().getName(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(NewFacebookLogin.this, MainActivity.class);
            startActivity(i);
        }
        //Reference the login button
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        //Set permissions
        loginButton.setReadPermissions("user_friends", "user_likes");


        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                    Toast.makeText(getApplicationContext(), profile.getName(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent (NewFacebookLogin.this,RegisterActivity.class);
                    finish();
                    startActivity(i);
                }

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
        //LoginManager to add permissions later "on-the-go"
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(NewFacebookLogin.this, "Login Manager Succeed", Toast.LENGTH_SHORT).show();
                Intent i = new Intent (NewFacebookLogin.this,RegisterActivity.class);
                finish();
                startActivity(i);
            }

            @Override
            public void onCancel() {
                Toast.makeText(NewFacebookLogin.this, "Login Manager Cancelled", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(NewFacebookLogin.this, "Login Manager Error", Toast.LENGTH_SHORT).show();

            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_facebook_login, menu);
        return true;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (requestCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fbInviteButton: {
                String appLinkUrl, previewImageUrl;

                appLinkUrl = "PLAYSTORE LINK GOES HERE";
                previewImageUrl = "ICON IMAGE WEB RESOURCE";

                if (AppInviteDialog.canShow()) {
                    AppInviteContent content = new AppInviteContent.Builder()
                            .setApplinkUrl(appLinkUrl)
                            .setPreviewImageUrl(previewImageUrl)
                            .build();

                    AppInviteDialog appInviteDialog = new AppInviteDialog(NewFacebookLogin.this);
                    appInviteDialog.registerCallback(callbackManager, new FacebookCallback<AppInviteDialog.Result>() {
                        @Override
                        public void onSuccess(AppInviteDialog.Result result) {
                        }

                        @Override
                        public void onCancel() {
                        }

                        @Override
                        public void onError(FacebookException e) {
                        }
                    });

                    appInviteDialog.show(content);
                }
            }
            break;
            case R.id.contactsButton: {
                Log.i("ContactsButton","Hit");
                final ContentResolver cr = getContentResolver();
                //Cursor to traverse the Android SQLite database to retrieve Name-Number Pairs
                cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);

                int i = 0;
                if (cur.getCount() > 0) {
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            while (cur.moveToNext()) {
                                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                                if (Integer.parseInt(cur.getString(
                                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                                    Cursor pCur = cr.query(
                                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                            null,
                                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                            new String[]{id}, null);

                                    while (pCur.moveToNext()) {
                                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                        tempContact = new contactStore();
                                        tempContact.setContactName(name);
                                        tempContact.setContactNumber(phoneNo);
                                        // Log.i("TempContact",tempContact.getContactName()+"  "+tempContact.getPhoneNumber());
                                        mContactStore.add(tempContact);


                                    }
                                    pCur.close();

                                }
                            }
                            cur.close();
                            handler.sendEmptyMessage(1);
                        }
                    };
                    Thread mNameNumberThread = new Thread(r);
                    mNameNumberThread.start();
                    cursor = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                            null, null, null);

                    if (cursor.getCount() > 0) {
                        Runnable ru = new Runnable() {

                            @Override
                            public void run() {
                                while (cursor.moveToNext()) {
                                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                                    int emailIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
                                    String email = cursor.getString(emailIdx);

                                    tempContact = new contactStore();
                                    tempContact.setContactName(name);
                                    tempContact.setContactEmail(email);
                                    tempContactStore.add(tempContact);


                                }
                                handler.sendEmptyMessage(0);
                            }

                        };

                        Thread mNameEmailThread = new Thread(ru);
                        mNameEmailThread.start();


                    }
                }


            }
            break;
            case R.id.publishPerm:{
                LoginManager.getInstance().logInWithPublishPermissions(this,Arrays.asList("publish_actions"));

            }break;
            case R.id.btnLikes:{
                  /* make the API call */
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/"+Profile.getCurrentProfile().getId()+"/likes",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                 /* handle the result */
                                Log.i("UserLikes",response.toString());
                            }
                        }
                ).executeAsync();

            }break;
            case R.id.btn_sign_in:
                // Signin button clicked
                signInWithGplus();
                break;
            case R.id.btn_sign_out:
                // Signout button clicked
                signOutFromGplus();
                break;
            case R.id.btn_revoke_access:
                // Revoke access button clicked
                revokeGplusAccess();
                break;

        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Log.i("Thread", "Emails Extracted");
                cursor.close();
            }
            if (msg.what == 1) {
                Log.i("Thread", "Name-Numbers Extracted");
                for (int i = 0; i < mContactStore.size(); i++) {
                    for (int j = 0; j < tempContactStore.size(); j++) {
                        if (tempContactStore.get(j).getContactName().equals(mContactStore.get(lastKnownContactLoc).getContactName()))
                            continue;
                        if (tempContactStore.get(j).getContactName().equals(mContactStore.get(i).getContactName())) {
                            // Log.i("Found",tempContactStore.get(j).getContactName()+"   "+tempContactStore.get(j).getContactEmail()+"  "+mContactStore.get(i).getContactName());
                           // Log.i("LastKnownMatch", i + "");
                            lastKnownContactLoc = i;
                            mContactStore.get(i).setContactEmail(tempContactStore.get(j).getContactEmail());

                        }
                    }
                }
                for(int i=0;i<mContactStore.size();i++){
                    Log.i("FinalContact", mContactStore.get(i).getContactName() + "  " + mContactStore.get(i).getContactNumber() + "  " + mContactStore.get(i).getContactEmail());
                }

            }
        }


    };
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

        // Get user's information
        getProfileInformation();
        Intent i = new Intent(NewFacebookLogin.this,RegisterActivity.class);
        finish();
        startActivity(i);

        // Update the UI after signin
        updateUI(true);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
        updateUI(false);

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!connectionResult.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = connectionResult;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }
    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);
            btnRevokeAccess.setVisibility(View.VISIBLE);

        } else {
            btnSignIn.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.GONE);
            btnRevokeAccess.setVisibility(View.GONE);

        }
    }
    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            updateUI(false);
        }
    }
    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }
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
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

                Log.e("MainActivity", "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl);

                txtName.setText(personName);
                txtEmail.setText(email);

                // by default the profile url gives 50x50 px image only
                // we can replace the value with whatever dimension we want by
                // replacing sz=X
                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;

                new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);

            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
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
    private void revokeGplusAccess() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.e("MainActivity", "User access revoked!");
                            mGoogleApiClient.connect();
                            updateUI(false);
                        }

                    });
        }
    }
    
}

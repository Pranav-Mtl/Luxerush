package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.shoping.BE.SigninBE;
import com.shoping.BL.SigninBL;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

public class Login extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    EditText etEmail,etPassword;
    TextView tvSignup,tvForgot;
    Button btnDone;

    String strEmail,strPassword;

    SigninBE objSigninBE;
    SigninBL objSigninBL;

    ProgressDialog progressDialog;

    ImageButton btnFB,btnGoogle;

    public static CallbackManager callbackmanager;



    private static final String TAG = "SignInActivity";


    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;


    String deviceId;
    GoogleCloudMessaging gcmObj;

    Context applicationContext;
    String gcmID;

    int xx,yy;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
    }

    private void initialize(){
        etEmail= (EditText) findViewById(R.id.signin_email);
        etPassword= (EditText) findViewById(R.id.signin_password);
        btnFB= (ImageButton) findViewById(R.id.signup_fb);
        btnGoogle= (ImageButton) findViewById(R.id.signup_google);
        tvSignup= (TextView) findViewById(R.id.signin_signup);
        tvForgot= (TextView) findViewById(R.id.signin_forgot);

        objSigninBE=new SigninBE();
        objSigninBL=new SigninBL();
        progressDialog = new ProgressDialog(Login.this);

        btnDone= (Button) findViewById(R.id.signin_done);
        btnDone.setOnClickListener(this);
        btnFB.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
        tvSignup.setOnClickListener(this);
        tvForgot.setOnClickListener(this);

        applicationContext=getApplicationContext();

        deviceId=Util.getSharedPrefrenceValue(getApplicationContext(),Constant.SP_DEVICE_ID);


        gcmID=Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_GCM_ID);

        if(gcmID==null){
            if (checkPlayServices()) {
                registerInBackground();
            }
        }


        LoginManager.getInstance().logOut();
        callbackmanager = CallbackManager.Factory.create();



        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signin_done:
                if(validate()){
                    objSigninBE.setEmail(strEmail);
                    objSigninBE.setPassword(strPassword);
                    objSigninBE.setGcm("");
                    objSigninBE.setDevice("");

                    if(Util.isInternetConnection(Login.this)){
                        new CallWS().execute();
                    }
                }
                break;
            case R.id.signup_fb:
                onFblogin();
                break;
            case R.id.signup_google:
                signIn();
                break;
            case R.id.signin_signup:
                startActivity(new Intent(getApplicationContext(), SignupScreen.class));
                break;
            case R.id.signin_forgot:
                startActivity(new Intent(getApplicationContext(),ForgotPassword.class));
                break;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private class CallWS extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            String result = objSigninBL.signinEmail(getApplicationContext(), objSigninBE);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try {

                if(Constant.WS_RESPONSE_SUCCESS.equalsIgnoreCase(s)){
                    Toast.makeText(getApplicationContext(), "SignIn Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),HomeScreenOptions.class));
                }
                else {
                    Snackbar snack = Snackbar.make(findViewById(R.id.login_root),
                            getResources().getString(R.string.signin_fail),
                            Snackbar.LENGTH_LONG).setText(getResources().getString(R.string.signin_fail)).setActionTextColor(getResources().getColor(R.color.golden));
                    ViewGroup group = (ViewGroup) snack.getView();
                    TextView tv = (TextView) group.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(Color.WHITE);
                    group.setBackgroundColor(getResources().getColor(R.color.golden));
                    snack.show();
                }
            } catch (NullPointerException e) {

            } catch (Exception e) {

            } finally {
                progressDialog.dismiss();
            }
        }
    }

    private boolean validate(){
        boolean flag=true;
        strEmail=etEmail.getText().toString();
        strPassword=etPassword.getText().toString();

        if (strEmail.trim().length() == 0) {
            etEmail.setError("Required");
            flag = false;
        }


        if (strPassword.trim().length() == 0) {
            etPassword.setError("Required");
            flag = false;
        }

        return flag;
    }

    private void onFblogin()
    {


        // Set permissions
        //LoginManager.getInstance().setReadPermissions(Arrays.asList("public_profile, email, user_birthday"));
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile"));

        LoginManager.getInstance().registerCallback(callbackmanager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        System.out.println("Success");
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject json, GraphResponse response) {
                                        if (response.getError() != null) {
                                            // handle error
                                            System.out.println("ERROR");
                                        } else {
                                            System.out.println("Success");
                                            try {

                                                String jsonresult = String.valueOf(json);
                                                System.out.println("JSON Result" + jsonresult);

                                                String str_email = json.getString("email");
                                                String str_name = json.getString("name");
                                                objSigninBE.setEmail(str_email);
                                                objSigninBE.setGcm(gcmID);
                                                objSigninBE.setDevice(deviceId);

                                                if(Util.isInternetConnection(Login.this)){
                                                    new CallWSSocial().execute();
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                        System.out.println("LoginResult" + loginResult);

                    }

                    @Override
                    public void onCancel() {
                        Log.d("FB CANCEL", "On cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("FB ERROR", error.toString());
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackmanager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.d("GOOGLE API", acct.getEmail());

            String str_email = acct.getEmail();
            String str_name = acct.getDisplayName();
            objSigninBE.setEmail(str_email);
            objSigninBE.setGcm(gcmID);
            objSigninBE.setDevice(deviceId);

            if(Util.isInternetConnection(Login.this)){
                new CallWSSocial().execute();
            }


            //new ValidateUserSocial().execute(acct.getEmail(),gcmID,deviceId);
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }
    // [END handleSignInResult]

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(true);
        }

        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        // When Play services not found in device
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                // Show Error dialog to install Play services
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {

                finish();
            }
            return false;
        } else {

        }
        return true;
    }

    /*--------------------------GCM KEY ----------------------------------------*/
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging
                                .getInstance(applicationContext);
                    }
                    gcmID = gcmObj.register(Constant.GOOGLE_PROJ_ID);
                    msg = "Registration ID :" + gcmID;

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(gcmID)) {
                    //Toast.makeText(getApplicationContext(),"GSM"+gcmID,Toast.LENGTH_SHORT).show();
                    Util.setSharedPrefrenceValue(applicationContext,Constant.PREFS_NAME,Constant.SP_GCM_ID,gcmID);
                } else {
                    /*Toast.makeText(
                       applicationContext,
                            "Reg ID Creation Failed.\n\nEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time."
                                    + msg, Toast.LENGTH_LONG).show();*/
                }
            }
        }.execute(null, null, null);
    }


    private class CallWSSocial extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            String result = objSigninBL.signinSocial(getApplicationContext(), objSigninBE);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                if(Constant.WS_RESPONSE_SUCCESS.equalsIgnoreCase(s)){
                    Toast.makeText(getApplicationContext(),"SignIn Successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),HomeScreenOptions.class));
                }
                else {
                    Snackbar snack = Snackbar.make(findViewById(R.id.login_root),
                            getResources().getString(R.string.signin_fail_social),
                            Snackbar.LENGTH_LONG).setText(getResources().getString(R.string.signin_fail_social)).setActionTextColor(getResources().getColor(R.color.golden));
                    ViewGroup group = (ViewGroup) snack.getView();
                    TextView tv = (TextView) group.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(Color.WHITE);
                    group.setBackgroundColor(getResources().getColor(R.color.golden));
                    snack.show();
                }
            } catch (NullPointerException e) {

            } catch (Exception e) {

            } finally {
                progressDialog.dismiss();
            }
        }
    }

}

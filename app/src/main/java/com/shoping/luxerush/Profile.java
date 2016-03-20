package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shoping.BE.CheckOutBE;
import com.shoping.BL.CheckOutBL;
import com.shoping.BL.ProfileBL;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;

public class Profile extends AppCompatActivity  implements View.OnClickListener{

    TextView tvName,tvMobile,tvEmail;
    EditText etName,etMobile,etEmail,etAddress,etCity,etZip;
    EditText etOldPassword,etNewPassword,etConfirmPassword;
    Button btnUpdate,btnChangePassword;

    String strName,strMobile,strEmail,strAddress,strCity,strZip,strOldPassword,strNewPassword,strConfirmPassword;

    CheckOutBL objCheckOutBL;
    CheckOutBE objCheckOutBE;

    ProgressDialog progressDialog;

    ProfileBL objProfileBL;


    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initialize();

        if(Util.isInternetConnection(Profile.this))
            new CallWs().execute(userID);
    }

    private void initialize(){
        tvName= (TextView) findViewById(R.id.profile_name);
        tvMobile= (TextView) findViewById(R.id.profile_mobile);
        tvEmail= (TextView) findViewById(R.id.profile_email);

        etName= (EditText) findViewById(R.id.edit_name);
        etMobile= (EditText) findViewById(R.id.edit_mobile);
        etEmail= (EditText) findViewById(R.id.edit_email);
        etAddress= (EditText) findViewById(R.id.edit_address);
        etCity= (EditText) findViewById(R.id.edit_city);
        etZip= (EditText) findViewById(R.id.edit_zip);

        etOldPassword= (EditText) findViewById(R.id.edit_oldpassword);
        etNewPassword= (EditText) findViewById(R.id.edit_newpassword);
        etConfirmPassword= (EditText) findViewById(R.id.edit_confirmpassword);

        btnUpdate= (Button) findViewById(R.id.edit_updade);
        btnChangePassword= (Button) findViewById(R.id.edit_change_password);

        btnUpdate.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);


        objCheckOutBL=new CheckOutBL();
        objCheckOutBE=new CheckOutBE();
        objProfileBL=new ProfileBL();
        progressDialog=new ProgressDialog(Profile.this);

        userID= Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_LOGIN_ID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {


            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_updade:
                if(validate()){
                    objCheckOutBE.setName(strName);
                    objCheckOutBE.setEmail(strEmail);
                    objCheckOutBE.setMobile(strMobile);
                    objCheckOutBE.setAddress(strAddress);
                    objCheckOutBE.setZip(strZip);
                    objCheckOutBE.setCity(strCity);

                    if(Util.isInternetConnection(Profile.this))
                        new CallWsUpdate().execute(userID);
                }
                break;
            case R.id.edit_change_password:
                if(validatePassword()){
                    if(Util.isInternetConnection(Profile.this))
                        new CallWsUpdatePassword().execute(userID,strOldPassword,strNewPassword);
                }
                break;
        }
    }


    private class CallWs extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {

            objCheckOutBL.getUserDetails(params[0], objCheckOutBE);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            try{

                etName.setText(objCheckOutBE.getName());
                etEmail.setText(objCheckOutBE.getEmail());
                etAddress.setText(objCheckOutBE.getAddress());
                etCity.setText(objCheckOutBE.getCity());
                etZip.setText(objCheckOutBE.getZip());
                etMobile.setText(objCheckOutBE.getMobile());

                tvName.setText(objCheckOutBE.getName());
                tvEmail.setText(objCheckOutBE.getEmail());
                tvMobile.setText(objCheckOutBE.getMobile());

            }catch (NullPointerException e){

            }catch (Exception e){

            }
            finally {
                progressDialog.dismiss();
            }
        }
    }

    private boolean validate(){
        boolean flag=true;
        strName=etName.getText().toString();
        strEmail=etEmail.getText().toString();
        strMobile=etMobile.getText().toString();
        strAddress=etAddress.getText().toString();
        strCity=etCity.getText().toString();
        strZip=etZip.getText().toString();

        if(strName.trim().length()==0){
            etName.setError("Required");
            flag=false;
        }

        if(strEmail.trim().length()==0){
            etEmail.setError("Required");
            flag=false;
        }
        if(strMobile.trim().length()==0){
            etMobile.setError("Required");
            flag=false;
        }
        if(strAddress.trim().length()==0){
            etAddress.setError("Required");
            flag=false;
        }
        if(strCity.trim().length()==0){
            etCity.setError("Required");
            flag=false;
        }
        if(strZip.trim().length()==0){
            etZip.setError("Required");
            flag=false;
        }
        return flag;
    }

    private class CallWsUpdate extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {

           String result=objProfileBL.updateDetails(params[0],objCheckOutBE);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try{

                if(Constant.WS_RESPONSE_SUCCESS.equalsIgnoreCase(s)){
                    Toast.makeText(getApplicationContext(),"Profile Updated",Toast.LENGTH_SHORT).show();
                }
            }catch (NullPointerException e){

            }catch (Exception e){

            }
            finally {
                progressDialog.dismiss();
            }
        }
    }

    private class CallWsUpdatePassword extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {

            String result=objProfileBL.updatePassword(params[0],params[1],params[2]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try{

                if(Constant.WS_RESPONSE_SUCCESS.equalsIgnoreCase(s)){
                    Toast.makeText(getApplicationContext(),"Profile Updated",Toast.LENGTH_SHORT).show();
                }
            }catch (NullPointerException e){

            }catch (Exception e){

            }
            finally {
                progressDialog.dismiss();
            }
        }
    }

    private boolean validatePassword() {
        boolean flag = true;
        strOldPassword = etOldPassword.getText().toString();
        strNewPassword = etNewPassword.getText().toString();
        strConfirmPassword = etConfirmPassword.getText().toString();


        if (strOldPassword.trim().length() == 0) {
            etOldPassword.setError("Required");
            flag = false;
        }

        if (strNewPassword.trim().length() == 0) {
            etNewPassword.setError("Required");
            flag = false;
        }
        if (strConfirmPassword.trim().length() == 0) {
            etConfirmPassword.setError("Required");
            flag = false;
        }

        if(!strConfirmPassword.equalsIgnoreCase(strNewPassword)){
            etConfirmPassword.setError("Password Mismatch");
            flag = false;
        }
        return flag;
    }
}

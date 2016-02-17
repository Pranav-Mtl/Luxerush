package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.shoping.BE.CheckOutBE;
import com.shoping.BE.ItemDetailBE;
import com.shoping.BE.SubscriptionItemDetailBE;
import com.shoping.BL.CheckOutBL;
import com.shoping.BL.SubscriptionItemDetailBL;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;

public class SubscriptionCheckout extends AppCompatActivity implements View.OnClickListener {

    Button btnDone;
    String userID;

    CheckOutBL objCheckOutBL;
    CheckOutBE objCheckOutBE;
    Spinner spnStates;

    EditText etName,etAddress1,etAddress2,etCity,etZip,etMobile,etEmail;
    String strName,strAddress1,strAddress2,strCity,strZip,strMobile,strEmail,strStates;

    ProgressDialog progressDialog;

    SubscriptionItemDetailBE objSubscriptionItemDetailBE;
    SubscriptionItemDetailBL objSubscriptionItemDetailBL;

    String productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_checkout);

        initialize();

        if(Util.isInternetConnection(SubscriptionCheckout.this)){
            new CallWs().execute(userID);
        }
    }

    private void initialize(){

        btnDone= (Button) findViewById(R.id.checkout_done);
        etName= (EditText) findViewById(R.id.checkout_name);
        etAddress1= (EditText) findViewById(R.id.checkout_address1);

        etCity= (EditText) findViewById(R.id.checkout_city);
        etZip= (EditText) findViewById(R.id.checkout_zip);
        etMobile= (EditText) findViewById(R.id.checkout_mobile);
        etEmail= (EditText) findViewById(R.id.checkout_email);
        spnStates= (Spinner) findViewById(R.id.checkout_states);

        btnDone.setOnClickListener(this);

        objCheckOutBL=new CheckOutBL();
        objCheckOutBE=new CheckOutBE();
        progressDialog=new ProgressDialog(SubscriptionCheckout.this);

        objSubscriptionItemDetailBE=new SubscriptionItemDetailBE();
        objSubscriptionItemDetailBL=new SubscriptionItemDetailBL();

        productID=getIntent().getStringExtra("ID").toString();


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
            case R.id.checkout_done:
                if(validate()) {
                    objCheckOutBE.setName(strName);
                    objCheckOutBE.setCity(strCity);
                    objCheckOutBE.setMobile(strMobile);
                    objCheckOutBE.setAddress(strAddress1);
                    objCheckOutBE.setZip(strZip);
                    objCheckOutBE.setEmail(strEmail);

                    if(Util.isInternetConnection(SubscriptionCheckout.this)){
                        new GetProduct().execute(productID,userID);
                    }
                    //startActivity(new Intent(getApplicationContext(), PaymentScreen.class).putExtra("CheckOutBE", objCheckOutBE).putExtra("ItemDetailBE", objItemDetailBE));
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
                etAddress1.setText(objCheckOutBE.getAddress());
                etCity.setText(objCheckOutBE.getCity());
                etZip.setText(objCheckOutBE.getZip());
                etMobile.setText(objCheckOutBE.getMobile());

                try {
                    ArrayAdapter<String> adapterState = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item,objCheckOutBL.listStates);
                    adapterState.setDropDownViewResource(R.layout.spinner_item);
                    spnStates.setAdapter(adapterState);
                }catch (Exception e){

                }


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
        strAddress1=etAddress1.getText().toString();

        strCity=etCity.getText().toString();
        strZip=etZip.getText().toString();
        strMobile=etMobile.getText().toString();
        strStates=spnStates.getSelectedItem().toString();

        if(strName.trim().length()==0){
            etName.setError("Required");
            flag=false;
        }
        if(strEmail.trim().length()==0){
            etEmail.setError("Required");
            flag=false;
        }
        if(strAddress1.trim().length()==0){
            etAddress1.setError("Required");
            flag=false;
        }
        if(strAddress1.trim().length()==0){
            etAddress1.setError("Required");
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

        if(strMobile.trim().length()==0){
            etMobile.setError("Required");
            flag=false;
        }

        if(strStates.trim().equalsIgnoreCase("Select State")){
            Toast.makeText(getApplicationContext(), "Please select state", Toast.LENGTH_LONG).show();
            flag=false;
        }
        return flag;
    }


    private class GetProduct extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            String result=objSubscriptionItemDetailBL.setSubsDetail(params[0],params[1],objCheckOutBE);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                if(Constant.WS_RESPONSE_SUCCESS.equalsIgnoreCase(s)){
                    //Toast.makeText(getApplicationContext(),"Product")
                }
            }catch (Exception e){

            }
            finally {
                progressDialog.dismiss();
            }
        }
    }
}

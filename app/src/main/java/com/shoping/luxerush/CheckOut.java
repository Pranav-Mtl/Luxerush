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
import com.shoping.BL.CheckOutBL;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;

public class CheckOut extends AppCompatActivity implements View.OnClickListener {
    Button btnDone;
    String userID;

    CheckOutBL objCheckOutBL;
    CheckOutBE objCheckOutBE;
    Spinner spnStates;

    EditText etName,etAddress1,etAddress2,etCity,etZip,etMobile,etEmail;
    String strName,strAddress1,strAddress2,strCity,strZip,strMobile,strEmail,strStates;

    ProgressDialog progressDialog;

    ItemDetailBE objItemDetailBE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        initialize();

        if(Util.isInternetConnection(CheckOut.this)){
            new CallWs().execute(userID);
        }
    }

    private void initialize(){

        btnDone= (Button) findViewById(R.id.checkout_done);
        etName= (EditText) findViewById(R.id.checkout_name);
        etAddress1= (EditText) findViewById(R.id.checkout_address1);
        etAddress2= (EditText) findViewById(R.id.checkout_address2);
        etCity= (EditText) findViewById(R.id.checkout_city);
        etZip= (EditText) findViewById(R.id.checkout_zip);
        etMobile= (EditText) findViewById(R.id.checkout_mobile);
        etEmail= (EditText) findViewById(R.id.checkout_email);
        spnStates= (Spinner) findViewById(R.id.checkout_states);

        btnDone.setOnClickListener(this);

        objCheckOutBL=new CheckOutBL();
        objCheckOutBE=new CheckOutBE();
        progressDialog=new ProgressDialog(CheckOut.this);

        objItemDetailBE= (ItemDetailBE) getIntent().getSerializableExtra("ItemDetailBE");

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
                    startActivity(new Intent(getApplicationContext(), PaymentScreen.class).putExtra("CheckOutBE", objCheckOutBE).putExtra("ItemDetailBE", objItemDetailBE));
                }
                break;
        }
    }

    private class CallWs extends AsyncTask<String,String,String>{

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
        strAddress2=etAddress2.getText().toString();
        strZip=etZip.getText().toString();
        strMobile=etMobile.getText().toString();
        strCity=spnStates.getSelectedItem().toString();

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

        if(strAddress2.trim().length()==0){
            etAddress2.setError("Required");
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

        if(strCity.trim().equalsIgnoreCase("Select City")){
            Toast.makeText(getApplicationContext(),"Please select city",Toast.LENGTH_LONG).show();
            flag=false;
        }
        return flag;
    }
}

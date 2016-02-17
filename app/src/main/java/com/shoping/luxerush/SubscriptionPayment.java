package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.shoping.BL.SubscriptionPaymentBL;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;

public class SubscriptionPayment extends AppCompatActivity implements View.OnClickListener {

    Button btnPlaceOrder,btnCancelOrder;

    RadioGroup rbPaymentType;

    SubscriptionPaymentBL objSubscriptionPaymentBL;

    ProgressDialog progressDialog;

    String paymentType;

    String userID;

    String packageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_payment);
        initialize();
    }

    private void initialize(){
        btnPlaceOrder= (Button) findViewById(R.id.payment_place_order);
        btnCancelOrder= (Button) findViewById(R.id.payment_cancel_order);
        rbPaymentType= (RadioGroup) findViewById(R.id.radio_group_payment);


        btnCancelOrder.setOnClickListener(this);
        btnPlaceOrder.setOnClickListener(this);
        progressDialog=new ProgressDialog(SubscriptionPayment.this);

        userID=Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_LOGIN_ID);
        packageID=getIntent().getStringExtra("PackageID");

        objSubscriptionPaymentBL=new SubscriptionPaymentBL();
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
        switch (v.getId()) {
            case R.id.payment_place_order:
                int selectedID = rbPaymentType.getCheckedRadioButtonId();
                RadioButton rbPayment = (RadioButton) findViewById(selectedID);
                paymentType = rbPayment.getText().toString();
                if (Util.isInternetConnection(SubscriptionPayment.this)) {
                    new CallWS().execute(userID,packageID,"Online");
                }

                break;
        }
    }

    private class CallWS extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
           progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            String result=objSubscriptionPaymentBL.getSubscription(params[0], params[1], params[2],getApplicationContext());
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                Toast.makeText(getApplicationContext(), "Response--->" + s, Toast.LENGTH_SHORT).show();
                if(s.equalsIgnoreCase(Constant.WS_RESPONSE_SUCCESS)){

                }
            }catch (NullPointerException e){

            }catch (Exception e){

            }
            finally {
                progressDialog.dismiss();
            }
        }
    }
}

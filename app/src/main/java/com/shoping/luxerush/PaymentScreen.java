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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.shoping.BE.CheckOutBE;
import com.shoping.BE.ItemDetailBE;
import com.shoping.BL.PaymentBL;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;

public class PaymentScreen extends AppCompatActivity implements View.OnClickListener {


    EditText etPromoCode;
    Button btnApplyCode;

    Button btnPlaceOrder,btnCancelOrder;

    PaymentBL objPaymentBL;

    ProgressDialog progressDialog;

    String userID,deviceID;
    String promoCode="",promoID="";
    String paymentType;

    CheckOutBE objCheckOutBE;
    ItemDetailBE objItemDetailBE;

    RadioGroup rbPaymentType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);
        initialize();
    }

    private void initialize(){

        etPromoCode= (EditText) findViewById(R.id.payment_promo_code);
        btnApplyCode= (Button) findViewById(R.id.payment_promo_code_btn);
        btnPlaceOrder= (Button) findViewById(R.id.payment_place_order);
        btnCancelOrder= (Button) findViewById(R.id.payment_cancel_order);
        rbPaymentType= (RadioGroup) findViewById(R.id.radio_group_payment);

        btnApplyCode.setOnClickListener(this);
        btnCancelOrder.setOnClickListener(this);
        btnPlaceOrder.setOnClickListener(this);


        objCheckOutBE= (CheckOutBE) getIntent().getSerializableExtra("CheckOutBE");

        objPaymentBL=new PaymentBL();
        progressDialog=new ProgressDialog(PaymentScreen.this);

        userID=Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_LOGIN_ID);
        deviceID=Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_DEVICE_ID);

        objCheckOutBE= (CheckOutBE) getIntent().getSerializableExtra("CheckOutBE");
        objItemDetailBE= (ItemDetailBE) getIntent().getSerializableExtra("ItemDetailBE");



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
            case R.id.payment_promo_code_btn:
                promoCode=etPromoCode.getText().toString();
                if(Util.isInternetConnection(PaymentScreen.this))
                    if(promoCode.trim().length()!=0) {
                        new ValidatePromoCode().execute(userID, deviceID,promoCode,objItemDetailBE.getProductCategory());
                    }
                break;
            case R.id.payment_place_order:
                int selectedID=rbPaymentType.getCheckedRadioButtonId();
                RadioButton rbPayment= (RadioButton) findViewById(selectedID);
                paymentType=rbPayment.getText().toString();
                if(Util.isInternetConnection(PaymentScreen.this)) {
                    promoID = Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_PROMO_ID);
                    if (promoID == null)
                        promoID = "";
                    new CheckoutPayment().execute(userID, promoID, promoCode,paymentType);
                }

                break;
        }
    }

    private class ValidatePromoCode extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            String result=objPaymentBL.validatePromoCode(params[0],params[1],params[2],params[3],getApplicationContext());
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                if(s.equalsIgnoreCase(Constant.WS_RESPONSE_SUCCESS)){
                    Toast.makeText(getApplicationContext(),"Promo code applied",Toast.LENGTH_SHORT).show();
                    etPromoCode.setEnabled(false);
                    btnApplyCode.setEnabled(false);
                }
                else if(s.equalsIgnoreCase("already")){
                    Toast.makeText(getApplicationContext(),"Promo code already used",Toast.LENGTH_SHORT).show();
                }
                else if(s.equalsIgnoreCase("invalid")){
                    Toast.makeText(getApplicationContext(),"Invalid promo code",Toast.LENGTH_SHORT).show();
                }
            }catch (NullPointerException e){

            }catch (Exception e){

            }
            finally {
                progressDialog.dismiss();
            }
        }
    }

    private class CheckoutPayment extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            String result=objPaymentBL.sendSingleOrder(params[0],params[1],params[2],params[3],objItemDetailBE,objCheckOutBE);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                Toast.makeText(getApplicationContext(),"Response--->"+s,Toast.LENGTH_SHORT).show();
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

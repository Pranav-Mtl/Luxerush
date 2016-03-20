package com.shoping.luxerush;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shoping.BE.BuyWebViewBE;
import com.shoping.BE.CheckOutBE;
import com.shoping.BE.ItemDetailBE;
import com.shoping.BL.GetSlotBL;
import com.shoping.BL.PaymentBL;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;

import java.util.Calendar;

public class PaymentScreen extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener {


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

    LinearLayout llRent;
    TextView tvDate;
    Spinner spnSlot;

    GetSlotBL objGetSlotBL;

    BuyWebViewBE objBuyWebViewBE;

    String strDate,strSlot;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);
        initialize();

        if(Util.isInternetConnection(getApplicationContext())) {
            if (objItemDetailBE.getProductCategory().equalsIgnoreCase(Constant.CATEGORY_RENT)) {
                new GetSlot().execute();
            }
        }
    }

    private void initialize(){

        etPromoCode= (EditText) findViewById(R.id.payment_promo_code);
        btnApplyCode= (Button) findViewById(R.id.payment_promo_code_btn);
        btnPlaceOrder= (Button) findViewById(R.id.payment_place_order);
        btnCancelOrder= (Button) findViewById(R.id.payment_cancel_order);
        rbPaymentType= (RadioGroup) findViewById(R.id.radio_group_payment);
        llRent= (LinearLayout) findViewById(R.id.layout_rent_date);
        tvDate= (TextView) findViewById(R.id.rent_date);
        spnSlot= (Spinner) findViewById(R.id.rent_slot);

        tvDate.setOnClickListener(this);
        btnApplyCode.setOnClickListener(this);
        btnCancelOrder.setOnClickListener(this);
        btnPlaceOrder.setOnClickListener(this);


        objCheckOutBE= (CheckOutBE) getIntent().getSerializableExtra("CheckOutBE");

        objPaymentBL=new PaymentBL();
        objGetSlotBL=new GetSlotBL();
        objBuyWebViewBE=new BuyWebViewBE();
        progressDialog=new ProgressDialog(PaymentScreen.this);

        userID=Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_LOGIN_ID);
        deviceID=Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_DEVICE_ID);

        objCheckOutBE= (CheckOutBE) getIntent().getSerializableExtra("CheckOutBE");
        objItemDetailBE= (ItemDetailBE) getIntent().getSerializableExtra("ItemDetailBE");

        Log.d("Category",objItemDetailBE.getProductCategory());
        Log.d("TAg",objItemDetailBE.getTag());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(objItemDetailBE.getProductCategory().equalsIgnoreCase(Constant.CATEGORY_RENT)){
            llRent.setVisibility(View.VISIBLE);
        }
        else{
            llRent.setVisibility(View.GONE);
        }

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

                    if(objItemDetailBE.getProductCategory().equalsIgnoreCase(Constant.CATEGORY_RENT)){
                        strSlot=spnSlot.getSelectedItem().toString();
                        if(strSlot.equalsIgnoreCase("Select Slot")){
                            Toast.makeText(getApplicationContext(),"Please select slot",Toast.LENGTH_SHORT).show();
                        }else {
                            new CheckoutPayment().execute(userID, promoID, promoCode, paymentType, strDate, strSlot);
                        }
                    }else
                    {
                        strDate="";
                        strSlot="";
                        new CheckoutPayment().execute(userID, promoID, promoCode,paymentType,strDate,strSlot);
                    }

                }

                break;
            case R.id.rent_date:
                setDate();
                break;
        }
    }

    public void setDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);

        final Calendar later = Calendar.getInstance();
        later.add(Calendar.MONTH, 1);


        datePickerDialog.getDatePicker().setMaxDate(later.getTimeInMillis());

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear += 1;
        tvDate.setText(zeroPrefix(dayOfMonth) + "-" + zeroPrefix(monthOfYear) + "-" + year);
        strDate=zeroPrefix(dayOfMonth) + "-" + zeroPrefix(monthOfYear) + "-" + year;

    }

    public String zeroPrefix(int quantity) {
        if (quantity < 10) {
            return "0" + quantity;
        }


        return "" + quantity;
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
            String result=objPaymentBL.sendSingleOrder(params[0],params[1],params[2],params[3],objItemDetailBE,objCheckOutBE,objBuyWebViewBE,params[4],params[5]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                if(paymentType.equalsIgnoreCase("Online")) {
                    if (objBuyWebViewBE.isStatus()) {
                        startActivity(new Intent(getApplicationContext(), BuyWebView.class).putExtra("BuyWebViewBE", objBuyWebViewBE));
                    }
                }
                else
                {
                    if(s.equalsIgnoreCase(Constant.WS_RESPONSE_SUCCESS)){
                        startActivity(new Intent(getApplicationContext(),HomeSelection.class));
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Something went wrong. Please try again.",Toast.LENGTH_SHORT).show();

                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                progressDialog.dismiss();
            }
        }
    }
    /*--------------------------------*/

    private class GetSlot extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            objGetSlotBL.GetSlots(getApplicationContext());
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            try{

                try {
                    ArrayAdapter<String> adapterVehicleType = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item,objGetSlotBL.listSlots);
                    adapterVehicleType.setDropDownViewResource(R.layout.spinner_item);
                    spnSlot.setAdapter(adapterVehicleType);
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

}

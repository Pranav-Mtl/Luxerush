package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.shoping.BE.BuyWebViewBE;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;

import java.net.URLDecoder;

public class BuyWebView extends AppCompatActivity {

    private WebView webView;



    ProgressDialog mProgressDialog;

    String addToURL="?embed=form";

    String userID;

    String amount;

    Button btnDone;

    BuyWebViewBE objBuyWebViewBE;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_web_view);
        init();
    }

    private void init(){
        mProgressDialog=new ProgressDialog(BuyWebView.this);


        webView = (WebView) findViewById(R.id.loop_webView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        objBuyWebViewBE= (BuyWebViewBE) getIntent().getSerializableExtra("BuyWebViewBE");

        String s=objBuyWebViewBE.getUrl() +addToURL+"&data_name="+objBuyWebViewBE.getName()+"&data_email="+objBuyWebViewBE.getEmail()+"&data_phone="+objBuyWebViewBE.getPhone()+"&data_readonly=data_\n" +
                "name&data_readonly=data_phone";


        userID= Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_LOGIN_ID);

        startWebView(s);

    }

    private void startWebView(String url) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //Show loader on url load
            public void onLoadResource (WebView view, String url) {

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                try{
                    if (progressDialog == null) {
                        // in standard case YourActivity.this
                        progressDialog = new ProgressDialog(BuyWebView.this);
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();
                    }

                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }

            public void onPageFinished(WebView view, String url) {
                try{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }

        });


        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);


        //Load url in webview
        webView.loadUrl(url);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {

            try {

                //Toast.makeText(getApplicationContext(),"BAck Clicked",Toast.LENGTH_SHORT).show();
                Log.d("URL RETURN", webView.getUrl());

                String url = webView.getUrl();

                String ss[] = url.split("&");

                String keyValue[] = ss[0].split("=");
                String param_key = URLDecoder.decode(keyValue[0]);
// the	tracking	id	value	(i.e.	12345)	is
                // stored	in	param_value


                String param_value =
                        URLDecoder.decode(keyValue[1]);

                Log.d("Key", param_key);
                Log.d("Paymenti id", param_value);

                String status[] = ss[1].split("=");

                String statusKey = URLDecoder.decode(status[0]);
                String statusValue = URLDecoder.decode(status[1]);

                Log.d("STATUS Key", statusKey);
                Log.d("STATUS VALUE", statusValue);

                if (statusValue.equals("success")) {
                    Toast.makeText(getApplicationContext(),"Payment Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomeScreenOptions.class));
                    // finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Payment failed. Please try again.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }catch (ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
                finish();
            }
            catch (Exception e){
                e.printStackTrace();
                finish();
            }



            /*for	(String	referrerValue	:	ss) {
                String keyValue[] =
                        referrerValue.split("=");
// the	key,	tracking_id	in	this	case,	is
                //	stored	in	param_key
                String param_key = URLDecoder.decode(keyValue[0]);
// the	tracking	id	value	(i.e.	12345)	is
                // stored	in	param_value


                String param_value =
                        URLDecoder.decode(keyValue[1]);
                //	do	something	with
                //	the	param	value
                System.out.println("KEY" + param_key);
                System.out.println("VALUE" + param_value);

                Log.d("Key", param_key);
                Log.d("Value", param_value);
            }

*/


            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

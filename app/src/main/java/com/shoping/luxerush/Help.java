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
import android.widget.Toast;

import com.shoping.BL.HelpBL;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;

public class Help extends AppCompatActivity implements View.OnClickListener {

    HelpBL objHelpBL;

    ProgressDialog progressDialog;

    EditText etMessage;
    Button btnDone;

    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        init();
    }

    private void init(){

        btnDone= (Button) findViewById(R.id.submitBtn);
        etMessage= (EditText) findViewById(R.id.message);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        userID=Util.getSharedPrefrenceValue(getApplicationContext(),Constant.SP_LOGIN_ID);

        objHelpBL=new HelpBL();
        progressDialog=new ProgressDialog(Help.this);

        btnDone.setOnClickListener(this);

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
            case R.id.submitBtn:
                if(etMessage.getText().toString().length()>0){
                    if(Util.isInternetConnection(Help.this))
                        new SendComment().execute(userID,etMessage.getText().toString());
                }
        }
    }

    private class SendComment extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            String result=objHelpBL.help(params[0],params[1]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try{

                if(s.equalsIgnoreCase(Constant.WS_RESPONSE_SUCCESS)){
                    Toast.makeText(getApplicationContext(),"Message sent to LuxeRush Team.",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Something went wrong. Try Again",Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){

            }finally {
                progressDialog.dismiss();
            }
        }
    }
}

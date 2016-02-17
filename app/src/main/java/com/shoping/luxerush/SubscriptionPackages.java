package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.adapter.SubscriptionPackageAdapter;

public class SubscriptionPackages extends AppCompatActivity implements View.OnClickListener {

    RecyclerView listPackage;
    SubscriptionPackageAdapter objSubscriptionPackageAdapter;
    ProgressDialog progressDialog;
    Button btnDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_packages);
        init();

        if(Util.isInternetConnection(SubscriptionPackages.this)){
            new GetAllPackages().execute();
        }
    }

    private void init(){
        listPackage= (RecyclerView) findViewById(R.id.subscription_package);
        btnDone= (Button) findViewById(R.id.package_done);

        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listPackage.setLayoutManager(llm);

        progressDialog=new ProgressDialog(SubscriptionPackages.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

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
            case R.id.package_done:
                if(objSubscriptionPackageAdapter.mSelectedItem==-1){
                    Toast.makeText(getApplicationContext(),"Please select a package",Toast.LENGTH_SHORT).show();
                }
                else
                {
                  startActivity(new Intent(getApplicationContext(),SubscriptionPayment.class).putExtra("PackageID", Constant.packageID[objSubscriptionPackageAdapter.mSelectedItem]));
                }
                break;

        }
    }

    private class GetAllPackages extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            objSubscriptionPackageAdapter=new SubscriptionPackageAdapter(getApplicationContext());
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            try {

                listPackage.setAdapter(objSubscriptionPackageAdapter);
            }
            catch (NullPointerException e){

            }catch (Exception e){

            }finally {
                progressDialog.dismiss();
            }
        }
    }


}

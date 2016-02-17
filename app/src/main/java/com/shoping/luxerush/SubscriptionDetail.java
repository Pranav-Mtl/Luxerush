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
import android.widget.TextView;

import com.shoping.BE.SubscriptionDetailBE;
import com.shoping.BL.SubscriptionDetailBL;
import com.shoping.BL.SubscriptionPackageBL;
import com.shoping.Configuration.Util;
import com.shoping.adapter.SubscriptionBrandAdapter;
import com.shoping.adapter.SubscriptionPackageAdapter;

public class SubscriptionDetail extends AppCompatActivity implements View.OnClickListener {
    TextView tvName,tvClothes;

    RecyclerView listBrands;
    ProgressDialog progressDialog;
    SubscriptionDetailBE objSubscriptionDetailBE;
    SubscriptionDetailBL objSubscriptionDetailBL;

    SubscriptionBrandAdapter objSubscriptionBrandAdapter;

    String packageID;

    Button btnDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_detail);
        init();

        if(Util.isInternetConnection(SubscriptionDetail.this)){
            new GetAllPackages().execute(packageID);
        }
    }

    private void init(){
        tvName= (TextView) findViewById(R.id.subscription_package_name);
        tvClothes= (TextView) findViewById(R.id.subscription_package_clothes);
        listBrands= (RecyclerView) findViewById(R.id.subscription_brands);
        btnDone= (Button) findViewById(R.id.package_done);

        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listBrands.setLayoutManager(llm);

        objSubscriptionDetailBE=new SubscriptionDetailBE();
        objSubscriptionDetailBL=new SubscriptionDetailBL();

        progressDialog=new ProgressDialog(SubscriptionDetail.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnDone.setOnClickListener(this);

        packageID=getIntent().getStringExtra("ID");

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
                startActivity(new Intent(getApplicationContext(),SubscriptionPayment.class).putExtra("PackageID",packageID));
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
           objSubscriptionDetailBL.getPackagesDetail(params[0],objSubscriptionDetailBE);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                objSubscriptionBrandAdapter=new SubscriptionBrandAdapter(getApplicationContext());
                listBrands.setAdapter(objSubscriptionBrandAdapter);

                tvName.setText(objSubscriptionDetailBE.getName() + " @ ₹" + objSubscriptionDetailBE.getPrice());
                tvClothes.setText(objSubscriptionDetailBE.getBags()+" bags and "+objSubscriptionDetailBE.getClothes()+" clothes");
            }
            catch (NullPointerException e){

            }catch (Exception e){

            }finally {
                progressDialog.dismiss();
            }
        }
    }

}

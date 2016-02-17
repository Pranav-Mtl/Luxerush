package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.shoping.Configuration.Util;
import com.shoping.adapter.PopularItemGridAdapter;
import com.shoping.adapter.SubscriptionListAdapter;

public class SubscriptionItemList extends AppCompatActivity {

    RecyclerView rvGrid;
    private GridLayoutManager lLayout;

    String subsID;

    ProgressDialog progressDialog;

    SubscriptionListAdapter objSubscriptionListAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_item_list);
        init();

        if(Util.isInternetConnection(SubscriptionItemList.this)){
            new CallWS().execute(subsID);
        }
    }

    private void init(){
        rvGrid= (RecyclerView) findViewById(R.id.recycle_subscription_item);

        rvGrid.setHasFixedSize(true);

        lLayout = new GridLayoutManager(SubscriptionItemList.this, 2);

        rvGrid.setLayoutManager(lLayout);

        progressDialog=new ProgressDialog(SubscriptionItemList.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        subsID=getIntent().getStringExtra("SubscriptionID");


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


    private class CallWS extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
        }

        @Override
        protected String doInBackground(String... params) {
            objSubscriptionListAdapter=new SubscriptionListAdapter(getApplicationContext(),params[0]);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                rvGrid.setAdapter(objSubscriptionListAdapter);
            }catch (NullPointerException e){

            }catch (Exception e){

            }
            finally {
                progressDialog.dismiss();
            }
        }
    }

}

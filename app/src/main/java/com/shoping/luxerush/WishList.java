package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.adapter.PopularItemGridAdapter;
import com.shoping.adapter.WishListAdapter;

public class WishList extends AppCompatActivity implements View.OnClickListener {

    RecyclerView wishList;

    ImageButton btnCart;

    WishListAdapter objWishListAdapter;

    private GridLayoutManager lLayout;

    ProgressDialog progressDialog;
    String userID;

    Intent intent;
    String category,tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        initialize();

        if(Util.isInternetConnection(WishList.this)){
            new CallWS().execute(category,tag,userID);
        }
    }

    private void initialize(){


        wishList= (RecyclerView) findViewById(R.id.recycle_wish_list);


        wishList.setHasFixedSize(true);

        lLayout = new GridLayoutManager(WishList.this, 2);

        wishList.setLayoutManager(lLayout);

        userID= Util.getSharedPrefrenceValue(getApplicationContext(),Constant.SP_LOGIN_ID);

        progressDialog=new ProgressDialog(WishList.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        intent=getIntent();
        category=intent.getStringExtra("Category");
        tag=intent.getStringExtra("Tag");

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
        }
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
            objWishListAdapter=new WishListAdapter(getApplicationContext(),params[0],params[1],params[2]);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                wishList.setAdapter(objWishListAdapter);
            }catch (NullPointerException e){

            }catch (Exception e){

            }
            finally {
                progressDialog.dismiss();
            }
        }
    }
}

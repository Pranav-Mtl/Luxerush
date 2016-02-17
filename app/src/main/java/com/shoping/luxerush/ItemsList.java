package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
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

public class ItemsList extends AppCompatActivity implements View.OnClickListener{

    RecyclerView rvGrid;

    ImageButton btnCart,btnWishList;

    private GridLayoutManager lLayout;

    PopularItemGridAdapter objPopularItemGridAdapter;

    Intent intent;
    String category,tag;

    ProgressDialog progressDialog;

    String userID;

    FloatingActionButton fbFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initialize();

        if(Util.isInternetConnection(ItemsList.this)){
            new CallWS().execute(category,tag,userID);
        }

    }

    private void initialize(){


        rvGrid= (RecyclerView) findViewById(R.id.recycle_popular_item);

        btnWishList= (ImageButton) findViewById(R.id.home_screen_wishlist);
        fbFilter= (FloatingActionButton) findViewById(R.id.fab_filter);


        btnWishList.setOnClickListener(this);
        fbFilter.setOnClickListener(this);
        rvGrid.setHasFixedSize(true);

        lLayout = new GridLayoutManager(ItemsList.this, 2);

        rvGrid.setLayoutManager(lLayout);

        userID=Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_LOGIN_ID);

        progressDialog=new ProgressDialog(ItemsList.this);
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
            case R.id.home_screen_wishlist:
                Intent intentWish=new Intent(getApplicationContext(),WishList.class);
                intentWish.putExtra("Category",category);
                intentWish.putExtra("Tag", tag);
                startActivity(intentWish);
                break;
            case R.id.fab_filter:
                startActivity(new Intent(getApplicationContext(),Filter.class));
                break;
        }
    }

    private class CallWS extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
        }

        @Override
        protected String doInBackground(String... params) {
            objPopularItemGridAdapter=new PopularItemGridAdapter(getApplicationContext(),params[0],params[1],params[2]);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                rvGrid.setAdapter(objPopularItemGridAdapter);
            }catch (NullPointerException e){

            }catch (Exception e){

            }
            finally {
                progressDialog.dismiss();
            }
        }
    }
}

package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shoping.BE.HomeScreenBE;
import com.shoping.BL.HomeScreenBL;
import com.shoping.Configuration.RecyclerItemClickListener;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.adapter.DrawerAdapter;

public class HomeScreenOptions extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llAllOption,llBuy,llPreLoved,llNew,llRent,llSubscription,llAlaCarte;
    ImageButton btnCart;

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
    ActionBarDrawerToggle mDrawerToggle;

    DrawerAdapter drawerAdapter;

    String userID;

    HomeScreenBL objHomeScreenBL;
    HomeScreenBE objHomeScreenBE;

    ProgressDialog progressDialog;

    View _itemColoured;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_options);
        initialize();

        if(userID!=null){
            if(Util.isInternetConnection(HomeScreenOptions.this)){
                new GetData().execute(userID);
            }
        }


        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                        if (position != 0) {
                            if (_itemColoured != null) {
                                _itemColoured.setBackgroundColor(getResources().getColor(R.color.white));
                                _itemColoured.invalidate();
                            }
                            _itemColoured = view;
                            view.setBackgroundColor(getResources().getColor(R.color.lightGray));
                        }




                    }
                }));
    }

    private void initialize(){
        llAllOption= (LinearLayout) findViewById(R.id.home_options_all);
        llBuy= (LinearLayout) findViewById(R.id.home_options_buy);
        llPreLoved= (LinearLayout) findViewById(R.id.home_options_preloved);
        llNew= (LinearLayout) findViewById(R.id.home_options_new);
        llRent= (LinearLayout) findViewById(R.id.home_options_rent);
        llSubscription= (LinearLayout) findViewById(R.id.home_options_subscription);
        llAlaCarte= (LinearLayout) findViewById(R.id.home_options_alacarte);

        llAllOption.setOnClickListener(this);
        llBuy.setOnClickListener(this);
        llPreLoved.setOnClickListener(this);
        llNew.setOnClickListener(this);
        llRent.setOnClickListener(this);
        llSubscription.setOnClickListener(this);
        llAlaCarte.setOnClickListener(this);

        objHomeScreenBL=new HomeScreenBL();
        objHomeScreenBE=new HomeScreenBE();
        progressDialog=new ProgressDialog(HomeScreenOptions.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setHasFixedSize(true);

        userID= Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_LOGIN_ID);
        if(userID==null)
            drawerAdapter = new DrawerAdapter(Constant.TITLES_LOGIN, Constant.ICONS,"Sign in", getApplicationContext());       // Creating the Adapter of com.example.balram.sampleactionbar.MyAdapter class(which we are going to see in a bit)
        else
            drawerAdapter = new DrawerAdapter(Constant.TITLES_LOGIN, Constant.ICONS,Constant.NAME, getApplicationContext());       // Creating the Adapter of com.example.balram.sampleactionbar.MyAdapter class(which we are going to see in a bit)

        // And passing the titles,icons,header view name, header view email,
        // and header  view profile picture
        mRecyclerView.setAdapter(drawerAdapter);

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }


        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.home_options_all:
                Intent intentAll=new Intent(getApplicationContext(),ItemsList.class);
                intentAll.putExtra("Category", Constant.CATEGORY_ALL);
                intentAll.putExtra("Tag",Constant.TAG_ALL);
                startActivity(intentAll);
                break;
            case R.id.home_options_buy:
                Intent intentBuy=new Intent(getApplicationContext(),ItemsList.class);
                intentBuy.putExtra("Category",Constant.CATEGORY_BUY);
                intentBuy.putExtra("Tag",Constant.TAG_BUY);
                startActivity(intentBuy);
                break;
            case R.id.home_options_preloved:
                Intent intentLoved=new Intent(getApplicationContext(),ItemsList.class);
                intentLoved.putExtra("Category",Constant.CATEGORY_BUY);
                intentLoved.putExtra("Tag", Constant.TAG_PRELOVED);
                startActivity(intentLoved);
                break;
            case R.id.home_options_new:
                Intent intentNew=new Intent(getApplicationContext(),ItemsList.class);
                intentNew.putExtra("Category",Constant.CATEGORY_BUY);
                intentNew.putExtra("Tag", Constant.TAG_NEW);
                startActivity(intentNew);
                break;
            case R.id.home_options_rent:
                Intent intentRent=new Intent(getApplicationContext(),ItemsList.class);
                intentRent.putExtra("Category",Constant.CATEGORY_RENT);
                intentRent.putExtra("Tag",Constant.TAG_RENT);
                startActivity(intentRent);
                break;
            case R.id.home_options_subscription:
                if(userID==null) {
                    startActivity(new Intent(getApplicationContext(),Login.class));
                    Toast.makeText(getApplicationContext(),"Please Login First",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(objHomeScreenBE.getSubscribe().equalsIgnoreCase("N")) {
                        Intent intentSubscription = new Intent(getApplicationContext(), SubscriptionPackages.class);
                        intentSubscription.putExtra("Category", Constant.CATEGORY_RENT);
                        intentSubscription.putExtra("Tag", Constant.TAG_SUBSCRIPTION);
                        startActivity(intentSubscription);
                    }
                    else if(objHomeScreenBE.getSubscribe().equalsIgnoreCase("Y")){
                        Intent intentSubscription = new Intent(getApplicationContext(), SubscriptionItemList.class);
                        intentSubscription.putExtra("SubscriptionID", objHomeScreenBE.getSubscribtionID());
                        startActivity(intentSubscription);
                    }
                }
                break;
            case R.id.home_options_alacarte:
                Intent intentCarte=new Intent(getApplicationContext(),ItemsList.class);
                intentCarte.putExtra("Category",Constant.CATEGORY_RENT);
                intentCarte.putExtra("Tag", Constant.TAG_ALA_CARTE);
                startActivity(intentCarte);
                break;
           /* case R.id.home_screen_cart:
                startActivity(new Intent(getApplicationContext(),CartScreen.class));
                break;*/
        }
    }

    private class GetData extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            objHomeScreenBL.homeScreenBL(params[0],objHomeScreenBE);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            try{

            }catch (NullPointerException e){

            }catch (Exception e){

            }finally {
                progressDialog.dismiss();
            }
        }
    }
}

package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.shoping.BL.OrderHistoryBL;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.Fragment.BuyFragment;
import com.shoping.Fragment.RentFragment;
import com.shoping.Fragment.SubscriptionFragment;

import java.util.Locale;

public class OrderHistory extends AppCompatActivity {

    ViewPager mViewPager;
    PagerSlidingTabStrip tabs;
    public static int width = 0;

    CustomPagerAdapter mCustomPagerAdapter;

    OrderHistoryBL objOrderHistoryBL;

    String userID;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        init();

        if(Util.isInternetConnection(OrderHistory.this)){
            new CallWS().execute(userID);
        }
    }

    private void init(){
        mViewPager= (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(2);
        tabs= (PagerSlidingTabStrip) findViewById(R.id.pager_tab_strip);


        tabs.setTextSize(23);
        tabs.setTextColor(getResources().getColor(R.color.white));
        tabs.setIndicatorColor(getResources().getColor(R.color.golden));
        tabs.setTabBackground(Color.parseColor("#000000"));

        tabs.setDividerColor(getResources().getColor(R.color.white));



       // tabs.setShouldExpand(true);
      /*
       tabs.setUnderlineColor(getResources().getColor(R.color.tabUnderLineColor));
       tabs.setUnderlineHeight(5);
        */
        tabs.setIndicatorHeight(8);

        userID=Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_LOGIN_ID);

        progressDialog=new ProgressDialog(OrderHistory.this);

        objOrderHistoryBL=new OrderHistoryBL();

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


    class CustomPagerAdapter extends FragmentPagerAdapter {

        Context mContext;

        public CustomPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {

            // Create fragment object
            switch (position) {
                case 0:
                    // Top Rated fragment activity
                    return new BuyFragment();
                case 1:
                    // Movies fragment activity
                    return new RentFragment();
                case 2:
                    // Top Rated fragment activity
                    return new SubscriptionFragment();




            }
            return null;
        }


        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.dealer_tab_one_title);
                case 1:
                    return getString(R.string.dealer_tab_two_title);
                case 2:
                    return getString(R.string.dealer_tab_three_title);



            }

            return null;
        }
    }

    private class CallWS extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            objOrderHistoryBL.getHistory(params[0],getApplicationContext());
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                mCustomPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), getApplicationContext());

                mViewPager.setAdapter(mCustomPagerAdapter);
                tabs.setViewPager(mViewPager);
            }
            catch (NullPointerException e){

            }catch (Exception e){

            }
            finally {
                progressDialog.dismiss();
            }
        }
    }
}

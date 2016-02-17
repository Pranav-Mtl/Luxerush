package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shoping.BE.ItemDetailBE;
import com.shoping.BE.SubscriptionItemDetailBE;
import com.shoping.BL.ItemDetailBL;
import com.shoping.BL.SubscriptionItemDetailBL;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.Container.GalleryPagerContainer;
import com.shoping.adapter.RelatedItemAdapter;
import com.squareup.picasso.Picasso;

public class SubscriptionItemDetail extends AppCompatActivity implements View.OnClickListener {

    GalleryPagerContainer mContainer;
    LinearLayout llSubscription;
    TextView tvSubscriptionOriginalPrice,tvSubscriptionPercentage,tvSubscriptionDiscountPrice;
    TextView tvDescription,tvMaterial,tvSize,tvCode,tvTitle;

    ProgressDialog progressDialog;

    ViewPager pager;

    Button btnSubscription;

    String productID,userID;

    SubscriptionItemDetailBE objSubscriptionItemDetailBE;
    SubscriptionItemDetailBL objSubscriptionItemDetailBL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_item_detail);
        initialize();

        if(Util.isInternetConnection(SubscriptionItemDetail.this)){
            new CallWS().execute(productID);
        }
    }

    private void initialize(){
        mContainer = (GalleryPagerContainer) findViewById(R.id.item_pager_container);

        progressDialog=new ProgressDialog(SubscriptionItemDetail.this);


        llSubscription= (LinearLayout) findViewById(R.id.detail_layout_subscription_price);

        tvTitle= (TextView) findViewById(R.id.detail_item_title);


        tvDescription= (TextView) findViewById(R.id.detail_dress_description);
        tvMaterial= (TextView) findViewById(R.id.detail_dress_material);
        tvSize= (TextView) findViewById(R.id.detail_dress_size);
        tvCode= (TextView) findViewById(R.id.detail_dress_code);

        btnSubscription= (Button) findViewById(R.id.detail_subscription_button);

        objSubscriptionItemDetailBE=new SubscriptionItemDetailBE();
        objSubscriptionItemDetailBL=new SubscriptionItemDetailBL();

        productID=getIntent().getStringExtra("ProductID").toString();

        userID=Util.getSharedPrefrenceValue(getApplicationContext(),Constant.SP_LOGIN_ID);

        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);


        btnSubscription.setOnClickListener(this);


        pager = mContainer.getViewPager();



        mContainer=new GalleryPagerContainer(getApplicationContext());





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



    private class CallWS extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            String result=objSubscriptionItemDetailBL.getSubsDetail(objSubscriptionItemDetailBE,params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try{

                tvTitle.setText(objSubscriptionItemDetailBE.getProductName());
                tvDescription.setText(objSubscriptionItemDetailBE.getDescription());
                tvMaterial.setText(objSubscriptionItemDetailBE.getProductType());
                tvSize.setText(objSubscriptionItemDetailBE.getSize());



                //A little space between pages

                ItemPageAdapter adapter=new ItemPageAdapter(getApplicationContext());
                pager.setAdapter(adapter);
                pager.setPadding(10,0,10,0);

                //If hardware acceleration is enabled, you should also remove
                // clipping on the pager for its children.


            }catch (NullPointerException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                progressDialog.dismiss();
            }
        }
    }

    private class ItemPageAdapter extends PagerAdapter {
        Context context;
        LayoutInflater inflater;
        ImageView imgPager;


        ItemPageAdapter(Context contex) {
            this.context = contex;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.list_pager_raw, container,
                    false);

            imgPager= (ImageView) itemView.findViewById(R.id.list_pager_image);

            Picasso.with(getApplicationContext())
                    .load(Constant.SubscriptionDetailImages[position])
                    .placeholder(R.drawable.ic_default_loading)
                    .resize(350,550)
                    .error(R.drawable.ic_default_loading)
                    .into(imgPager);

            //imgPager.setBackgroundResource(R.drawable.ic_default_popular_item);
            ((ViewPager) container).addView(itemView);

            return itemView;
        }

        @Override
        public int getCount() {
            if(Constant.SubscriptionDetailImages==null)
                return 0;

            return Constant.SubscriptionDetailImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        /*public float getPageWidth(int position)
        {
            return .9f;
        }*/

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_subscription_button:
                startActivity(new Intent(getApplicationContext(),SubscriptionCheckout.class).putExtra("ID",productID));
                break;
        }
    }



}

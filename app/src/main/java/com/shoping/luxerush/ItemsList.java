package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.Container.GalleryPagerContainer;
import com.shoping.adapter.PopularItemGridAdapter;
import com.squareup.picasso.Picasso;

public class ItemsList extends AppCompatActivity implements View.OnClickListener{

    RecyclerView rvGrid;

    ImageButton btnCart,btnWishList;

    private GridLayoutManager lLayout;

    PopularItemGridAdapter objPopularItemGridAdapter;

    Intent intent;
    String category,tag,brand;

    ProgressDialog progressDialog;

    String userID;

    FloatingActionButton fbFilter;

    String brands="",categoryFilter="",size="",min="",max="";

    GalleryPagerContainer mContainer;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initialize();

        if(Util.isInternetConnection(ItemsList.this)){
            new CallWS().execute(category,tag,userID,brand);
        }

    }

    private void initialize(){
        mContainer = (GalleryPagerContainer) findViewById(R.id.banner_pager_container);

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
        brand=intent.getStringExtra("Brand");


        pager = mContainer.getViewPager();
        mContainer=new GalleryPagerContainer(getApplicationContext());


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
                startActivityForResult(new Intent(getApplicationContext(), Filter.class), 1);
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
            objPopularItemGridAdapter=new PopularItemGridAdapter(getApplicationContext(),params[0],params[1],params[2],params[3]);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                rvGrid.setAdapter(objPopularItemGridAdapter);

                ItemPageAdapter adapter=new ItemPageAdapter(getApplicationContext());
                pager.setAdapter(adapter);
                pager.setPadding(0, 0, 0, 0);


            }catch (NullPointerException e){

            }catch (Exception e){

            }
            finally {
                progressDialog.dismiss();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK){

                brands=data.getStringExtra("Brands");
                categoryFilter=data.getStringExtra("Category");
                size=data.getStringExtra("Size");
                min=data.getStringExtra("MinPrice");
                max=data.getStringExtra("MaxPrice");

                if(Util.isInternetConnection(ItemsList.this)){
                    new CallWSFilter().execute(category,tag,userID,brands,categoryFilter,size,min,max);
                }
        }
    }

    private class CallWSFilter extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
        }

        @Override
        protected String doInBackground(String... params) {
            objPopularItemGridAdapter=new PopularItemGridAdapter(getApplicationContext(),params[0],params[1],params[2],params[3],params[4],params[5],params[6],params[7]);
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
                    .load(Constant.bannerImage[position])
                    .placeholder(R.drawable.ic_default_loading)
                    .error(R.drawable.ic_default_loading)
                    .into(imgPager);

            //imgPager.setBackgroundResource(R.drawable.ic_default_popular_item);
            ((ViewPager) container).addView(itemView);

            return itemView;
        }

        @Override
        public int getCount() {
            if(Constant.bannerID==null)
                return 0;

            return Constant.bannerID.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        public float getPageWidth(int position)
        {
            return 1f;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}

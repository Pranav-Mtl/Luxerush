package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.shoping.Configuration.RecyclerItemClickListener;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.Container.GalleryPagerContainer;
import com.shoping.adapter.DrawerAdapter;
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

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
    ActionBarDrawerToggle mDrawerToggle;

    DrawerAdapter drawerAdapter;

    View _itemColoured;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initialize();

        if(Util.isInternetConnection(ItemsList.this)){
            new CallWS().execute(category,tag,userID,brand);
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

                        if(position==0){
                            if(userID!=null){
                                startActivity(new Intent(getApplicationContext(),Profile.class));
                            }
                            else
                            {
                                startActivity(new Intent(getApplicationContext(), HowItWork.class));
                            }
                        }
                        if(position==1){
                            Drawer.closeDrawers();
                        }
                        if(position==2){
                            if(userID!=null){
                                startActivity(new Intent(getApplicationContext(),OrderHistory.class));
                            }
                            else
                            {
                                startActivity(new Intent(getApplicationContext(),HowItWork.class));
                            }
                        }
                        if(position==3){

                            Util.rateUs(getApplicationContext());

                        }
                        if(position==4){
                            if(userID!=null){
                                startActivity(new Intent(getApplicationContext(),Help.class));
                            }
                            else
                            {
                                startActivity(new Intent(getApplicationContext(),HowItWork.class));
                            }

                        }
                        if(position==5){
                            Util.setSharedPrefrenceValue(getApplicationContext(), Constant.PREFS_NAME, Constant.SP_LOGIN_ID, null);
                            Intent intent = new Intent(getApplicationContext(), HowItWork.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

                    }
                }));

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

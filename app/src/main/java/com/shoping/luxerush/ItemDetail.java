package com.shoping.luxerush;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.shoping.BE.ItemDetailBE;
import com.shoping.BL.ItemDetailBL;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.Container.GalleryPagerContainer;
import com.shoping.adapter.PopularItemGridAdapter;
import com.shoping.adapter.RelatedItemAdapter;
import com.squareup.picasso.Picasso;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ItemDetail extends AppCompatActivity implements View.OnClickListener {

    GalleryPagerContainer mContainer;
    LinearLayout llBuy,llRent,llSubscription;
    TextView tvTitle,tvBuyOriginalPrice,tvBuyPercentage,tvBuyDiscountPrice;
    TextView tvRentOriginalPrice,tvRentPercentage,tvRentDiscountPrice;
    TextView tvSubscriptionOriginalPrice,tvSubscriptionPercentage,tvSubscriptionDiscountPrice;
    TextView tvDescription,tvMaterial,tvSize,tvCode;
    TextView tvConditionText,tvCondition;

    TextView tvText;

    ImageButton btnCart,btnFB,btnTwitter,btnPinterest;

    Button btnAddCart,btnBuyRent,btnSubscription;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    String productID,category,tag,keyword;
    Intent intent;
    ItemDetailBL objItemDetailBL;

    ItemDetailBE objItemDetailBE;

    ViewPager pager;

    RecyclerView listCart;
    Animation anim;

    ProgressDialog progressDialog;

    String arrayImage[];

    RelatedItemAdapter objRelatedItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        initialize();

        if(Util.isInternetConnection(ItemDetail.this)){
            new CallWS().execute(category, tag, productID, keyword);
        }
    }

    private void initialize(){
        mContainer = (GalleryPagerContainer) findViewById(R.id.item_pager_container);

        progressDialog=new ProgressDialog(ItemDetail.this);

        llBuy= (LinearLayout) findViewById(R.id.detail_layout_buying_price);
        llRent= (LinearLayout) findViewById(R.id.detail_layout_rent_price);
        llSubscription= (LinearLayout) findViewById(R.id.detail_layout_subscription_price);

        tvTitle= (TextView) findViewById(R.id.detail_item_title);
        tvBuyOriginalPrice= (TextView) findViewById(R.id.detail_buying_original_price);
        tvBuyDiscountPrice= (TextView) findViewById(R.id.detail_buying_discount_price);

        tvBuyPercentage= (TextView) findViewById(R.id.detail_buying_price_percentage);


        tvRentOriginalPrice= (TextView) findViewById(R.id.detail_rent_original_price);
        tvRentDiscountPrice= (TextView) findViewById(R.id.detail_rent_discount_price);
        tvRentPercentage= (TextView) findViewById(R.id.detail_rent_price_percentage);

        tvCondition= (TextView) findViewById(R.id.detail_dress_condition);
        tvConditionText= (TextView) findViewById(R.id.detail_dress_condition_text);

        tvSubscriptionDiscountPrice= (TextView) findViewById(R.id.detail_subscription_discount_price);
        tvSubscriptionOriginalPrice= (TextView) findViewById(R.id.detail_subscription_original_price);
        tvSubscriptionPercentage= (TextView) findViewById(R.id.detail_subscription_price_percentage);

        tvDescription= (TextView) findViewById(R.id.detail_dress_description);
        tvMaterial= (TextView) findViewById(R.id.detail_dress_material);
        tvSize= (TextView) findViewById(R.id.detail_dress_size);
        tvCode= (TextView) findViewById(R.id.detail_dress_code);

        btnCart= (ImageButton) findViewById(R.id.home_screen_cart);
        btnFB= (ImageButton) findViewById(R.id.detail_share_fb);
        btnTwitter= (ImageButton) findViewById(R.id.detail_share_twitter);
        btnPinterest= (ImageButton) findViewById(R.id.detail_share_pinterest);

        btnAddCart= (Button) findViewById(R.id.detail_buying_button_cart);
        btnBuyRent= (Button) findViewById(R.id.detail_rent_button);
        btnSubscription= (Button) findViewById(R.id.detail_subscription_button);

        listCart= (RecyclerView) findViewById(R.id.recycle_related_item);

        listCart.setHasFixedSize(true);

        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);

        listCart.setLayoutManager(llm);

        btnCart.setOnClickListener(this);
        btnFB.setOnClickListener(this);
        btnTwitter.setOnClickListener(this);
        btnPinterest.setOnClickListener(this);
        btnAddCart.setOnClickListener(this);
        btnBuyRent.setOnClickListener(this);
        btnSubscription.setOnClickListener(this);

        objItemDetailBL=new ItemDetailBL();
        objItemDetailBE=new ItemDetailBE();

        intent=getIntent();
        productID=intent.getStringExtra("ProductID");
        category=intent.getStringExtra("Category");
        tag=intent.getStringExtra("Tag");
        keyword=intent.getStringExtra("Keyword");

        objItemDetailBE.setProductCategory(category);
        objItemDetailBE.setProductID(productID);

        if(category.equalsIgnoreCase(Constant.CATEGORY_BUY)){
            llRent.setVisibility(View.GONE);
            llSubscription.setVisibility(View.GONE);
            if(tag.equalsIgnoreCase(Constant.TAG_PRELOVED)){
                tvCondition.setVisibility(View.VISIBLE);
                tvConditionText.setVisibility(View.VISIBLE);
            }
        }
        else if(category.equalsIgnoreCase(Constant.CATEGORY_RENT)){
            llBuy.setVisibility(View.GONE);

            if(tag.equalsIgnoreCase(Constant.TAG_ALA_CARTE)) {
                btnBuyRent.setText("Add to cart");
                btnCart.setVisibility(View.VISIBLE);
            }

        }

        anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shape);
        btnCart.setAnimation(anim);

         pager = mContainer.getViewPager();



        mContainer=new GalleryPagerContainer(getApplicationContext());





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

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
            String result=objItemDetailBL.getDetail(params[0], params[1], params[2], params[3],objItemDetailBE);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try{

                tvTitle.setText(objItemDetailBE.getProductName());
                tvBuyDiscountPrice.setText("₹"+objItemDetailBE.getBuyingDiscountPrice());
                tvRentDiscountPrice.setText("₹"+objItemDetailBE.getRentDiscountPrice());
                tvDescription.setText(objItemDetailBE.getDescription());
                tvMaterial.setText(objItemDetailBE.getProductType());
                tvSize.setText(objItemDetailBE.getSize());
                tvSubscriptionDiscountPrice.setText("₹"+objItemDetailBE.getSubscriptionPrice());
                tvBuyPercentage.setText("₹"+objItemDetailBE.getBuyingDiscountPrice());
                tvRentPercentage.setText("₹"+objItemDetailBE.getRentDiscountPrice());

                tvBuyOriginalPrice.setText("₹"+objItemDetailBE.getBuyingPrice());
                tvRentOriginalPrice.setText("₹"+objItemDetailBE.getRentPrice());
                tvSubscriptionOriginalPrice.setText("₹"+objItemDetailBE.getSubscriptionPrice());

                tvCode.setText(objItemDetailBE.getCode());

                tvCondition.setText(objItemDetailBE.getProductCondition());

                objRelatedItemAdapter=new RelatedItemAdapter(getApplicationContext(),category,tag);
                listCart.setAdapter(objRelatedItemAdapter);

                arrayImage=objItemDetailBE.getProductImage();
                ItemPageAdapter adapter=new ItemPageAdapter(getApplicationContext());
                pager.setAdapter(adapter);
                pager.setPadding(10,0,10,0);
                //A little space between pages

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
            case R.id.home_screen_cart:
                startActivity(new Intent(getApplicationContext(),CartScreen.class).putExtra("ItemDetailBE",objItemDetailBE));
                break;
            case R.id.detail_share_fb:
                shareFB();
                break;
            case R.id.detail_share_twitter:
                if(checkTwitter()){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/*");

                    intent.putExtra(Intent.EXTRA_TEXT,"Hello");
                    intent.setPackage("com.twitter.android");
                    startActivity(intent);
                }
                else {
                    String tweetUrl =
                            String.format("https://twitter.com/intent/tweet?text=%s",
                                    urlEncode("HIIIIIIII"));
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));
                    startActivity(intent);
                    //Toast.makeText(getApplicationContext(),"You need to install twitter app first.",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.detail_share_pinterest:
                if(checkPinterest()) {
                    ShareCompat.IntentBuilder lIntentBuilder = ShareCompat.IntentBuilder.from(this);
                    Uri lPictureUri = null;
                    lPictureUri = Uri.fromFile(new File("mnt/sdcard/1.jpg"));
                    lIntentBuilder.setStream(lPictureUri);
                    String lType = null != lPictureUri ? "image/jpeg" : "text/plain";
                    lIntentBuilder.setType(lType);
                    lIntentBuilder.setText("My Description");
                    Intent shareIntent = lIntentBuilder.getIntent().setPackage("com.pinterest");
                    shareIntent.putExtra("com.pinterest.EXTRA_DESCRIPTION", "Messagessssssssss");
                    startActivity(shareIntent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"You need to install pinterest app first.",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.detail_buying_button_cart:
                Intent intentCart=new Intent(getApplicationContext(),SingleProductBuy.class);
                intentCart.putExtra("ItemDetailBE",objItemDetailBE);
                intentCart.putExtra("Tag",category);
                intentCart.putExtra("ID",productID);
                startActivity(intentCart);
                break;
            case R.id.detail_rent_button:
                if(tag.equalsIgnoreCase(Constant.TAG_ALA_CARTE)) {
                    String ss[]=objItemDetailBE.getProductImage();
                    String cartJson = Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_CART_ITEM);
                    if (cartJson == null || cartJson.trim().equalsIgnoreCase("[]")){
                        JSONObject js = new JSONObject();
                            js.put(Constant.KEY_Id, productID);
                            js.put(Constant.KEY_Name, objItemDetailBE.getProductName());
                            js.put(Constant.KEY_Image,ss[0]);
                            js.put(Constant.KEY_Size, objItemDetailBE.getSize());
                            js.put(Constant.KEY_Discount, objItemDetailBE.getRentDiscountPrice());
                            JSONArray ja = new JSONArray();
                            ja.add(js);
                            Log.d("JSON", ja.toString());

                        Util.setSharedPrefrenceValue(getApplicationContext(),Constant.PREFS_NAME,Constant.SP_CART_ITEM,ja.toString());
                } else
                    {
                        JSONObject js = new JSONObject();
                        js.put(Constant.KEY_Id, productID);
                        js.put(Constant.KEY_Name, objItemDetailBE.getProductName());
                        js.put(Constant.KEY_Image,ss[0]);
                        js.put(Constant.KEY_Size, objItemDetailBE.getSize());
                        js.put(Constant.KEY_Discount, objItemDetailBE.getRentDiscountPrice());

                        JSONParser jsonP=new JSONParser();
                        try {
                            Object obj = jsonP.parse(cartJson);
                            JSONArray jsonArrayObject = (JSONArray) obj;
                            jsonArrayObject.add(js);
                            Log.d("JSON", jsonArrayObject.toString());
                            Util.setSharedPrefrenceValue(getApplicationContext(), Constant.PREFS_NAME, Constant.SP_CART_ITEM, jsonArrayObject.toString());
                        }catch (Exception e){

                        }
                    }
                    Toast.makeText(getApplicationContext(),"Product Added to cart",Toast.LENGTH_SHORT).show();

                    btnCart.startAnimation(anim);

                }
                else if(tag.equalsIgnoreCase(Constant.TAG_RENT)) {
                    Intent intentRent = new Intent(getApplicationContext(), SingleProductBuy.class);
                    intentRent.putExtra("ItemDetailBE", objItemDetailBE);
                    startActivity(intentRent);
                }
                break;
            case R.id.detail_subscription_button:
                Intent intentSubscription=new Intent(getApplicationContext(),SingleProductBuy.class);
                intentSubscription.putExtra("ItemDetailBE",objItemDetailBE);
                intentSubscription.putExtra("Tag",category);
                intentSubscription.putExtra("ID",productID);
                startActivity(intentSubscription);
                break;
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
                    .load(arrayImage[position])
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
            if(arrayImage==null)
            return 0;

            return arrayImage.length;
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

    private void shareFB(){
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Hello Facebook")
                    .setContentDescription(
                            "The 'Hello Facebook' sample  showcases simple Facebook integration")
                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                    .build();

            shareDialog.show(linkContent);
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public boolean checkTwitter()
    {
        boolean flag=false;
        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo("com.twitter.android", 0);
            flag= true;
        } catch (PackageManager.NameNotFoundException e) {
            flag= false;
        }
        return flag;
    }

    public boolean checkPinterest()
    {
        boolean flag=false;
        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo("com.pinterest", 0);
            flag= true;
        } catch (PackageManager.NameNotFoundException e) {
            flag= false;
        }
        return flag;
    }

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            Log.d("TAG", "UTF-8 should always be supported", e);
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }

}

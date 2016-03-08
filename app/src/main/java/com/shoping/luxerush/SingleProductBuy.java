package com.shoping.luxerush;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoping.BE.ItemDetailBE;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.squareup.picasso.Picasso;

public class SingleProductBuy extends AppCompatActivity implements View.OnClickListener {

    ImageView ivImage;
    TextView tvTitle,tvOriginal,tvDiscount,tvSize,tvColor;

    ItemDetailBE objItemDetailBE;
    Button btnDone;

    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product_buy);

        initialize();
    }

    private void initialize(){
       ivImage= (ImageView) findViewById(R.id.item_image);
        tvTitle= (TextView) findViewById(R.id.item_title);
        tvOriginal= (TextView) findViewById(R.id.item_original_price);
        tvDiscount= (TextView) findViewById(R.id.item_discount_price);
        tvSize= (TextView) findViewById(R.id.item_size);
        tvColor= (TextView) findViewById(R.id.item_color);

        btnDone= (Button) findViewById(R.id.cart_done);


        btnDone.setOnClickListener(this);

        objItemDetailBE= (ItemDetailBE) getIntent().getSerializableExtra("ItemDetailBE");

        userID= Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_LOGIN_ID);

        String ss[]=objItemDetailBE.getProductImage();
        try {
            Picasso.with(getApplicationContext())
                    .load(ss[0])
                    .placeholder(R.drawable.ic_default_loading)
                    .resize(275, 525)
                    .error(R.drawable.ic_default_loading)
                    .into(ivImage);
        }catch (Exception e){

        }

        tvTitle.setText(objItemDetailBE.getProductName());
        tvOriginal.setText("₹" +objItemDetailBE.getBuyingPrice());
        tvDiscount.setText("₹"+objItemDetailBE.getBuyingDiscountPrice());
        tvSize.setText("Size: "+objItemDetailBE.getSize());
        tvColor.setText("Color: "+objItemDetailBE.getColor());


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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cart_done:
                if(userID!=null) {
                    Intent intentRent=new Intent(getApplicationContext(),CheckOut.class);
                    intentRent.putExtra("ItemDetailBE",objItemDetailBE);
                    startActivity(intentRent);
                }
                else
                {
                    startActivity(new Intent(getApplicationContext(),Login.class));
                }
                break;
        }
    }
}

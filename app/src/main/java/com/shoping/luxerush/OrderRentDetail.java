package com.shoping.luxerush;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.shoping.adapter.OrderRentDetailAdapter;

public class OrderRentDetail extends AppCompatActivity {

    RecyclerView listCart;

    OrderRentDetailAdapter objOrderRentDetailAdapter;

    String productJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_rent_detail);
        init();
    }

    private void init(){
        listCart= (RecyclerView) findViewById(R.id.recycle_rent_products);
        listCart.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        listCart.setLayoutManager(llm);

        productJson=getIntent().getStringExtra("Product");
        objOrderRentDetailAdapter=new OrderRentDetailAdapter(getApplicationContext(),productJson);
        listCart.setAdapter(objOrderRentDetailAdapter);

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
}

package com.shoping.luxerush;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.shoping.BE.ItemDetailBE;
import com.shoping.adapter.CartAdapter;

public class CartScreen extends AppCompatActivity implements OnClickListener {

    RecyclerView listCart;

    TextView tvItem,tvTotal;

    CartAdapter objCartAdapter;

    Button btnDone;

    ItemDetailBE objItemDetailBE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_screen);
        initialize();

        objCartAdapter=new CartAdapter(getApplicationContext());
        listCart.setAdapter(objCartAdapter);
    }


    private void initialize(){

        listCart= (RecyclerView) findViewById(R.id.cart_list);

        tvTotal= (TextView) findViewById(R.id.cart_total);
        btnDone= (Button) findViewById(R.id.cart_done);


        btnDone.setOnClickListener(this);


        listCart.setHasFixedSize(true);

        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        listCart.setLayoutManager(llm);

        objItemDetailBE= (ItemDetailBE) getIntent().getSerializableExtra("ItemDetailBE");




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
                String id="";
                if(objCartAdapter.listId!=null){
                   if(objCartAdapter.listId.size()>0){
                       Log.d("Cart Size",objCartAdapter.listId.size()+"");
                       for(int i=0;i<objCartAdapter.listId.size();i++){
                           if(i==0){
                               id=objCartAdapter.listId.get(i).toString();
                           }
                           else {
                               id = id + "," + objCartAdapter.listId.get(i).toString();
                           }

                           Log.d("Cart Item Name",objCartAdapter.listId.get(i).toString());
                       }
                       objItemDetailBE.setProductID(id);
                       startActivity(new Intent(getApplicationContext(), CheckOut.class).putExtra("ItemDetailBE", objItemDetailBE));
                   }

                }

                break;
        }
    }
}

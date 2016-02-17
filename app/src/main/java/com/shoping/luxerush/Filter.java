package com.shoping.luxerush;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Filter extends AppCompatActivity implements View.OnClickListener{

    RelativeLayout rlBrand,rlCategory,rlSize;

    Button btnDone;
    String brands="",category="",size="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        initialize();
    }


    private void initialize(){
        rlBrand= (RelativeLayout) findViewById(R.id.filter_brand_rl);
        rlCategory= (RelativeLayout) findViewById(R.id.filter_category_rl);
        rlSize= (RelativeLayout) findViewById(R.id.filter_size_rl);
        btnDone= (Button) findViewById(R.id.filter_apply);

        rlBrand.setOnClickListener(this);
        rlCategory.setOnClickListener(this);
        rlSize.setOnClickListener(this);
        btnDone.setOnClickListener(this);


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
            case R.id.filter_brand_rl:
                startActivityForResult(new Intent(getApplicationContext(),FilterBrand.class),1);
                break;
            case R.id.filter_category_rl:
                startActivityForResult(new Intent(getApplicationContext(),FilterCategory.class),2);
                break;
            case R.id.filter_size_rl:
                startActivityForResult(new Intent(getApplicationContext(),FilterSize.class),3);
                break;
            case R.id.filter_apply:
                Log.d("Brands",brands);
                Log.d("Category",category);
                Log.d("Size",size);
               // finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK){
            brands=data.getStringExtra("Brands");

        }
        if(requestCode==2 && resultCode==RESULT_OK){
            category=data.getStringExtra("Category");

        }
        if(requestCode==3 && resultCode==RESULT_OK){
            size=data.getStringExtra("Size");

        }
    }
}

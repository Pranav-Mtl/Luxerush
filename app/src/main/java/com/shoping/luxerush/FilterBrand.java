package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.shoping.adapter.FilterBrandAdapter;
import com.shoping.adapter.SubscriptionPackageAdapter;

public class FilterBrand extends AppCompatActivity implements View.OnClickListener {

    RecyclerView listBrand;

    ProgressDialog progressDialog;
    Button btnDone;

    FilterBrandAdapter objFilterBrandAdapter;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_brand);
        init();
    }

    private void init(){
        listBrand= (RecyclerView) findViewById(R.id.filter_brand);
        btnDone= (Button) findViewById(R.id.filter_brand_apply);

        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listBrand.setLayoutManager(llm);

        progressDialog=new ProgressDialog(FilterBrand.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent=getIntent();

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnDone.setOnClickListener(this);

        objFilterBrandAdapter=new FilterBrandAdapter(getApplicationContext());
        listBrand.setAdapter(objFilterBrandAdapter);

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
            case R.id.filter_brand_apply:
                if(objFilterBrandAdapter.mSelectedItem!=-1){
                    String values=objFilterBrandAdapter.hashMap.values().toString();
                    values=values.replace("[","").replace("]","");
                    intent.putExtra("Brands",values);
                    setResult(RESULT_OK,intent);
                    finish();
                }

                break;
        }
    }
}

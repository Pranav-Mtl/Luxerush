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
import com.shoping.adapter.FilterSizeAdapter;

public class FilterSize extends AppCompatActivity implements View.OnClickListener {

    RecyclerView listSize;

    ProgressDialog progressDialog;
    Button btnDone;

    FilterSizeAdapter objFilterSizeAdapter;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_size);
        init();
    }

    private void init(){
        listSize= (RecyclerView) findViewById(R.id.filter_size);
        btnDone= (Button) findViewById(R.id.filter_size_apply);

        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listSize.setLayoutManager(llm);

        progressDialog=new ProgressDialog(FilterSize.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent=getIntent();

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnDone.setOnClickListener(this);

        objFilterSizeAdapter=new FilterSizeAdapter(getApplicationContext());
        listSize.setAdapter(objFilterSizeAdapter);

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
            case R.id.filter_size_apply:
                if(objFilterSizeAdapter.mSelectedItem!=-1){
                    String values=objFilterSizeAdapter.hashMap.values().toString();
                    values=values.replace("[","").replace("]","");
                    intent.putExtra("Size",values);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
        }
    }
}

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
import com.shoping.adapter.FilterCategoryAdapter;

public class FilterCategory extends AppCompatActivity implements View.OnClickListener {

    RecyclerView listCategory;

    ProgressDialog progressDialog;
    Button btnDone;

    FilterCategoryAdapter objFilterCategoryAdapter;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_category);
        init();
    }

    private void init(){
        listCategory= (RecyclerView) findViewById(R.id.filter_category);
        btnDone= (Button) findViewById(R.id.filter_category_apply);

        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listCategory.setLayoutManager(llm);

        intent=getIntent();

        progressDialog=new ProgressDialog(FilterCategory.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnDone.setOnClickListener(this);

        objFilterCategoryAdapter=new FilterCategoryAdapter(getApplicationContext());
        listCategory.setAdapter(objFilterCategoryAdapter);

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
            case R.id.filter_category_apply:
                if(objFilterCategoryAdapter.mSelectedItem!=-1){
                    String values=objFilterCategoryAdapter.hashMap.values().toString();
                    values=values.replace("[","").replace("]","");
                    intent.putExtra("Category",values);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
        }
    }
}

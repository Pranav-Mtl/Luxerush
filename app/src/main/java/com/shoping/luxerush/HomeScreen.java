package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.GridView;

import com.shoping.adapter.PopularItemAdapter;
import com.shoping.adapter.PopularItemGridAdapter;

public class HomeScreen extends AppCompatActivity {

RecyclerView rvGrid;

    private GridLayoutManager lLayout;

    PopularItemGridAdapter objPopularItemGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initialize();

        objPopularItemGridAdapter=new PopularItemGridAdapter(getApplicationContext());
        rvGrid.setAdapter(objPopularItemGridAdapter);
    }

    private void initialize(){


        rvGrid= (RecyclerView) findViewById(R.id.recycle_popular_item);


        rvGrid.setHasFixedSize(true);


        lLayout = new GridLayoutManager(HomeScreen.this, 2);

        rvGrid.setLayoutManager(lLayout);
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

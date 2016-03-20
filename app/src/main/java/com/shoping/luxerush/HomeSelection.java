package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.shoping.BL.HomeSelectionBL;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.adapter.HomeSelectionAdapter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class HomeSelection extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rvGrid;
    LinearLayout btnDress,btnBag;
    private GridLayoutManager lLayout;
    String userID;
    ProgressDialog progressDialog;

    HomeSelectionAdapter objHomeSelectionAdapter;

    HomeSelectionBL objHomeSelectionBL;

    String jsonALL,jsonBAG,jsonCLOTH;

    boolean flagALL,flagBAG,flagCLOTH;

    LinearLayout llHomeSelection;

    Intent intent;
    String category,tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_selection);
        init();

        if(Util.isInternetConnection(getApplicationContext())){
            new CallWs().execute("all");
        }
    }

    private void init(){
        rvGrid= (RecyclerView) findViewById(R.id.recycle_brand_list);
        btnBag= (LinearLayout) findViewById(R.id.home_selection_bag);
        btnDress= (LinearLayout) findViewById(R.id.home_selection_dress);
        llHomeSelection= (LinearLayout) findViewById(R.id.home_selection_done);

        rvGrid.setHasFixedSize(true);

        lLayout = new GridLayoutManager(HomeSelection.this, 2);

        rvGrid.setLayoutManager(lLayout);

        userID= Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_LOGIN_ID);

        progressDialog=new ProgressDialog(HomeSelection.this);
        objHomeSelectionBL=new HomeSelectionBL();

        intent=getIntent();
        category=intent.getStringExtra("Category");
        tag=intent.getStringExtra("Tag");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnDress.setOnClickListener(this);
        btnBag.setOnClickListener(this);
        llHomeSelection.setOnClickListener(this);
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
            case R.id.home_selection_bag:
                if(!flagBAG){
                    parseJSON(jsonBAG);
                    flagBAG=true;
                }
                else {
                    parseJSON(jsonALL);
                    flagBAG=false;
                }
                objHomeSelectionAdapter.notifyDataSetChanged();
                break;
            case R.id.home_selection_dress:
                if(!flagCLOTH) {
                    parseJSON(jsonCLOTH);
                    flagCLOTH=true;
                }
                else {
                    parseJSON(jsonALL);
                    flagCLOTH=false;
                }
                objHomeSelectionAdapter.notifyDataSetChanged();
                break;
            case R.id.home_selection_done:
                String values;
                if(objHomeSelectionAdapter.selectedBrand==null){
                    values="";
                }else{
                     values=objHomeSelectionAdapter.selectedBrand.values().toString().replace("[","").replace("]","");
                }
                Intent intentBuy=new Intent(getApplicationContext(),ItemsList.class);
                intentBuy.putExtra("Category",category);
                intentBuy.putExtra("Tag",tag);
                intentBuy.putExtra("Brand",values);
                startActivity(intentBuy);
                break;
        }
    }

    private class CallWs extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
        }

        @Override
        protected String doInBackground(String... params) {
            objHomeSelectionBL.getBrandsList(getApplicationContext());
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                jsonALL=Util.getSharedPrefrenceValue(getApplicationContext(),Constant.SP_PRODUCT_ALL);
                jsonBAG=Util.getSharedPrefrenceValue(getApplicationContext(),Constant.SP_PRODUCT_BAG);
                jsonCLOTH=Util.getSharedPrefrenceValue(getApplicationContext(),Constant.SP_PRODUCT_CLOTH);
                parseJSON(jsonALL);
                objHomeSelectionAdapter=new HomeSelectionAdapter(getApplicationContext());
                rvGrid.setAdapter(objHomeSelectionAdapter);
            }catch (NullPointerException e){

            }catch(Exception e){

            }
            finally {
                progressDialog.dismiss();
            }
        }
    }

    private void parseJSON(String result){
        JSONParser jsonP=new JSONParser();

        try {
            Object obj =jsonP.parse(result);
            JSONArray jsonArrayObject = (JSONArray)obj;

            Constant.allBrandID=new String[jsonArrayObject.size()];
            Constant.allBrandName=new String[jsonArrayObject.size()];
            Constant.allBrandImage=new String[jsonArrayObject.size()];

            for(int i=0;i<jsonArrayObject.size();i++) {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                Constant.allBrandID[i]=jsonObject.get("brand_id").toString();
                Constant.allBrandName[i]=jsonObject.get("brand_name").toString();
                Constant.allBrandImage[i]=jsonObject.get("brand_image").toString();

            }



        } catch (Exception e) {


        }


    }
}

package com.shoping.luxerush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class HomeScreenOptions extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llAllOption;
    ImageButton btnCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_options);
        initialize();
    }

    private void initialize(){
        llAllOption= (LinearLayout) findViewById(R.id.home_options_all);
        btnCart= (ImageButton) findViewById(R.id.home_screen_cart);

        btnCart.setOnClickListener(this);

        llAllOption.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.home_options_all:
                startActivity(new Intent(getApplicationContext(),HomeScreen.class));
                break;
            case R.id.home_screen_cart:
                startActivity(new Intent(getApplicationContext(),CartScreen.class));
                break;
        }
    }
}

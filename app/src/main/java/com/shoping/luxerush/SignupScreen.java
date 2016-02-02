package com.shoping.luxerush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignupScreen extends AppCompatActivity implements View.OnClickListener{

    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        initialize();
    }

    private void initialize(){
        tvLogin= (TextView) findViewById(R.id.signup_signin);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup_signin:
                startActivity(new Intent(getApplicationContext(),Login.class));
                break;
        }
    }
}

package com.shoping.luxerush;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shoping.luxerush.R;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {
    EditText etEmail;
    Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initialize();
    }
    private void initialize(){
        etEmail= (EditText) findViewById(R.id.forgot_email);
        btnDone= (Button) findViewById(R.id.forgot_done);

        btnDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forgot_done:

                break;
        }
    }

    private class CallWS extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }
}

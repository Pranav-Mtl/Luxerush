package com.shoping.luxerush;

import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.pinterest.android.pdk.PDKClient;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;

public class SplashActivity extends AppCompatActivity {

    String userID;

    int SPLASH_TIME = 2000;

    String deviceId;

    String YOUR_APP_ID="4816696115734785333";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        PDKClient.configureInstance(this,YOUR_APP_ID);
        PDKClient.getInstance().onConnect(this);

        deviceId= Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_DEVICE_ID);
        userID=Util.getSharedPrefrenceValue(getApplicationContext(),Constant.SP_LOGIN_ID);


        if(deviceId==null){
            String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            Util.setSharedPrefrenceValue(getApplicationContext(),Constant.PREFS_NAME,Constant.SP_DEVICE_ID,android_id);

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    if(userID==null) {
                        startActivity(new Intent(getApplicationContext(), HowItWork.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                    }else{
                        startActivity(new Intent(getApplicationContext(), HomeScreenOptions.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                    }
                }

        }, SPLASH_TIME);
    }

}

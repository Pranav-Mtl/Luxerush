package com.shoping.BL;


import android.util.Log;

import com.shoping.Constant.Constant;
import com.shoping.WS.RestFullWS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by appslure on 08-12-2015.
 */
public class SubscriptionPackageBL {

    /*  Fetch All Routes when user comes first time  */
    public String getAllPackages()
    {
        String result=callWS();
        parseMonthlyJson(result);
        return "";
    }
    private String callWS()
    {
        String URL="";
        String txtJson= RestFullWS.serverRequest(URL, Constant.WS_SUBSCRIPTION_PACKAGE);
        return txtJson;
    }

    private void parseMonthlyJson(String result){
        JSONParser jsonP=new JSONParser();
        try {
            Object obj =jsonP.parse(result);
            JSONArray jsonArrayObject = (JSONArray)obj;

            Constant.packageID=new String[jsonArrayObject.size()];
            Constant.packageName=new String[jsonArrayObject.size()];
            Constant.packageClothes=new String[jsonArrayObject.size()];
            Constant.packageBags=new String[jsonArrayObject.size()];
            Constant.packagePrice=new String[jsonArrayObject.size()];

            for(int i=0;i<jsonArrayObject.size();i++) {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());

                Constant.packageID[i]=jsonObject.get("package_id").toString();
                Constant.packageName[i]=jsonObject.get("package_name").toString();
                Constant.packageClothes[i]=jsonObject.get("cloths_allow").toString();
                Constant.packageBags[i]=jsonObject.get("bags_allow").toString();
                Constant.packagePrice[i]=jsonObject.get("package_value").toString();
            }

           /* JSONObject js = new JSONObject();
            js.put("Key","hello");
            js.put("Key1","hello1");
            js.put("Key2","hello2");
            JSONArray ja = new JSONArray();
            ja.add(js);
            Log.d("JSON",ja.toString());*/




        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

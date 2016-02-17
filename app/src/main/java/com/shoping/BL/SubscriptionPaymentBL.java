package com.shoping.BL;

import android.content.Context;

import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.WS.RestFullWS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by appslure on 15-02-2016.
 */
public class SubscriptionPaymentBL {

    Context mContext;
    public String getSubscription(String userID,String packageID,String paymenType,Context context){

        mContext=context;
        String result=callWSSubscription(userID, packageID, paymenType);
        String status=validateSubscription(result);

        return status;
    }

    private String callWSSubscription(String userID,String packageID,String paymenType) {
        String text = "";

        //appslure.in/luxerush/webservices/user_subscribe.php?package_id=1&user_id=1

        try
        {

            String URL="user_id="+userID+"&package_id="+packageID+"&payment_type="+paymenType;
            text= RestFullWS.serverRequest(URL, Constant.WS_TAKE_SUBSCRIPTION);

        }
        catch (Exception e)
        {

        }
        return text;
    }

    private String validateSubscription(String strValue)    {
        String status="";
        JSONParser jsonP=new JSONParser();

        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());
            status=jsonObject.get("result").toString();



        } catch (Exception e) {


        }
        return status;
    }
}

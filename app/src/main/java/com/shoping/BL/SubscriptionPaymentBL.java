package com.shoping.BL;

import android.content.Context;
import android.util.Log;

import com.shoping.BE.BuyWebViewBE;
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
    BuyWebViewBE objBuyWebViewBE;
    public String getSubscription(String userID,String packageID,String paymenType,Context context,BuyWebViewBE buyWebViewBE){
        objBuyWebViewBE=buyWebViewBE;
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
        boolean status=false;
        JSONParser jsonP=new JSONParser();

        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());
            status=(boolean)jsonObject.get("success");

            objBuyWebViewBE.setStatus((boolean) jsonObject.get("success"));
            Log.d("STATUS URL-->", status + "");

            if(status)
            {

                /*String link=jsonObject.get("link").toString();
                Log.d("link URL-->",link);*/

                objBuyWebViewBE.setName(jsonObject.get("name").toString());
                objBuyWebViewBE.setPhone(jsonObject.get("phone").toString());
                objBuyWebViewBE.setEmail(jsonObject.get("email").toString());

                jsonObject=(JSONObject)jsonP.parse(jsonObject.get("link").toString());
                objBuyWebViewBE.setUrl(jsonObject.get("url").toString());


            }



        } catch (Exception e) {


        }
        return "";
    }
}

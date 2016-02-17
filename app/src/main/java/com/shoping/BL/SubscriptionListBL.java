package com.shoping.BL;

import android.content.Context;

import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.WS.RestFullWS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by appslure on 16-02-2016.
 */
public class SubscriptionListBL {

    public String getSubsList(String subID){
        String result=callWS(subID);
        validateDetails(result);

        return "";
    }

    private String callWS(String subID) {
        String text = "";
        try
        {

            String URL="subscription_id="+subID;
            text= RestFullWS.serverRequest(URL, Constant.WS_SUBSCRIPTION_LIST);

        }
        catch (Exception e)
        {

        }
        return text;
    }

    private String validateDetails(String strValue)    {
        JSONParser jsonP=new JSONParser();
        String status="";
        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            Constant.productIDSubs=new String[jsonArrayObject.size()];
            Constant.productNameSubs=new String[jsonArrayObject.size()];
            Constant.productImageSubs=new String[jsonArrayObject.size()];
            Constant.productDiscountedPriceSubs=new String[jsonArrayObject.size()];

            for(int i=0;i<jsonArrayObject.size();i++) {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                Constant.productIDSubs[i]=jsonObject.get("product_id").toString();
                Constant.productNameSubs[i]=jsonObject.get("product_name").toString();
                Constant.productImageSubs[i]=jsonObject.get("product_image").toString();
                Constant.productDiscountedPriceSubs[i]=jsonObject.get("discount_price").toString();

            }

        } catch (Exception e) {


        }
        return status;
    }
}

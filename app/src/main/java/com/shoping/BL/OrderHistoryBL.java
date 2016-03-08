package com.shoping.BL;

import android.content.Context;

import com.shoping.BE.HomeScreenBE;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.WS.RestFullWS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by appslure on 18-02-2016.
 */
public class OrderHistoryBL {
    Context mContext;
    public void getHistory(String userID,Context context){
        mContext=context;
        String result=callWS(userID);
        validate(result);

    }

    private String callWS(String userID) {
        String text = "";
        try
        {

            String URL="user_id="+userID;
            text= RestFullWS.serverRequest(URL, Constant.WS_ORDER_HISTORY);

        }
        catch (Exception e)
        {

        }
        return text;
    }

    private void validate(String strValue)    {
        String buy="",rent="",subscription="";
        JSONParser jsonP=new JSONParser();

        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());
            buy=jsonObject.get("buy_order").toString();
            rent=jsonObject.get("rent_order").toString();
            subscription=jsonObject.get("subscribe_order").toString();


            Util.setSharedPrefrenceValue(mContext,Constant.PREFS_NAME,Constant.SP_ORDER_BUY,buy);
            Util.setSharedPrefrenceValue(mContext,Constant.PREFS_NAME,Constant.SP_ORDER_RENT,rent);
            Util.setSharedPrefrenceValue(mContext,Constant.PREFS_NAME,Constant.SP_ORDER_SUBSCRIPTION,subscription);


        } catch (Exception e) {


        }

    }

}



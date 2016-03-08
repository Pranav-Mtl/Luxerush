package com.shoping.BL;

import android.content.Context;

import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.WS.RestFullWS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by appslure on 26-02-2016.
 */
public class HomeSelectionBL {

    Context mContext;
    public String getBrandsList(Context context){
        mContext=context;
        String result=callWS();
        validate(result);

        return "";
    }

    private String callWS(){
        String text = "";
        try
        {

            String URL="";
            text= RestFullWS.serverRequest(URL, Constant.WS_BRAND_ALL);

        }
        catch (Exception e)
        {

        }
        return text;
    }

    private void validate(String strValue)    {
        JSONParser jsonP=new JSONParser();
        String details="",bag,cloth,size,minPrice,maxPrice,banner;
        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;


                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());
                details=jsonObject.get("all_brands").toString();
                bag=jsonObject.get("bags").toString();
                cloth=jsonObject.get("clothes").toString();

                Util.setSharedPrefrenceValue(mContext,Constant.PREFS_NAME,Constant.SP_PRODUCT_ALL,details);
                Util.setSharedPrefrenceValue(mContext,Constant.PREFS_NAME,Constant.SP_PRODUCT_BAG,bag);
                Util.setSharedPrefrenceValue(mContext,Constant.PREFS_NAME,Constant.SP_PRODUCT_CLOTH,cloth);


        } catch (Exception e) {


        }

    }

}

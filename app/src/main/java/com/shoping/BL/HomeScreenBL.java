package com.shoping.BL;

import com.shoping.BE.HomeScreenBE;
import com.shoping.Constant.Constant;
import com.shoping.WS.RestFullWS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by appslure on 15-02-2016.
 */
public class HomeScreenBL {
    HomeScreenBE objHomeScreenBE;
    public String homeScreenBL(String userID,HomeScreenBE homeScreenBE){
        objHomeScreenBE=homeScreenBE;
        String result=callWS(userID);
        String status=validate(result);

        return status;
    }

    private String callWS(String userID) {
        String text = "";
        try
        {

            String URL="user_id="+userID;
            text= RestFullWS.serverRequest(URL, Constant.WS_GET_USER_DETAIL);

        }
        catch (Exception e)
        {

        }
        return text;
    }

    private String validate(String strValue)    {
        String status="";
        JSONParser jsonP=new JSONParser();

        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());
            status=jsonObject.get("personal_detail").toString();

            obj =jsonP.parse(status);
            jsonArrayObject = (JSONArray)obj;
            jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());

            objHomeScreenBE.setName(jsonObject.get("name").toString());
            objHomeScreenBE.setMobile(jsonObject.get("phone").toString());
            objHomeScreenBE.setSubscribe(jsonObject.get("user_subscribe").toString());
            objHomeScreenBE.setSubscribtionID(jsonObject.get("subscription_id").toString());

        } catch (Exception e) {


        }
        return status;
    }

}

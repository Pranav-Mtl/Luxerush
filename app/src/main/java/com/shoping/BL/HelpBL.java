package com.shoping.BL;

import android.content.Context;

import com.shoping.BE.SigninBE;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.WS.RestFullWS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by appslure on 20-03-2016.
 */
public class HelpBL {

    public String help(String userID,String comment) {


        String result = callWS(userID,comment);
        String finalvalue = validate(result);
        return finalvalue;
    }

    private String callWS(String userID,String comment) {
        String text = null;
        try
        {

            String URL="user_id="+userID+"&comment="+comment;
            text= RestFullWS.serverRequest(URL, Constant.WS_HELP);

        }
        catch (Exception e)
        {

        }
        return text;
    }

    public String validate(String strValue)    {
        JSONParser jsonP=new JSONParser();
        String status="";
        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            JSONObject jsonObject=(JSONObject)jsonP.parse(jsonArrayObject.get(0).toString());
            status=jsonObject.get("result").toString();






        } catch (Exception e) {


        }
        return status;
    }


}

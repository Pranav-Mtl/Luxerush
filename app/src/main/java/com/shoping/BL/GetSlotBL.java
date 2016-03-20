package com.shoping.BL;

import android.content.Context;

import com.shoping.BE.SigninBE;
import com.shoping.Constant.Constant;
import com.shoping.WS.RestFullWS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

/**
 * Created by pranav.mittal on 3/10/2016.
 */
public class GetSlotBL {
    public ArrayList listSlots;
    public String GetSlots(Context context) {

        listSlots=new ArrayList();
        listSlots.add("Select Slot");
        String result = callWS();
        String finalvalue = validate(result);
        return finalvalue;
    }

    private String callWS() {
        String text = null;
        try
        {

            String URL="";
            text= RestFullWS.serverRequest(URL, Constant.WS_Get_Slots);

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

            for(int i=0;i<jsonArrayObject.size();i++) {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                listSlots.add(jsonObject.get("slot").toString());
            }


        } catch (Exception e) {


        }
        return status;
    }
}

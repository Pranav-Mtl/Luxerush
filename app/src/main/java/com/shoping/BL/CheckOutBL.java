package com.shoping.BL;

import com.shoping.BE.CheckOutBE;
import com.shoping.Constant.Constant;
import com.shoping.WS.RestFullWS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by appslure on 10-02-2016.
 */
public class CheckOutBL {

    CheckOutBE objCheckOutBE;
    public List listStates;

    public void getUserDetails(String userID,CheckOutBE checkOutBE){

        objCheckOutBE=checkOutBE;
        listStates=new ArrayList();
        listStates.add("Select State");
        String result=callWS(userID);
        validate(result);


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

    private void validate(String strValue)    {
        String personalDetail="",state="";
        JSONParser jsonP=new JSONParser();

        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());

            personalDetail=jsonObject.get("personal_detail").toString();
            state=jsonObject.get("city").toString();
            validateDetails(personalDetail);
            validateStates(state);

        } catch (Exception e) {


        }

    }
    private void validateDetails(String strValue)    {
        String personalDetail="",state="";
        JSONParser jsonP=new JSONParser();

        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());

           objCheckOutBE.setName(jsonObject.get("name").toString());
            objCheckOutBE.setEmail(jsonObject.get("email").toString());
            objCheckOutBE.setAddress(jsonObject.get("address").toString());
            objCheckOutBE.setZip(jsonObject.get("pincode").toString());
            objCheckOutBE.setCity(jsonObject.get("city").toString());
            objCheckOutBE.setMobile(jsonObject.get("phone").toString());

        } catch (Exception e) {


        }

    }

    private void validateStates(String strValue)    {
        String personalDetail="",state="";
        JSONParser jsonP=new JSONParser();

        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            for(int i=0;i<jsonArrayObject.size();i++) {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                listStates.add(jsonObject.get("city").toString());

            }

        } catch (Exception e) {


        }

    }

}

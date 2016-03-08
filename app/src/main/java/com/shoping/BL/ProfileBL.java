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
 * Created by appslure on 17-02-2016.
 */
public class ProfileBL {

    CheckOutBE objCheckOutBE;

    public String updateDetails(String userID,CheckOutBE checkOutBE){

        objCheckOutBE=checkOutBE;
        String result=callWS(userID);
        String status=validate(result);

        return status;


    }

    private String callWS(String userID) {
        String text = "";
        try
        {
            //http://appslure.in/luxerush/webservices/profile_updated.php?user_id=7&email=pk@gmail.com&phone=9818182346&street_address=test%20address&city=Delhi&npicode=113345
            String URL="user_id="+userID+"&email="+objCheckOutBE.getEmail()+"&phone="+objCheckOutBE.getMobile()+"&street_address="+objCheckOutBE.getAddress()+"&city="+objCheckOutBE.getCity()+"&pincode="+objCheckOutBE.getZip();
            text= RestFullWS.serverRequest(URL, Constant.WS_UPDATE_PROFILE);

        }
        catch (Exception e)
        {

        }
        return text;
    }

    private String validate(String strValue)    {
        String status="",state="";
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

    public String updatePassword(String userID,String oldPassword,String newPassword){


        String result=callWSUpdate(userID,oldPassword,newPassword);
        String status=validate(result);

        return status;


    }

    private String callWSUpdate(String userID,String oldPassword,String newPassword) {
        String text = "";
        try
        {
            //http://appslure.in/luxerush/webservices/profile_updated.php?user_id=7&email=pk@gmail.com&phone=9818182346&street_address=test%20address&city=Delhi&npicode=113345
            String URL="user_id="+userID+"&old_password="+oldPassword+"&new_password="+newPassword;
            text= RestFullWS.serverRequest(URL, Constant.WS_CHANGE_PASSWORD);

        }
        catch (Exception e)
        {

        }
        return text;
    }

}

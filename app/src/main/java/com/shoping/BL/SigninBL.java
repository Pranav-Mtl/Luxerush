package com.shoping.BL;

import android.content.Context;

import com.shoping.BE.SigninBE;
import com.shoping.BE.SignupBE;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.WS.RestFullWS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by appslure on 05-02-2016.
 */
public class SigninBL {


   SigninBE objSigninBE;
    Context mContext;
    public String signinEmail(Context context,SigninBE signinBE) {

        objSigninBE=signinBE;
        mContext=context;
        String result = callWS();
        String finalvalue = validate(result);
        return finalvalue;
    }

    private String callWS() {
        String text = null;
        try
        {

            String URL="email="+objSigninBE.getEmail()+"&pass="+objSigninBE.getPassword()+"&gcm_id="+objSigninBE.getGcm()+ "&device_id="+objSigninBE.getDevice();
            text= RestFullWS.serverRequest(URL, Constant.WS_SIGNIN);

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

            if(status.equalsIgnoreCase(Constant.WS_RESPONSE_SUCCESS)){
                String userID=jsonObject.get("user_id").toString();
                Util.setSharedPrefrenceValue(mContext, Constant.PREFS_NAME, Constant.SP_LOGIN_ID, userID);
            }




        } catch (Exception e) {


        }
        return status;
    }

    /* Social SignIN*/

    public String signinSocial(Context context,SigninBE signinBE) {

        objSigninBE=signinBE;
        mContext=context;
        String result = callWSSocial();
        String finalvalue = validate(result);
        return finalvalue;
    }

    private String callWSSocial() {
        String text = null;
        try
        {
            //http://appslure.in/luxerush/webservices/social_login.php?email=abcde@gmail.com&pass=abc&gcm_id=hgh&device_id=hjhfj
            String URL="email="+objSigninBE.getEmail()+"&gcm_id="+objSigninBE.getGcm()+ "&device_id="+objSigninBE.getDevice();
            text= RestFullWS.serverRequest(URL, Constant.WS_SIGNIN_SOCIAL);

        }
        catch (Exception e)
        {

        }
        return text;
    }
}

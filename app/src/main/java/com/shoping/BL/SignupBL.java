package com.shoping.BL;

import android.content.Context;

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
public class SignupBL {

    SignupBE objSignupBE;
    Context mContext;
    public String signupEmail(Context context,SignupBE signupBE) {

        objSignupBE=signupBE;
        mContext=context;
        String result = callWS();
        String finalvalue = validate(result);
        return finalvalue;
    }

    private String callWS() {
        String text = null;
        try
        {
            //name=abcd&email=abcde@gmail.com&pass=abc&mobile=98765432104&gcm_id=abcfghf&device_id=jfhh
            String URL="name="+objSignupBE.getName()+"&email="+objSignupBE.getEmail()+"&pass="+objSignupBE.getPassword()+"&mobile="+objSignupBE.getMobile()+"&gcm_id="+objSignupBE.getGcm()+ "&device_id="+objSignupBE.getDevice();
            text= RestFullWS.serverRequest(URL, Constant.WS_SIGNUP);

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


    /*-------------------------------------------------------*/


    public String signupSocial(Context context,SignupBE signupBE) {

        objSignupBE=signupBE;
        mContext=context;
        String result = callWSSocial();
        String finalvalue = validate(result);
        return finalvalue;
    }

    private String callWSSocial() {
        String text = null;
        try
        {
            //http://appslure.in/luxerush/webservices/social_signup.php?name=abcd&email=abcde@gmail.com&gcm_id=abcfghf&device_id=jfhh
            String URL="name="+objSignupBE.getName()+"&email="+objSignupBE.getEmail()+"&gcm_id=" + objSignupBE.getGcm()+ "&device_id="+objSignupBE.getDevice();
            text= RestFullWS.serverRequest(URL, Constant.WS_SIGNUP_social);

        }
        catch (Exception e)
        {

        }
        return text;
    }


}

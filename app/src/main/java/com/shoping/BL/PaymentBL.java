package com.shoping.BL;

import android.content.Context;

import com.shoping.BE.CheckOutBE;
import com.shoping.BE.ItemDetailBE;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.WS.RestFullWS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by appslure on 15-02-2016.
 */
public class PaymentBL {

    Context mContext;
    public String validatePromoCode(String userID,String deviceID,String code,String category,Context context){

        mContext=context;
        String result=callWSPromoCode(userID, deviceID, code,category);
        String status=validatePromoCode(result);

        return status;
    }

    private String callWSPromoCode(String userID,String deviceID,String code,String category) {
        String text = "";

        //http://appslure.in/luxerush/webservices/promo_check.php?user_id=1&device_id=qqq&promocode=Test1&type=buy
        try
        {

            String URL="user_id="+userID+"&device_id="+deviceID+"&promocode="+code+"&type="+category;
            text= RestFullWS.serverRequest(URL, Constant.WS_VALIDATE_PROMO);

        }
        catch (Exception e)
        {

        }
        return text;
    }

    private String validatePromoCode(String strValue)    {
        String status="";
        JSONParser jsonP=new JSONParser();

        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());
            status=jsonObject.get("result").toString();

            if(status.equalsIgnoreCase(Constant.WS_RESPONSE_SUCCESS)){
                String promoID=jsonObject.get("user_promo_id").toString();
                Util.setSharedPrefrenceValue(mContext,Constant.PREFS_NAME,Constant.SP_PROMO_ID,promoID);
            }

        } catch (Exception e) {


        }
        return status;
    }
    /* Single Order Payment*/

    public String sendSingleOrder(String userID,String promoID,String code,String paymentType,ItemDetailBE objItemDetailBE,CheckOutBE objCheckOutBE){
       String result=callWSSingleOrder(userID,promoID,code,paymentType,objItemDetailBE,objCheckOutBE);
        String status=validateSingleOrder(result);
        return status;
    }

    private String callWSSingleOrder(String userID,String promoID,String code,String paymentType,ItemDetailBE objItemDetailBE,CheckOutBE objCheckOutBE) {
        String text = "";

        //http://appslure.in/luxerush/webservices/promo_check.php?user_id=1&device_id=qqq&promocode=Test1&type=buy
        try
        {

            String URL="user_id="+userID+"&delivery_address="+objCheckOutBE.getAddress()+"&pincode="+objCheckOutBE.getZip()+"&contact="+objCheckOutBE.getMobile()+"&city="+objCheckOutBE.getCity()+"&promocode="+code+"&product="+objItemDetailBE.getProductID()+"&payment_type="+paymentType+"&user_promo_id="+promoID;
            if(objItemDetailBE.getProductCategory().equalsIgnoreCase(Constant.CATEGORY_RENT))
                text= RestFullWS.serverRequest(URL, Constant.WS_ORDER_RENT);
            else if(objItemDetailBE.getProductCategory().equalsIgnoreCase(Constant.CATEGORY_BUY))
                text= RestFullWS.serverRequest(URL, Constant.WS_ORDER_BUY);


        }
        catch (Exception e)
        {

        }
        return text;
    }

    private String validateSingleOrder(String strValue)    {
        String status="";
        JSONParser jsonP=new JSONParser();

        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());
            status=jsonObject.get("result").toString();

            if(status.equalsIgnoreCase(Constant.WS_RESPONSE_SUCCESS)){

            }

        } catch (Exception e) {


        }
        return status;
    }
}

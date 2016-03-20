package com.shoping.BL;

import android.content.Context;
import android.util.Log;

import com.shoping.BE.BuyWebViewBE;
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
    BuyWebViewBE objBuyWebViewBE;
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

    String payment;
    public String sendSingleOrder(String userID,String promoID,String code,String paymentType,ItemDetailBE objItemDetailBE,CheckOutBE objCheckOutBE,BuyWebViewBE buyWebViewBE,String slotID,String slotDate){
        objBuyWebViewBE=buyWebViewBE;
        payment=paymentType;
       String result=callWSSingleOrder(userID,promoID,code,paymentType,objItemDetailBE,objCheckOutBE,slotID,slotDate);
        String status=validateSingleOrder(result);
        return status;
    }

    private String callWSSingleOrder(String userID,String promoID,String code,String paymentType,ItemDetailBE objItemDetailBE,CheckOutBE objCheckOutBE,String slotID,String slotDate) {
        String text = "";

        //http://appslure.in/luxerush/webservices/promo_check.php?user_id=1&device_id=qqq&promocode=Test1&type=buy
        try
        {


            if(objItemDetailBE.getProductCategory().equalsIgnoreCase(Constant.CATEGORY_RENT)) {
                String URL="user_id="+userID+"&delivery_address="+objCheckOutBE.getAddress()+"&pincode="+objCheckOutBE.getZip()+"&contact="+objCheckOutBE.getMobile()+"&city="+objCheckOutBE.getCity()+"&promocode="+code+"&product="+objItemDetailBE.getProductID()+"&payment_type="+paymentType+"&user_promo_id="+promoID+"&slot_id="+slotID+"&slot_date="+slotDate;
                text = RestFullWS.serverRequest(URL, Constant.WS_ORDER_RENT);
            }
            else if(objItemDetailBE.getProductCategory().equalsIgnoreCase(Constant.CATEGORY_BUY)) {
                String URL="user_id="+userID+"&delivery_address="+objCheckOutBE.getAddress()+"&pincode="+objCheckOutBE.getZip()+"&contact="+objCheckOutBE.getMobile()+"&city="+objCheckOutBE.getCity()+"&promocode="+code+"&product="+objItemDetailBE.getProductID()+"&payment_type="+paymentType+"&user_promo_id="+promoID;
                text = RestFullWS.serverRequest(URL, Constant.WS_ORDER_BUY);
            }


        }
        catch (Exception e)
        {

        }
        return text;
    }

    private String validateSingleOrder(String strValue)    {
        boolean status=false;
        String statusses="";
        JSONParser jsonP=new JSONParser();

        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());

            if(!payment.equalsIgnoreCase("Online")){
                statusses=jsonObject.get("result").toString();
            }
            else {

                status = (boolean) jsonObject.get("success");

                objBuyWebViewBE.setStatus((boolean) jsonObject.get("success"));
                Log.d("STATUS URL-->", status + "");

                if (status) {

                /*String link=jsonObject.get("link").toString();
                Log.d("link URL-->",link);*/

                    objBuyWebViewBE.setName(jsonObject.get("name").toString());
                    objBuyWebViewBE.setPhone(jsonObject.get("phone").toString());
                    objBuyWebViewBE.setEmail(jsonObject.get("email").toString());

                    jsonObject = (JSONObject) jsonP.parse(jsonObject.get("link").toString());
                    objBuyWebViewBE.setUrl(jsonObject.get("url").toString());


                }
            }


        } catch (Exception e) {


        }
        return statusses;
    }
}

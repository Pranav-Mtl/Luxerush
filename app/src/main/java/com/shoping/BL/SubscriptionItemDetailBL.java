package com.shoping.BL;

import com.shoping.BE.CheckOutBE;
import com.shoping.BE.ItemDetailBE;
import com.shoping.BE.SubscriptionItemDetailBE;
import com.shoping.Constant.Constant;
import com.shoping.WS.RestFullWS;
import com.shoping.luxerush.SubscriptionItemDetail;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by appslure on 16-02-2016.
 */
public class SubscriptionItemDetailBL {
    SubscriptionItemDetailBE objSubscriptionItemDetailBE;


    public String getSubsDetail(SubscriptionItemDetailBE subscriptionItemDetailBE,String productID){
       objSubscriptionItemDetailBE=subscriptionItemDetailBE;
        String result=callWS(productID);
        validate(result);

        return "";
    }

    private String callWS(String productID) {
        String text = "";
        try
        {

            String URL="product_id="+productID;
            text= RestFullWS.serverRequest(URL, Constant.WS_SUBSCRIPTION_LIST_DETAIL);

        }
        catch (Exception e)
        {

        }
        return text;
    }

    private void validate(String strValue)    {
        JSONParser jsonP=new JSONParser();
        String detail,image,related;
        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());

            detail=jsonObject.get("product_detail").toString();
            image=jsonObject.get("product_image").toString();

            validateDetail(detail);
            validateImages(image);



        } catch (Exception e) {


        }

    }

    private void validateDetail(String strValue)    {
        JSONParser jsonP=new JSONParser();
        String status="";
        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());

            objSubscriptionItemDetailBE.setProductName(jsonObject.get("product_name").toString());
            objSubscriptionItemDetailBE.setSubscriptionPrice(jsonObject.get("subscription_rent_price").toString());
            objSubscriptionItemDetailBE.setSize(jsonObject.get("size").toString());
            objSubscriptionItemDetailBE.setColor(jsonObject.get("color").toString());
            objSubscriptionItemDetailBE.setTag(jsonObject.get("tag").toString());
            objSubscriptionItemDetailBE.setBrand(jsonObject.get("brand").toString());
            objSubscriptionItemDetailBE.setDescription(jsonObject.get("product_description").toString());
            objSubscriptionItemDetailBE.setProductType(jsonObject.get("product_type").toString());
            objSubscriptionItemDetailBE.setProductCondition(jsonObject.get("product_condition").toString());




        } catch (Exception e) {
            e.printStackTrace();

        }

    }




    private void validateImages(String strValue)    {
        JSONParser jsonP=new JSONParser();
        String status="";
        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            Constant.SubscriptionDetailImages=new String[jsonArrayObject.size()];


            for(int i=0;i<jsonArrayObject.size();i++) {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                Constant.SubscriptionDetailImages[i]=jsonObject.get("image").toString();
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    /*------------*/
    CheckOutBE objCheckOutBE;
    public String setSubsDetail(String productID,String userID,CheckOutBE checkOutBE){
        objCheckOutBE=checkOutBE;
        String result=callWSSub(productID,userID);
        String status=validateSub(result);

        return status;
    }

    private String callWSSub(String productID,String userID) {
        String text = "";
        try
        {

            String URL="product="+productID+"&user_id="+userID;
            text= RestFullWS.serverRequest(URL, Constant.WS_TAKE_SUBSCRIPTION_PRODUCT);

        }
        catch (Exception e)
        {

        }
        return text;
    }


    private String validateSub(String strValue)    {
        JSONParser jsonP=new JSONParser();
        String status="";
        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());

            status=jsonObject.get("result").toString();



        } catch (Exception e) {


        }
        return status;
    }


}

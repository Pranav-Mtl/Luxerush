package com.shoping.BL;

import android.content.Context;

import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.WS.RestFullWS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by appslure on 08-02-2016.
 */
public class ItemsListBL {

    Context mContext;
    public String getList(String category,String tag,Context context,String userID){
        mContext=context;
        String result=callWS(category, tag,userID);
        validate(result);

        return "";
    }

    private String callWS(String category,String tag,String userID) {
        String text = "";
        try
        {
            if(userID==null)
                userID="";

            String URL="type="+category+"&tag="+tag+"&user_id="+userID;
            text= RestFullWS.serverRequest(URL, Constant.WS_ITEM_LIST);

        }
        catch (Exception e)
        {

        }
        return text;
    }

    private void validate(String strValue)    {
        JSONParser jsonP=new JSONParser();
        String details="",brand,category,size,minPrice,maxPrice;
        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;

                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());


                details=jsonObject.get("product_list").toString();
                brand=jsonObject.get("brands").toString();
                category=jsonObject.get("category").toString();
                size=jsonObject.get("size").toString();
                minPrice=jsonObject.get("min_price").toString();
                maxPrice=jsonObject.get("max_price").toString();

                validateDetails(details);

            Util.setSharedPrefrenceValue(mContext, Constant.PREFS_NAME, Constant.SP_FILTER_BRAND, brand);
            Util.setSharedPrefrenceValue(mContext,Constant.PREFS_NAME,Constant.SP_FILTER_CATEGORY,category);
            Util.setSharedPrefrenceValue(mContext,Constant.PREFS_NAME,Constant.SP_FILTER_SIZE,size);
            Util.setSharedPrefrenceValue(mContext,Constant.PREFS_NAME,Constant.SP_FILTER_MIN,minPrice);
            Util.setSharedPrefrenceValue(mContext,Constant.PREFS_NAME,Constant.SP_FILTER_MAX,maxPrice);

        } catch (Exception e) {


        }

    }

    /*-------------------------------*/

    private String validateDetails(String strValue)    {
        JSONParser jsonP=new JSONParser();
        String status="";
        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            Constant.productID=new String[jsonArrayObject.size()];
            Constant.productName=new String[jsonArrayObject.size()];
            Constant.productImage=new String[jsonArrayObject.size()];
            Constant.productOriginalPrice=new String[jsonArrayObject.size()];
            Constant.productDiscountedPrice=new String[jsonArrayObject.size()];
            Constant.productTag=new String[jsonArrayObject.size()];
            Constant.productWishlist=new String[jsonArrayObject.size()];
            Constant.productKeyword=new String[jsonArrayObject.size()];

            for(int i=0;i<jsonArrayObject.size();i++) {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                Constant.productID[i]=jsonObject.get("product_id").toString();
                Constant.productName[i]=jsonObject.get("product_name").toString();
                Constant.productImage[i]=jsonObject.get("product_image").toString();
                Constant.productOriginalPrice[i]=jsonObject.get("original_price").toString();
                Constant.productDiscountedPrice[i]=jsonObject.get("discount_price").toString();
                Constant.productTag[i]=jsonObject.get("tag").toString();
                Constant.productWishlist[i]=jsonObject.get("wishlist").toString();
                Constant.productKeyword[i]=jsonObject.get("keyword").toString();

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return status;
    }

}

package com.shoping.BL;

import com.shoping.Constant.Constant;
import com.shoping.WS.RestFullWS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by appslure on 10-02-2016.
 */
public class WishListBL {
    public String addToWishlist(String userID,String productID,String type){

        String result=callWS(userID,productID,type);
        String status=validate(result);

        return status;
    }

    private String callWS(String userID,String productID,String type) {
        String text = "";
        try
        {

            String URL="user_id="+userID+"&product_id="+productID+"&type="+type;
            text= RestFullWS.serverRequest(URL, Constant.WS_ADD_WISHLIST);

        }
        catch (Exception e)
        {

        }
        return text;
    }

    private String validate(String strValue)    {
        String status="";
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

    /*   GET WISH  LIST  */

    public String getWishList(String userID){

        String result=callWishWS(userID);
        String status=validateWishList(result);

        return status;
    }

    private String callWishWS(String userID) {
        String text = "";
        try
        {

            String URL="user_id="+userID;
            text= RestFullWS.serverRequest(URL, Constant.WS_GET_WISHLIST);

        }
        catch (Exception e)
        {

        }
        return text;
    }

    private String validateWishList(String strValue)    {
        JSONParser jsonP=new JSONParser();
        String status="";
        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());

            status=jsonObject.get("result").toString();

            if(status.equalsIgnoreCase(Constant.WS_RESPONSE_SUCCESS)) {
                String detail=jsonObject.get("details").toString();
                obj =jsonP.parse(detail);
                jsonArrayObject = (JSONArray)obj;

                Constant.productIDWish = new String[jsonArrayObject.size()];
                Constant.productNameWish = new String[jsonArrayObject.size()];
                Constant.productImageWish = new String[jsonArrayObject.size()];
                Constant.productOriginalPriceWish = new String[jsonArrayObject.size()];
                Constant.productDiscountedPriceWish = new String[jsonArrayObject.size()];
                Constant.productTagWish = new String[jsonArrayObject.size()];
                Constant.productWishlistWish = new String[jsonArrayObject.size()];
                Constant.productKeywordWish = new String[jsonArrayObject.size()];

                for (int i = 0; i < jsonArrayObject.size(); i++) {
                    jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                    Constant.productIDWish[i] = jsonObject.get("product_id").toString();
                    Constant.productNameWish[i] = jsonObject.get("product_name").toString();
                    Constant.productImageWish[i] = jsonObject.get("product_image").toString();
                    Constant.productOriginalPriceWish[i] = jsonObject.get("original_price").toString();
                    Constant.productDiscountedPriceWish[i] = jsonObject.get("discount_price").toString();
                    Constant.productTagWish[i] = jsonObject.get("tag").toString();
                    Constant.productWishlistWish[i] = jsonObject.get("wishlist").toString();
                    Constant.productKeywordWish[i] = jsonObject.get("keyword").toString();

                }
            }

        } catch (Exception e) {


        }
        return status;
    }

}

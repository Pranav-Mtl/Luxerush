package com.shoping.BL;

import com.shoping.BE.ItemDetailBE;
import com.shoping.Constant.Constant;
import com.shoping.WS.RestFullWS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by appslure on 08-02-2016.
 */
public class ItemDetailBL {

    ItemDetailBE objItemDetailBE;
    public String getDetail(String category,String tag,String id,String keyword,ItemDetailBE itemDetailBE){
        objItemDetailBE=itemDetailBE;
        String result=callWS(category,tag,id,keyword);
        validate(result);

        return "";
    }

    private String callWS(String category,String tag,String id,String keyword) {
        String text = "";
        try
        {

            String URL="type="+category+"&tag="+tag+"&product_id="+id+"&keyword="+keyword;
            text= RestFullWS.serverRequest(URL, Constant.WS_ITEM_DETAIL);

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
                related=jsonObject.get("related_product").toString();

                validateDetail(detail);
                validateImages(image);
                validateRelated(related);


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

            objItemDetailBE.setProductName(jsonObject.get("product_name").toString());
            objItemDetailBE.setBuyingPrice(jsonObject.get("buying_price").toString());
            objItemDetailBE.setBuyingDiscountPrice(jsonObject.get("discount_buying_price").toString());
            objItemDetailBE.setRentPrice(jsonObject.get("rent_price").toString());
            objItemDetailBE.setRentDiscountPrice(jsonObject.get("discount_rent_price").toString());
            objItemDetailBE.setSubscriptionPrice(jsonObject.get("subscription_rent_price").toString());
            objItemDetailBE.setSize(jsonObject.get("size").toString());
            objItemDetailBE.setColor(jsonObject.get("color").toString());
            objItemDetailBE.setTag(jsonObject.get("tag").toString());
            objItemDetailBE.setBrand(jsonObject.get("brand").toString());
            objItemDetailBE.setDescription(jsonObject.get("product_description").toString());
            objItemDetailBE.setProductType(jsonObject.get("product_type").toString());
            objItemDetailBE.setProductCondition(jsonObject.get("product_condition").toString());



        } catch (Exception e) {
            e.printStackTrace();

        }

    }

 private void validateRelated(String strValue)    {
        JSONParser jsonP=new JSONParser();
        String status="";
        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;
            Constant.productIDRelated=new String[jsonArrayObject.size()];
            Constant.productNameRelated=new String[jsonArrayObject.size()];
            Constant.productImageRelated=new String[jsonArrayObject.size()];
            Constant.productOriginalPriceRelated=new String[jsonArrayObject.size()];
            Constant.productDiscountedPriceRelated=new String[jsonArrayObject.size()];
            Constant.productTagRelated=new String[jsonArrayObject.size()];
            Constant.productWishlistRelated=new String[jsonArrayObject.size()];
            Constant.productKeywordRelated=new String[jsonArrayObject.size()];

            for(int i=0;i<jsonArrayObject.size();i++) {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                Constant.productIDRelated[i]=jsonObject.get("product_id").toString();
                Constant.productNameRelated[i]=jsonObject.get("product_name").toString();
                Constant.productImageRelated[i]=jsonObject.get("product_image").toString();
                Constant.productOriginalPriceRelated[i]=jsonObject.get("original_price").toString();
                Constant.productDiscountedPriceRelated[i]=jsonObject.get("discount_price").toString();
                Constant.productTagRelated[i]=jsonObject.get("tag").toString();
                Constant.productWishlistRelated[i]=jsonObject.get("wishlist").toString();
                Constant.productKeywordRelated[i]=jsonObject.get("keyword").toString();

            }

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
            Constant.productDetailImages=new String[jsonArrayObject.size()];


            for(int i=0;i<jsonArrayObject.size();i++) {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                Constant.productDetailImages[i]=jsonObject.get("image").toString();
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


}

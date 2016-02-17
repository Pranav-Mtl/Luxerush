package com.shoping.BL;

import com.shoping.BE.SubscriptionDetailBE;
import com.shoping.Constant.Constant;
import com.shoping.WS.RestFullWS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by appslure on 13-02-2016.
 */
public class SubscriptionDetailBL {

    /*  Fetch All Routes when user comes first time  */
    SubscriptionDetailBE objSubscriptionDetailBE;
    public String getPackagesDetail(String packageID,SubscriptionDetailBE subscriptionDetailBE)
    {
        objSubscriptionDetailBE=subscriptionDetailBE;
        String result=callWS(packageID);
        validate(result);
        return "";
    }
    private String callWS(String packageID)
    {
        String URL="package_id="+packageID;
        String txtJson= RestFullWS.serverRequest(URL, Constant.WS_SUBSCRIPTION_DETAIL);
        return txtJson;
    }

    private void validate(String result){
        String detail,brand;
        JSONParser jsonP=new JSONParser();
        try {
            Object obj =jsonP.parse(result);
            JSONArray jsonArrayObject = (JSONArray)obj;


                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());
                detail=jsonObject.get("package_detail").toString();
                brand=jsonObject.get("brand").toString();
                parseBrandJson(brand);
                parseDetail(detail);




        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void parseDetail(String result){
        String detail,brand;
        JSONParser jsonP=new JSONParser();
        try {
            Object obj =jsonP.parse(result);
            JSONArray jsonArrayObject = (JSONArray)obj;


            JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(0).toString());
            objSubscriptionDetailBE.setName(jsonObject.get("package_name").toString());
            objSubscriptionDetailBE.setBags(jsonObject.get("bags_allow").toString());
            objSubscriptionDetailBE.setClothes(jsonObject.get("cloths_allow").toString());
            objSubscriptionDetailBE.setPrice(jsonObject.get("package_value").toString());



        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void parseBrandJson(String result){
        JSONParser jsonP=new JSONParser();
        try {
            Object obj =jsonP.parse(result);
            JSONArray jsonArrayObject = (JSONArray)obj;

            Constant.brandImage=new String[jsonArrayObject.size()];
            Constant.brandName=new String[jsonArrayObject.size()];
            for(int i=0;i<jsonArrayObject.size();i++) {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                Constant.brandImage[i]=jsonObject.get("brand_image").toString();
                Constant.brandName[i]=jsonObject.get("brand_name").toString();

            }





        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

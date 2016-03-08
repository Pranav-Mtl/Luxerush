package com.shoping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.luxerush.R;
import com.squareup.picasso.Picasso;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by appslure on 18-02-2016.
 */
public class OrderRentDetailAdapter extends  RecyclerView.Adapter<OrderRentDetailAdapter.OrderRentDetailHolder> {

    Context mContext;

    public OrderRentDetailAdapter(Context context,String product){
        mContext=context;
        parseJson(product);
    }


    @Override
    public OrderRentDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(mContext).
                inflate(R.layout.rent_product_raw, parent, false);

        return new OrderRentDetailHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderRentDetailHolder holder, int position) {
        holder.tvName.setText(Constant.rentProductName[position]);
        holder.tvPrice.setText("Price: ₹"+Constant.rentProductPrice[position]);
        holder.tvStartdate.setText("Start Date: "+Constant.rentProductStart[position]);
        holder.tvEndDate.setText("End Date: "+Constant.rentProductEnd[position]);

        if(Constant.rentProductReturned[position].equalsIgnoreCase("N"))
            holder.tvReturend.setText("Returned: NO");
        else
            holder.tvReturend.setText("Returned: Yes");

        holder.tvCondition.setText("Returned Condition: "+Constant.rentProductCondition[position]);

        Picasso.with(mContext)
                .load(Constant.rentProductImage[position])
                .placeholder(R.drawable.ic_default_loading)
                .error(R.drawable.ic_default_loading)
                .into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        if(Constant.rentProductName==null)
            return 0;
        return Constant.rentProductName.length;
    }

    public static class OrderRentDetailHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvName,tvPrice,tvStartdate,tvEndDate,tvReturend,tvCondition;


        public OrderRentDetailHolder(View gridView) {
            super(gridView);

            ivImage= (ImageView) gridView.findViewById(R.id.rent_product_image);
            tvName= (TextView) gridView.findViewById(R.id.rent_product_title);
            tvPrice= (TextView) gridView.findViewById(R.id.rent_product_price);
            tvStartdate= (TextView) gridView.findViewById(R.id.rent_product_startdate);
            tvEndDate= (TextView) gridView.findViewById(R.id.rent_product_enddate);
            tvReturend= (TextView) gridView.findViewById(R.id.rent_product_returned);
            tvCondition= (TextView) gridView.findViewById(R.id.rent_product_condition);


        }
    }private void parseJson(String result){
        JSONParser jsonP=new JSONParser();
        try {
            Object obj =jsonP.parse(result);
            JSONArray jsonArrayObject = (JSONArray)obj;

            Constant.rentProductName=new String[jsonArrayObject.size()];
            Constant.rentProductImage=new String[jsonArrayObject.size()];
            Constant.rentProductCondition=new String[jsonArrayObject.size()];
            Constant.rentProductEnd=new String[jsonArrayObject.size()];
            Constant.rentProductStart=new String[jsonArrayObject.size()];
            Constant.rentProductReturned=new String[jsonArrayObject.size()];
            Constant.rentProductPrice=new String[jsonArrayObject.size()];

            for(int i=0;i<jsonArrayObject.size();i++) {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                Constant.rentProductName[i]=jsonObject.get("product_name").toString();
                Constant.rentProductImage[i]=jsonObject.get("product_image").toString();
                Constant.rentProductCondition[i]=jsonObject.get("return_condition").toString();
                Constant.rentProductEnd[i]=jsonObject.get("end_date").toString();
                Constant.rentProductStart[i]=jsonObject.get("start_date").toString();
                Constant.rentProductReturned[i]=jsonObject.get("returned").toString();
                Constant.rentProductPrice[i]=jsonObject.get("price").toString();

            }





        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}

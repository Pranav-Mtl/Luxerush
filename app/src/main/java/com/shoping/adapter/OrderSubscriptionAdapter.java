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
public class OrderSubscriptionAdapter extends  RecyclerView.Adapter<OrderSubscriptionAdapter.OrderSubscriptionHolder> {

    Context mContext;
    String buyJson;

    public OrderSubscriptionAdapter(Context context){
        mContext=context;
        buyJson= Util.getSharedPrefrenceValue(mContext, Constant.SP_ORDER_SUBSCRIPTION);
        Log.d("JSON-->>", buyJson);
        parseJson(buyJson);
    }


    @Override
    public OrderSubscriptionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(mContext).
                inflate(R.layout.order_subscription_raw, parent, false);

        return new OrderSubscriptionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderSubscriptionHolder holder, int position) {
        try {
            holder.tvStatus.setText(Constant.subscriptionOrderStatus[position]);
            holder.tvID.setText("Order ID: " + Constant.subscriptionOrderID[position]);


            holder.tvOrderDate.setText("Ordered on: "+Constant.subscriptionOrderDate[position]);
            holder.tvName.setText(Constant.subscriptionOrderName[position]);

            Picasso.with(mContext)
                    .load(Constant.subscriptionOrderImage[position])
                    .placeholder(R.drawable.ic_default_loading)
                    .error(R.drawable.ic_default_loading)
                    .into(holder.ivImage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
      if(Constant.subscriptionOrderID==null){
            return 0;
        }
        return Constant.subscriptionOrderID.length;
    }

    public static class OrderSubscriptionHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvName,tvStatus,tvID,tvOrderDate;


        public OrderSubscriptionHolder(View gridView) {
            super(gridView);

            ivImage= (ImageView) gridView.findViewById(R.id.buy_order_image);
            tvName= (TextView) gridView.findViewById(R.id.buy_order_title);
            tvStatus= (TextView) gridView.findViewById(R.id.buy_order_status);
            tvID= (TextView) gridView.findViewById(R.id.buy_order_id);
            tvOrderDate= (TextView) gridView.findViewById(R.id.buy_order_date);


        }
    }


    private void parseJson(String result){
        JSONParser jsonP=new JSONParser();
        try {
            Object obj =jsonP.parse(result);
            JSONArray jsonArrayObject = (JSONArray)obj;

            Constant.subscriptionOrderID=new String[jsonArrayObject.size()];
            Constant.subscriptionOrderDate=new String[jsonArrayObject.size()];
            Constant.subscriptionOrderStatus=new String[jsonArrayObject.size()];
            Constant.subscriptionOrderName=new String[jsonArrayObject.size()];
            Constant.subscriptionOrderImage=new String[jsonArrayObject.size()];

            for(int i=0;i<jsonArrayObject.size();i++) {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                Constant.subscriptionOrderID[i]=jsonObject.get("order_id").toString();
                Constant.subscriptionOrderDate[i]=jsonObject.get("order_date").toString();
                Constant.subscriptionOrderStatus[i]=jsonObject.get("order_status").toString();
                Constant.subscriptionOrderName[i]=jsonObject.get("product_name").toString();
                Constant.subscriptionOrderImage[i]=jsonObject.get("product_image").toString();

            }





        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

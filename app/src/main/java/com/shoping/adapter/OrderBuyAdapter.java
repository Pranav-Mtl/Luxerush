package com.shoping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
public class OrderBuyAdapter extends  RecyclerView.Adapter<OrderBuyAdapter.OrderBuyHolder> {

    Context mContext;
    String buyJson;

    public OrderBuyAdapter(Context context){
        mContext=context;
        buyJson= Util.getSharedPrefrenceValue(mContext, Constant.SP_ORDER_BUY);
        Log.d("JSON-->>",buyJson);
        parseJson(buyJson);
    }

    @Override
    public OrderBuyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(mContext).
                inflate(R.layout.order_buy_raw, parent, false);

        return new OrderBuyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderBuyHolder holder, int position) {

        try {
            holder.tvStatus.setText(Constant.buyOrderStatus[position]);
            holder.tvID.setText("Order ID: " + Constant.buyOrderID[position]);
            holder.tvAddress.setText(Constant.buyOrderAddress[position]);
            holder.tvPaymeny.setText("Payment type: "+Constant.buyOrderPaymentType[position]);
            if(Constant.buyOrderDeliverydate[position].equalsIgnoreCase("0000-00-00")){
                holder.tvDeliveryDate.setText("Delivered on: Not Delivered yet");
            }
            else
            {
                holder.tvDeliveryDate.setText("Delivered on: "+Constant.buyOrderDeliverydate[position]);
            }

            holder.tvOrderDate.setText("Ordered on: "+Constant.buyOrderDate[position]);
            holder.tvName.setText(Constant.buyOrderName[position]+" @ ₹"+Constant.buyOrderPrice[position]);

            Picasso.with(mContext)
                    .load(Constant.buyOrderImage[position])
                    .placeholder(R.drawable.ic_default_loading)
                    .error(R.drawable.ic_default_loading)
                    .into(holder.ivImage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(Constant.buyOrderID==null){
            return 0;
        }
        return Constant.buyOrderID.length;
    }

    public static class OrderBuyHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvName,tvStatus,tvID,tvOrderDate,tvDeliveryDate,tvAddress,tvPaymeny;


        public OrderBuyHolder(View gridView) {
            super(gridView);

            ivImage= (ImageView) gridView.findViewById(R.id.buy_order_image);
            tvName= (TextView) gridView.findViewById(R.id.buy_order_title);
            tvStatus= (TextView) gridView.findViewById(R.id.buy_order_status);
            tvID= (TextView) gridView.findViewById(R.id.buy_order_id);
            tvOrderDate= (TextView) gridView.findViewById(R.id.buy_order_date);
            tvDeliveryDate= (TextView) gridView.findViewById(R.id.buy_order_delivery_date);
            tvAddress= (TextView) gridView.findViewById(R.id.buy_order_address);
            tvPaymeny= (TextView) gridView.findViewById(R.id.buy_order_payment);


        }
    }

    private void parseJson(String result){
        JSONParser jsonP=new JSONParser();
        try {
            Object obj =jsonP.parse(result);
            JSONArray jsonArrayObject = (JSONArray)obj;

            Constant.buyOrderID=new String[jsonArrayObject.size()];
            Constant.buyOrderDate=new String[jsonArrayObject.size()];
            Constant.buyOrderPaymentType=new String[jsonArrayObject.size()];
            Constant.buyOrderStatus=new String[jsonArrayObject.size()];
            Constant.buyOrderName=new String[jsonArrayObject.size()];
            Constant.buyOrderPrice=new String[jsonArrayObject.size()];
            Constant.buyOrderDeliverydate=new String[jsonArrayObject.size()];
            Constant.buyOrderImage=new String[jsonArrayObject.size()];
            Constant.buyOrderAddress=new String[jsonArrayObject.size()];

            for(int i=0;i<jsonArrayObject.size();i++) {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                Constant.buyOrderID[i]=jsonObject.get("order_id").toString();
                Constant.buyOrderDate[i]=jsonObject.get("order_date").toString();
                Constant.buyOrderPaymentType[i]=jsonObject.get("payment_type").toString();
                Constant.buyOrderStatus[i]=jsonObject.get("order_status").toString();
                Constant.buyOrderName[i]=jsonObject.get("product_name").toString();
                Constant.buyOrderPrice[i]=jsonObject.get("price").toString();
                Constant.buyOrderDeliverydate[i]=jsonObject.get("delivery_date").toString();
                Constant.buyOrderImage[i]=jsonObject.get("product_image").toString();
                Constant.buyOrderAddress[i]=jsonObject.get("street_address").toString()+", "+jsonObject.get("city").toString()+", "+jsonObject.get("pincode").toString();


            }





        } catch (Exception e) {

            e.printStackTrace();
        }
    }


}

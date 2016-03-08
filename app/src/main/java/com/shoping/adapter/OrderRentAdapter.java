package com.shoping.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.shoping.luxerush.ItemDetail;
import com.shoping.luxerush.OrderRentDetail;
import com.shoping.luxerush.R;
import com.squareup.picasso.Picasso;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by appslure on 18-02-2016.
 */
public class OrderRentAdapter extends  RecyclerView.Adapter<OrderRentAdapter.OrderRentHolder> {

    Context mContext;
    String buyJson;

    public OrderRentAdapter(Context context){
        mContext=context;
        buyJson= Util.getSharedPrefrenceValue(mContext, Constant.SP_ORDER_RENT);
        Log.d("JSON-->>", buyJson);
        parseJson(buyJson);
    }

    @Override
    public OrderRentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(mContext).
                inflate(R.layout.order_rent_raw, parent, false);

        return new OrderRentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderRentHolder holder, int position) {
        try {
            holder.tvStatus.setText(Constant.rentOrderStatus[position]);
            holder.tvID.setText("Order ID: " + Constant.rentOrderID[position]);
            holder.tvAddress.setText(Constant.rentOrderAddress[position]);
            holder.tvPayment.setText("Payment Type: "+Constant.rentOrderPaymentType[position]);
            holder.tvOrderDate.setText("Ordered on: " + Constant.rentOrderDate[position]);
            holder.tvPrice.setText("Total Amount: ₹"+Constant.rentOrderPrice[position]);


            JSONParser jsonP=new JSONParser();
            try {
                Object obj = jsonP.parse(Constant.rentOrderProducts[position]);
                JSONArray jsonArrayObject = (JSONArray) obj;
                holder.tvItem.setText("Total Item: "+jsonArrayObject.size());
            }catch (Exception e){

            }


            holder.llDetail.setOnClickListener(clickListener);
            holder.llDetail.setTag(holder);



    }catch (Exception e){
            e.printStackTrace();
        }
    }


View.OnClickListener clickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        int position;
        OrderRentHolder holder = (OrderRentHolder) view.getTag();
        position = holder.getAdapterPosition();

        switch (view.getId()){
            case R.id.rl_rent_detail:
                Intent intent=new Intent(mContext,OrderRentDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Product", Constant.rentOrderProducts[position]);
                mContext.startActivity(intent);
                break;
        }
    }};

    @Override
    public int getItemCount() {
        if(Constant.rentOrderID==null){
            return 0;
        }
        return Constant.rentOrderID.length;
    }

    public static class OrderRentHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvName,tvStatus,tvID,tvOrderDate,tvItem,tvAddress,tvPayment,tvPrice;
        LinearLayout llDetail;


        public OrderRentHolder(View gridView) {
            super(gridView);

            tvStatus= (TextView) gridView.findViewById(R.id.buy_order_status);
            tvID= (TextView) gridView.findViewById(R.id.buy_order_id);
            tvOrderDate= (TextView) gridView.findViewById(R.id.buy_order_date);
            tvAddress= (TextView) gridView.findViewById(R.id.buy_order_address);
            tvItem= (TextView) gridView.findViewById(R.id.buy_order_item);
            tvPayment= (TextView) gridView.findViewById(R.id.buy_order_payment);
            llDetail= (LinearLayout) gridView.findViewById(R.id.rl_rent_detail);
            tvPrice=(TextView) gridView.findViewById(R.id.buy_order_price);


        }
    }

    private void parseJson(String result){
        JSONParser jsonP=new JSONParser();
        try {
            Object obj =jsonP.parse(result);
            JSONArray jsonArrayObject = (JSONArray)obj;

            Constant.rentOrderID=new String[jsonArrayObject.size()];
            Constant.rentOrderDate=new String[jsonArrayObject.size()];
            Constant.rentOrderPaymentType=new String[jsonArrayObject.size()];
            Constant.rentOrderStatus=new String[jsonArrayObject.size()];
            Constant.rentOrderAddress=new String[jsonArrayObject.size()];
            Constant.rentOrderProducts=new String[jsonArrayObject.size()];
            Constant.rentOrderPrice=new String[jsonArrayObject.size()];

            for(int i=0;i<jsonArrayObject.size();i++) {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                Constant.rentOrderID[i]=jsonObject.get("order_id").toString();
                Constant.rentOrderDate[i]=jsonObject.get("order_date").toString();
                Constant.rentOrderPaymentType[i]=jsonObject.get("payment_type").toString();
                Constant.rentOrderStatus[i]=jsonObject.get("order_status").toString();
                Constant.rentOrderPrice[i]=jsonObject.get("price").toString();
                Constant.rentOrderAddress[i]=jsonObject.get("street_address").toString()+", "+jsonObject.get("city").toString()+", "+jsonObject.get("pincode").toString();
                Constant.rentOrderProducts[i]=jsonObject.get("products").toString();

            }





        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

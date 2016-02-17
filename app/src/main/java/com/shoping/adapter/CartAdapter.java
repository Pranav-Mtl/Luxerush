package com.shoping.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.luxerush.ItemDetail;
import com.shoping.luxerush.R;
import com.squareup.picasso.Picasso;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;


/**
 * Created by appslure on 18-01-2016.
 */
public class CartAdapter extends  RecyclerView.Adapter<CartAdapter.CartHolder> {

    Context mContext;

    String cartJson;

    ArrayList listName;
    ArrayList listImage;
    ArrayList listSize;
    public ArrayList listId;
    ArrayList listPrice;

    JSONArray jsonArrayObject;

    public CartAdapter(Context context){
        mContext=context;


        cartJson= Util.getSharedPrefrenceValue(mContext,Constant.SP_CART_ITEM);

        if(cartJson==null|| cartJson.equalsIgnoreCase("[]")){

        }
        else{
            listName=new ArrayList();
            listImage=new ArrayList();
            listSize=new ArrayList();
            listId=new ArrayList();
            listPrice=new ArrayList();
            parseJson(cartJson);
        }

    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(mContext).
                inflate(R.layout.cart_item_raw, parent, false);

        return new CartHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartHolder holder, int position) {

        holder.tvName.setText(listName.get(position).toString());
        holder.tvSize.setText(listSize.get(position).toString());
        holder.tvPrice.setText("₹" + listPrice.get(position).toString());


        Picasso.with(mContext)
                .load(listImage.get(position).toString())
                .placeholder(R.drawable.ic_default_loading)
                .resize(100,100)
                .error(R.drawable.ic_default_loading)
                .into(holder.ivImage);

        holder.llRemove.setOnClickListener(clickListener);
        holder.llRemove.setTag(holder);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position;
            CartHolder holder = (CartHolder) view.getTag();
            position = holder.getAdapterPosition();

            switch (view.getId()){
                case R.id.cart_remove:
                    listId.remove(position);
                    listName.remove(position);
                    listSize.remove(position);
                    listImage.remove(position);
                    listPrice.remove(position);
                    jsonArrayObject.remove(position);
                    Util.setSharedPrefrenceValue(mContext,Constant.PREFS_NAME,Constant.SP_CART_ITEM,jsonArrayObject.toString());
                    notifyDataSetChanged();
                    break;
            }
        }};


    @Override
    public int getItemCount() {
        if(listId==null)
            return 0;

        return listId.size();
    }




    public static class CartHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvName,tvSize,tvPrice;
        LinearLayout llRemove;

        public CartHolder(View gridView) {
            super(gridView);

            ivImage= (ImageView) gridView.findViewById(R.id.cart_image);
            tvName= (TextView) gridView.findViewById(R.id.cart_name);
            tvSize= (TextView) gridView.findViewById(R.id.cart_size);
            tvPrice= (TextView) gridView.findViewById(R.id.cart_price);
            llRemove= (LinearLayout) gridView.findViewById(R.id.cart_remove);


        }
    }

    private void parseJson(String result){
        JSONParser jsonP=new JSONParser();
        try {
            Object obj =jsonP.parse(result);
             jsonArrayObject = (JSONArray)obj;



            for(int i=0;i<jsonArrayObject.size();i++) {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                listName.add(jsonObject.get(Constant.KEY_Name).toString());
                listImage.add(jsonObject.get(Constant.KEY_Image).toString());
                listId.add(jsonObject.get(Constant.KEY_Id).toString());
                listPrice.add(jsonObject.get(Constant.KEY_Discount).toString());
                listSize.add(jsonObject.get(Constant.KEY_Size).toString());

            }

           /* JSONObject js = new JSONObject();
            js.put("Key","hello");
            js.put("Key1","hello1");
            js.put("Key2","hello2");
            JSONArray ja = new JSONArray();
            ja.add(js);
            Log.d("JSON",ja.toString());*/




        } catch (Exception e) {

            e.printStackTrace();
        }
    }


}

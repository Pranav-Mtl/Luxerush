package com.shoping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoping.luxerush.R;


/**
 * Created by appslure on 18-01-2016.
 */
public class CartAdapter extends  RecyclerView.Adapter<CartAdapter.CartHolder> {

    Context mContext;

    public CartAdapter(Context context){
        mContext=context;




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


    }

    @Override
    public int getItemCount() {

        return 5;
    }




    public static class CartHolder extends RecyclerView.ViewHolder {

        public CartHolder(View gridView) {
            super(gridView);



        }
    }


}

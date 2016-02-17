package com.shoping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoping.Constant.Constant;
import com.shoping.luxerush.R;
import com.squareup.picasso.Picasso;

/**
 * Created by appslure on 13-02-2016.
 */
public class SubscriptionBrandAdapter extends  RecyclerView.Adapter<SubscriptionBrandAdapter.SubscriptionBrandHolder> {

    Context mContext;

    public SubscriptionBrandAdapter(Context context){
        mContext=context;
    }

    @Override
    public SubscriptionBrandHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(mContext).
                inflate(R.layout.subscription_brand_raw, parent, false);

        return new SubscriptionBrandHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubscriptionBrandHolder holder, int position) {
        holder.tvName.setText(Constant.brandName[position]);

        try {
            Picasso.with(mContext)
                    .load(Constant.brandImage[position])
                    .placeholder(R.drawable.ic_default_loading)
                    .resize(100, 100)
                    .error(R.drawable.ic_default_loading)
                    .into(holder.ivImage);
        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        if(Constant.brandName==null)
            return 0;
        return Constant.brandName.length;
    }

    public static class SubscriptionBrandHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageView ivImage;

        public SubscriptionBrandHolder(View gridView) {
            super(gridView);

            tvName= (TextView) gridView.findViewById(R.id.brand_name);
            ivImage= (ImageView) gridView.findViewById(R.id.iv_image);


        }
    }

}

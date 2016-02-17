package com.shoping.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shoping.BL.SubscriptionListBL;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.luxerush.ItemDetail;
import com.shoping.luxerush.R;
import com.shoping.luxerush.SubscriptionItemDetail;
import com.squareup.picasso.Picasso;

/**
 * Created by appslure on 16-02-2016.
 */
public class SubscriptionListAdapter extends  RecyclerView.Adapter<SubscriptionListAdapter.SubscriptionListHolder> {

    Context mContext;
    SubscriptionListBL objSubscriptionListBL;

    public SubscriptionListAdapter(Context context,String subsID){
        mContext=context;
        objSubscriptionListBL=new SubscriptionListBL();
        objSubscriptionListBL.getSubsList(subsID);
    }

    @Override
    public SubscriptionListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(mContext).
                inflate(R.layout.subscription_item_raw, parent, false);

        return new SubscriptionListHolder(itemView,mContext);
    }

    @Override
    public void onBindViewHolder(SubscriptionListHolder holder, int position) {
        try {
            holder.title.setText(Constant.productNameSubs[position]);



            Picasso.with(mContext)
                    .load(Constant.productImageSubs[position])
                    .placeholder(R.drawable.ic_default_loading)
                    .resize(275, 525)
                    .error(R.drawable.ic_default_loading)
                    .into(holder.ivImage);

            holder.rlLayout.setOnClickListener(clickListener);
            holder.rlLayout.setTag(holder);


        }catch (Exception e){

        }

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position;
            SubscriptionListHolder holder = (SubscriptionListHolder) view.getTag();
            position = holder.getAdapterPosition();

            switch (view.getId()){
                case R.id.itemlist_layout:
                    Intent intent=new Intent(mContext, SubscriptionItemDetail.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("ProductID",Constant.productIDSubs[position]);
                    mContext.startActivity(intent);
                    break;

            }
        }};


    @Override
    public int getItemCount() {
        if(Constant.productIDSubs==null)
            return 0;
        return Constant.productIDSubs.length;
    }

    public static class SubscriptionListHolder extends RecyclerView.ViewHolder{

        Context mContext;
        ImageView ivImage;
        TextView originalPrice,discountedPrice,title;
        ImageButton btnWishlist;
        RelativeLayout rlLayout;

        public SubscriptionListHolder(View gridView,Context context) {
            super(gridView);
            mContext=context;
            ivImage= (ImageView) gridView.findViewById(R.id.itemlist_image);
            discountedPrice= (TextView) gridView.findViewById(R.id.itemlist_discount);
            title= (TextView) gridView.findViewById(R.id.itemlist_title);
            rlLayout= (RelativeLayout) gridView.findViewById(R.id.itemlist_layout);
            //gridView.setOnClickListener(this);

        }

    }

}

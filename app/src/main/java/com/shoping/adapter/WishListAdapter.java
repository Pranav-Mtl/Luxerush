package com.shoping.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shoping.BL.WishListBL;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.luxerush.ItemDetail;
import com.shoping.luxerush.R;
import com.squareup.picasso.Picasso;

/**
 * Created by appslure on 10-02-2016.
 */
public class WishListAdapter extends  RecyclerView.Adapter<WishListAdapter.WishListHolder> {

    Context mContext;

    WishListBL objWishListBL;

    String category,tag,userID;

    public WishListAdapter(Context context,String category,String tag,String userID){
        mContext=context;
        this.category=category;
        this.tag=tag;
        this.userID=userID;

        objWishListBL=new WishListBL();
        objWishListBL.getWishList(userID);
    }

    @Override
    public WishListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(mContext).
                inflate(R.layout.popular_item_raw, parent, false);

        return new WishListHolder(itemView,mContext);
    }

    @Override
    public void onBindViewHolder(WishListHolder holder, int position) {
        holder.title.setText(Constant.productNameWish[position]);
        holder.originalPrice.setText("₹"+Constant.productOriginalPriceWish[position]);
        holder.discountedPrice.setText("₹" + Constant.productDiscountedPriceWish[position]);

        if(Constant.productWishlistWish[position].equalsIgnoreCase("N")){
            holder.btnWishlist.setBackgroundResource(R.drawable.ic_red_heart);
        }
        else
        {
            holder.btnWishlist.setBackgroundResource(R.drawable.ic_filled_heart);
        }

        Picasso.with(mContext)
                .load(Constant.productImageWish[position])
                .placeholder(R.drawable.ic_default_loading)
                .resize(275,525)
                .error(R.drawable.ic_default_loading)
                .into(holder.ivImage);

        holder.rlLayout.setOnClickListener(clickListener);
        holder.rlLayout.setTag(holder);

        holder.btnWishlist.setOnClickListener(clickListener);
        holder.btnWishlist.setTag(holder);


    }


View.OnClickListener clickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        int position;
        WishListHolder holder = (WishListHolder) view.getTag();
        position = holder.getAdapterPosition();

        switch (view.getId()){
            case R.id.itemlist_layout:
                Intent intent=new Intent(mContext, ItemDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ProductID",Constant.productIDWish[position]);
                intent.putExtra("Category",category);
                intent.putExtra("Tag",tag);
                intent.putExtra("Keyword",Constant.productKeywordWish[position]);
                mContext.startActivity(intent);
                break;
            case R.id.itemlist_wishlist:

                if(Util.isInternetConnection(mContext)){
                    if(userID==null){
                        Toast.makeText(mContext, "You are not Logged in. Please Login first", Toast.LENGTH_LONG).show();
                    }
                    else {
                        try {
                            String result = new CallWS().execute(userID, Constant.productIDWish[position], category).get();
                            if(result.equalsIgnoreCase(Constant.WS_RESPONSE_SUCCESS)){
                                Constant.productWishlistWish[position]="Y";
                                Toast.makeText(mContext,"Product added to wishList",Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                            }
                            else
                            {
                                Constant.productWishlistWish[position]="N";
                                Toast.makeText(mContext,"Product removed from wishList",Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }};


    @Override
    public int getItemCount() {
        if(Constant.productIDWish==null)
            return 0;
        return Constant.productIDWish.length;
    }

public static class WishListHolder extends RecyclerView.ViewHolder{

        Context mContext;
        ImageView ivImage;
        TextView originalPrice,discountedPrice,title;
        ImageButton btnWishlist;
        RelativeLayout rlLayout;

        public WishListHolder(View gridView,Context context) {
            super(gridView);
            mContext=context;
            ivImage= (ImageView) gridView.findViewById(R.id.itemlist_image);
            originalPrice= (TextView) gridView.findViewById(R.id.itemlist_original);
            discountedPrice= (TextView) gridView.findViewById(R.id.itemlist_discount);
            title= (TextView) gridView.findViewById(R.id.itemlist_title);
            btnWishlist= (ImageButton) gridView.findViewById(R.id.itemlist_wishlist);
            rlLayout= (RelativeLayout) gridView.findViewById(R.id.itemlist_layout);
            //gridView.setOnClickListener(this);

        }


    }


    private class CallWS extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            String result=objWishListBL.addToWishlist(params[0],params[1],params[2]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}

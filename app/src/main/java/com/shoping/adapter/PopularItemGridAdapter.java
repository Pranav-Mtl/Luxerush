package com.shoping.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shoping.BL.ItemsListBL;
import com.shoping.BL.WishListBL;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.luxerush.ItemDetail;
import com.shoping.luxerush.R;
import com.squareup.picasso.Picasso;

/**
 * Created by appslure on 25-01-2016.
 */
public class PopularItemGridAdapter extends  RecyclerView.Adapter<PopularItemGridAdapter.PopularItemGridHolder> {

    Context mContext;

    PopularItemAdapter objPopularItemAdapter;
    ItemsListBL objItemsListBL;
    String category,tag,userID;

    WishListBL objWishListBL;

    public PopularItemGridAdapter(Context context,String category,String tag,String userID,String brand){
        mContext=context;
        objItemsListBL=new ItemsListBL();
        objWishListBL=new WishListBL();
        this.category=category;
        this.tag=tag;
        this.userID=userID;
        objItemsListBL.getList(category,tag,mContext,userID,brand);
    }

    public PopularItemGridAdapter(Context context,String category,String tag,String userID,String brand,String categoryFilter,String size,String minPrice,String maxPrice){
        mContext=context;
        objItemsListBL=new ItemsListBL();
        objWishListBL=new WishListBL();
        this.category=category;
        this.tag=tag;
        this.userID=userID;
        objItemsListBL.getFilterList(category,tag,mContext,userID, brand,categoryFilter, size, minPrice, maxPrice);
    }

    @Override
    public PopularItemGridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(mContext).
                inflate(R.layout.popular_item_raw, parent, false);

        return new PopularItemGridHolder(itemView,mContext);
    }

    @Override
    public void onBindViewHolder(PopularItemGridHolder holder, int position) {

        try {
            holder.title.setText(Constant.productName[position]);
            holder.originalPrice.setText("₹" + Constant.productOriginalPrice[position]);
            holder.discountedPrice.setText("₹" + Constant.productDiscountedPrice[position]);

            if (Constant.productWishlist[position].equalsIgnoreCase("N")) {
                holder.btnWishlist.setBackgroundResource(R.drawable.ic_red_heart);
            } else {
                holder.btnWishlist.setBackgroundResource(R.drawable.ic_filled_heart);
            }

            Picasso.with(mContext)
                    .load(Constant.productImage[position])
                    .placeholder(R.drawable.ic_default_loading)
                    .resize(275, 525)
                    .error(R.drawable.ic_default_loading)
                    .into(holder.ivImage);

            holder.rlLayout.setOnClickListener(clickListener);
            holder.rlLayout.setTag(holder);

            holder.btnWishlist.setOnClickListener(clickListener);
            holder.btnWishlist.setTag(holder);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position;
            PopularItemGridHolder holder = (PopularItemGridHolder) view.getTag();
            position = holder.getAdapterPosition();

            switch (view.getId()){
                case R.id.itemlist_layout:
                    Intent intent=new Intent(mContext, ItemDetail.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("ProductID",Constant.productID[position]);
                    intent.putExtra("Category",category);
                    intent.putExtra("Tag",tag);
                    intent.putExtra("Keyword",Constant.productKeyword[position]);
                    mContext.startActivity(intent);
                    break;
                case R.id.itemlist_wishlist:

                        if(Util.isInternetConnection(mContext)){
                            if(userID==null){
                                Toast.makeText(mContext,"You are not Logged in. Please Login first",Toast.LENGTH_LONG).show();
                            }
                            else {
                                try {
                                    String result = new CallWS().execute(userID, Constant.productID[position], category).get();
                                    if(result.equalsIgnoreCase(Constant.WS_RESPONSE_SUCCESS)){
                                        Constant.productWishlist[position]="Y";
                                        Toast.makeText(mContext,"Product added to wishList",Toast.LENGTH_LONG).show();
                                        notifyDataSetChanged();
                                    }
                                    else
                                    {
                                        Constant.productWishlist[position]="N";
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
        if(Constant.productID==null)
            return 0;
        return Constant.productID.length;
    }

    public static class PopularItemGridHolder extends RecyclerView.ViewHolder{

        Context mContext;
        ImageView ivImage;
        TextView originalPrice,discountedPrice,title;
        ImageButton btnWishlist;
        RelativeLayout rlLayout;

        public PopularItemGridHolder(View gridView,Context context) {
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

    private class CallWS extends AsyncTask<String,String,String>{
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

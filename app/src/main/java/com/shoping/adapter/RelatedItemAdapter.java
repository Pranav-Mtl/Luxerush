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

import com.shoping.Constant.Constant;
import com.shoping.luxerush.ItemDetail;
import com.shoping.luxerush.R;
import com.squareup.picasso.Picasso;

/**
 * Created by appslure on 09-02-2016.
 */
public class RelatedItemAdapter extends  RecyclerView.Adapter<RelatedItemAdapter.RelatedHolder> {

    Context mContext;
    String category,tag;

    public RelatedItemAdapter(Context context,String category,String tag){
        mContext=context;
        this.category=category;
        this.tag=tag;

    }

    @Override
    public RelatedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(mContext).
                inflate(R.layout.related_item_raw, parent, false);

        return new RelatedHolder(itemView,mContext);
    }

    @Override
    public void onBindViewHolder(RelatedHolder holder, int position) {

        holder.title.setText(Constant.productNameRelated[position]);
        holder.originalPrice.setText("₹"+Constant.productOriginalPriceRelated[position]);
        holder.discountedPrice.setText("₹" + Constant.productDiscountedPriceRelated[position]);

        if(Constant.productWishlistRelated[position].equalsIgnoreCase("N")){
            holder.btnWishlist.setBackgroundResource(R.drawable.ic_red_heart);
        }
        else
        {
            holder.btnWishlist.setBackgroundResource(R.drawable.ic_filled_heart);
        }

        Picasso.with(mContext)
                .load(Constant.productImageRelated[position])
                .placeholder(R.drawable.ic_default_loading)
                .resize(232,462)
                .error(R.drawable.ic_default_loading)
                .into(holder.ivImage);

        holder.rlLayout.setOnClickListener(clickListener);
        holder.rlLayout.setTag(holder);

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position;
            RelatedHolder holder = (RelatedHolder) view.getTag();
            position = holder.getAdapterPosition();

            switch (view.getId()){
                case R.id.itemlist_layout:
                    Intent intent=new Intent(mContext, ItemDetail.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("ProductID",Constant.productIDRelated[position]);
                    intent.putExtra("Category",category);
                    intent.putExtra("Tag",tag);
                    intent.putExtra("Keyword",Constant.productKeywordRelated[position]);
                    mContext.startActivity(intent);
                    break;
            }
        }};


    @Override
    public int getItemCount() {
        if(Constant.productIDRelated==null)
            return 0;
        return Constant.productIDRelated.length;
    }


    public static class RelatedHolder extends RecyclerView.ViewHolder{

        Context mContext;
        ImageView ivImage;
        TextView originalPrice,discountedPrice,title;
        ImageButton btnWishlist;
        RelativeLayout rlLayout;

        public RelatedHolder(View gridView,Context context) {
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
}

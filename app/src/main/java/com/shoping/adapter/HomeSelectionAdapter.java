package com.shoping.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shoping.BL.HomeSelectionBL;
import com.shoping.Constant.Constant;
import com.shoping.luxerush.R;
import com.shoping.luxerush.SubscriptionDetail;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by appslure on 26-02-2016.
 */
public class HomeSelectionAdapter extends  RecyclerView.Adapter<HomeSelectionAdapter.HomeSelectionHolder> {

    Context mContext;
    HomeSelectionBL objHomeSelectionBL;
    public HashMap<Integer,String> selectedBrand=new HashMap<Integer,String>();

    public HomeSelectionAdapter(Context context){
        mContext=context;
    }

    @Override
    public HomeSelectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(mContext).
                inflate(R.layout.home_selection_raw, parent, false);

        return new HomeSelectionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeSelectionHolder holder, int position) {
        holder.tvName.setText(Constant.allBrandName[position]);

        Picasso.with(mContext)
                .load(Constant.allBrandImage[position])
                .placeholder(R.drawable.ic_default_loading)
                .error(R.drawable.ic_default_loading)
                .into(holder.ivImage);

        holder.rlItemList.setOnClickListener(clickListener);
        holder.rlItemList.setTag(holder);


    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position;
            HomeSelectionHolder holder = (HomeSelectionHolder) view.getTag();
            position = holder.getAdapterPosition();

            switch (view.getId()){
                case R.id.itemlist_layout:
                    if(holder.cbCheck.isChecked()){
                        holder.cbCheck.setChecked(false);
                        selectedBrand.remove(position);
                    }
                    else {
                        holder.cbCheck.setChecked(true);
                        selectedBrand.put(position,Constant.allBrandID[position]);
                    }

                    break;
            }
        }};


    @Override
    public int getItemCount() {
        if(Constant.allBrandID==null)
            return 0;
        return Constant.allBrandID.length;
    }

    public static class HomeSelectionHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvName;
        CheckBox cbCheck;
        RelativeLayout rlItemList;


        public HomeSelectionHolder(View gridView) {
            super(gridView);

            ivImage= (ImageView) gridView.findViewById(R.id.itemlist_image);
            tvName= (TextView) gridView.findViewById(R.id.itemlist_title);
            cbCheck= (CheckBox) gridView.findViewById(R.id.check_item);
            rlItemList= (RelativeLayout) gridView.findViewById(R.id.itemlist_layout);


        }
    }

}

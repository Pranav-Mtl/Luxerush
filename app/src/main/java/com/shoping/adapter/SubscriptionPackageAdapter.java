package com.shoping.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shoping.BL.SubscriptionPackageBL;
import com.shoping.Constant.Constant;
import com.shoping.luxerush.ItemDetail;
import com.shoping.luxerush.R;
import com.shoping.luxerush.SubscriptionDetail;

/**
 * Created by appslure on 13-02-2016.
 */

public class SubscriptionPackageAdapter extends  RecyclerView.Adapter<SubscriptionPackageAdapter.SubscriptionPackagHolder> {
    Context mContext;

    SubscriptionPackageBL objSubscriptionPackageBL;

    public int mSelectedItem = -1;



    CheckBox lastChecked;

    public SubscriptionPackageAdapter(Context context){
        mContext=context;
        objSubscriptionPackageBL=new SubscriptionPackageBL();
        objSubscriptionPackageBL.getAllPackages();
    }
    @Override
    public SubscriptionPackagHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(mContext).
                inflate(R.layout.subscription_package_raw, parent, false);

        return new SubscriptionPackagHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubscriptionPackagHolder holder,final int position) {
        holder.tvName.setText(Constant.packageName[position]);
        holder.tvClothes.setText(Constant.packageBags[position]+ " bags and "+Constant.packageClothes[position]+" clothes");
        holder.tvPrice.setText("₹"+Constant.packagePrice[position]);


        holder.cbCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckBox cb = (CheckBox) v;

                if (cb.isChecked()) {

                    if (lastChecked != null) {
                        lastChecked.setChecked(false);
                        lastChecked = null;
                    }

                    mSelectedItem = position;
                    lastChecked = cb;

                } else {
                    if (lastChecked != null) {
                        lastChecked.setChecked(false);
                        lastChecked = null;
                    }
                    mSelectedItem = -1;
                }


            }
        });

        holder.llPacakge.setOnClickListener(clickListener);
        holder.llPacakge.setTag(holder);

    }

    @Override
    public int getItemCount() {
        if(Constant.packageID==null)
            return 0;
        return Constant.packageID.length;
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position;
            SubscriptionPackagHolder holder = (SubscriptionPackagHolder) view.getTag();
            position = holder.getAdapterPosition();

            switch (view.getId()){
                case R.id.ll_package:
                    Intent intent=new Intent(mContext, SubscriptionDetail.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("ID",Constant.packageID[position]);
                    mContext.startActivity(intent);
                    break;
            }
        }};

    public static class SubscriptionPackagHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvClothes,tvPrice;
        CheckBox cbCheckbox;
        LinearLayout llPacakge;

        public SubscriptionPackagHolder(View gridView) {
            super(gridView);

            tvName= (TextView) gridView.findViewById(R.id.package_name);
            tvClothes= (TextView) gridView.findViewById(R.id.package_clothes);
            tvPrice= (TextView) gridView.findViewById(R.id.package_price);
            cbCheckbox= (CheckBox) gridView.findViewById(R.id.package_checkbox);
            llPacakge= (LinearLayout) gridView.findViewById(R.id.ll_package);

        }
    }

}

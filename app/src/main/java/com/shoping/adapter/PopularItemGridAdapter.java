package com.shoping.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.shoping.luxerush.ItemDetail;
import com.shoping.luxerush.R;

/**
 * Created by appslure on 25-01-2016.
 */
public class PopularItemGridAdapter extends  RecyclerView.Adapter<PopularItemGridAdapter.PopularItemGridHolder> {

    Context mContext;

    PopularItemAdapter objPopularItemAdapter;

    public PopularItemGridAdapter(Context context){
        mContext=context;
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


    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class PopularItemGridHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context mContext;
        public PopularItemGridHolder(View gridView,Context context) {
            super(gridView);
            mContext=context;

            gridView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mContext.startActivity(new Intent(mContext, ItemDetail.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        }
    }

}

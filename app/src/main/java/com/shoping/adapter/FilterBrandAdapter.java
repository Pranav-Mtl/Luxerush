package com.shoping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.luxerush.R;
import com.squareup.picasso.Picasso;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by appslure on 15-02-2016.
 */
public class FilterBrandAdapter extends  RecyclerView.Adapter<FilterBrandAdapter.FilterBrandHolder> {

    Context mContext;
    String brandJson;

    ArrayList listName=new ArrayList();
    ArrayList listImage=new ArrayList();
    ArrayList listID=new ArrayList();

   public  HashMap<Integer,String> hashMap=new HashMap<Integer,String> ();

    public int mSelectedItem = -1;
    CheckBox lastChecked;

    public FilterBrandAdapter(Context context){
        mContext=context;
        brandJson= Util.getSharedPrefrenceValue(mContext, Constant.SP_FILTER_BRAND);
        validate(brandJson);

    }

    @Override
    public FilterBrandHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(mContext).
                inflate(R.layout.filter_brand_raw, parent, false);

        return new FilterBrandHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FilterBrandHolder holder,final int position) {

        holder.tvName.setText(listName.get(position).toString());

        Picasso.with(mContext)
                .load(listImage.get(position).toString())
                .placeholder(R.drawable.ic_default_loading)
                .resize(350,550)
                .error(R.drawable.ic_default_loading)
                .into(holder.ivImage);


        holder.cbCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckBox cb = (CheckBox) v;

                if (cb.isChecked()) {

                    hashMap.put(position,listID.get(position).toString());

                    mSelectedItem++;


                } else {
                    hashMap.remove(position);
                    mSelectedItem--;
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return listName.size();
    }

    public static class FilterBrandHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        CheckBox cbCheckbox;
        ImageView ivImage;


        public FilterBrandHolder(View gridView) {
            super(gridView);

            tvName= (TextView) gridView.findViewById(R.id.brand_item_name);
            cbCheckbox= (CheckBox) gridView.findViewById(R.id.brand_item_checkbox);
            ivImage= (ImageView) gridView.findViewById(R.id.brand_item_image);

        }
    }

    private void validate(String strValue)    {
        String personalDetail="",state="";
        JSONParser jsonP=new JSONParser();

        try {
            Object obj =jsonP.parse(strValue);
            JSONArray jsonArrayObject = (JSONArray)obj;

            for(int i=0;i<jsonArrayObject.size();i++) {
                JSONObject jsonObject = (JSONObject) jsonP.parse(jsonArrayObject.get(i).toString());
                listName.add(jsonObject.get("brand_name").toString());
                listImage.add(jsonObject.get("brand_image").toString());
                listID.add(jsonObject.get("brand_id").toString());
            }


        } catch (Exception e) {


        }

    }
}

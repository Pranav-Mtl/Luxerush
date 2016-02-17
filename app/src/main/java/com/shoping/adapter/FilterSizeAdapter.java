package com.shoping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;
import com.shoping.luxerush.R;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by appslure on 15-02-2016.
 */
public class FilterSizeAdapter extends  RecyclerView.Adapter<FilterSizeAdapter.FilterSizeHolder>{

    Context mContext;

    String sizeJson;

    ArrayList listSize=new ArrayList();

    public HashMap<String,String> hashMap=new HashMap<String,String> ();

    public int mSelectedItem = -1;

    public FilterSizeAdapter(Context context){
        mContext=context;
        sizeJson= Util.getSharedPrefrenceValue(mContext, Constant.SP_FILTER_SIZE);
        validate(sizeJson);

    }
    @Override
    public FilterSizeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(mContext).
                inflate(R.layout.filter_category_raw, parent, false);

        return new FilterSizeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FilterSizeHolder holder,final int position) {
        holder.tvName.setText(listSize.get(position).toString());

        holder.cbCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckBox cb = (CheckBox) v;

                if (cb.isChecked()) {

                    hashMap.put(position+"", listSize.get(position).toString());

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
        return listSize.size();
    }

    public static class FilterSizeHolder extends RecyclerView.ViewHolder  {

        TextView tvName;
        CheckBox cbCheckbox;
        ImageView ivImage;


        public FilterSizeHolder(View gridView) {
            super(gridView);

            tvName= (TextView) gridView.findViewById(R.id.category_item_name);
            cbCheckbox= (CheckBox) gridView.findViewById(R.id.category_item_checkbox);

        }
    }

    private void validate(String strValue)    {
        try {
            String ss[] = strValue.split(",");
            for (int i = 0; i < ss.length; i++) {
                listSize.add(ss[i]);
            }
        }catch (Exception e){

        }


    }
}

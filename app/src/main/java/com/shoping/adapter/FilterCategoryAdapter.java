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
public class FilterCategoryAdapter extends  RecyclerView.Adapter<FilterCategoryAdapter.FilterCategoryHolder> {
    Context mContext;

    String categoryJson;

    ArrayList listName=new ArrayList();
    ArrayList listID=new ArrayList();

    public HashMap<Integer,String> hashMap=new HashMap<Integer,String> ();

    public int mSelectedItem = -1;

    public FilterCategoryAdapter(Context context){
        mContext=context;
        categoryJson= Util.getSharedPrefrenceValue(mContext, Constant.SP_FILTER_CATEGORY);
        validate(categoryJson);

    }

    @Override
    public FilterCategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(mContext).
                inflate(R.layout.filter_category_raw, parent, false);

        return new FilterCategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FilterCategoryHolder holder,final int position) {
        holder.tvName.setText(listName.get(position).toString());

        holder.cbCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckBox cb = (CheckBox) v;

                if (cb.isChecked()) {

                    hashMap.put(position, listID.get(position).toString());

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

    public static class FilterCategoryHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        CheckBox cbCheckbox;
        ImageView ivImage;


        public FilterCategoryHolder(View gridView) {
            super(gridView);

            tvName= (TextView) gridView.findViewById(R.id.category_item_name);
            cbCheckbox= (CheckBox) gridView.findViewById(R.id.category_item_checkbox);



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
                listName.add(jsonObject.get("category_name").toString());
                listID.add(jsonObject.get("category_id").toString());
            }


        } catch (Exception e) {


        }

    }
}

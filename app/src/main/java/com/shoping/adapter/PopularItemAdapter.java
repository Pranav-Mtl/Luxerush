package com.shoping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.shoping.luxerush.R;

/**
 * Created by appslure on 24-01-2016.
 */
public class PopularItemAdapter extends BaseAdapter {

    Context context;

    public PopularItemAdapter(Context mContext){
        context=mContext;
    }
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertView!=null)
        {
            gridView=convertView;
        }

        else {
            gridView = new View(context);
            gridView = layoutInflater.inflate(R.layout.popular_item_raw, null);
        }
        return gridView;
    }
}

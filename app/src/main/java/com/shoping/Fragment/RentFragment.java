package com.shoping.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shoping.adapter.OrderRentAdapter;
import com.shoping.adapter.OrderSubscriptionAdapter;
import com.shoping.luxerush.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RentFragment extends Fragment {

    RecyclerView listBuy;

    OrderRentAdapter objOrderBuyAdapter;

    public RentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_rent, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        listBuy= (RecyclerView) view.findViewById(R.id.rent_order_list);



        listBuy.setHasFixedSize(true);

        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listBuy.setLayoutManager(llm);


        objOrderBuyAdapter=new OrderRentAdapter(getActivity());
        listBuy.setAdapter(objOrderBuyAdapter);
    }

}

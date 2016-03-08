package com.shoping.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shoping.adapter.OrderBuyAdapter;
import com.shoping.adapter.OrderSubscriptionAdapter;
import com.shoping.luxerush.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubscriptionFragment extends Fragment {

    RecyclerView listBuy;

    OrderSubscriptionAdapter objOrderBuyAdapter;

    public SubscriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_subscription, container, false);
        View view= inflater.inflate(R.layout.fragment_subscription, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        listBuy= (RecyclerView) view.findViewById(R.id.subscription_order_list);



        listBuy.setHasFixedSize(true);

        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listBuy.setLayoutManager(llm);


        objOrderBuyAdapter=new OrderSubscriptionAdapter(getActivity());
        listBuy.setAdapter(objOrderBuyAdapter);
    }


}

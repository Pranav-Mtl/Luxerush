package com.shoping.Fragment;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shoping.adapter.OrderBuyAdapter;
import com.shoping.luxerush.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class BuyFragment extends Fragment {


    RecyclerView listBuy;

    OrderBuyAdapter objOrderBuyAdapter;
    public BuyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_buy, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        listBuy= (RecyclerView) view.findViewById(R.id.buy_order_list);



        listBuy.setHasFixedSize(true);

        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listBuy.setLayoutManager(llm);


        objOrderBuyAdapter=new OrderBuyAdapter(getActivity());
        listBuy.setAdapter(objOrderBuyAdapter);
    }

}

package com.shoping.luxerush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.efor18.rangeseekbar.RangeSeekBar;
import com.shoping.Configuration.Util;
import com.shoping.Constant.Constant;

public class FilterPrice extends AppCompatActivity implements View.OnClickListener {

    TextView min,max;

    Button btnDone;

    Intent intent;

    String minPriceJson,maxPriceJson;
    int intMin,intMax;

    String minPrice;
    String maxPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_price);
        init();
    }

    private void init(){
        min = (TextView) findViewById(R.id.minValue);
        max = (TextView) findViewById(R.id.maxValue);
        btnDone= (Button) findViewById(R.id.filter_price_apply);

        intent=getIntent();
        minPriceJson= Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_FILTER_MIN);
        maxPriceJson= Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_FILTER_MAX);

        if(minPriceJson!=null && maxPriceJson!=null){

            intMin=Integer.valueOf(minPriceJson);
            intMax=Integer.valueOf(maxPriceJson);

            minPrice=minPriceJson;
            maxPrice=maxPriceJson;
            min.setText(minPriceJson);

            max.setText(maxPriceJson);

            RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(intMin,intMax, this);
            seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                    // handle changed range values

                    minPrice=minValue.toString();
                    maxPrice=maxValue.toString();
                    min.setText(minValue.toString());

                    max.setText(maxValue.toString());

                }
            });

            // add RangeSeekBar to pre-defined layout
            ViewGroup layout = (ViewGroup) findViewById(R.id.layout);
            layout.addView(seekBar);

        }


        btnDone.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.filter_price_apply:
                    intent.putExtra("MinPrice",minPrice);
                    intent.putExtra("MaxPrice",maxPrice);
                    setResult(RESULT_OK,intent);
                    finish();

                break;
        }
    }
}

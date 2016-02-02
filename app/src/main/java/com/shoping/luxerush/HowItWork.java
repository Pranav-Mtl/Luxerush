package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.shoping.Container.PagerContainer;

import java.util.Locale;

public class HowItWork extends AppCompatActivity {


    ViewPager mViewPager;

    PagerContainer mContainer;

    ImageButton btnFB,btnEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_it_work);
        btnFB= (ImageButton) findViewById(R.id.hit_fb);
        btnEmail= (ImageButton) findViewById(R.id.hit_mail);


        mContainer = (PagerContainer) findViewById(R.id.pager_container);

        ViewPager pager = mContainer.getViewPager();
        //ViewPager pager=(ViewPager) view.findViewById(R.id.pager);
        //pager.setPageTransformer(true, new BigImage());

        mContainer=new PagerContainer(getApplicationContext());



       // PagerContainer.setLayout(getActivity(),llCareager,llOldNewCar,llServices);

        DealerPageAdapter adapter=new DealerPageAdapter(getApplicationContext());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(adapter.getCount());
        //A little space between pages

        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        pager.setClipChildren(false);

        btnFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeScreenOptions.class));
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignupScreen.class));
            }
        });
    }




    private class DealerPageAdapter extends PagerAdapter {
        Context context;
        LayoutInflater inflater;
        ImageView imgPager;

        DealerPageAdapter(Context contex) {
            this.context = contex;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.fragment_hiwfragment_one, container,
                    false);





            ((ViewPager) container).addView(itemView);

            return itemView;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        /*public float getPageWidth(int position)
        {
            return .9f;
        }*/

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}

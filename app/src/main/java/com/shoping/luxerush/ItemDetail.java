package com.shoping.luxerush;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoping.Container.GalleryPagerContainer;

public class ItemDetail extends AppCompatActivity {

    GalleryPagerContainer mContainer;

    TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        initialize();
    }

    private void initialize(){
        mContainer = (GalleryPagerContainer) findViewById(R.id.item_pager_container);




        ViewPager pager = mContainer.getViewPager();



        mContainer=new GalleryPagerContainer(getApplicationContext());


        ItemPageAdapter adapter=new ItemPageAdapter(getApplicationContext());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(adapter.getCount());
        pager.setPadding(20,0,20,0);
        //A little space between pages

        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        pager.setClipChildren(false);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {


            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private class ItemPageAdapter extends PagerAdapter {
        Context context;
        LayoutInflater inflater;
        ImageView imgPager;


        ItemPageAdapter(Context contex) {
            this.context = contex;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.list_pager_raw, container,
                    false);

            imgPager= (ImageView) itemView.findViewById(R.id.list_pager_image);


            imgPager.setBackgroundResource(R.drawable.ic_default_popular_item);
            ((ViewPager) container).addView(itemView);

            return itemView;
        }

        @Override
        public int getCount() {
            return 10;
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

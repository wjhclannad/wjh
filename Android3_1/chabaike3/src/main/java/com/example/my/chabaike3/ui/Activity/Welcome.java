package com.example.my.chabaike3.ui.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.my.chabaike3.MainActivity;
import com.example.my.chabaike3.R;

import java.util.ArrayList;
import java.util.List;

public class Welcome extends Activity {
    private ViewPager viewpager_wc;
    private LinearLayout linear_wc;
    private int[] imagers;
    private List<ImageView> imageViews;
    private wcMyadapter adapter_wc;
    private int cunter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ini();
        inidata();
        adapter_wc=new wcMyadapter();
        viewpager_wc.setAdapter(adapter_wc);
        viewpager_wc.addOnPageChangeListener(adapter_wc);
    }
    private void ini() {
        viewpager_wc= (ViewPager) findViewById(R.id.viewpager__wc);
        linear_wc= (LinearLayout) findViewById(R.id.linear_wc);
        imagers=new int[]{R.mipmap.slide1,R.mipmap.slide2,R.mipmap.slide3 };
        imageViews=new ArrayList<ImageView>();
    }

    private void inidata() {
        View view=null;
        ImageView imageView=null;
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(10,10);
        ViewGroup.LayoutParams vlp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewPager.LayoutParams.MATCH_PARENT);
        lp.leftMargin=10;

        for (int i=0;i<imagers.length;i++){
            imageView=new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(vlp);
            imageView.setImageResource(imagers[i]);
            imageViews.add(imageView);

            if (i==2){
                imageViews.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Welcome.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            view =new View(this);
            if (i==0){
                view.setBackgroundResource(R.drawable.page_now);
            }else {
                view.setBackgroundResource(R.drawable.page);
            }
            view.setLayoutParams(lp);

            linear_wc.addView(view);
        }
    }
    class  wcMyadapter extends PagerAdapter implements ViewPager.OnPageChangeListener{

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews.get(position));
            return imageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            linear_wc.getChildAt(position).setBackgroundResource(R.drawable.page_now);
            linear_wc.getChildAt(cunter).setBackgroundResource(R.drawable.page);
            cunter=position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}

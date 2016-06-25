package com.example.my.chabaike3;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.my.chabaike3.beans.TabInfo;
import com.example.my.chabaike3.ui.Fragment.Fragment_cbk;

public class MainActivity extends FragmentActivity {
    private ViewPager viewpager_main;
    private TabLayout tab_main;
    private TabInfo[] tabs = new TabInfo[]{
            new TabInfo(6,"社会热点"),
            new TabInfo(1,"企业要闻"),
            new TabInfo(2,"医疗新闻"),
            new TabInfo(3,"生活贴士"),
            new TabInfo(4,"药品新闻"),
            new TabInfo(7,"疾病快讯"),
            new TabInfo(5,"食品新闻")
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniview();
    }

    private void iniview() {
        viewpager_main= (ViewPager) findViewById(R.id.viewpager_main);
        tab_main= (TabLayout) findViewById(R.id.tab_main);
        viewpager_main.setAdapter(new ContentAdapter(getSupportFragmentManager()));
        tab_main.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab_main.setupWithViewPager(viewpager_main);
    }



    public class ContentAdapter extends FragmentStatePagerAdapter {
        public ContentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment_cbk cf = new Fragment_cbk();
            Bundle bundle = new Bundle();
            bundle.putInt("id",tabs[position].num);
            cf.setArguments(bundle);
            return cf;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position].name;
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}

package com.singwai.currenttoptennews;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.singwai.currenttoptennews.activity.Fragment.BaseFragment;
import com.singwai.currenttoptennews.activity.Fragment.HomeFragment;
import com.singwai.currenttoptennews.activity.Fragment.NewsFragment;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ViewPager pager;
    private ArrayList<BaseFragment> fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager)this.findViewById(R.id.mainViewPager);
        initViewPager();

    }


    private void initViewPager() {
        //Build Configuration
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());

        MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(fragmentPagerAdapter);
        pager.setCurrentItem(1);

    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
}

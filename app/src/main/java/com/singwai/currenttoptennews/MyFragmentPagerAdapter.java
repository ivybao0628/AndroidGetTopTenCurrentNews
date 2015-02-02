package com.singwai.currenttoptennews;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.singwai.currenttoptennews.activity.Fragment.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Singwai Chan on 2/1/15.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<BaseFragment> fragments;

    public MyFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public MyFragmentPagerAdapter(FragmentManager fragmentManager, ArrayList<BaseFragment> fragments) {
        super(fragmentManager);
        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Object obj = super.instantiateItem(container, position);
        return obj;
    }
}


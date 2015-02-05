package com.singwai.currenttoptennews;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.singwai.currenttoptennews.R;
import com.singwai.currenttoptennews.activity.Fragment.HomeFragment;

import java.util.ArrayList;

public class HomeActivity extends ActionBarActivity {

    private FrameLayout fragmentLayout;
    private HomeFragment homeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, homeFragment).commit();
    }

}

package com.singwai.currenttoptennews.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.singwai.currenttoptennews.R;
import com.singwai.currenttoptennews.fragment.HomeFragment;

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

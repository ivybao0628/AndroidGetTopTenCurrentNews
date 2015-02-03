package com.singwai.currenttoptennews.configutation;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.singwai.currenttoptennews.Utility.SharePreferenceUtility;

/**
 * Created by Singwai Chan on 2/1/15.
 */
public class Configuration {
    private static Configuration _instance = null;
    private static Context _context;

    //All the configuration value
    private static final String IS_AUTO_SWAP = "isAutoSwap";
    private static final String AUTO_SWAP_TIME = "autoSwapTime";
    private static final String NEWS_SECTION = "newsSectionPosition";

    public static Configuration get_instance(Context context) {
        if (_instance == null) {
            //Since the configuration will live for very long time, we should use the application context.
            Configuration._context = context.getApplicationContext();
            _instance = new Configuration();
            loadConfiguration();
        }
        return _instance;
    }

    public static Configuration get_instance() {
        if (_instance == null) {
            throw new RuntimeException("A context is required to initialize a configuration object");
        }
        return _instance;
    }

    public boolean getAutoSwap() {
        return isAutoSwap;
    }

    public void setAutoSwap(boolean isAutoSwap) {
        this.isAutoSwap = isAutoSwap;
    }

    public int getAutoSwapTime() {
        return autoSwapTime;
    }

    public void setAutoSwapTime(int autoSwapTime) {
        this.autoSwapTime = autoSwapTime;
    }

    public int getNewsSectionPosition() {
        return newsSectionPosition;
    }

    public void setNewsSectionPosition(int newsSectionPosition) {
        this.newsSectionPosition = newsSectionPosition;
    }

    private boolean isAutoSwap;
    private int autoSwapTime;
    private int newsSectionPosition;


    //Save updated configuration info to SharePreferences
    //In this purposes, we will only save the configuration when user fire the search button.
    public static void saveConfiguration() {
        SharedPreferences.Editor editor = SharePreferenceUtility.getEditor(_context);
        editor.putBoolean(IS_AUTO_SWAP, _instance.getAutoSwap());
        editor.putInt(AUTO_SWAP_TIME, _instance.getAutoSwapTime());
        editor.putInt(NEWS_SECTION, _instance.getNewsSectionPosition());
        editor.commit();
        return;
    }

    //Get configuration info from SharePreferences
    private static void loadConfiguration() {
        SharedPreferences sharedPreferences = SharePreferenceUtility.getSharePreferences(_context);
        _instance.setAutoSwap(sharedPreferences.getBoolean(IS_AUTO_SWAP, true));
        _instance.setAutoSwapTime(sharedPreferences.getInt(AUTO_SWAP_TIME, 15));
        _instance.setNewsSectionPosition(sharedPreferences.getInt(NEWS_SECTION, 0));
    }

    //Singleton design.
    private Configuration() {
    }

    public static void print() {
        if (_instance == null) {
            return;
        } else {
            Log.e("Checking Configuration ", "is Auto swipe " + _instance.getAutoSwap());
            Log.e("Checking Configuration ", "Timer:  " + _instance.getAutoSwapTime());
            Log.e("Checking Configuration ", " Position: " + _instance.getNewsSectionPosition());
        }
    }
}

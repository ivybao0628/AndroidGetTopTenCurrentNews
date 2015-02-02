package com.singwai.currenttoptennews.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.singwai.currenttoptennews.R;

/**
 * Created by Singwai Chan on 2/1/15.
 * This is a helper class to get the "Default Share Preference"
 */
public class SharePreferenceUtility {
    private SharePreferenceUtility() {
    }

    public static SharedPreferences getSharePreferences(Context context) {
        return context.getSharedPreferences(context.getResources().getString(R.string.defaultSharePreferences), Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return context.getSharedPreferences(context.getResources().getString(R.string.defaultSharePreferences), Context.MODE_PRIVATE).edit();
    }

}

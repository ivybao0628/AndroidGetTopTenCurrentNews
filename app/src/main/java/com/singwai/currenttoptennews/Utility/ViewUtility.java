package com.singwai.currenttoptennews.Utility;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Singwai Chan on 2/2/15.
 */
public class ViewUtility {

    //Reorder the existing linearlayout in the view.
    public static void shuffleLinearLayout (LinearLayout linearLayout){
        Log.e("Shuffle Views", "Shuffle Views!!");


        int childCount = linearLayout.getChildCount();

        //Put all views in an array
        ArrayList<View> views = new ArrayList<>(childCount);
        for (int i = 0 ; i < childCount ; i++){
            views.add(linearLayout.getChildAt(i));
            Log.e (linearLayout.getChildAt(i).getId()+"", "My ID is ");
        }

        //Remove all views in linearLayout
        linearLayout.removeAllViews();
        //shuffle the array
        Collections.shuffle(views, new Random(System.nanoTime()));

        //Put everything back
        for (int i = 0 ; i < views.size() ; i ++){
            linearLayout.addView(views.get(i));
            Log.e (views.get(i).getId()+"", "My ID is ");
        }
    }
}

package com.singwai.currenttoptennews.Utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

    /*
     * This is a function to call when there is an error.
     * After user click okay, it should exit the application.
     * To avoid leak window, make sure context is a in the foreground.
     */
    public static void existApplicationErrorDialog (final Context context, final String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(message);
        builder.setCancelable(false);
        builder.setNeutralButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(homeIntent);
            }
        });

    }
}

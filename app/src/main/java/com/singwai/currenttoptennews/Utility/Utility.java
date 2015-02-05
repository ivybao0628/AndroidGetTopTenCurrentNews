package com.singwai.currenttoptennews.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Singwai Chan on 2/3/15.
 */
public class Utility {

    public static String cleanURL (final String input){
        return input.replaceAll("[^a-zA-Z0-9]", "+");
    }

    public static boolean isOnline (final Context context, final String url){
        if (isOnMainThread()){
            throw new RuntimeException("Network request shouldn't be run on main thread");
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo!= null && activeNetworkInfo.isConnected()){
            return true;
            //Should try to connect to a URL, for example wifi to ensure that device does have public internet.

        }
        return false;
    }

    public static boolean isOnline (final Context context){
        Utility.isOnline(context, "www.google.com");
    }


    public static boolean isOnMainThread (){
        return (Looper.getMainLooper().getThread() == Thread.currentThread()) ;
    }
}

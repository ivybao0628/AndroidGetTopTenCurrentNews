package com.singwai.currenttoptennews.Utility;

/**
 * Created by Singwai Chan on 2/3/15.
 */
public class Utility {

    public static String cleanURL (final String input){
        return input.replaceAll("[^a-zA-Z0-9]", "+");
    }
}

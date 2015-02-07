package com.singwai.currenttoptennews.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.singwai.currenttoptennews.fragment.NewsFragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Singwai Chan on 2/1/15.
 */
public class AsyncHttpImageLinkToBitmap extends AsyncTask<String, Void, Void> {

    //Call back to attachment the imageView if needed.
    private NewsFragment.FragmentCallback fragmentCallback;
    private NewsItem newsItem;

    public AsyncHttpImageLinkToBitmap(NewsFragment.FragmentCallback fragmentCallback, NewsItem newsItem) {
        this.fragmentCallback = fragmentCallback;
        this.newsItem = newsItem;
    }

    @Override
    protected Void doInBackground(String... params) {
        String url = newsItem.getImageLink();
        try {
            HttpUriRequest request = new HttpGet(url);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);

            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                byte[] bytes = EntityUtils.toByteArray(entity);

                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0,
                        bytes.length);
                newsItem.setBitmap(bitmap);
            } else {
                throw new IOException("Download failed, HTTP response code " + statusCode + " - " + statusLine.getReasonPhrase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        fragmentCallback.onTaskDone();
    }
}

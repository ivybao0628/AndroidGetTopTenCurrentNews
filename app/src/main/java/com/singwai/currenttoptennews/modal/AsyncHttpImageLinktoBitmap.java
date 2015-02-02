package com.singwai.currenttoptennews.modal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

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
public class AsyncHttpImageLinktoBitmap extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap result = null;
        String url = params[0];
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
                return bitmap;
            } else {
                throw new IOException("Download failed, HTTP response code " + statusCode + " - " + statusLine.getReasonPhrase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}

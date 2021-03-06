package com.singwai.currenttoptennews.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.singwai.currenttoptennews.R;
import com.singwai.currenttoptennews.Utility.ViewUtility;
import com.singwai.currenttoptennews.model.AsyncHttpImageLinkToBitmap;
import com.singwai.currenttoptennews.model.NewsItem;

/**
 * Created by Singwai Chan on 2/1/15.
 */
public class NewsFragment extends BaseFragment  {

    private NewsItem newsItem;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView linkTextView;
    private TextView publishDateTextView;
    private ImageView thumbnailImageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = this.getArguments();
        newsItem = new NewsItem(
                args.getString(NewsItem.PARSE_TITLE, "ERROR"),
                args.getString(NewsItem.PARSE_DESCRIPTION, "ERROR"),
                args.getString(NewsItem.PARSE_LINK, "ERROR"),
                args.getString(NewsItem.PARSE_PUBLISH_DATE, "ERROR"),
                args.getString(NewsItem.PARSE_IMAGE_LINK, "ERROR"));
        //todo get bitmap here
        getBitmapTask();
    }

    public void getBitmapTask() {
        AsyncHttpImageLinkToBitmap testAsyncTask = new AsyncHttpImageLinkToBitmap(new FragmentCallback() {
            @Override
            public void onTaskDone() {
                //Attach the image
                if (thumbnailImageView != null) {
                    thumbnailImageView.setImageBitmap(newsItem.getBitmap());
                }
            }
        }, newsItem);

        testAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_news, null);
            titleTextView = (TextView) rootView.findViewById(R.id.textViewNewsTitle);
            titleTextView.setText("Title: " + newsItem.getTitle());
            descriptionTextView = (TextView) rootView.findViewById(R.id.textViewNewsDescription);
            descriptionTextView.setText("Description: " + newsItem.getDescription());

            linkTextView = (TextView) rootView.findViewById(R.id.textViewNewsLink);
            linkTextView.setText("Link: " + newsItem.getLink());

            publishDateTextView = (TextView) rootView.findViewById(R.id.textViewNewsPublishDate);
            publishDateTextView.setText("Publish Date: " + newsItem.getPubDate());
            thumbnailImageView = (ImageView) rootView.findViewById(R.id.imageViewNewsThumbnail);
            if (thumbnailImageView != null && newsItem.getBitmap() != null) {
                thumbnailImageView.setImageBitmap(newsItem.getBitmap());
            }
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
            //Shuffle views here.
            //This mean that the first the the view will always look the same.
            ViewUtility.shuffleLinearLayout((LinearLayout) rootView);
        }
        Log.e("onCreateView", "Checking " + newsItem.getTitle());
        return rootView;
    }

    public interface FragmentCallback {
        public void onTaskDone();
    }
}

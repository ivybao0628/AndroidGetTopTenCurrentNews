package com.singwai.currenttoptennews;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.singwai.currenttoptennews.activity.Fragment.BaseFragment;
import com.singwai.currenttoptennews.activity.Fragment.NewsFragment;
import com.singwai.currenttoptennews.configutation.Configuration;
import com.singwai.currenttoptennews.modal.AsyncGetNews;
import com.singwai.currenttoptennews.modal.NewsItem;

import java.util.ArrayList;


public class NewsActivity extends ActionBarActivity implements OnTaskCompleted {

    private ViewPager pager;
    private ArrayList<BaseFragment> fragments;
    private ArrayList<NewsItem> newsItems;
    private MyFragmentPagerAdapter fragmentPagerAdapter;

    private AsyncGetNews asyncGetNews;

    private Handler handler;
    private Runnable runnable;

    /*
    *When this activity starts, configuration already has the config that user selected.
    * Get Latest News (Async task)
    * OnTaskCompleted
    *   Build Fragments
    *   Set adapter
    *   Start Auto Swipe
    *
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) this.findViewById(R.id.mainViewPager);

        getLatestNews(Configuration.get_instance().getNewsSectionPosition());
        //Add something to tell user the page is loading.

    }

    public void setNewsItems (ArrayList<NewsItem> newsItems){
        this.newsItems = newsItems;
    }


    @Override
    public void onResume(){
        super.onResume();
        startAutoSwipeViewPager();
    }

    @Override
    public void onPause (){
        super.onPause();
        stopAutoSwipeViewPager();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (asyncGetNews != null && asyncGetNews.getStatus() == AsyncTask.Status.RUNNING)

                //todo does this ready working properly?
                asyncGetNews.cancel(true);
            Log.e("Back Back ", "Back Button");
        }
        return super.onKeyDown(keyCode, event);
    }

    /*Setting is handle by another activity
    private void initViewPager() {
        //Build Configuration
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());

        fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(fragmentPagerAdapter);
        pager.setCurrentItem(0);

    }*/

    //Run the async task
    public void getLatestNews(int section) {
        if (asyncGetNews == null) {
            asyncGetNews = new AsyncGetNews(this, this);
            asyncGetNews.execute(section);
            asyncGetNews = null;

        }
    }

    //Start swipe, set adapter
    @Override
    public void asyncTaskCompleted() {
        fragments = new ArrayList<BaseFragment>();
        addNewsFragment(newsItems);
        fragmentPagerAdapter = new MyFragmentPagerAdapter(this.getSupportFragmentManager(), fragments);
        pager.setAdapter(fragmentPagerAdapter);
        pager.setCurrentItem(0);
    }

    public void addNewsFragment(ArrayList<NewsItem> newsItems) {
        for (int i = 0; i < newsItems.size(); i++) {
            NewsItem temp = newsItems.get(i);
            //for (int i = 0; i < 10; i++) {
            //   NewsItem temp = new NewsItem("title" + i , "Description " + i , "link " + i , "publish date: " + i, "imagelink " + i);
            Bundle args = new Bundle();
            args.putString(NewsItem.PARSE_TITLE, temp.getTitle());
            args.putString(NewsItem.PARSE_DESCRIPTION, temp.getDescription());
            args.putString(NewsItem.PARSE_IMAGE_LINK, temp.getImageLink());
            args.putString(NewsItem.PARSE_LINK, temp.getLink());
            args.putString(NewsItem.PARSE_PUBLISH_DATE, temp.getPubDate());

            NewsFragment fragment = new NewsFragment();
            fragment.setArguments(args);
            fragments.add(fragment);

        }
    }

/*    public void removeNewsFragment (){
        //Check lenght of fragment
        stopAutoSwipeViewPager();
        if (fragments.size()>1){
            //Remove all news fragments
            //Remove from the back for faster performance.
            for (int i = fragments.size()-1 ; i > 0; i --){
                fragments.remove(i);
            }
        }
        fragmentPagerAdapter.notifyDataSetChanged();
        pager.setCurrentItem(0);

    }*/

    public void startAutoSwipeViewPager() {
        if (fragments!= null && Configuration.get_instance(this).getAutoSwap()) {
            //Turn on auto
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    int pageNum = NewsActivity.this.pager.getCurrentItem();
                    pageNum = ++pageNum % fragments.size();
                    pager.setCurrentItem(pageNum);
                    //Set for the next swipe.
                    handler.postDelayed(this, Configuration.get_instance().getAutoSwapTime() * 1000);
                    Log.e ("swap", "swaping page"+ pageNum);
                }
            };
            //Start the first swipe.
            handler.postDelayed(runnable, Configuration.get_instance().getAutoSwapTime() * 1000 );
        }
    }

    public void stopAutoSwipeViewPager(){
        if (handler!= null){
            handler.removeCallbacks(runnable);
        }
    }




// swipe example
// public void swipViewPager (){
//        Handler handler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                //Load Configuration
//                int pageNumber = MainActivity.this.pager.getCurrentItem();
//                if (MainActivity.this.fragments.size()-1 == pageNumber){
//                    pageNumber = 1;
//                }
//                MainActivity.this.pager.setCurrentItem(pageNumber);
//            }
//        };
//        //2 seconds.
//        handler.postDelayed(runnable, 5000L);
//    }




}

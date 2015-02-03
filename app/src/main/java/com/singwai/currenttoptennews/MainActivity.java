package com.singwai.currenttoptennews;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.singwai.currenttoptennews.activity.Fragment.BaseFragment;
import com.singwai.currenttoptennews.activity.Fragment.HomeFragment;
import com.singwai.currenttoptennews.activity.Fragment.NewsFragment;
import com.singwai.currenttoptennews.configutation.Configuration;
import com.singwai.currenttoptennews.modal.AsyncGetNews;
import com.singwai.currenttoptennews.modal.NewsItem;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ViewPager pager;
    private ArrayList<BaseFragment> fragments;
    private MyFragmentPagerAdapter fragmentPagerAdapter;

    private AsyncGetNews asyncGetNews;


    private Handler handler;
    private Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) this.findViewById(R.id.mainViewPager);
        initViewPager();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (asyncGetNews != null && asyncGetNews.getStatus() == AsyncTask.Status.RUNNING)
                asyncGetNews.cancel(true);
            Log.e("Back Back ", "Back Button");
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initViewPager() {
        //Build Configuration
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());

        fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
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
        fragmentPagerAdapter.notifyDataSetChanged();
        pager.setCurrentItem(1);
        startAutoSwipeViewPager();

    }

    public void removeNewsFragment (){
        //Check lenght of fragment
        stopAutoAwipeViewPager();
        if (fragments.size()>1){
            //Remove all news fragments
            //Remove from the back for faster performance.
            for (int i = fragments.size()-1 ; i > 1; i --){
                fragments.remove(i);
            }
        }
        fragmentPagerAdapter.notifyDataSetChanged();
        pager.setCurrentItem(0);

    }

    public void startAutoSwipeViewPager() {
        if (fragments.size() > 1 && Configuration.get_instance(this).getAutoSwap()) {
            //Turn on auto
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    int newPage = MainActivity.this.pager.getCurrentItem();
                    if (newPage == 0){
                        handler.postDelayed(this, Configuration.get_instance().getAutoSwapTime() * 1000);
                        return;
                    }
                    newPage = ++newPage % fragments.size();
                    if (newPage == 0) {
                        newPage++;
                    }
                    pager.setCurrentItem(newPage);
                    handler.postDelayed(this, Configuration.get_instance().getAutoSwapTime() * 1000);
                    Log.e ("swap", "swaping page"+ newPage);
                }
            };
            handler.postDelayed(runnable, Configuration.get_instance().getAutoSwapTime() * 1000 );
        }
    }

    public void stopAutoAwipeViewPager(){
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

    public void getLatestNews(int section) {
        if (asyncGetNews == null) {
            asyncGetNews = new AsyncGetNews(this);
            asyncGetNews.execute(section);
            asyncGetNews = null;
        }
       //todo move the bing search here.
    }

    @Override
    public void onPause (){
        super.onPause();
        stopAutoAwipeViewPager();

    }

    @Override
    public void onResume(){
        super.onResume();
        startAutoSwipeViewPager();
    }




    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //We will download the image on the fly.
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

package com.singwai.currenttoptennews;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.singwai.currenttoptennews.configutation.Configuration;


public class SplashActivity extends Activity {

    private ImageView imageView;

    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imageView = (ImageView) this.findViewById(R.id.splashImageView);
        imageView.setImageResource(R.drawable.ic_launcher);
        imageView.startAnimation(animation);

        //Consider run the async task here so data can start loading date.

        //Set Thread to transition to a different activity.
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //Load Configuration
                Configuration.get_instance(SplashActivity.this.getApplicationContext());
                // Intent to jump to the next activity
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        };
        //2 seconds.
        handler.postDelayed(runnable, 2000L);
    }


    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);

    }
}

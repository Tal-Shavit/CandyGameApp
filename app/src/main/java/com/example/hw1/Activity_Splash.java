package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Splash extends AppCompatActivity {

    private ImageView ActivitySplash_IMG_mouth;
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mediaPlayer = MediaPlayer.create(this, R.raw.childrenlogo);
        mediaPlayer.start();

        findViews();

        ActivitySplash_IMG_mouth.setVisibility(View.INVISIBLE);

        moveMouth(ActivitySplash_IMG_mouth);
    }

    public void moveMouth(View view){
        view.setVisibility(View.VISIBLE);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int h = displayMetrics.heightPixels;
        view.setY(-h);
        view.setScaleY(0.0f);
        view.setScaleX(0.0f);
        view.animate().scaleY(1.0f).scaleX(1.0f).translationY(0).setDuration(5000).setInterpolator(new AccelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mediaPlayer.stop();
                openStartScreen();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    public void openStartScreen() {
        Intent intent = new Intent(this, Activity_Start.class);
        startActivity(intent);
        finish();
    }

    public void findViews(){
        ActivitySplash_IMG_mouth = findViewById(R.id.ActivitySplash_IMG_mouth);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }
}
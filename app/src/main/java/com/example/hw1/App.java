package com.example.hw1;

import android.app.Application;

import com.example.hw1.Models.MySP;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MySP.getInstance(this);
    }
}

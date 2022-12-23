package com.example.hw1;

import android.content.Context;
import android.content.SharedPreferences;

public class MySP {
    private SharedPreferences preferences;
    private static MySP MySP;

    private MySP(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences("TOP TEN", Context.MODE_PRIVATE);
    }

    public static MySP getInstance(Context context) {
        if (MySP == null) {
            MySP = new MySP(context);
        }
        return MySP;
    }

    public int getIntSP(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public void putIntSP(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public String getStringSP(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public void putStringSP(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}


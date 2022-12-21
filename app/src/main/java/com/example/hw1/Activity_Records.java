package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;

public class Activity_Records extends AppCompatActivity {

    public static final String KEY_SCORE = "KEY_SCORE";
    public static String KEY_NAME = "KEY_NAME";
    private ArrayList<UserItems> userItemsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        initFragment();

        SharedPreferences.Editor editor = getSharedPreferences("RECORDS", MODE_PRIVATE).edit();
        editor.putString("USER_NAME", "no user name");
        editor.apply();

    }

    private void initFragment() {
        /*Fragment_Map fragment_map = Fragment_Map.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.records_LAY_map, fragment_map);
        transaction.commit();*/

        Fragment_Map fragment_map = new Fragment_Map();
        getSupportFragmentManager().beginTransaction().add(R.id.records_LAY_map,fragment_map).commit();

        /*Fragment_List fragment_list = Fragment_List.newInstance();
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.add(R.id.records_LAY_list, fragment_list);
        transaction1.commit();*/

        //getSupportFragmentManager().beginTransaction().add(R.id.panel_LAY_map,mapFragment).commit();

    }
}
package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hw1.Fragments.Fragment_List;
import com.example.hw1.Fragments.Fragment_Map;
import com.example.hw1.Interfaces.CallBack_Location;
import com.example.hw1.Interfaces.RecyclerViewInterface;
import com.example.hw1.Models.UserItems;
import com.google.android.material.button.MaterialButton;


public class Activity_Records extends AppCompatActivity {// implements RecyclerViewInterface

    private MaterialButton EndActivity_BTN_start;
    private Fragment_Map fragment_map = new Fragment_Map();
    private Fragment_List fragment_list;

    private CallBack_Location callBack_location = new CallBack_Location() {
        @Override
        public void locationReady(UserItems userItems) {
            fragment_map.locationReady(userItems);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        EndActivity_BTN_start = findViewById(R.id.EndActivity_BTN_start);
        initView();
        initFragment();
        fragment_list.setCallBack_location(callBack_location);

        SharedPreferences.Editor editor = getSharedPreferences("RECORDS", MODE_PRIVATE).edit();
        editor.putString("USER_NAME", "no user name");
        editor.apply();

    }

    private void initView() {
        EndActivity_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStartScreen();
            }
        });
    }

    private void openStartScreen() {
        Intent scoreIntent = new Intent(this, Activity_Start.class);
        startActivity(scoreIntent);
    }

    private void initFragment() {
        fragment_map = Fragment_Map.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.records_LAY_map, fragment_map);
        transaction.commit();

        fragment_list = Fragment_List.newInstance();
        fragment_list.setCallBack_location(callBack_location);
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.add(R.id.records_LAY_list, fragment_list);
        transaction1.commit();
    }
}
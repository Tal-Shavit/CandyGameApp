package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class Activity_Start extends AppCompatActivity {

    private MaterialButton startActivity_BTN_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        findViews();
        initViews();
    }

    private void initViews() {
        startActivity_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGame();
            }
        });
    }

    public void findViews(){
        startActivity_BTN_start = findViewById(R.id.startActivity_BTN_start);
    }

    private void openGame() {
        Intent myIntent = new Intent(Activity_Start.this, Activity_Main.class);
        startActivity(myIntent);
    }
}
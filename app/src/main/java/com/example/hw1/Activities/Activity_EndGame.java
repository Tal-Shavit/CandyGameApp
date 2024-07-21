package com.example.hw1.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.hw1.Models.DataBase;
import com.example.hw1.Models.MySP;
import com.example.hw1.Models.UserItems;
import com.example.hw1.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import im.delight.android.location.SimpleLocation;

public class Activity_EndGame extends AppCompatActivity {

    public static String KEY_NAME = "KEY_NAME";
    public static String KEY_SCORE = "KEY_SCORE";
    private MaterialTextView EndActivity_LBL_score;
    private MaterialButton EndActivity_BTN_start, EndActivity_BTN_records, EndActivity_BTN_saveScore;
    private String name;
    private int score;
    private double lat, lon;
    private DataBase newDb;
    SimpleLocation location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        name = getIntent().getStringExtra(KEY_NAME);
        score = getIntent().getIntExtra(KEY_SCORE, 0);

        findViews();
        initViews();

        String fromJSON = MySP.getInstance(this).getStringSP("MY_DB1", "");
        newDb = new Gson().fromJson(fromJSON, DataBase.class);

        if (newDb == null) {
            newDb = new DataBase();
        }
    }

    private void initViews() {
        EndActivity_LBL_score.setText(name.toUpperCase() + " you lose!\n" + "Your score is " + score);
        onStartButton();
        onRecordButton();
        onSaveScoreButton();
    }

    private void onSaveScoreButton() {
        EndActivity_BTN_saveScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lat != 0.0 && lon != 0.0) {
                    UserItems userItems = new UserItems().setScore(score).setName(name).setLat(lat).setLon(lon);
                    newDb.getUserItems().add(userItems);

                    String json = new Gson().toJson(newDb);
                    MySP.getInstance(Activity_EndGame.this).putStringSP("MY_DB1", json);
                    EndActivity_BTN_saveScore.setVisibility(View.INVISIBLE);
                } else {
                    Toast toast = Toast.makeText(Activity_EndGame.this, "Didn't get location", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private void onRecordButton() {
        EndActivity_BTN_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRecordsScreen();
            }
        });
    }

    private void onStartButton() {
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

    private void openRecordsScreen() {
        Intent scoreIntent = new Intent(this, Activity_Records.class);
        startActivity(scoreIntent);
        finish();
    }

    public void findViews() {
        EndActivity_LBL_score = findViewById(R.id.EndActivity_LBL_score);
        EndActivity_BTN_start = findViewById(R.id.EndActivity_BTN_start);
        EndActivity_BTN_records = findViewById(R.id.EndActivity_BTN_records);
        EndActivity_BTN_saveScore = findViewById(R.id.EndActivity_BTN_saveScore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestLocationPermission(new SimpleLocation(this));
    }

    private void requestLocationPermission(SimpleLocation location) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        } else {
            putLatLon(location);
        }
    }

    private void putLatLon(SimpleLocation location) {
        location.beginUpdates();
        lat = location.getLatitude();
        lon = location.getLongitude();
    }
}
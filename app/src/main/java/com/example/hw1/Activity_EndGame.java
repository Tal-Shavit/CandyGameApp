package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hw1.Models.DataBase;
import com.example.hw1.Models.UserItems;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

public class Activity_EndGame extends AppCompatActivity {

    public static String KEY_NAME = "KEY_NAME";
    public static String KEY_SCORE = "KEY_SCORE";

    private MaterialTextView EndActivity_LBL_score;
    private MaterialButton EndActivity_BTN_start;
    private MaterialButton EndActivity_BTN_records;

    private String name;
    private int score;
    private Context context;
    private double lat;//////////
    private double lon;//////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        name  = getIntent().getStringExtra(KEY_NAME);
        score = getIntent().getIntExtra(KEY_SCORE,0);

        findViews();
        initViews();

        String fromJSON = MySP.getInstance(this).getStringSP("MY_DB1","");
        DataBase newDb = new Gson().fromJson(fromJSON,DataBase.class);

        if(newDb == null){
            newDb = new DataBase();
        }

        UserItems userItems = new UserItems(name,score,lat,lon).setScore(score).setName(name).setLat(0.0).setLon(0.0);
        newDb.getUserItems().add(userItems);

        String json = new Gson().toJson(newDb);
        MySP.getInstance(this).putStringSP("MY_DB1",json);

        /*String fromJSON2 = MySP.getInstance(this).getStringSP("MY_DB","");
        DataBase newDb2 = new Gson().fromJson(fromJSON2,DataBase.class);

        for (int i = 0; i < newDb2.getUserItems().size() ; i++) {
            Log.d("LALA", ""+newDb2.getUserItems().get(i).getName());
        }*/

        //DataBase dataBase = new DataBase();

        /*String json = new Gson().toJson(dataBase);
        MySP.getInstance(this).putStringSP("MY_DB",json);

        String fromJSON = MySP.getInstance(this).getStringSP("MY_DB","");
        DataBase newDb = new Gson().fromJson(fromJSON,DataBase.class);*/
    }

    private void initViews() {
        EndActivity_LBL_score.setText(name.toUpperCase() + " you lose!\n" + "Your score is " + score);
        EndActivity_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStartScreen();
            }
        });
        EndActivity_BTN_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRecordsScreen();
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
    }
}
package com.example.hw1.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.hw1.R;
import com.google.android.material.button.MaterialButton;

public class Activity_Start extends AppCompatActivity {

    private MaterialButton startActivity_BTN_start, startActivity_BTN_records;
    private Switch startActivity_SWC_speed, startActivity_SWC_state;
    private EditText startActivity_TXT_name;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        findViews();
        initViews();
    }

    private void initViews() {
        onStartGameButton();
        onRecordsButton();
    }

    private void onStartGameButton() {
        startActivity_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int state;
                int delay;
                String name = startActivity_TXT_name.getText().toString();
                if (startActivity_SWC_state.isChecked() == false)
                    state = 0;
                else
                    state = 1;
                if (startActivity_SWC_speed.isChecked() == false)
                    delay = 500; //slower
                else
                    delay = 300; //faster
                if (name.equals("")) {
                    toast = Toast.makeText(Activity_Start.this, "YOU NEED TO FILL NAME! ", Toast.LENGTH_SHORT);
                    toast.show();
                    startActivity_TXT_name.setHint("YOU NEED TO FILL ME!");
                } else
                    openGame(state, delay, name);
            }
        });
    }

    private void onRecordsButton() {
        startActivity_BTN_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRecords();
            }
        });
    }

    public void findViews() {
        startActivity_BTN_start = findViewById(R.id.startActivity_BTN_start);
        startActivity_BTN_records = findViewById(R.id.startActivity_BTN_records);
        startActivity_SWC_speed = findViewById(R.id.startActivity_SWC_speed);
        startActivity_SWC_state = findViewById(R.id.startActivity_SWC_state);
        startActivity_TXT_name = findViewById(R.id.startActivity_TXT_name);
    }

    private void openGame(int state, int delay, String name) {
        Intent myIntent = new Intent(Activity_Start.this, Activity_Main.class);
        myIntent.putExtra(Activity_Main.KEY_STATE, state);
        myIntent.putExtra(Activity_Main.KEY_SPEED, delay);
        myIntent.putExtra(Activity_Main.KEY_NAME, name);
        startActivity(myIntent);
    }

    private void openRecords() {
        Intent myIntent = new Intent(Activity_Start.this, Activity_Records.class);
        startActivity(myIntent);
    }

}
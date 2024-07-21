package com.example.hw1.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hw1.Class_Game;
import com.example.hw1.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Activity_Main extends AppCompatActivity {

    public static String KEY_STATE = "KEY_STATE";
    public static String KEY_SPEED = "KEY_SPEED";
    public static String KEY_NAME = "KEY_NAME";
    private static int DELAY;
    private Class_Game game;
    private Context context = null;
    private ShapeableImageView[] main_IMG_lips;
    private ExtendedFloatingActionButton main_FAB_left, main_FAB_right;
    private LinearLayout panel_col;
    private MaterialTextView main_TXT_score;
    private ShapeableImageView mouth;
    private LinearLayout.LayoutParams linearParam = null;
    private ArrayList<LinearLayout> arrOfLayout;
    private Timer timer;
    private Toast toast1, toast2;
    private Random random;
    private int btnOrSnr, fastOrSlow;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random = new Random();
        toasts();
        context = this;
        name = getIntent().getStringExtra(KEY_NAME);

        findViews();
        initView();
        isBtnOrSnr();
    }

    private void initView() {
        Intent prevIntent = getIntent();
        btnOrSnr = prevIntent.getIntExtra(KEY_STATE, 0);
        fastOrSlow = prevIntent.getIntExtra(KEY_SPEED, 0);
        DELAY = fastOrSlow;
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        game = new Class_Game(3, context, this, toast1, toast2, vibrator, 2);
        game.setPanel_col(panel_col);
        arrOfLayout = new ArrayList(game.getNUM_OF_COL());

        insertImageView();
    }

    private void toasts() {
        toast1 = Toast.makeText(this, "IT'S A POISON! ", Toast.LENGTH_SHORT);
        toast2 = Toast.makeText(this, "YAMMMMM! ", Toast.LENGTH_SHORT);
    }

    private void findViews() {
        main_FAB_left = findViewById(R.id.main_FAB_left);
        main_FAB_right = findViewById(R.id.main_FAB_right);
        main_IMG_lips = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_lip1),
                findViewById(R.id.main_IMG_lip2),
                findViewById(R.id.main_IMG_lip3)
        };
        main_TXT_score = findViewById(R.id.main_TXT_score);
        panel_col = findViewById(R.id.panel_col);
    }

    public void insertImageView() {
        for (int i = 0; i < game.getNUM_OF_COL(); i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
            mouth = new ShapeableImageView(context);
            mouth.setId(i + 1);
            mouth.setImageResource(R.drawable.openmouth);
            if (i != game.getCurrentPosition())
                mouth.setVisibility(View.INVISIBLE);
            game.getArrOfMouth().add(mouth);
            LinearLayout linearL = new LinearLayout(context);
            linearL.setGravity(Gravity.BOTTOM);
            linearL.setOrientation(LinearLayout.VERTICAL);
            linearL.setId(i + 1);
            linearParam = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            insertObject(i, linearL);
            arrOfLayout.add(linearL);
            arrOfLayout.get(i).addView(mouth, lp);
            panel_col.addView(linearL, linearParam);

        }
    }

    public void insertObject(int i, LinearLayout linearL) {
        for (int j = 0; j < game.getNUM_OF_POIS_ROW(); j++) {
            ShapeableImageView image = new ShapeableImageView(context);
            image.setId(View.generateViewId());
            image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
            game.getMatOfObjects()[j][i] = image;
            linearL.addView(image);
        }
    }

    private void clicked() {
        main_FAB_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int myPosition = game.getCurrentPosition();
                if (game.getArrOfMouth().get(game.getNUM_OF_COL() - 1).getVisibility() != View.VISIBLE) {
                    game.getArrOfMouth().get(myPosition + 1).setVisibility(View.VISIBLE);
                    game.getArrOfMouth().get(myPosition).setVisibility(View.INVISIBLE);
                    game.setCurrentPosition(myPosition + 1);
                }
            }
        });

        main_FAB_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int myPosition = game.getCurrentPosition();
                if (game.getArrOfMouth().get(0).getVisibility() != View.VISIBLE) {
                    game.getArrOfMouth().get(myPosition - 1).setVisibility(View.VISIBLE);
                    game.getArrOfMouth().get(myPosition).setVisibility(View.INVISIBLE);
                    game.setCurrentPosition(myPosition - 1);
                }
            }
        });
    }

    public void moveMouth(int curPos) {
        for (int i = 0; i < game.getNUM_OF_COL(); i++) {
            if (i != curPos)
                game.getArrOfMouth().get(i).setVisibility(View.INVISIBLE);
            else
                game.getArrOfMouth().get(i).setVisibility(View.VISIBLE);
        }
        game.setCurrentPosition(curPos);
    }

    private void startTimerUi() {
        timer = new Timer();
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                game.update();
                                updateUI();
                            }
                        });
                    }
                }
                , DELAY, DELAY);
    }

    private void updateUI() {
        int visible[][] = game.getVisible();
        for (int i = 0; i < game.getNUM_OF_POIS_ROW(); i++) {
            for (int j = 0; j < game.getNUM_OF_COL(); j++) {
                if (visible[i][j] == 1) {
                    game.getMatOfObjects()[i][j].setImageResource(R.drawable.poison2);
                    game.getMatOfObjects()[i][j].setVisibility(View.VISIBLE);
                } else if (visible[i][j] == 2) {
                    game.getMatOfObjects()[i][j].setImageResource(R.drawable.candy);
                    game.getMatOfObjects()[i][j].setVisibility(View.VISIBLE);
                } else if (visible[i][j] == 3) {
                    game.getMatOfObjects()[i][j].setImageResource(R.drawable.popcorn);
                    game.getMatOfObjects()[i][j].setVisibility(View.VISIBLE);
                } else if (visible[i][j] == 4) {
                    game.getMatOfObjects()[i][j].setImageResource(R.drawable.chocolate_purple);
                    game.getMatOfObjects()[i][j].setVisibility(View.VISIBLE);
                } else if (visible[i][j] == 5) {
                    game.getMatOfObjects()[i][j].setImageResource(R.drawable.poison);
                    game.getMatOfObjects()[i][j].setVisibility(View.VISIBLE);
                } else if (visible[i][j] == 6) {
                    game.getMatOfObjects()[i][j].setImageResource(R.drawable.snack);
                    game.getMatOfObjects()[i][j].setVisibility(View.VISIBLE);
                } else {
                    game.getMatOfObjects()[i][j].setVisibility(View.INVISIBLE);
                }

            }
        }
        main_TXT_score.setText("" + game.getScore());
    }

    public void updateLive() {
        if (game.getWrong() > 0 && game.getWrong() <= 3) {
            main_IMG_lips[main_IMG_lips.length - game.getWrong()].setVisibility(View.INVISIBLE);
        }
        if (game.isLose()) {
            openEndScreen(name, game.getScore());
        }
    }

    public void isBtnOrSnr() {
        if (btnOrSnr == 0) {
            clicked();
        }
        if (btnOrSnr == 1) {
            main_FAB_right.setVisibility(View.INVISIBLE);
            main_FAB_left.setVisibility(View.INVISIBLE);
            initSensor();
        }
    }

    public void initSensor() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(aSensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private SensorEventListener aSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            int curPos = (int) x;
            if (curPos > -3 && curPos <= 3)
                moveMouth(2);
            if (curPos <= -3 && curPos > -6)
                moveMouth(3);
            if (curPos >= -9 && curPos <= -6)
                moveMouth(4);
            if (curPos > 3 && curPos <= 6)
                moveMouth(1);
            if (curPos > 6 && curPos <= 9)
                moveMouth(0);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        startTimerUi();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    public void openEndScreen(String name, int score) {
        Intent scoreIntent = new Intent(this, Activity_EndGame.class);
        scoreIntent.putExtra(Activity_EndGame.KEY_SCORE, score);
        scoreIntent.putExtra(Activity_EndGame.KEY_NAME, name);
        startActivity(scoreIntent);
        finish();
    }

}

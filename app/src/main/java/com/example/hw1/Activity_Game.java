package com.example.hw1;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class Activity_Game {

    private Activity_Main activityMain;

    private final int NUM_OF_COL = 3;
    private final int NUM_OF_POIS_ROW = 7;
    private int wrong = 0;
    private int currentPosition = 1;


    private Context context;
    private ArrayList<ShapeableImageView> arrOfMouth;
    private ShapeableImageView[][] matOfPois;
    private int[][] visible;
    private LinearLayout panel_col;

    private Timer timer;
    private Toast toast;
    private Vibrator vibrator;
    private Random random;
    private int countDown = 0;
    private int countStaticDown;


    public Activity_Game(int lives, Context context, Activity_Main activityMain, Toast toast, Vibrator vibrator, int countStaticDown) {
        random = new Random();
        this.context = context;
        arrOfMouth = new ArrayList<>(NUM_OF_COL);
        this.activityMain = activityMain;
        this.countStaticDown = countStaticDown;
        matOfPois = new ShapeableImageView[NUM_OF_POIS_ROW][NUM_OF_COL];
        this.toast = toast;
        this.vibrator = vibrator;

        visible = new int[NUM_OF_POIS_ROW][NUM_OF_COL];
        for (int i = 1; i < NUM_OF_POIS_ROW; i++) {
            for (int j = 0; j < NUM_OF_COL; j++) {
                visible[i][j] = 0;
            }
        }
    }


    public int getWrong() {
        return wrong;
    }

    public ShapeableImageView[][] getMatOfPois() {
        return matOfPois;
    }

    public int getNUM_OF_COL() {
        return NUM_OF_COL;
    }

    public int getNUM_OF_POIS_ROW() {
        return NUM_OF_POIS_ROW;
    }

    public int[][] getVisible() {
        return visible;
    }

    public LinearLayout getPanel_col() {
        return panel_col;
    }

    public Activity_Game setPanel_col(LinearLayout panel_col) {
        this.panel_col = panel_col;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public ArrayList<ShapeableImageView> getArrOfMouth() {
        return arrOfMouth;
    }

    public Activity_Game setArrOfMouth(ArrayList<ShapeableImageView> arrOfMouth) {
        this.arrOfMouth = arrOfMouth;
        return this;
    }


    public int getCurrentPosition() {
        return currentPosition;
    }

    public Activity_Game setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
        return this;
    }

    public void update() {
        checkIfEatPoision();
        countDown++;

        for (int i = NUM_OF_POIS_ROW - 1; i > 0; i--) {
            for (int j = 0; j < NUM_OF_COL; j++) {
                visible[i][j] = visible[i - 1][j];
            }
        }

        for (int i = 0; i < NUM_OF_COL; i++) {
            visible[0][i] = 0;
        }

        if (countDown == countStaticDown) {
            visible[0][random.nextInt(NUM_OF_COL)] = 1;
            countDown = 0;
        }
    }

    public void checkIfEatPoision() {
        if (visible[NUM_OF_POIS_ROW - 1][currentPosition] == 1) {
            wrong++;
            arrOfMouth.get(currentPosition).setImageResource(R.drawable.death);
            toast.show();
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else
            arrOfMouth.get(currentPosition).setImageResource(R.drawable.openmouth);

    }
}


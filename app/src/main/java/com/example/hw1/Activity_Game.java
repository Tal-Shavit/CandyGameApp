package com.example.hw1;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.android.material.imageview.ShapeableImageView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class Activity_Game {

    private Activity_Main activityMain;

    private final int NUM_OF_COL = 5;
    private final int NUM_OF_POIS_ROW = 7;
    private int wrong = 0;
    private int currentPosition = 2;
    private int score = 0;
    private int lives;

    private Context context;
    private ArrayList<ShapeableImageView> arrOfMouth;
    private ShapeableImageView[][] matOfObjects;
    private int[][] visible;
    private LinearLayout panel_col;

    private Timer timer;
    private Toast toast1;
    private Toast toast2;
    private Vibrator vibrator;
    private Random random;
    private int countDown = 0;
    private int countStaticDown;


    public Activity_Game(int lives, Context context, Activity_Main activityMain, Toast toast1,Toast toast2, Vibrator vibrator, int countStaticDown) {
        random = new Random();
        this.context = context;
        arrOfMouth = new ArrayList<>(NUM_OF_COL);
        this.activityMain = activityMain;
        this.countStaticDown = countStaticDown;
        matOfObjects = new ShapeableImageView[NUM_OF_POIS_ROW][NUM_OF_COL];
        this.toast1 = toast1;
        this.toast2 = toast2;
        this.vibrator = vibrator;
        this.lives = lives;

        visible = new int[NUM_OF_POIS_ROW][NUM_OF_COL];
        for (int i = 1; i < NUM_OF_POIS_ROW; i++) {
            for (int j = 0; j < NUM_OF_COL; j++) {
                visible[i][j] = 0;
            }
        }
    }

    public int getScore() {
        return score;
    }

    public Activity_Game setScore(int score) {
        this.score = score;
        return this;
    }

    public int getWrong() {
        return wrong;
    }

    public ShapeableImageView[][] getMatOfObjects() {
        return matOfObjects;
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
        checkIfEatCandy();
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
            int rand1 = random.nextInt(NUM_OF_COL); //which col
            int rand2 = random.nextInt(NUM_OF_COL); //which col
            int rand3 = random.nextInt(NUM_OF_COL); //which col
            int randManyObject = (int) (Math.random() * (3 - 1 + 1) + 1);
            ; //how many object down 1-3
            if (randManyObject == 1) {
                visible[0][rand1] = (int) (Math.random() * (6 - 1 + 1) + 1); //pois or candy
            }
            if (randManyObject == 2) {
                visible[0][rand1] = (int) (Math.random() * (6 - 1 + 1) + 1); //pois or candy
                if (rand2 != rand1)
                    visible[0][rand2] = (int) (Math.random() * (6 - 1 + 1) + 1); //pois or candy
            }
            if (randManyObject == 3) {
                visible[0][rand1] = (int) (Math.random() * (6 - 1 + 1) + 1); //pois or candy
                if ((rand2 != rand1) && (rand3 != rand2)) {
                    visible[0][rand2] = (int) (Math.random() * (6 - 1 + 1) + 1); //pois or candy
                    visible[0][rand3] = (int) (Math.random() * (6 - 1 + 1) + 1); //pois or candy
                }
            }
            countDown = 0;
        }
    }

    public void checkIfEatPoision() {
        if (visible[NUM_OF_POIS_ROW - 1][currentPosition] == 1 || visible[NUM_OF_POIS_ROW - 1][currentPosition] == 5) {
            wrong++;
            //score-=10;
            activityMain.updateLive();
            arrOfMouth.get(currentPosition).setImageResource(R.drawable.death);
            toast1.show();
            makeSound(R.raw.vomit2);
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else
            arrOfMouth.get(currentPosition).setImageResource(R.drawable.openmouth);
    }

    public void checkIfEatCandy() {
        if (visible[NUM_OF_POIS_ROW - 1][currentPosition] == 2 || visible[NUM_OF_POIS_ROW - 1][currentPosition] == 3
        ||visible[NUM_OF_POIS_ROW - 1][currentPosition] == 4 || visible[NUM_OF_POIS_ROW - 1][currentPosition] == 6) {
            score += 10;
            arrOfMouth.get(currentPosition).setImageResource(R.drawable.lips);
            makeSound(R.raw.candysound);
            toast2.show();

        }
    }

    public void makeSound(int idSound){
        MediaPlayer mp = MediaPlayer.create(context, idSound);
        mp.start();
    }

    public boolean isLose(){
        return lives == wrong;
    }




}


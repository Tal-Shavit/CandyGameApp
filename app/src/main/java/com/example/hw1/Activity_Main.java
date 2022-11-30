package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Activity_Main extends AppCompatActivity {

    private Activity_Game game;
    private Context context = null;

    private ShapeableImageView[] main_IMG_lips;
    private ExtendedFloatingActionButton main_FAB_left;
    private ExtendedFloatingActionButton main_FAB_right;
    private LinearLayout panel_col;

    private ShapeableImageView mouth;

    private LinearLayout.LayoutParams linearParam = null;
    private ArrayList<LinearLayout> arrOfLayout;

    private Timer timer;
    private final int DELAY = 500;
    private Toast toast;

    /*final Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, DELAY);
            //secondlyFunction();
        }
    };*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toast = Toast.makeText(this, "IT'S A POISION! " , Toast.LENGTH_SHORT);
        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        context = this;


        findViews();

        game = new Activity_Game(3, context, this, toast, vibrator, 2);
        game.setPanel_col(panel_col);
        arrOfLayout = new ArrayList(game.getNUM_OF_COL());
        initView();
        clicked();
    }


   private void initView() {
       insertImageView();
    }


    private void findViews() {
        main_FAB_left = findViewById(R.id.main_FAB_left);
        main_FAB_right = findViewById(R.id.main_FAB_right);
        main_IMG_lips = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_lip1),
                findViewById(R.id.main_IMG_lip2),
                findViewById(R.id.main_IMG_lip3)
        };
        panel_col = findViewById(R.id.panel_col);
    }

    public void insertImageView() {
        for (int i = 0; i < game.getNUM_OF_COL(); i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
            //ShapeableImageView mouth = new ShapeableImageView(context);
            mouth = new ShapeableImageView(context);
            mouth.setId(i + 1);
            mouth.setImageResource(R.drawable.openmouth);
            if (i != 1)
                mouth.setVisibility(View.INVISIBLE);
            game.getArrOfMouth().add(mouth);
            LinearLayout linearL = new LinearLayout(context);
            linearL.setGravity(Gravity.BOTTOM);
            linearL.setOrientation(LinearLayout.VERTICAL);
            linearL.setId(i + 1);
            linearParam = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            insertPoision(i, linearL);
            arrOfLayout.add(linearL);
            arrOfLayout.get(i).addView(mouth, lp);
            panel_col.addView(linearL, linearParam);

        }
    }


    public void insertPoision(int i, LinearLayout linearL) {
        for (int j = 0; j < game.getNUM_OF_POIS_ROW(); j++) {
            ShapeableImageView pois = new ShapeableImageView(context);
            pois.setId(View.generateViewId());
            pois.setImageResource(R.drawable.poison2);
            pois.setVisibility(View.INVISIBLE);
            pois.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
            game.getMatOfPois()[j][i] = pois;
            linearL.addView(pois);
        }
    }

    private void clicked() {
        main_FAB_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int myPosition = game.getCurrentPosition();
                if(game.getArrOfMouth().get(game.getNUM_OF_COL()-1).getVisibility() != View.VISIBLE){
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
                if(game.getArrOfMouth().get(0).getVisibility() != View.VISIBLE){
                    game.getArrOfMouth().get(myPosition - 1).setVisibility(View.VISIBLE);
                    game.getArrOfMouth().get(myPosition).setVisibility(View.INVISIBLE);
                    game.setCurrentPosition(myPosition - 1);
                }
            }
        });
    }

    private void startTimerUi(){
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
                if (visible[i][j] == 1)
                    game.getMatOfPois()[i][j].setVisibility(View.VISIBLE);
                else
                    game.getMatOfPois()[i][j].setVisibility(View.INVISIBLE);
            }
        }
        if (game.getWrong() > 0 && game.getWrong() <= 3) {
            main_IMG_lips[main_IMG_lips.length - game.getWrong()].setVisibility(View.INVISIBLE);
        }
    }

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

}
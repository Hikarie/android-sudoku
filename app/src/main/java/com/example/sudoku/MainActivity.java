package com.example.sudoku;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private int dim;
    private int dif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupWindowAnimations();

        Button button1 = (Button)findViewById(R.id.button1);
        //Button button3 = (Button)findViewById(R.id.button3);

        button1.setOnClickListener(new MyOnclickListener());
        //button3.setOnClickListener(new MyOnclickListener());

        Explode transition = new Explode();
        transition.setDuration(500);
        transition.setInterpolator(new AccelerateInterpolator());

        final Button difficulty = (Button)findViewById(R.id.difficulty);
        difficulty.setText("Easy");
        difficulty.setTextColor(0xFFAAC9CE);
        dif = 1;
        difficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(difficulty.getText()=="Easy"){
                    difficulty.setText("Medium");
                    difficulty.setTextColor(0xFFFFDD94);
                    dif = 2;
                }
                else if (difficulty.getText() == "Medium") {
                    difficulty.setText("Hard");
                    difficulty.setTextColor(0xFFFA897B);
                    dif = 3;
                }
                else{
                    difficulty.setText("Easy");
                    difficulty.setTextColor(0xFF86E3CE);
                    dif = 1;
                }
            }
        });

        final Button box = (Button)findViewById(R.id.box);
        box.setText("9");
        box.setTextColor(0xFFF2A490);
        dim = 9;
        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(box.getText()=="4"){
                    box.setText("9");
                    box.setTextColor(0xFFF2A490);
                    dim = 9;
                }
                else if (box.getText() == "9"){
                    box.setText("16");
                    box.setTextColor(0xFF478BA2);
                    dim = 16;
                }
                else{
                    box.setText("4");
                    box.setTextColor(0xFFCCABD8);
                    dim = 4;
                }
            }
        });

        Button more = (Button) findViewById(R.id.button2);
        more.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(v.getId()==R.id.button2) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Created by Hikari in May 2019");
                    builder.show();
                }
            }
        });
    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
    }

    protected void transitionTo(Intent i) {
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(i, transitionActivityOptions.toBundle());
    }

    private class MyOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if(v.getId()==R.id.button1){
                Intent i = new Intent(MainActivity.this,GameActivity.class);
                i.putExtra("dim",dim);
                i.putExtra("dif",dif);
                transitionTo(i);
//                overridePendingTransition(R.anim.splash_screen_fade, R.anim.splash_screen_hold);
            }
        }
    }
}

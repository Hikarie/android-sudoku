package com.example.sudoku;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;


public class GameActivity extends Activity {

    Sudoku sdk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int difficulty=bundle.getInt("dif");
        int dimension=bundle.getInt("dim");

        Button exit = (Button)findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.this.finish();
            }
        });

        sdk= (Sudoku) findViewById(R.id.sdk);

        //Sudoku sdk = new Sudoku(dimension,view.getContext());
        sdk.init(dimension,difficulty);
        sdk.setVisibility(View.VISIBLE);

        Button []btn = new Button[dimension];
        findAllBt(btn,dimension);

        for(int i=0;i<dimension;i++){
            final int num = i+1;
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sdk.setCurNum(num);
                }
            });
        }

        Button sub = (Button) findViewById(R.id.submit);
        sub.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(v.getId()==R.id.submit) {
                    int res = sdk.check();
                    if (res == 1) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setMessage("你还没有填完哦！");
                        builder.show();
                    } else {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("");
                        builder.setMessage("确定要提交吗？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int res = sdk.check();
                                if (res == 2) {
                                    Toast.makeText(GameActivity.this, "恭喜你！答对了", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(GameActivity.this, "答错了哦，再检查下吧", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                builder.setCancelable(true);
                            }
                        });
                        builder.show();
                    }
                }
            }
        });
    }

    // fixible button layout

    public void findAllBt(Button []btn, int dimension) {
        if(dimension == 4) {
            final TableLayout layout = (TableLayout) findViewById(R.id.four);
            layout.setVisibility(View.VISIBLE);
            btn[0] = (Button) findViewById(R.id.fbtn1);
            btn[1] = (Button) findViewById(R.id.fbtn2);
            btn[2] = (Button) findViewById(R.id.fbtn3);
            btn[3] = (Button) findViewById(R.id.fbtn4);
        }
        else if(dimension == 9) {
            final TableLayout layout = (TableLayout) findViewById(R.id.nine);
            layout.setVisibility(View.VISIBLE);
            btn[0] = (Button) findViewById(R.id.nbtn1);
            btn[1] = (Button) findViewById(R.id.nbtn2);
            btn[2] = (Button) findViewById(R.id.nbtn3);
            btn[3] = (Button) findViewById(R.id.nbtn4);
            btn[4] = (Button) findViewById(R.id.nbtn5);
            btn[5] = (Button) findViewById(R.id.nbtn6);
            btn[6] = (Button) findViewById(R.id.nbtn7);
            btn[7] = (Button) findViewById(R.id.nbtn8);
            btn[8] = (Button) findViewById(R.id.nbtn9);
        }
        else if(dimension == 16){
            final TableLayout layout = (TableLayout) findViewById(R.id.six);
            layout.setVisibility(View.VISIBLE);
            btn[0] = (Button) findViewById(R.id.btn1);
            btn[1] = (Button) findViewById(R.id.btn2);
            btn[2] = (Button) findViewById(R.id.btn3);
            btn[3] = (Button) findViewById(R.id.btn4);
            btn[4] = (Button) findViewById(R.id.btn5);
            btn[5] = (Button) findViewById(R.id.btn6);
            btn[6] = (Button) findViewById(R.id.btn7);
            btn[7] = (Button) findViewById(R.id.btn8);
            btn[8] = (Button) findViewById(R.id.btn9);
            btn[9] = (Button) findViewById(R.id.btn10);
            btn[10] = (Button) findViewById(R.id.btn11);
            btn[11] = (Button) findViewById(R.id.btn12);
            btn[12] = (Button) findViewById(R.id.btn13);
            btn[13] = (Button) findViewById(R.id.btn14);
            btn[14] = (Button) findViewById(R.id.btn15);
            btn[15] = (Button) findViewById(R.id.btn16);
        }
    }
}

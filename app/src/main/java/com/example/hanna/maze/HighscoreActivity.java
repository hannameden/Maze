package com.example.hanna.maze;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

public class HighscoreActivity extends AppCompatActivity {

    private int size;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String choice = getIntent().getStringExtra("size");
        String levelString = getIntent().getStringExtra("level");

        if(choice.equals("10 x 10")){
            size = 10;
        }else if(choice.equals("5 x 5")){
            size = 5;
        }else {
            size = 15;
        }

        if(levelString.equals("Easy"))
            level = 2;
        else if(levelString.equals("Lil hard"))
            level = 4;
        else
            level = 1;


        //setContentView(new Highscore(this, size, level));
    }
}

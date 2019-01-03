package com.example.hanna.maze;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "GameActivity";
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         String choice = getIntent().getStringExtra("choice");


        if(choice.equals("10 x 10")){
            size = 10;
        }else if(choice.equals("5 x 5")){
            size = 5;
        }else {
            size = 15;
        }
        setContentView(new Game(this, size));
    }


    /*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("x", Player.currentCell.getX());
        outState.putInt("y", Player.currentCell.getY());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        int x = savedInstanceState.getInt("x");
        int y = savedInstanceState.getInt("y");

        Player.setCurrentCell(x, y);
    }
    */

}

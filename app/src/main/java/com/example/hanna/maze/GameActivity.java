package com.example.hanna.maze;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "GameActivity";

    private int size;
    private int level;

    private int seed;

    private Game game;

    private int x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         String choice = getIntent().getStringExtra("size");
         level = getIntent().getIntExtra("level", level);

        if (choice.equals("10 x 10")) {
            size = 10;
        } else if (choice.equals("5 x 5")) {
            size = 5;
        } else {
            size = 15;
        }

        if(level == 1)
            seed = 3;
        else if(level == 2)
            seed = 4;
        else
            seed = 1;

        game = new Game(this, this, size, seed);

        setContentView(game);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("x", Player.currentCell.getX());
        outState.putInt("y", Player.currentCell.getY());

        outState.putInt("seconds", game.getSeconds());
        outState.putInt("tenSecs", game.getTenSecs());

        outState.putLong("timer", game.getTimer());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        game.setPlayerX(savedInstanceState.getInt("x"));
        game.setPlayerY(savedInstanceState.getInt("y"));

        game.setSeconds(savedInstanceState.getInt("seconds"));
        game.setTenSecs(savedInstanceState.getInt("seconds"));

        game.setTimer(savedInstanceState.getLong("timer"));

    }

    public void sendToMainActivity(){
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    public int getLevel(){
        return level;
    }
}

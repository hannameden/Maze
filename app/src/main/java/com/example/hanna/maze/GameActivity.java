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

    private Game game;

    private int x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         String choice = getIntent().getStringExtra("size");
         String levelString = getIntent().getStringExtra("level");

        if (choice.equals("10 x 10")) {
            size = 10;
        } else if (choice.equals("5 x 5")) {
            size = 5;
        } else {
            size = 15;
        }

        if(levelString.equals("Easy"))
            level = 3;
        else if(levelString.equals("Lil hard"))
            level = 4;
        else
            level = 1;

        game = new Game(this, this, size, level);

        setContentView(game);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("x", Player.currentCell.getX());
        outState.putInt("y", Player.currentCell.getY());
        outState.putString("timer", game.getTime());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        game.setPlayerX(savedInstanceState.getInt("x"));
        game.setPlayerY(savedInstanceState.getInt("y"));
        game.setTime(savedInstanceState.getString("timer"));

    }

    public void sendToMainActivity(){
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
}

package com.example.hanna.maze;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "GameActivity";

    private int size, level, seed;
    private MediaPlayer mediaPlayer;
    private Game game;

    private int x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mediaPlayer = MediaPlayer.create(this, R.raw.action);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100,100);

        mediaPlayer.start();

        size = getIntent().getIntExtra("size", size);
        level = getIntent().getIntExtra("level", level);

       if (level == 1)
            seed = 3;
        else if (level == 2)
            seed = 4;
        else
            seed = 1;

        game = new Game(this, this, size, seed);

        setContentView(game);
    }
    
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("x", Player.currentCell.getX());
        editor.putInt("y", Player.currentCell.getY());

        editor.putInt("seconds", game.getSeconds());
        editor.putInt("tenSecs", game.getTenSecs());

        editor.putLong("timer", game.getTimer());

        editor.commit();

        mediaPlayer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);

        game.setPlayerX(sharedPreferences.getInt("x", 0));
        game.setPlayerY(sharedPreferences.getInt("y", 0));

        game.setSeconds(sharedPreferences.getInt("seconds", 0));
        game.setTenSecs(sharedPreferences.getInt("tenSecs", 0));

        game.setTimer(sharedPreferences.getLong("timer", 0));

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
        game.setTenSecs(savedInstanceState.getInt("tenSecs"));

        game.setTimer(savedInstanceState.getLong("timer"));
        mediaPlayer.start();

    }

    public void sendToMainActivity() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        mediaPlayer.stop();
    }

    public int getLevel() {
        return level;
    }

}

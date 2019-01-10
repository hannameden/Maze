package com.example.hanna.maze;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

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

    public void sendToMainActivity() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        mediaPlayer.stop();
    }


    public int getLevel() {
        return level;
    }

    public void countDown(){

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Be ready")
                .setMessage("The game will start in.. ")
                .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            private static final int AUTO_DISMISS_MILLIS = 3000;
            @Override
            public void onShow(final DialogInterface dialog) {
                final Button defaultButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                final CharSequence positiveButtonText = defaultButton.getText();
                new CountDownTimer(AUTO_DISMISS_MILLIS, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                        ((AlertDialog) dialog).setMessage("The game will start in.. " +String.format(
                                Locale.getDefault(), "%s %d",
                                positiveButtonText,
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1 //add one so it never displays zero
                        ));
                    }
                    @Override
                    public void onFinish() {
                        if (((AlertDialog) dialog).isShowing()) {

                            Intent gameIntent = new Intent(Game.CURRENT_CONTEXT, GameActivity.class);
                            gameIntent.putExtra("size", size);
                            gameIntent.putExtra("level", seed);

                            startActivity(gameIntent);
                            dialog.dismiss();
                        }
                    }
                }.start();
            }
        });
        dialog.show();
    }
}

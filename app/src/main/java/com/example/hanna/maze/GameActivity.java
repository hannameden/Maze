package com.example.hanna.maze;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;

/**
 * The GameActivity displays the game by settings its content view to a Game object which is
 * of the type SurfaceView by inheritance.
 *
 * @author Hanna Medén, Niklas Nordgren
 * @version 2019-01-16
 */
public class GameActivity extends AppCompatActivity {

    private static final String TAG = "GameActivity";

    private int size, level, seed;
    //private MediaPlayer mediaPlayer;
    private Game game;

    private boolean isPaused;

    private int x, y;

    /**
     * Initializes the seed which is used to create the Maze of the game.
     * The value of the seed is dependant on the value of the level variable.
     *
     * Instantiates a Game object which is set to this Activitys ContentView.
     *
     * {@inheritDoc}
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        mediaPlayer = MediaPlayer.create(this, R.raw.action);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100,100);
        mediaPlayer.start();
        */

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

    /**
     * Sets the isPaused boolean to true.
     * Saves Game related variables by using SharedPreferences.
     *
     * {@inheritDoc}
     */
    @Override
    protected void onPause() {
        super.onPause();

        isPaused = true;

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("x", Player.currentCell.getX());
        editor.putInt("y", Player.currentCell.getY());

        editor.putInt("seconds", game.getSeconds());
        editor.putInt("tenSecs", game.getTenSecs());

        editor.putLong("timer", game.getTimer());

        editor.commit();

        //mediaPlayer.stop();
    }

    /**
     * Loads Game related variables using SharedPreferences, if the Game has been paused.
     *
     * {@inheritDoc}
     */
    @Override
    protected void onResume() {
        super.onResume();

        if(isPaused){
            SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);

            game.setPlayerX(sharedPreferences.getInt("x", 0));
            game.setPlayerY(sharedPreferences.getInt("y", 0));

            game.setSeconds(sharedPreferences.getInt("seconds", 0));
            game.setTenSecs(sharedPreferences.getInt("tenSecs", 0));

            game.setTimer(sharedPreferences.getLong("timer", 0));
        }

    }


    /**
     *
     *
     * @param outState
     * {@inheritDoc}
     */
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
        //mediaPlayer.start();

    }

    public void sendToMainActivity() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        //mediaPlayer.stop();
    }

    public int getLevel() {
        return level;
    }

}

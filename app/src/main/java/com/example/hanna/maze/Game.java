package com.example.hanna.maze;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 *
 * Used by the GameActivity class as a its ContentView
 *
 * Holds all of the necessary objects to create the Maze Game.
 *
 * {@inheritDoc}
 *
 * @author Hanna Medén, Niklas Nordgren
 * @version 2019-01-16
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "Game";

    public static Context CURRENT_CONTEXT;

    private GameActivity gameActivity;

    private GameThread gameThread;
    private Maze maze;
    private Player player;
    private InputManager inputManager;

    public static int currentConfig;

    private Paint paint;
    private int seconds, tenSecs;
    private int playerX, playerY;

    private int size, seed;

    private String timerString;
    private float xTimerPlacement;
    private float yTimerPlacement;

    private long timer;
    private long now;
    private long lastTime;

    /**
     * Instantiates a new Game object that implicitly instantiates all of the necessary objects
     * to create the Maze game.
     *
     * @param context
     * @param gameActivity
     * @param size
     * @param seed
     */
    public Game(Context context, GameActivity gameActivity, int size, int seed) {
        super(context);

        this.gameActivity = gameActivity;
        this.size = size;
        this.seed = seed;

        getHolder().addCallback(this);

        setFocusable(true);

        CURRENT_CONTEXT = context;

        paint = new Paint();
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    private void init(int size) {

        maze = new Maze(size, seed);

        player = new Player(this, playerX, playerY);
        inputManager = new InputManager(player);
        this.setOnTouchListener(inputManager);

        lastTime = System.nanoTime();

        unlockRotation();

        gameThread = new GameThread(getHolder(), this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    /**
     * {@inheritDoc}
     *
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        DisplayMetrics dm = new DisplayMetrics();

        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);

        MainActivity.width = dm.widthPixels;
        MainActivity.height = dm.heightPixels;

        currentConfig = getResources().getConfiguration().orientation;

        if (currentConfig == Configuration.ORIENTATION_PORTRAIT) {
            xTimerPlacement = MainActivity.width / 2;
            yTimerPlacement = MainActivity.height / 12;
        } else {
            xTimerPlacement = MainActivity.width / 3;
            yTimerPlacement = MainActivity.height / 8;
        }

        init(size);
    }

    /**
     * {@inheritDoc}
     *
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * {@inheritDoc}
     *
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                gameThread.setRunning(false);
                gameThread.join();

            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }

    }

    /**
     *  Used to update variables related to the Game object.
     *  Updates the timer that is displayed onto the screen.
     */
    public void update() {

        now = System.nanoTime();
        timer += now - lastTime;

        tenSecs = (int) (timer / 100000000);

        if (timer >= 1000000000) {
            seconds++;
            timer = 0;
        }

        if (tenSecs != 10)
            timerString = seconds + "." + tenSecs;

        lastTime = now;

    }

    /**
     * {@inheritDoc}
     *
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);

        maze.render(canvas);
        player.render(canvas);
        inputManager.render(canvas);

        canvas.drawText(timerString, xTimerPlacement, yTimerPlacement, paint);
    }

    /**
     *
     * Gets the time represented as String object
     *
     * @return timerString
     */
    public String getTimerString() {
        return timerString;
    }

    /**
     * Gets the seconds represented as an Integer.
     *
     * @return seconds
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Gets the tenths represented as an Integer.
     *
     * @return tenSecs
     */
    public int getTenSecs() {
        return tenSecs;
    }

    /**
     * Sets the seconds of the timer.
     *
     * @param seconds
     */
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Sets the tenths of the timer.
     *
     * @param tenSecs
     */
    public void setTenSecs(int tenSecs) {
        this.tenSecs = tenSecs;
    }

    /**
     * Resets the game by re-initializing game related variables
     * and by calling the methods lockRotation() and countDown().
     */
    public void resetGame() {

        playerX = 0;
        playerY = 0;

        seconds = 0;
        tenSecs = 0;

        lockRotation();
        countDown();
    }

    /**
     * Displays an AlertDialog with a countdown on top of it indicating that the game is about to begin.
     */
    public void countDown(){

        AlertDialog dialog = new AlertDialog.Builder(Game.CURRENT_CONTEXT)
                .setTitle("Be ready")
                .setMessage("The game will start in.. ")
                .setCancelable(false)
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

                            init(size);
                            dialog.dismiss();
                        }
                    }
                }.start();
            }
        });
        dialog.show();
    }

    /**
     * Sets the playerX
     * @param playerX
     */
    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    /**
     * Sets the playerY
     * @param playerY
     */
    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    /**
     * Sets running
     * @param val
     */
    public void setRunning(boolean val) {
        gameThread.setRunning(val);
    }

    /**
     * Gets the GameActivity object.
     * @return gameActivity
     */
    public GameActivity getGameActivity() {
        return gameActivity;
    }

    /**
     * Gets the timer.
     * @return timer
     */
    public long getTimer() {
        return timer;
    }

    /**
     * Sets the timer.
     * @param timer
     */
    public void setTimer(long timer) {
        this.timer = timer;
    }

    /**
     * Gets the level.
     * @return gameActivity objects level
     */
    public int getLevel(){
        return gameActivity.getLevel();
    }

    /**
     * Gets the size.
     * @return size
     */
    public int getSize(){
        return size;
    }

    /**
     * Locks the screenorientation to the Game objects currentConfig.
     */
    public void lockRotation(){
        gameActivity.setRequestedOrientation(Game.currentConfig);
    }

    /**
     * Unlocks the screenorientation of the Game objects currentConfig.
     */
    public void unlockRotation(){
        gameActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
}

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

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

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

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);

        maze.render(canvas);
        player.render(canvas);
        inputManager.render(canvas);

        canvas.drawText(timerString, xTimerPlacement, yTimerPlacement, paint);
    }

    public String getTimerString() {
        return timerString;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getTenSecs() {
        return tenSecs;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void setTenSecs(int tenSecs) {
        this.tenSecs = tenSecs;
    }

    public void resetGame() {

        playerX = 0;
        playerY = 0;

        seconds = 0;
        tenSecs = 0;

        lockRotation();
        countDown();
    }

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

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public void setRunning(boolean val) {
        gameThread.setRunning(val);
    }

    public GameActivity getGameActivity() {
        return gameActivity;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    public int getLevel(){
        return gameActivity.getLevel();
    }

    public int getSize(){
        return size;
    }

    public void lockRotation(){
        gameActivity.setRequestedOrientation(Game.currentConfig);
    }

    public void unlockRotation(){
        gameActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
}

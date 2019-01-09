package com.example.hanna.maze;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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

        init(size);

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

}

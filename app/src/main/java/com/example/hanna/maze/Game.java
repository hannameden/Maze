package com.example.hanna.maze;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "Game";

    public static Context CURRENT_CONTEXT;

    private GameThread gameThread;
    private Maze maze;
    private Player player;
    private InputManager inputManager;

    public static int currentConfig;

    private Paint paint;
    private int seconds, tenSecs;
    private int playerX, playerY;

    private int size, level;

    public Game(Context context, int size, int level) {
        super(context);

        this.size = size;
        this.level = level;

        getHolder().addCallback(this);

        setFocusable(true);

        CURRENT_CONTEXT = context;

        paint = new Paint();
        paint.setTextSize(150);
        paint.setTextAlign(Paint.Align.CENTER);

    }

    private void init(int size) {
        maze = new Maze(size, level);
        player = new Player(this, playerX, playerY);
        inputManager = new InputManager(player);
        this.setOnTouchListener(inputManager);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        DisplayMetrics dm = new DisplayMetrics();

        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);

        MainActivity.width = dm.widthPixels;
        MainActivity.height = dm.heightPixels;

        currentConfig = getResources().getConfiguration().orientation;

        init(size);

        gameThread = new GameThread(getHolder(), this);
        gameThread.setRunning(true);
        gameThread.start();
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


    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);

        maze.render(canvas);
        player.render(canvas);
        inputManager.render(canvas);

        /*

        if (currentConfig == Configuration.ORIENTATION_PORTRAIT)
            canvas.drawText(seconds + "." + tenSecs, MainActivity.width / 2, MainActivity.height / 10, paint);
        else
            canvas.drawText(seconds + "." + tenSecs, MainActivity.width - MainActivity.width / 12, MainActivity.height / 2, paint);

            */

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
        init(size);

    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }



}

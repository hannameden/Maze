package com.example.hanna.maze;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    public static Context CURRENT_CONTEXT;

    private GameThread gameThread;
    private Maze maze;
    private Player player;
    private Controls controls;
    private InputManager inputManager;

    public static int currentConfig;

    private int size;

    public Game(Context context, int size) {
        super(context);

        this.size = size;

        getHolder().addCallback(this);

        setFocusable(true);
    }

    private void init(){
        maze = new Maze(size);
        player = new Player();
        controls = new Controls();
        inputManager = new InputManager();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        DisplayMetrics dm = new DisplayMetrics();

        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);

        MainActivity.width = dm.widthPixels;
        MainActivity.height = dm.heightPixels;

        currentConfig = getResources().getConfiguration().orientation;

        init();

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
        controls.render(canvas);
        inputManager.render(canvas);

    }
}

package com.example.hanna.maze;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    public static Context CURRENT_CONTEXT;

    private Thread gameThread;

    public Game(Context context) {
        super(context);

        getHolder().addCallback(this);

        CURRENT_CONTEXT = context;

        gameThread = new GameThread(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void update() {


    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

    }
}

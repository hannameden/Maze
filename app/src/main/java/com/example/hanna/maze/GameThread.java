package com.example.hanna.maze;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private static final String TAG = "MainThread";

    public static final int MAX_FPS = 30;

    private SurfaceHolder surfaceHolder;
    private Game game;
    private boolean running;

    public static Canvas canvas;

    public GameThread(SurfaceHolder surfaceHolder, Game game){
        super();
        this.surfaceHolder = surfaceHolder;
        this.game = game;
    }

    @Override
    public void run() {

        int fps = 30;
        double timePerUpdate = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();

        while(running) {
            canvas = null;

            now = System.nanoTime();
            delta += (now - lastTime) / timePerUpdate;
            lastTime = now;

            if(delta >= 1) {

                try{
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder){
                        game.update();
                        game.draw(canvas);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    if(canvas != null){
                        try{
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }

                delta--;
            }

        }

    }

    public void setRunning(boolean running){
        this.running = running;
    }

}

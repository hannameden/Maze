package com.example.hanna.maze;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Used to run the Game loop in the run method.
 * Calling the Game objects, update and draw method 30 times a second.
 *  @author Hanna Medén, Niklas Nordgren
 *  @version 2019-01-16
 */
public class GameThread extends Thread {

    private static final String TAG = "MainThread";

    public static final int MAX_FPS = 30;

    private SurfaceHolder surfaceHolder;
    private Game game;
    private boolean running;

    public static Canvas canvas;

    /**
     * Instantiates the thread.
     * @param surfaceHolder
     * @param game
     */
    public GameThread(SurfaceHolder surfaceHolder, Game game){
        super();
        this.surfaceHolder = surfaceHolder;
        this.game = game;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * Sets running variable.
     */
    public void setRunning(boolean running){
        this.running = running;
    }

}

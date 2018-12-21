package com.example.hanna.maze;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private static final String TAG = "MainThread";

    public static final int MAX_FPS = 30;

    private double averageFPS;
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
        long timer = 0;
        int averageFPS = 0;

        while(running) {
            canvas = null;

            now = System.nanoTime();
            timer += now - lastTime;
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

                averageFPS ++;
                delta--;
            }

            if(timer >= 1000000000) {
                //System.out.println("Average FPS: " + averageFPS);
                averageFPS = 0;
                timer = 0;
            }

        }

    }

    public void setRunning(boolean running){
        this.running = running;
    }

}

package com.example.hanna.maze;

import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class InputManager implements View.OnTouchListener {

    private static final String TAG = "InputManager";

    private Player player;

    private int controllerWidth;
    private int controllerHeight;

    private Paint paint;

    private Rect upRect, downRect, leftRect, rightRect;

    private int maxMazeHeight = 14;

    public InputManager(Player player) {

        this.player = player;

        controllerWidth = 75;
        controllerHeight = controllerWidth;

        paint = new Paint();

        if (Game.currentConfig == Configuration.ORIENTATION_PORTRAIT) {
            upRect = new Rect(MainActivity.width / 2 - controllerWidth, MainActivity.height / 2 + (maxMazeHeight * Cell.CELLSIZE) / 2 + controllerHeight, MainActivity.width / 2 + controllerWidth, MainActivity.height / 2 + (maxMazeHeight * Cell.CELLSIZE) / 2 + controllerHeight * 3);
            downRect = new Rect(MainActivity.width / 2 - controllerWidth, MainActivity.height / 2 + (maxMazeHeight * Cell.CELLSIZE) / 2 + controllerHeight * 5, MainActivity.width / 2 + controllerWidth, MainActivity.height / 2 + (maxMazeHeight * Cell.CELLSIZE) / 2 + controllerHeight * 7);
            leftRect = new Rect(MainActivity.width / 2 - controllerWidth * 3, MainActivity.height / 2 + (maxMazeHeight * Cell.CELLSIZE) / 2 + controllerHeight * 3, MainActivity.width / 2 - controllerWidth, MainActivity.height / 2 + (maxMazeHeight * Cell.CELLSIZE) / 2 + controllerHeight * 5);
            rightRect = new Rect(MainActivity.width / 2 + controllerWidth, MainActivity.height / 2 + (maxMazeHeight * Cell.CELLSIZE) / 2 + controllerHeight * 3, MainActivity.width / 2 + controllerWidth * 3, MainActivity.height / 2 + (maxMazeHeight * Cell.CELLSIZE) / 2 + controllerHeight * 5);
        } else if (Game.currentConfig == Configuration.ORIENTATION_LANDSCAPE) {

        }

    }


    public void update() {

    }

    public void render(Canvas canvas) {

        //canvas.drawRect(MainActivity.width / 2 - controllerWidth, MainActivity.height / 2 + (Maze.cells[0].length * Cell.CELLSIZE) / 2 + controllerHeight, MainActivity.width / 2 + controllerWidth, MainActivity.height / 2 + (Maze.cells[0].length * Cell.CELLSIZE) / 2 + controllerHeight * 3, paint);

        canvas.drawRect(upRect, paint);
        canvas.drawRect(downRect, paint);
        canvas.drawRect(leftRect, paint);
        canvas.drawRect(rightRect, paint);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Point point = new Point((int) event.getX(), (int) event.getY());

        if (upRect.contains(point.x, point.y)) {
            player.moveUp();
        } else if (downRect.contains(point.x, point.y)) {
            player.moveDown();
        } else if (leftRect.contains(point.x, point.y)) {
            player.moveLeft();
        } else if (rightRect.contains(point.x, point.y)) {
            player.moveRight();
        }


        return false;
    }
}

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

/**
 * This class handles all the inputs.
 * @author Hanna Medén, Niklas Nordgren
 * @version 2019-01-16
 */
public class InputManager implements View.OnTouchListener {

    private static final String TAG = "InputManager";

    private Player player;

    private int controllerWidth;
    private int controllerHeight;

    private Paint paint;

    private Rect upRect, downRect, leftRect, rightRect;

    /**
     * Instantiates with a player object.
     */
    public InputManager(Player player) {

        this.player = player;

        controllerWidth = MainActivity.width / 6;
        controllerHeight = controllerWidth;

        paint = new Paint();
        paint.setARGB(50, 100, 100, 100);

        if (Game.currentConfig == Configuration.ORIENTATION_PORTRAIT) {
            controllerWidth = MainActivity.width / 6;
            controllerHeight = controllerWidth;
            //upRect = new Rect(MainActivity.width / 2 - controllerWidth, MainActivity.height / 2 + (maxMazeHeight * Cell.CELLSIZE) / 2 + controllerHeight, MainActivity.width / 2 + controllerWidth, MainActivity.height / 2 + (maxMazeHeight * Cell.CELLSIZE) / 2 + controllerHeight * 3);
            //downRect = new Rect(MainActivity.width / 2 - controllerWidth, MainActivity.height / 2 + (maxMazeHeight * Cell.CELLSIZE) / 2 + controllerHeight * 5, MainActivity.width / 2 + controllerWidth, MainActivity.height / 2 + (maxMazeHeight * Cell.CELLSIZE) / 2 + controllerHeight * 7);
            leftRect = new Rect(MainActivity.width / 2 - controllerWidth / 2 * 3, MainActivity.height - controllerHeight / 2 * 5, MainActivity.width / 2 - controllerWidth / 2, MainActivity.height - controllerHeight / 2 * 3);
            rightRect = new Rect(MainActivity.width / 2 + controllerWidth / 2, MainActivity.height - controllerHeight / 2 * 5, MainActivity.width / 2 + controllerWidth / 2 * 3, MainActivity.height - controllerHeight / 2 * 3);
            upRect = new Rect(MainActivity.width / 2 - controllerWidth / 2, MainActivity.height - controllerHeight / 2 * 7, MainActivity.width / 2 + controllerWidth / 2, MainActivity.height - controllerHeight / 2 * 5);
            downRect = new Rect(MainActivity.width / 2 - controllerWidth / 2, MainActivity.height - controllerHeight / 2 * 3, MainActivity.width / 2 + controllerWidth / 2, MainActivity.height - controllerHeight / 2);


        } else if (Game.currentConfig == Configuration.ORIENTATION_LANDSCAPE) {
            controllerHeight = MainActivity.height / 6;
            controllerWidth = controllerHeight;

            upRect = new Rect(controllerWidth / 2 * 3, MainActivity.height / 2 - controllerHeight / 2 - MainActivity.statusBarHeight / 2 - controllerHeight, controllerWidth / 2 * 5, MainActivity.height / 2 - controllerHeight / 2 - MainActivity.statusBarHeight / 2);
            downRect = new Rect(controllerWidth / 2 * 3, MainActivity.height / 2 + controllerHeight / 2 - MainActivity.statusBarHeight / 2, controllerWidth / 2 * 5, MainActivity.height / 2 + controllerHeight / 2 * 3 - MainActivity.statusBarHeight / 2);
            leftRect = new Rect(controllerWidth / 2, MainActivity.height / 2 - controllerHeight / 2 - MainActivity.statusBarHeight / 2, controllerWidth / 2 * 3, MainActivity.height / 2 + controllerHeight / 2 - MainActivity.statusBarHeight / 2);
            rightRect = new Rect(controllerWidth / 2 * 5, MainActivity.height / 2 - controllerHeight / 2 - MainActivity.statusBarHeight / 2, controllerWidth / 2 * 7, MainActivity.height / 2 + controllerHeight / 2 - MainActivity.statusBarHeight / 2);
        }

    }

    /**
     * Currently not used.
     */
    public void update() {

    }

    /**
     * Draws the on screen controls.
     * @param canvas
     */
    public void render(Canvas canvas) {

        canvas.drawRect(upRect, paint);
        canvas.drawRect(downRect, paint);
        canvas.drawRect(leftRect, paint);
        canvas.drawRect(rightRect, paint);
    }

    /**
     * Method for moving the player with the controls.
     * {@inheritDoc}
     * @param v
     * @param event
     * @return
     */
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

package com.example.hanna.maze;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import static java.security.AccessController.getContext;

public class Cell {

    public static final int CELLSIZE = 60;

    public int[] walls = {1, 1, 1, 1}; //N, S, W, E    1 = wall, 0 = no wall

    public static final int WALLSIZE = 7;

    private int x, y;
    private int xPixels, yPixels;
    private boolean isVisited;
    private boolean isSolution;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;

        if(Game.currentConfig == Configuration.ORIENTATION_LANDSCAPE)
            this.xPixels = x * Cell.CELLSIZE + MainActivity.width / 2 - Maze.cells.length * CELLSIZE / 2 + MainActivity.statusBarHeight / 2;
        else
            this.xPixels = x * Cell.CELLSIZE + MainActivity.width / 2 - Maze.cells.length * CELLSIZE / 2;

        if(Game.currentConfig == Configuration.ORIENTATION_LANDSCAPE)
            this.yPixels = y * Cell.CELLSIZE + MainActivity.height / 2 - Maze.cells[0].length * CELLSIZE / 2 - MainActivity.statusBarHeight / 2;
        else
            this.yPixels = y * Cell.CELLSIZE + MainActivity.height / 2 - Maze.cells[0].length * CELLSIZE / 2;

    }

    public void drawWalls(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        //North
        if(walls[0] == 1)
            canvas.drawRect(xPixels + WALLSIZE, yPixels+ WALLSIZE, xPixels+CELLSIZE, yPixels, paint);

        //South
        if(walls[1] == 1)
            canvas.drawRect(xPixels + WALLSIZE, yPixels + CELLSIZE + WALLSIZE, xPixels + CELLSIZE, yPixels + CELLSIZE, paint);

        //West
        if(walls[2] == 1)
            canvas.drawRect(xPixels + WALLSIZE, yPixels + WALLSIZE, xPixels, yPixels + CELLSIZE, paint);

        //East
        if(walls[3] == 1)
            canvas.drawRect(xPixels + CELLSIZE + WALLSIZE, yPixels + WALLSIZE, xPixels + CELLSIZE , yPixels + CELLSIZE, paint);

    }

    public void fillCell(Canvas canvas, Paint paint) {
        canvas.drawRect(xPixels + WALLSIZE * 2, yPixels + WALLSIZE * 2, xPixels + CELLSIZE - WALLSIZE, yPixels + CELLSIZE - WALLSIZE, paint);

    }

    public boolean getIsVisited() {
        return isVisited;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public boolean isSolution() {
        return isSolution;
    }

    public void setSolution(boolean isSolution) {
        this.isSolution = isSolution;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getxPixels() {
        return xPixels;
    }

    public int getyPixels() {
        return yPixels;
    }
}

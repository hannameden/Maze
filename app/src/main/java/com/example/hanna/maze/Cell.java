package com.example.hanna.maze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Cell {

    public static final float CELLSIZE = 60;

    public int[] walls = {1, 1, 1, 1}; //N, S, W, E    1 = wall, 0 = no wall

    private int x, y;
    private float xPixels, yPixels;
    private boolean isVisited;
    private boolean isSolution;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.xPixels = x * Cell.CELLSIZE;
        this.yPixels = y * Cell.CELLSIZE;
    }

    public void drawWalls(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);


        //North
        if(walls[0] == 1)
            canvas.drawRect(xPixels+5, yPixels+5, xPixels+CELLSIZE, yPixels, paint);

        //South
        if(walls[1] == 1)
            canvas.drawRect(xPixels+5, yPixels + CELLSIZE + 5, xPixels + CELLSIZE, yPixels + CELLSIZE, paint);

        //West
        if(walls[2] == 1)
            canvas.drawRect(xPixels+5, yPixels+5, xPixels, yPixels + CELLSIZE, paint);

        //East
        if(walls[3] == 1)
            canvas.drawRect(xPixels + CELLSIZE + 5 , yPixels + 5, xPixels + CELLSIZE , yPixels + CELLSIZE, paint);
        
    }

    public void fillCell(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawRect(xPixels+2, yPixels+2, CELLSIZE-5, CELLSIZE-5, paint);
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

}

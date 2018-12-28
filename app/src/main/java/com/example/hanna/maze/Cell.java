package com.example.hanna.maze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Cell {

    public static final int CELLSIZE = 60;

    public int[] walls = {1, 1, 1, 1}; //N, S, W, E    1 = wall, 0 = no wall

    private int wallSize = 7;

    private int x, y;
    private int xPixels, yPixels;
    private boolean isVisited;
    private boolean isSolution;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.xPixels = x * Cell.CELLSIZE + MainActivity.width / 2 - Maze.cells.length * CELLSIZE / 2;
        this.yPixels = y * Cell.CELLSIZE + MainActivity.height / 2 - Maze.cells[0].length * CELLSIZE / 2;
    }

    public void drawWalls(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        //North
        if(walls[0] == 1)
            canvas.drawRect(xPixels+wallSize, yPixels+wallSize, xPixels+CELLSIZE, yPixels, paint);

        //South
        if(walls[1] == 1)
            canvas.drawRect(xPixels + wallSize, yPixels + CELLSIZE + wallSize, xPixels + CELLSIZE, yPixels + CELLSIZE, paint);

        //West
        if(walls[2] == 1)
            canvas.drawRect(xPixels + wallSize, yPixels + wallSize, xPixels, yPixels + CELLSIZE, paint);

        //East
        if(walls[3] == 1)
            canvas.drawRect(xPixels + CELLSIZE + wallSize , yPixels + wallSize, xPixels + CELLSIZE , yPixels + CELLSIZE, paint);
        
    }

    public void fillCell(Canvas canvas, Paint paint) {
        canvas.drawRect(xPixels + wallSize * 2, yPixels + wallSize * 2, xPixels + CELLSIZE - wallSize, yPixels + CELLSIZE - wallSize, paint);

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

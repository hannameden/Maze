package com.example.hanna.maze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Controls {

    private int x,y;
    private Cell cellLeft, cellRight, cellUp, cellDown;

    public Controls(){
        this.x = Maze.cells[0][0].getxPixels() + Cell.WALLSIZE;
        this.y = Maze.cells[0][0].getyPixels() + Cell.WALLSIZE;

        cellDown = Maze.cells[2][2];
    }
    public void render(Canvas canvas){

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawRect(x + Cell.WALLSIZE, y + Cell.WALLSIZE, x + Cell.CELLSIZE - Cell.WALLSIZE * 2, y + Cell.CELLSIZE - Cell.WALLSIZE * 2, paint);

    }
}

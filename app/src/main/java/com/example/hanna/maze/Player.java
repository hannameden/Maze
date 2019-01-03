package com.example.hanna.maze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player {

    public static Cell currentCell;
    private int x, y;

    private int speed = Cell.CELLSIZE;

    public Player() {

        this.x = Maze.cells[0][0].getxPixels() + Cell.WALLSIZE;
        this.y = Maze.cells[0][0].getyPixels() + Cell.WALLSIZE;


        currentCell = Maze.cells[0][0];

    }

    public void update() {

    }



    public void render(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawRect(x + Cell.WALLSIZE, y + Cell.WALLSIZE, x + Cell.CELLSIZE - Cell.WALLSIZE * 2, y + Cell.CELLSIZE - Cell.WALLSIZE * 2, paint);

    }

    public static void setCurrentCell(int x, int y) {
        currentCell = Maze.cells[x][y];
    }
}

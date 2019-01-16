package com.example.hanna.maze;

import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import static java.security.AccessController.getContext;

/**
 * This class is used by the Maze class to generated the actual Maze in the game.
 * The actual Maze is implemented as a two dimensional array of Cell objects.
 *
 * @author Hanna Medén, Niklas Nordgren
 * @version 2019-01-16
 */
public class Cell {

    public static final int CELLSIZE = 60;

    public int[] walls = {1, 1, 1, 1}; //N, S, W, E    1 = wall, 0 = no wall

    public static final int WALLSIZE = 7;

    private int x, y;
    private int xPixels, yPixels;
    private boolean isVisited;
    private boolean isSolution;

    /**
     * Instantiates a new Cell object with position x and y in the two dimensional Cell array created by the Maze class.
     * Determines each Cell object pixel position on the screen depending on the x and y position aswell as the current configuration setting.
     *
     * @param x the x position
     * @param y the y position
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;

        if(Game.currentConfig == Configuration.ORIENTATION_LANDSCAPE)
            this.xPixels = x * Cell.CELLSIZE + MainActivity.width - MainActivity.width / 3 - Maze.cells.length * CELLSIZE / 2 + MainActivity.statusBarHeight / 2;
        else
            this.xPixels = x * Cell.CELLSIZE + MainActivity.width / 2 - Maze.cells.length * CELLSIZE / 2;

        if(Game.currentConfig == Configuration.ORIENTATION_LANDSCAPE)
            this.yPixels = y * Cell.CELLSIZE + MainActivity.height / 2 - Maze.cells[0].length * CELLSIZE / 2 - MainActivity.statusBarHeight / 2;
        else
            this.yPixels = y * Cell.CELLSIZE + MainActivity.height / 3 - Maze.cells[0].length * CELLSIZE / 2;

    }

    /**
     *
     * Draws the walls of the Cell object onto the incoming Canvas.
     *
     * @param canvas
     */
    public void drawWalls(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        //North
        if(walls[0] == 1)
            canvas.drawRect(xPixels, yPixels + WALLSIZE, xPixels + CELLSIZE, yPixels, paint);

        //South
        if(walls[1] == 1)
            canvas.drawRect(xPixels, yPixels + CELLSIZE + WALLSIZE, xPixels + CELLSIZE + WALLSIZE, yPixels + CELLSIZE, paint);

        //West
        if(walls[2] == 1)
            canvas.drawRect(xPixels, yPixels + CELLSIZE, xPixels + WALLSIZE, yPixels, paint);

        //East
        if(walls[3] == 1)
            canvas.drawRect(xPixels + CELLSIZE, yPixels + CELLSIZE, xPixels + CELLSIZE + WALLSIZE, yPixels, paint);

    }

    /**
     *
     * Fills the Cell object with a rectangle with the color specified by the incoming Paint object
     * onto the incoming Canvas object.
     *
     * @param canvas
     * @param paint
     */
    public void fillCell(Canvas canvas, Paint paint) {
        canvas.drawRect(xPixels + WALLSIZE * 2, yPixels + WALLSIZE * 2, xPixels + CELLSIZE - WALLSIZE, yPixels + CELLSIZE - WALLSIZE, paint);

    }

    /**
     *
     * This method is used during the creation the Maze, part of the recursive backtracking algorithm.
     *
     * @return true if the Cell object has been visited, otherwise false
     */
    public boolean getIsVisited() {
        return isVisited;
    }

    /**
     *
     * This method is used during the creation the Maze, part of the recursive backtracking algorithm.
     *
     * @param isVisited true if the Cell object has been visited, otherwise false
     */
    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    /**
     *
     * This method is currently not used by the application.
     * Used to display the correct path leading to the end of the Maze.
     *
     * @return true if this Cell object leads to the end of the Maze, otherwise false
     */
    public boolean isSolution() {
        return isSolution;
    }

    /**
     * This method is currently not used by the application.
     * Used to display the correct path leading to the end of the Maze.
     *
     * @param isSolution
     */
    public void setSolution(boolean isSolution) {
        this.isSolution = isSolution;
    }

    /**
     *
     * @return x the Cell objects x position in the Maze
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return y the Cell objects y position in the Maze
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @return xPixels the Cell objects x position of where it is located on the screen
     */
    public int getxPixels() {
        return xPixels;
    }

    /**
     *
     * @return yPixels the Cell objects y position of where it is located on the screen
     */
    public int getyPixels() {
        return yPixels;
    }
}

package com.example.hanna.maze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;


public class Player {

    private static final String TAG = "Player";

    private Game game;

    public static Cell currentCell;
    private int x, y;
    private boolean goalIsFound;

    private int speed = Cell.CELLSIZE;

    public Player(Game game, int playerX, int playerY) {

        this.game = game;

        Log.d(TAG, "Player: " + x + " " + y);

        this.x = Maze.cells[playerX][playerY].getxPixels() + Cell.WALLSIZE;
        this.y = Maze.cells[playerX][playerY].getyPixels() + Cell.WALLSIZE;

        currentCell = Maze.cells[playerX][playerY];

    }

    public void update(){

    }

    public void moveLeft() {

        if (x > 0 && currentCell.walls[2] != 1) {
            x -= speed;
            currentCell = Maze.cells[currentCell.getX() - 1][currentCell.getY()];
        }

    }

    public void moveDown() {

        if (currentCell.walls[1] != 1) {
            y += speed;
            currentCell = Maze.cells[currentCell.getX()][currentCell.getY() + 1];
            checkIfGoalIsReached();
        }

    }

    public void moveUp() {

        if (y > 0 && currentCell.walls[0] != 1) {
            y -= speed;
            currentCell = Maze.cells[currentCell.getX()][currentCell.getY() - 1];
        }

    }

    public void moveRight() {

        if (currentCell.walls[3] != 1) {
            x += speed;
            currentCell = Maze.cells[currentCell.getX() + 1][currentCell.getY()];
            checkIfGoalIsReached();
        }

    }


    public void render(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        //canvas.drawRect(x + Cell.WALLSIZE, y + Cell.WALLSIZE, x + Cell.CELLSIZE - Cell.WALLSIZE * 2, y + Cell.CELLSIZE - Cell.WALLSIZE * 2, paint);
        canvas.drawRect(x + Cell.WALLSIZE, y + Cell.WALLSIZE, x + Cell.CELLSIZE - Cell.WALLSIZE * 2, y + Cell.CELLSIZE - Cell.WALLSIZE * 2, paint);

    }

    public void checkIfGoalIsReached() {
        if (currentCell == Maze.cells[Maze.cells.length - 1][Maze.cells[0].length - 1] && !goalIsFound) {
            goalIsFound = true;
            Toast.makeText(Game.CURRENT_CONTEXT, "Goal is found!", Toast.LENGTH_SHORT).show();
            game.resetGame();
        }
    }

    public static void setCurrentCell(int x, int y) {
        currentCell = Maze.cells[x][y];
    }
}

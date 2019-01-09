package com.example.hanna.maze;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;


public class Player {

    private static final String TAG = "Player";

    private Game game;

    public static Cell currentCell;
    private int x, y;
    private boolean goalIsFound;
    public String name;

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
            playerWins();

            game.resetGame();
        }
    }
    public void playerWins(){

        final EditText input = new EditText(Game.CURRENT_CONTEXT);
        input.setInputType(InputType.TYPE_CLASS_TEXT );

        AlertDialog.Builder builder = new AlertDialog.Builder(game.CURRENT_CONTEXT);
        builder.setCancelable(false);
        builder.setTitle("Congratulations! ")
                .setMessage("Your time is " + game.getTime() + "\nPlease write in your name to get into the scoreboard!")
                .setView(input)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 name = input.getText().toString();


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                

                AlertDialog.Builder builder = new AlertDialog.Builder(game.getContext());
                builder.setCancelable(false)
                        .setMessage("What do you want to do?")
                        .setView(input)
                        .setPositiveButton("Menu ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.setNegativeButton("Restart level", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        game.resetGame();
                        dialog.cancel();

                    }
                });
                builder.show();



            }
        });


        builder.show();
        game.setRunning(false);
    }

    public static void setCurrentCell(int x, int y) {
        currentCell = Maze.cells[x][y];
    }
}

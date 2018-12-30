package com.example.hanna.maze;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;

public class Player {

    private Cell currentCell, goalCell, startCell;
    private int x, y;


    public Player(){
        this.x = Maze.cells[0][0].getxPixels() + Cell.WALLSIZE;
        this.y = Maze.cells[0][0].getyPixels() + Cell.WALLSIZE;

        currentCell = Maze.cells[0][0];



    }
    public void render(){

    }

    public void update(){
        if(currentCell == goalCell){

            /*
            AlertDialog.Builder builder = new AlertDialog.Builder();

            builder.setTitle("Congratulations")
                    .setMessage("Are you sure you want to delete this entry?")
                    .setNeutralButton("yay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();*/

        }

    }

    public void render(Canvas canvas){

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawRect(x + Cell.WALLSIZE, y + Cell.WALLSIZE, x + Cell.CELLSIZE - Cell.WALLSIZE * 2, y + Cell.CELLSIZE - Cell.WALLSIZE * 2, paint);

    }
}

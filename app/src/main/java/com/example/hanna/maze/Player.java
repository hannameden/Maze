package com.example.hanna.maze;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

public class Player {
    private Cell currentCell, goalCell, startCell;
    private int x, y;


    public Player(){




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
}

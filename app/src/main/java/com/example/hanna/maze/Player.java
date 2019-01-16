package com.example.hanna.maze;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.widget.EditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

/**
 * This class is used to create and move the player, it also keeps track
 * if the player reaches the goal. It also writes to the database.
 *  @author Hanna Medén, Niklas Nordgren
 *  @version 2019-01-16
 */
public class Player {

    private Game game;

    public static Cell currentCell;
    private int x, y;
    private boolean goalIsFound;

    private String playerName;
    private String playerTime;

    private int speed = Cell.CELLSIZE;

    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    private String levelString;
    private String sizeString;

    private ArrayList<Highscore> currentHighscoreResults;

    private boolean dataIsFetched;

    private Bitmap playerImage;

    /**
     * Takes in a Game object and the coordinates for where in the maze it is.
     * @param game
     * @param playerX
     * @param playerY
     */
    public Player(Game game, int playerX, int playerY) {

        this.game = game;

        this.x = Maze.cells[playerX][playerY].getxPixels() + Cell.WALLSIZE;
        this.y = Maze.cells[playerX][playerY].getyPixels() + Cell.WALLSIZE;

        currentCell = Maze.cells[playerX][playerY];

        databaseReference = FirebaseDatabase.getInstance().getReference();

        BitmapFactory bf = new BitmapFactory();
        playerImage = bf.decodeResource(game.CURRENT_CONTEXT.getResources(), R.drawable.alien_green);
        playerImage = Bitmap.createScaledBitmap(playerImage, Cell.CELLSIZE - Cell.WALLSIZE, Cell.CELLSIZE - Cell.WALLSIZE, false);

    }

    /**
     * Currently we don't use this method, but intend to do it for smoother
     * player movement.
     */
    public void update() {
    }

    /**
     * When the player moves left.
     */
    public void moveLeft() {

        if (x > 0 && currentCell.walls[2] != 1) {
            x -= speed;
            currentCell = Maze.cells[currentCell.getX() - 1][currentCell.getY()];
        }

    }
    /**
     * When the player moves down.
     */
    public void moveDown() {

        if (currentCell.walls[1] != 1) {
            y += speed;
            currentCell = Maze.cells[currentCell.getX()][currentCell.getY() + 1];
            checkIfGoalIsReached();
        }
    }

    /**
     * When the player moves up.
     */
    public void moveUp() {

        if (y > 0 && currentCell.walls[0] != 1) {
            y -= speed;
            currentCell = Maze.cells[currentCell.getX()][currentCell.getY() - 1];
        }
    }

    /**
     * When the player moves right.
     */
    public void moveRight() {

        if (currentCell.walls[3] != 1) {
            x += speed;
            currentCell = Maze.cells[currentCell.getX() + 1][currentCell.getY()];
            checkIfGoalIsReached();
        }
    }

    /**
     * For when we draw the player.
     * @param canvas
     */
    public void render(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawBitmap(playerImage, x, y, paint);
    }

    /**
     * Checks if the player has found the goal.
     */
    public void checkIfGoalIsReached() {

        if (currentCell == Maze.cells[Maze.cells.length - 1][Maze.cells[0].length - 1] && !goalIsFound) {
            goalIsFound = true;
            playerWins();
        }

    }

    /**
     * Method for when the player finds the goal. Saves the time and starts a popup.
     */
    public void playerWins() {

        playerTime = game.getTimerString();

        playerWinsPopup();

        game.setRunning(false);
    }

    /**
     * A popup for when the player has reached the goal. The player can write their name
     * in the textfield to register their time or press cancel.
     */
    public void playerWinsPopup() {

        game.lockRotation();

        final EditText input = new EditText(Game.CURRENT_CONTEXT);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        AlertDialog.Builder builder = new AlertDialog.Builder(game.getContext());

        builder.setCancelable(false);
        builder.setTitle("Congratulations! ")
                .setMessage("Your time is " + playerTime + "\nPlease write in your name to get into the scoreboard!")
                .setView(input)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        playerName = input.getText().toString();

                        if (playerName.isEmpty() == true) {

                            AlertDialog.Builder builder3 = new AlertDialog.Builder(Game.CURRENT_CONTEXT);
                            builder3.setTitle("Error")
                                    .setMessage("Please fill out your name or press 'Cancel'.")
                                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            playerWinsPopup();
                                        }
                                    })
                                    .create();
                            builder3.show();

                        } else {
                            saveResultToDatabase(playerName, Double.parseDouble(playerTime));
                            exitPopup();
                        }
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                exitPopup();
            }
        });
        builder.show();
    }

    /**
     * A method for the exit popup, choice between
     * go to the menu or restart the level.
     */
    public void exitPopup() {

        AlertDialog.Builder builder2 = new AlertDialog.Builder(Game.CURRENT_CONTEXT);
        builder2.setCancelable(false)
                .setMessage("What do you want to do now?")
                .setPositiveButton("Menu ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        game.getGameActivity().sendToMainActivity();
                    }
                });
        builder2.setNegativeButton("Restart level", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                game.resetGame();
                dialog.cancel();
            }
        });
        builder2.show();
    }

    /**
     * Currently not used but it is for setting the currentcell.
     * @param x
     * @param y
     */
    public static void setCurrentCell(int x, int y) {
        currentCell = Maze.cells[x][y];
    }

    private void saveResultToDatabase(final String playerName, final double playerTime) {
        //Compare with current database results and insert at correct position
        int level = game.getLevel();
        int size = game.getSize();

        if (size == 5) {
            sizeString = "5x5";
        } else if (size == 10) {
            sizeString = "10x10";
        } else {
            sizeString = "15x15";
        }

        if (level == 1) {
            levelString = "level1";
        } else if (level == 2) {
            levelString = "level2";
        } else {
            levelString = "level3";
        }

        currentHighscoreResults = new ArrayList<>();

        databaseReference.addValueEventListener(valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataIsFetched) {

                    for (DataSnapshot childDataSnapShot : dataSnapshot.child("highscore").child(sizeString).child(levelString).getChildren()) {

                        int playerRank = Integer.parseInt(childDataSnapShot.child("playerRank").getValue().toString());
                        String playerName = childDataSnapShot.child("playerName").getValue().toString();
                        double playerTime = Double.parseDouble(childDataSnapShot.child("playerTime").getValue().toString());

                        currentHighscoreResults.add(new Highscore(playerRank, playerName, playerTime));
                    }
                    Highscore newHighscore = new Highscore(0, playerName, playerTime);

                    insertNewHighscoreInDb(newHighscore, currentHighscoreResults);
                    dataIsFetched = true;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void insertNewHighscoreInDb(Highscore newHighscore, ArrayList<Highscore> currentHighscoreResults) {

        boolean inserted = false;
        int currentHighscoreResultsOriginalSize = currentHighscoreResults.size();

        for (int i = 0; i < currentHighscoreResultsOriginalSize; i++) {

            if (newHighscore.getPlayerTime() <= currentHighscoreResults.get(i).getPlayerTime() && !inserted) {
                currentHighscoreResults.add(i, newHighscore);
                inserted = true;
            }
        }
        if (!inserted)
            currentHighscoreResults.add(newHighscore);

        for (int i = 0; i < currentHighscoreResults.size(); i++) {
            Highscore highscore = new Highscore(i + 1, currentHighscoreResults.get(i).getPlayerName(), currentHighscoreResults.get(i).getPlayerTime());
            databaseReference.child("highscore").child(sizeString).child(levelString).child(Integer.toString(i + 1)).setValue(highscore);
        }
    }
}

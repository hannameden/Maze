package com.example.hanna.maze;

/**
 * Highscore class, handles the players time and rank on the highscore.
 * @author Hanna Medén, Niklas Nordgren
 * @version 2019-01-16
 */
public class Highscore {

    private int playerRank;
    private String playerName;
    private double playerTime;

    /**
     * Default constructor required for calls to DataSnapshot.getValue(Highscore.class)
     */
    public Highscore(){
    }

    /**
     * Instantiates the highscore object, with rank, name and time of the player.
     * @param playerRank
     * @param playerName
     * @param playerTime
     */
    public Highscore(int playerRank, String playerName, double playerTime){

        this.playerRank = playerRank;
        this.playerName = playerName;
        this.playerTime = playerTime;

    }

    /**
     * Returns the rank.
     * @return playerRank
     */
    public int getPlayerRank() {
        return playerRank;
    }

    /**
     * Sets the rank.
     * @param playerRank
     */
    public void setPlayerRank(int playerRank) {
        this.playerRank = playerRank;
    }

    /**
     * Returns the name.
     * @return playerName
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the name.
     * @param playerName
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Returns the time.
     * @return playerTime
     */
    public double getPlayerTime() {
        return playerTime;
    }

    /**
     * Sets the time.
     * @param playerTime
     */
    public void setPlayerTime(double playerTime) {
        this.playerTime = playerTime;
    }
}

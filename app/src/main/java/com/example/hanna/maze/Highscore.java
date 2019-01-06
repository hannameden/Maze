package com.example.hanna.maze;

import android.content.Context;

public class Highscore {

    private int playerRank;
    private String playerName;
    private double playerTime;

    public Highscore(){
        //Default constructor required for calls to DataSnapshot.getValue(Highscore.class)
    }

    public Highscore(int playerRank, String playerName, double playerTime){

        this.playerRank = playerRank;
        this.playerName = playerName;
        this.playerTime = playerTime;

    }

    public int getPlayerRank() {
        return playerRank;
    }

    public void setPlayerRank(int playerRank) {
        this.playerRank = playerRank;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public double getPlayerTime() {
        return playerTime;
    }

    public void setPlayerTime(double playerTime) {
        this.playerTime = playerTime;
    }
}

package com.braindead.scoreboard.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard implements Parcelable {

    private List<Player> playerList;
    private int numberOfPlayers;
    private int defaultScore;
    private String sessionName;

    private int currentPlayerNumber;
    private int delta;

    public Scoreboard(int numberOfPlayers, int defaultScore, String sessionName, int[] defaultColors) {
        playerList = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayers;
        this.defaultScore = defaultScore;
        this.sessionName = sessionName;
        this.currentPlayerNumber = 0;
        this.delta = 0;
        for (int i = 0; i < numberOfPlayers; i++) {
            playerList.add(new Player("Player " + (i + 1), defaultScore, defaultColors[i]));
        }
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /*public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }*/

    public int getDefaultScore() {
        return defaultScore;
    }

    public void setDefaultScore(int defaultScore) {
        this.defaultScore = defaultScore;
    }

    public int getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }

    public void setCurrentPlayerNumber(int currentPlayerNumber) {
        this.currentPlayerNumber = currentPlayerNumber;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public Player getPlayer(int i) {
        return playerList.get(i);
    }

    public Player getCurrentPlayer() {
        return playerList.get(currentPlayerNumber);
    }

    public void resetScoreboard() {
        for (Player player : playerList) {
            player.setScore(defaultScore);
            player.getPartialScoreList().clear();
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeList(playerList);
        out.writeInt(numberOfPlayers);
        out.writeInt(defaultScore);
        out.writeString(sessionName);
        out.writeInt(currentPlayerNumber);
        out.writeInt(delta);
    }

    public static final Parcelable.Creator<Scoreboard> CREATOR = new Parcelable.Creator<Scoreboard>() {
        public Scoreboard createFromParcel(Parcel in) {
            return new Scoreboard(in);
        }

        public Scoreboard[] newArray(int size) {
            return new Scoreboard[size];
        }
    };

    private Scoreboard(Parcel in) {
        playerList = in.readArrayList(null);
        numberOfPlayers = in.readInt();
        defaultScore = in.readInt();
        sessionName = in.readString();
        currentPlayerNumber = in.readInt();
        delta = in.readInt();
    }
}
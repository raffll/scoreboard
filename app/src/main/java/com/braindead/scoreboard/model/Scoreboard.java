package com.braindead.scoreboard.model;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {

    private List<Player> playerList = new ArrayList<>();
    private int numberOfPlayers;
    private int defaultScore;
    private String sessionName;

    private int currentPlayerNumber;
    private int delta;

    public Scoreboard(int numberOfPlayers, int defaultScore, int[] defaultColors, String sessionName) {
        this.numberOfPlayers = numberOfPlayers;
        this.defaultScore = defaultScore;
        this.sessionName = sessionName;
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

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

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
}
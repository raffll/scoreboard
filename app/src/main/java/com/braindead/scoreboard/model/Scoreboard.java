package com.braindead.scoreboard.model;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {

    private List<Player> playerList = new ArrayList<>();;
    private String sessionName;
    private int delta;

    public Scoreboard(int numberOfPlayers, int defaultScore, int[] defaultColors, String sessionName) {
        for (int i = 0; i < numberOfPlayers; i++) {
            playerList.add(new Player("Player " + (i + 1), defaultScore, defaultColors[i]));
        }
        this.sessionName = sessionName;
    }

    public void resetScoreboard() {
        for (Player player : playerList) {
            player.setScore(0);
            player.getPartialScoreList().clear();
        }
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
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
}
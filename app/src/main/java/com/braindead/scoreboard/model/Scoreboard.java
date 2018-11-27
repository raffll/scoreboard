package com.braindead.scoreboard.model;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {

    private List<Player> playerList;

    public Scoreboard(int numberOfPlayers, int defaultScore, int[] defaultColors) {
        playerList = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            playerList.add(new Player("Player " + (i + 1), defaultScore, defaultColors[i]));
        }
    }

    public void resetScoreboard() {
        for (Player player : playerList) {
            player.setScore(0);
        }
    }

    public Player getPlayer(int i) {
        return playerList.get(i);
    }
}
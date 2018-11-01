package com.braindead.scoreboard.model;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {

    public static final int MAX_PLAYERS = 10;

    private List<Player> playerList;

    public Scoreboard() {
        playerList = new ArrayList<>();
        for (int i = 0; i < MAX_PLAYERS; i++) {
            playerList.add(new Player("Player " + (i + 1), 0, 0));
        }
    }

    public Player getPlayer(int i) {
        return playerList.get(i);
    }
}

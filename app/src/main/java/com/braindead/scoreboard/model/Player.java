package com.braindead.scoreboard.model;

public class Player {

    private String name;
    private int score;
    private int color;

    Player(String name, int score, int color) {
        this.name = name;
        this.score = score;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}

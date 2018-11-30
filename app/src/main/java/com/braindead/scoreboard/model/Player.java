package com.braindead.scoreboard.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "player")
public class Player {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "score")
    private int score;

    @ColumnInfo(name = "color")
    private int color;

    @Ignore
    private List<Integer> partialScoreList  = new ArrayList<>();

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

    public List<Integer> getPartialScoreList() {
        return partialScoreList;
    }

    public void setPartialScoreList(List<Integer> partialScoreList) {
        this.partialScoreList = partialScoreList;
    }
}
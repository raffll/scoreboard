package com.braindead.scoreboard.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "player")
public class Player implements Parcelable {

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
    private List<Integer> partialScoreList = new ArrayList<>();

    public Player(String name, int score, int color) {
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

    public int getLastPartialScore() {
        if (partialScoreList != null && !partialScoreList.isEmpty()) {
            return partialScoreList.get(partialScoreList.size() - 1);
        } else
            return 0;
    }

    public void removeLastPartialScore() {
        if (partialScoreList != null && !partialScoreList.isEmpty()) {
            partialScoreList.remove(partialScoreList.size() - 1);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeInt(score);
        out.writeInt(color);
        out.writeList(partialScoreList);
    }

    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    private Player(Parcel in) {
        id = in.readInt();
        name = in.readString();
        score = in.readInt();
        color = in.readInt();
        partialScoreList = in.readArrayList(null);
    }
}
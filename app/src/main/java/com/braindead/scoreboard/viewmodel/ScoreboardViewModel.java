package com.braindead.scoreboard.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.braindead.scoreboard.model.Scoreboard;

public class ScoreboardViewModel extends ViewModel {

    public ObservableArrayList<Integer> observablePlayerVisibility;
    public ObservableArrayList<String> observablePlayerNameList;
    public ObservableArrayList<String> observablePlayerScoreList;
    public ObservableArrayList<Boolean> observablePlayerIsActiveList;

    private Scoreboard scoreboard;
    private int currentPlayerNumber;

    public void init(int numberOfPlayers) {
        currentPlayerNumber = 0;
        scoreboard = new Scoreboard();
        initObservablePlayerVisibility(numberOfPlayers);
        initObservablePlayerNameList();
        initObservablePlayerScoreList();
        initObservablePlayerIsActiveList();
    }

    private void initObservablePlayerVisibility(int numberOfPlayers) {
        observablePlayerVisibility = new ObservableArrayList<>();
        for (int i = 0; i < Scoreboard.MAX_PLAYERS; i++) {
            if (i < numberOfPlayers) {
                observablePlayerVisibility.add(View.VISIBLE);
            } else {
                observablePlayerVisibility.add(View.GONE);
            }
        }
    }

    private void initObservablePlayerNameList() {
        observablePlayerNameList = new ObservableArrayList<>();
        for (int i = 0; i < Scoreboard.MAX_PLAYERS; i++) {
            observablePlayerNameList.add(scoreboard.getPlayer(i).getName());
        }
    }

    private void initObservablePlayerScoreList() {
        observablePlayerScoreList = new ObservableArrayList<>();
        for (int i = 0; i < Scoreboard.MAX_PLAYERS; i++) {
            observablePlayerScoreList.add(Integer.toString(scoreboard.getPlayer(i).getScore()));
        }
    }

    private void initObservablePlayerIsActiveList() {
        observablePlayerIsActiveList = new ObservableArrayList<>();
        for (int i = 0; i < Scoreboard.MAX_PLAYERS; i++) {
            if (i == currentPlayerNumber) {
                observablePlayerIsActiveList.add(true);
            } else {
                observablePlayerIsActiveList.add(false);
            }
        }
    }

    public void onClickedAtPlayer(int playerNumber) {
        currentPlayerNumber = playerNumber;
        updateObservablePlayerIsActiveList();
    }

    private void updateObservablePlayerIsActiveList() {
        for (int i = 0; i < Scoreboard.MAX_PLAYERS; i++) {
            if (i == currentPlayerNumber) {
                observablePlayerIsActiveList.set(i, true);
            } else {
                observablePlayerIsActiveList.set(i, false);
            }
        }
    }

    public void onClickedScoreChange(int delta) {
        delta = scoreboard.getPlayer(currentPlayerNumber).getScore() + delta;
        scoreboard.getPlayer(currentPlayerNumber).setScore(delta);
        observablePlayerScoreList.set(currentPlayerNumber, Integer.toString(scoreboard.getPlayer(currentPlayerNumber).getScore()));
    }

    @BindingAdapter("android:textStyle")
    public static void setTypeface(TextView textView, String style) {
        switch (style) {
            case "bold":
                textView.setTypeface(null, Typeface.BOLD);
                break;
            default:
                textView.setTypeface(null, Typeface.NORMAL);
                break;
        }
    }
}
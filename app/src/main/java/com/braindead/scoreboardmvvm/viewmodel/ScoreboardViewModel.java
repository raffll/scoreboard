package com.braindead.scoreboardmvvm.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.view.View;

import com.braindead.scoreboardmvvm.model.Scoreboard;

public class ScoreboardViewModel extends ViewModel {

    public ObservableArrayList<Integer> observablePlayerVisibility;
    public ObservableArrayList<String> observablePlayerNameList;
    public ObservableArrayList<String> observablePlayerScoreList;

    private Scoreboard scoreboard;
    private int currentPlayerNumber = 0;

    public void init(int numberOfPlayers) {
        scoreboard = new Scoreboard();
        initObservablePlayerVisibility(numberOfPlayers);
        initObservablePlayerNameList();
        initObservablePlayerScoreList();
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

    public void onClickedAtPlayer(int playerNumber) {
        currentPlayerNumber = playerNumber;
    }

    public void onClickedScoreChange(int delta) {
        delta = scoreboard.getPlayer(currentPlayerNumber).getScore() + delta;
        scoreboard.getPlayer(currentPlayerNumber).setScore(delta);
        observablePlayerScoreList.set(currentPlayerNumber, Integer.toString(scoreboard.getPlayer(currentPlayerNumber).getScore()));
    }
}

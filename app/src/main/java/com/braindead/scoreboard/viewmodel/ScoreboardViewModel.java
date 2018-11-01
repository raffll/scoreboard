package com.braindead.scoreboard.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;

import com.braindead.scoreboard.model.Scoreboard;

public class ScoreboardViewModel extends ViewModel {

    private Scoreboard scoreboard;
    private int currentPlayerNumber;

    public ObservableArrayList<Boolean> observablePlayerVisibility;
    public ObservableArrayList<String> observablePlayerNameList;
    public ObservableArrayList<String> observablePlayerScoreList;
    public ObservableArrayList<Boolean> observablePlayerIsActiveList;

    private MutableLiveData<Integer> currentPlayerSettings = new MutableLiveData<>();

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
                observablePlayerVisibility.add(true);
            } else {
                observablePlayerVisibility.add(false);
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

    public boolean onLongClickedAtPlayer(int playerNumber) {
        currentPlayerNumber = playerNumber;
        updateObservablePlayerIsActiveList();
        currentPlayerSettings.setValue(currentPlayerNumber);
        return true;
    }

    public void onClickedScoreChange(int delta) {
        delta = scoreboard.getPlayer(currentPlayerNumber).getScore() + delta;
        scoreboard.getPlayer(currentPlayerNumber).setScore(delta);
        observablePlayerScoreList.set(currentPlayerNumber, Integer.toString(scoreboard.getPlayer(currentPlayerNumber).getScore()));
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

    public LiveData<Integer> getCurrentPlayerSettings() {
        return currentPlayerSettings;
    }
}
package com.braindead.scoreboard.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;

import com.braindead.scoreboard.model.Scoreboard;

public class ScoreboardViewModel extends ViewModel {

    private Scoreboard scoreboard;

    private int currentPlayerNumber;

    public ObservableArrayList<Boolean> observablePlayerVisibilityList;
    public ObservableArrayList<Boolean> observablePlayerIsActiveList;
    public ObservableArrayList<String> observablePlayerNameList;
    public ObservableArrayList<String> observablePlayerScoreList;
    public ObservableArrayList<Integer> observablePlayerColorList;

    private MutableLiveData<Boolean> playerNameChangingEvent = new MutableLiveData<>();
    private MutableLiveData<Boolean> playerColorChangingEvent = new MutableLiveData<>();

    public void init(int numberOfPlayers) {
        currentPlayerNumber = 0;
        scoreboard = new Scoreboard();
        initObservablePlayerVisibility(numberOfPlayers);
        initObservablePlayerIsActiveList();
        initObservablePlayerNameList();
        initObservablePlayerScoreList();
        initObservablePlayerColorList();
    }

    private void initObservablePlayerVisibility(int numberOfPlayers) {
        observablePlayerVisibilityList = new ObservableArrayList<>();
        for (int i = 0; i < Scoreboard.MAX_PLAYERS; i++) {
            if (i < numberOfPlayers) {
                observablePlayerVisibilityList.add(true);
            } else {
                observablePlayerVisibilityList.add(false);
            }
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

    private void initObservablePlayerColorList() {
        observablePlayerColorList = new ObservableArrayList<>();
        for (int i = 0; i < Scoreboard.MAX_PLAYERS; i++) {
            observablePlayerColorList.add(scoreboard.getPlayer(i).getColor());
        }
    }

    public void onActivatePlayer(int playerNumber) {
        currentPlayerNumber = playerNumber;
        updateObservablePlayerIsActiveList();
    }

    public boolean onActivatePlayerNameChanging(int playerNumber) {
        currentPlayerNumber = playerNumber;
        updateObservablePlayerIsActiveList();
        playerNameChangingEvent.setValue(true);
        return true;
    }

    public boolean onActivatePlayerColorChanging(int playerNumber) {
        currentPlayerNumber = playerNumber;
        updateObservablePlayerIsActiveList();
        playerColorChangingEvent.setValue(true);
        return true;
    }

    public void onChangeCurrentPlayerScore(int delta) {
        delta = scoreboard.getPlayer(currentPlayerNumber).getScore() + delta;
        scoreboard.getPlayer(currentPlayerNumber).setScore(delta);
        observablePlayerScoreList.set(currentPlayerNumber, Integer.toString(scoreboard.getPlayer(currentPlayerNumber).getScore()));
    }

    public void onChangeCurrentPlayerName(String playerName) {
        scoreboard.getPlayer(currentPlayerNumber).setName(playerName);
        observablePlayerNameList.set(currentPlayerNumber, scoreboard.getPlayer(currentPlayerNumber).getName());
    }

    public void onChangeCurrentPlayerColor(int playerColor) {
        scoreboard.getPlayer(currentPlayerNumber).setColor(playerColor);
        observablePlayerColorList.set(currentPlayerNumber, scoreboard.getPlayer(currentPlayerNumber).getColor());
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

    public int getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }

    public LiveData<Boolean> getPlayerNameChangingEvent() {
        return playerNameChangingEvent;
    }

    public void disablePlayerNameChangingEvent() {
        this.playerNameChangingEvent.setValue(false);
    }

    public LiveData<Boolean> getPlayerColorChangingEvent() {
        return playerColorChangingEvent;
    }

    public void disablePlayerColorChangingEvent() {
        this.playerColorChangingEvent.setValue(false);
    }
}
package com.braindead.scoreboard.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;

import com.braindead.scoreboard.model.Scoreboard;

public class ScoreboardViewModel extends ViewModel {

    public static final int MAX_NUMBER_OF_PLAYERS = 10;
    public static final int DEFAULT_NUMBER_OF_PLAYERS = 2;
    public static final int[] DEFAULT_COLORS = {
            0xff2ECC71,
            0xff3498DB,
            0xffE74C3C,
            0xffF4D03F,
            0xffB3B6B7,
            0xff17202A,
            0xffE67E22,
            0xff8E44AD,
            0xff1ABC9C,
            0xff703325
    };

    private Scoreboard scoreboard;

    private int numberOfPlayers;
    private int currentPlayerNumber;

    public ObservableArrayList<Boolean> observablePlayerVisibilityList;
    public ObservableArrayList<Boolean> observablePlayerIsActiveList;
    public ObservableArrayList<String> observablePlayerNameList;
    public ObservableArrayList<String> observablePlayerScoreList;
    public ObservableArrayList<Integer> observablePlayerColorList;

    private MutableLiveData<Boolean> playerActivateEvent = new MutableLiveData<>();
    private MutableLiveData<Boolean> playerNameChangingEvent = new MutableLiveData<>();
    private MutableLiveData<Boolean> playerColorChangingEvent = new MutableLiveData<>();

    public void init(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        currentPlayerNumber = 0;
        scoreboard = new Scoreboard(numberOfPlayers, 0, DEFAULT_COLORS);
        initObservablePlayerVisibility();
        initObservablePlayerIsActiveList();
        initObservablePlayerNameList();
        initObservablePlayerScoreList();
        initObservablePlayerColorList();
    }

    private void initObservablePlayerVisibility() {
        observablePlayerVisibilityList = new ObservableArrayList<>();
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            if (i < numberOfPlayers) {
                observablePlayerVisibilityList.add(true);
            } else {
                observablePlayerVisibilityList.add(false);
            }
        }
    }

    private void initObservablePlayerIsActiveList() {
        observablePlayerIsActiveList = new ObservableArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            if (i == currentPlayerNumber) {
                observablePlayerIsActiveList.add(true);
            } else {
                observablePlayerIsActiveList.add(false);
            }
        }
    }

    private void initObservablePlayerNameList() {
        observablePlayerNameList = new ObservableArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            observablePlayerNameList.add(scoreboard.getPlayer(i).getName());
        }
    }

    private void initObservablePlayerScoreList() {
        observablePlayerColorList = new ObservableArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            observablePlayerColorList.add(scoreboard.getPlayer(i).getColor());
        }
    }

    private void initObservablePlayerColorList() {
        observablePlayerScoreList = new ObservableArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            observablePlayerScoreList.add(Integer.toString(scoreboard.getPlayer(i).getScore()));
        }
    }

    private void updateObservablePlayerIsActiveList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            if (i == currentPlayerNumber) {
                observablePlayerIsActiveList.set(i, true);
            } else {
                observablePlayerIsActiveList.set(i, false);
            }
        }
    }

    public void onActivatePlayer(int playerNumber) {
        currentPlayerNumber = playerNumber;
        updateObservablePlayerIsActiveList();
        playerActivateEvent.setValue(true);
    }

    public boolean onActivatePlayerNameChanging(int playerNumber) {
        onActivatePlayer(playerNumber);
        playerNameChangingEvent.setValue(true);
        return true;
    }

    public boolean onActivatePlayerColorChanging(int playerNumber) {
        onActivatePlayer(playerNumber);
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

    public int getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }

    public String getCurrentPlayerName() {
        return observablePlayerNameList.get(currentPlayerNumber);
    }

    public int getCurrentPlayerColor() {
        return observablePlayerColorList.get(currentPlayerNumber);
    }

    public LiveData<Boolean> getPlayerActivateEvent() {
        return playerActivateEvent;
    }

    public void disablePlayerActivateEvent() {
        this.playerActivateEvent.setValue(false);
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
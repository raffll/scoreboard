package com.braindead.scoreboard.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.braindead.scoreboard.model.Player;
import com.braindead.scoreboard.model.Scoreboard;

import static java.lang.Math.abs;

public class ScoreboardViewModel extends ViewModel {

    public static final int MAX_NUMBER_OF_PLAYERS = 10;
    public static final int DEFAULT_NUMBER_OF_PLAYERS = 2;
    public static final int[] DEFAULT_COLORS = {
            0xff2ECC71,
            0xff3498DB,
            0xffE74C3C,
            0xffF4D03F,
            0xffB3B6B7,
            0xff333333,
            0xffE67E22,
            0xff8E44AD,
            0xff1ABC9C,
            0xff703325
    };

    private Scoreboard scoreboard;

    private int numberOfPlayers;
    private int currentPlayerNumber;
    private int currentDelta;

    public ObservableArrayList<Boolean> observablePlayerVisibilityList;
    public ObservableArrayList<Boolean> observablePlayerIsActiveList;
    public ObservableArrayList<String> observablePlayerNameList;
    public ObservableArrayList<String> observablePlayerScoreList;
    public ObservableArrayList<String> observablePlayerPartialScoreList;
    public ObservableArrayList<Integer> observablePlayerColorList;
    public ObservableField<String> observableCurrentDelta;
    public ObservableField<Integer> observableCurrentColor;

    private MutableLiveData<Boolean> playerActivateEvent = new MutableLiveData<>();
    private MutableLiveData<Boolean> playerNameChangingEvent = new MutableLiveData<>();
    private MutableLiveData<Boolean> playerColorChangingEvent = new MutableLiveData<>();

    public void init(int numberOfPlayers, int defaultScore, String sessionName) {
        this.numberOfPlayers = numberOfPlayers;
        currentPlayerNumber = 0;
        currentDelta = 0;
        scoreboard = new Scoreboard(numberOfPlayers, defaultScore, DEFAULT_COLORS, sessionName);
        initObservablePlayerVisibility();
        initObservablePlayerIsActiveList();
        initObservablePlayerNameList();
        initObservablePlayerScoreList();
        initObservablePlayerPartialScoreList();
        initObservablePlayerColorList();
        initObservableCurrentDelta();
        initObservableCurrentColor();
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
        observablePlayerScoreList = new ObservableArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            observablePlayerScoreList.add(Integer.toString(scoreboard.getPlayer(i).getScore()));
        }
    }

    private void initObservablePlayerPartialScoreList() {
        observablePlayerPartialScoreList = new ObservableArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            observablePlayerPartialScoreList.add("");
        }
    }

    private void initObservablePlayerColorList() {
        observablePlayerColorList = new ObservableArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            observablePlayerColorList.add(scoreboard.getPlayer(i).getColor());
        }
    }

    private void initObservableCurrentDelta() {
        observableCurrentDelta = new ObservableField<>();
        updateObservableCurrentDelta();
    }

    private void initObservableCurrentColor() {
        observableCurrentColor = new ObservableField<>();
        updateObservableCurrentColor();
    }

    public void onActivatePlayer(int playerNumber) {
        currentPlayerNumber = playerNumber;
        updateObservablePlayerIsActiveList();
        updateObservableCurrentDelta();
        updateObservableCurrentColor();
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

    public void onChangeCurrentDelta(int delta) {
        currentDelta += delta;
        updateObservableCurrentDelta();
    }

    public void onChangeCurrentPlayerName(String playerName) {
        getCurrentPlayer().setName(playerName);
        updateObservablePlayerNameList();
    }

    public void onChangeCurrentPlayerScore() {
        getCurrentPlayer().setScore(getCurrentPlayer().getScore() + currentDelta);
        updateObservablePlayerScoreList();
        updateObservablePlayerPartialScoreList();
        currentDelta = 0;
        updateObservableCurrentDelta();
    }

    public void onChangeCurrentPlayerColor(int playerColor) {
        getCurrentPlayer().setColor(playerColor);
        updateObservablePlayerColorList();
        updateObservableCurrentColor();
    }

    public void onResetSession() {
        scoreboard.resetScoreboard();
        updateObservablePlayerScoreList();
        currentDelta = 0;
        updateObservableCurrentDelta();
    }

    public void onSaveSession() {

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

    private void updateObservablePlayerNameList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            observablePlayerNameList.set(i, getPlayer(i).getName());
        }
    }

    private void updateObservablePlayerScoreList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            observablePlayerScoreList.set(i, Integer.toString(getPlayer(i).getScore()));
        }
    }

    private void updateObservablePlayerPartialScoreList() {
        observablePlayerPartialScoreList.set(currentPlayerNumber, observablePlayerPartialScoreList.get(currentPlayerNumber) + currentDelta + " / ");
    }

    private void updateObservablePlayerColorList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            observablePlayerColorList.set(i, getPlayer(i).getColor());
        }
    }

    private void updateObservableCurrentDelta() {
        if (currentDelta >= 0) {
            observableCurrentDelta.set("+ " + Integer.toString(currentDelta));
        } else {
            observableCurrentDelta.set("- " + Integer.toString(abs(currentDelta)));
        }
    }

    private void updateObservableCurrentColor() {
        observableCurrentColor.set(getCurrentPlayer().getColor());
    }

    private Player getPlayer(int i) {
        return scoreboard.getPlayer(i);
    }

    public Player getCurrentPlayer() {
        return scoreboard.getPlayer(currentPlayerNumber);
    }

    public LiveData<Boolean> getPlayerActivateEvent() {
        return playerActivateEvent;
    }

    public void disablePlayerActivateEvent() {
        playerActivateEvent.setValue(false);
    }

    public LiveData<Boolean> getPlayerNameChangingEvent() {
        return playerNameChangingEvent;
    }

    public void disablePlayerNameChangingEvent() {
        playerNameChangingEvent.setValue(false);
    }

    public LiveData<Boolean> getPlayerColorChangingEvent() {
        return playerColorChangingEvent;
    }

    public void disablePlayerColorChangingEvent() {
        playerColorChangingEvent.setValue(false);
    }
}
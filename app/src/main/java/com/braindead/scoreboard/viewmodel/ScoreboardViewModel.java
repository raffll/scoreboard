package com.braindead.scoreboard.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

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

    public ObservableField<Integer> currentPlayerNumber = new ObservableField<>();
    public ObservableArrayList<Boolean> observablePlayerVisibilityList = new ObservableArrayList<>();
    public ObservableArrayList<Boolean> observablePlayerIsActiveList = new ObservableArrayList<>();
    public ObservableArrayList<String> observablePlayerNameList = new ObservableArrayList<>();
    public ObservableArrayList<String> observablePlayerScoreList = new ObservableArrayList<>();
    public ObservableArrayList<String> observablePlayerPartialScoreList = new ObservableArrayList<>();
    public ObservableArrayList<Integer> observablePlayerColorList = new ObservableArrayList<>();
    public ObservableField<String> observableCurrentDelta = new ObservableField<>();

    private MutableLiveData<Boolean> playerSettingsEvent = new MutableLiveData<>();

    public void init(int numberOfPlayers, int defaultScore, String sessionName) {
        this.numberOfPlayers = numberOfPlayers;
        this.currentPlayerNumber.set(0);
        this.scoreboard = new Scoreboard(numberOfPlayers, defaultScore, DEFAULT_COLORS, sessionName);
        initObservablePlayerVisibility();
        initObservablePlayerIsActiveList();
        initObservablePlayerNameList();
        initObservablePlayerScoreList();
        initObservablePlayerPartialScoreList();
        initObservablePlayerColorList();
        initObservableCurrentDelta();
    }

    private void initObservablePlayerVisibility() {
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            if (i < numberOfPlayers) {
                observablePlayerVisibilityList.add(true);
            } else {
                observablePlayerVisibilityList.add(false);
            }
        }
    }

    private void initObservablePlayerIsActiveList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            if (i == currentPlayerNumber.get()) {
                observablePlayerIsActiveList.add(true);
            } else {
                observablePlayerIsActiveList.add(false);
            }
        }
    }

    private void initObservablePlayerNameList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            observablePlayerNameList.add(scoreboard.getPlayerList().get(i).getName());
        }
    }

    private void initObservablePlayerScoreList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            observablePlayerScoreList.add(Integer.toString(scoreboard.getPlayerList().get(i).getScore()));
        }
    }

    private void initObservablePlayerPartialScoreList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            observablePlayerPartialScoreList.add("");
        }
    }

    private void initObservablePlayerColorList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            observablePlayerColorList.add(scoreboard.getPlayerList().get(i).getColor());
        }
    }

    private void initObservableCurrentDelta() {
        updateObservableCurrentDelta();
    }

    public boolean onActivatePlayer(int playerNumber) {
        currentPlayerNumber.set(playerNumber);
        updateObservablePlayerIsActiveList();
        updateObservableCurrentDelta();
        return true;
    }

    public boolean onActivatePlayerSettings(int playerNumber) {
        onActivatePlayer(playerNumber);
        playerSettingsEvent.setValue(true);
        return true;
    }

    public void onChangeCurrentDelta(int delta) {
        scoreboard.setDelta(scoreboard.getDelta() + delta);
        updateObservableCurrentDelta();
    }

    public void onChangeCurrentPlayerSettings(String playerName, int playerColor) {
        scoreboard.getPlayerList().get(currentPlayerNumber.get()).setName(playerName);
        scoreboard.getPlayerList().get(currentPlayerNumber.get()).setColor(playerColor);
        updateObservablePlayerNameList();
        updateObservablePlayerColorList();
    }

    public void onChangeCurrentPlayerScore() {
        scoreboard.getPlayerList().get(currentPlayerNumber.get()).setScore(
                scoreboard.getPlayerList().get(currentPlayerNumber.get()).getScore() + scoreboard.getDelta());
        updateObservablePlayerScoreList();
        updateObservablePlayerPartialScoreList();
        scoreboard.setDelta(0);
        updateObservableCurrentDelta();
    }

    public void onResetSession() {
        scoreboard.resetScoreboard();
        updateObservablePlayerScoreList();
        scoreboard.setDelta(0);
        updateObservableCurrentDelta();
    }

    public void onSaveSession() {

    }

    private void updateObservablePlayerIsActiveList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            if (i == currentPlayerNumber.get()) {
                observablePlayerIsActiveList.set(i, true);
            } else {
                observablePlayerIsActiveList.set(i, false);
            }
        }
    }

    private void updateObservablePlayerNameList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            observablePlayerNameList.set(i, scoreboard.getPlayerList().get(i).getName());
        }
    }

    private void updateObservablePlayerScoreList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            observablePlayerScoreList.set(i, Integer.toString(scoreboard.getPlayerList().get(i).getScore()));
        }
    }

    private void updateObservablePlayerColorList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            observablePlayerColorList.set(i, scoreboard.getPlayerList().get(i).getColor());
        }
    }

    private void updateObservablePlayerPartialScoreList() {
        observablePlayerPartialScoreList.set(currentPlayerNumber.get(), observablePlayerPartialScoreList.get(currentPlayerNumber.get()) + scoreboard.getDelta() + " / ");
    }

    private void updateObservableCurrentDelta() {
        if (scoreboard.getDelta() >= 0) {
            observableCurrentDelta.set("+ " + Integer.toString(scoreboard.getDelta()));
        } else {
            observableCurrentDelta.set("- " + Integer.toString(abs(scoreboard.getDelta())));
        }
    }

    public LiveData<Boolean> getPlayerSettingsEvent() {
        return playerSettingsEvent;
    }

    public void disablePlayerSettingsEvent() {
        playerSettingsEvent.setValue(false);
    }
}
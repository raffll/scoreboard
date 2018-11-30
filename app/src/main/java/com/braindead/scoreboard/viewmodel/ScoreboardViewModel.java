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
    private int currentPlayerNumber;

    public ObservableArrayList<Boolean> visibilityList = new ObservableArrayList<>();
    public ObservableArrayList<Boolean> isCurrentList = new ObservableArrayList<>();
    public ObservableArrayList<String> nameList = new ObservableArrayList<>();
    public ObservableArrayList<String> scoreList = new ObservableArrayList<>();
    public ObservableArrayList<String> partialScoreList = new ObservableArrayList<>();
    public ObservableArrayList<Integer> colorList = new ObservableArrayList<>();
    public ObservableField<String> currentName = new ObservableField<>();
    public ObservableField<Integer> currentColor = new ObservableField<>();
    public ObservableField<String> currentDelta = new ObservableField<>();
    public ObservableField<String> sessionName = new ObservableField<>();

    private MutableLiveData<Boolean> playerSettingsEvent = new MutableLiveData<>();

    public void init(int numberOfPlayers, int defaultScore, String sessionName) {
        this.numberOfPlayers = numberOfPlayers;
        this.currentPlayerNumber = 0;
        this.scoreboard = new Scoreboard(numberOfPlayers, defaultScore, DEFAULT_COLORS, sessionName);
        initVisibilityList();
        initIsCurrentList();
        initNameList();
        initScoreList();
        initPartialScoreList();
        initColorList();
        initCurrentName();
        initCurrentColor();
        initCurrentDelta();
        initSessionName();
    }

    private void initVisibilityList() {
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            if (i < numberOfPlayers) {
                visibilityList.add(true);
            } else {
                visibilityList.add(false);
            }
        }
    }

    private void initIsCurrentList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            if (i == currentPlayerNumber) {
                isCurrentList.add(true);
            } else {
                isCurrentList.add(false);
            }
        }
    }

    private void initNameList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            nameList.add(scoreboard.getPlayerList().get(i).getName());
        }
    }

    private void initScoreList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            scoreList.add(Integer.toString(scoreboard.getPlayerList().get(i).getScore()));
        }
    }

    private void initPartialScoreList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            partialScoreList.add("");
        }
    }

    private void initColorList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            colorList.add(scoreboard.getPlayerList().get(i).getColor());
        }
    }

    private void initCurrentName() {
        updateCurrentName();
    }

    private void initCurrentColor() {
        updateCurrentColor();
    }

    private void initCurrentDelta() {
        updateCurrentDelta();
    }

    private void initSessionName() {
        updateSessionName();
    }

    private void updateIsCurrentList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            if (i == currentPlayerNumber) {
                isCurrentList.set(i, true);
            } else {
                isCurrentList.set(i, false);
            }
        }
    }

    private void updateNameList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            nameList.set(i, scoreboard.getPlayerList().get(i).getName());
        }
    }

    private void updateScoreList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            scoreList.set(i, Integer.toString(scoreboard.getPlayerList().get(i).getScore()));
        }
    }

    private void updatePartialScoreList() {
        String temp;
        for (int i = 0; i < numberOfPlayers; i++) {
            temp = "";
            for (int k = 0; k < scoreboard.getPlayerList().get(i).getPartialScoreList().size(); k++) {
                temp += scoreboard.getPlayerList().get(i).getPartialScoreList().get(k) + " / ";
                partialScoreList.set(i, temp);
            }
        }
    }

    private void updateColorList() {
        for (int i = 0; i < numberOfPlayers; i++) {
            colorList.set(i, scoreboard.getPlayerList().get(i).getColor());
        }
    }

    private void updateCurrentName() {
        currentName.set(scoreboard.getPlayerList().get(currentPlayerNumber).getName());
    }

    private void updateCurrentColor() {
        currentColor.set(scoreboard.getPlayerList().get(currentPlayerNumber).getColor());
    }

    private void updateCurrentDelta() {
        if (scoreboard.getDelta() >= 0) {
            currentDelta.set("+ " + Integer.toString(scoreboard.getDelta()));
        } else {
            currentDelta.set("- " + Integer.toString(abs(scoreboard.getDelta())));
        }
    }

    private void updateSessionName() {
        sessionName.set(scoreboard.getSessionName());
    }

    public boolean onActivatePlayer(int playerNumber) {
        currentPlayerNumber = playerNumber;
        updateIsCurrentList();
        updateCurrentName();
        updateCurrentColor();
        updateCurrentDelta();
        return true;
    }

    public boolean onActivatePlayerSettings(int playerNumber) {
        onActivatePlayer(playerNumber);
        playerSettingsEvent.setValue(true);
        return true;
    }

    public void onChangeCurrentDelta(int delta) {
        scoreboard.setDelta(scoreboard.getDelta() + delta);
        updateCurrentDelta();
    }

    public void onChangeCurrentPlayerSettings(String playerName, int playerColor) {
        scoreboard.getPlayerList().get(currentPlayerNumber).setName(playerName);
        scoreboard.getPlayerList().get(currentPlayerNumber).setColor(playerColor);
        updateNameList();
        updateColorList();
        updateCurrentName();
        updateCurrentColor();
    }

    public void onChangeCurrentPlayerScore() {
        scoreboard.getPlayerList().get(currentPlayerNumber).setScore(
                scoreboard.getPlayerList().get(currentPlayerNumber).getScore() + scoreboard.getDelta());
        scoreboard.getPlayerList().get(currentPlayerNumber).getPartialScoreList().add(scoreboard.getDelta());
        updateScoreList();
        updatePartialScoreList();
        scoreboard.setDelta(0);
        updateCurrentDelta();
    }

    public void onResetSession() {
        scoreboard.resetScoreboard();
        updateScoreList();
        scoreboard.setDelta(0);
        updateCurrentDelta();
    }

    public void onSaveSession() {

    }

    public LiveData<Boolean> getPlayerSettingsEvent() {
        return playerSettingsEvent;
    }

    public void disablePlayerSettingsEvent() {
        playerSettingsEvent.setValue(false);
    }
}
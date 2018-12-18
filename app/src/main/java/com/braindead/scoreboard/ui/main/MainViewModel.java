package com.braindead.scoreboard.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.braindead.scoreboard.model.Scoreboard;

import static java.lang.Math.abs;

public class MainViewModel extends ViewModel {

    public static final int MAX_NUMBER_OF_PLAYERS = 10;
    public static final int DEFAULT_NUMBER_OF_PLAYERS = 2;
    public static final int DEFAULT_SCORE = 0;
    public static final String DEFAULT_SESSION_NAME = "Scoreboard";
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

    public ObservableArrayList<Boolean> visibilityList;
    public ObservableArrayList<Boolean> isCurrentList;
    public ObservableArrayList<String> nameList;
    public ObservableArrayList<String> scoreList;
    public ObservableArrayList<String> partialScoreList;
    public ObservableArrayList<Integer> colorList;
    public ObservableField<String> currentName;
    public ObservableField<Integer> currentColor;
    public ObservableField<String> currentDelta;
    public ObservableField<String> sessionName;
    public ObservableField<Integer> fontSize;

    private MutableLiveData<Boolean> playerSettingsEvent = new MutableLiveData<>();

    public void init() {
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
        initFontSize(30);
    }

    private void initVisibilityList() {
        visibilityList = new ObservableArrayList<>();
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            visibilityList.add(false);
        }
    }

    private void initIsCurrentList() {
        isCurrentList = new ObservableArrayList<>();
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            isCurrentList.add(false);
        }
    }

    private void initNameList() {
        nameList = new ObservableArrayList<>();
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            nameList.add("");
        }
    }

    private void initScoreList() {
        scoreList = new ObservableArrayList<>();
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            scoreList.add("0");
        }
    }

    private void initPartialScoreList() {
        partialScoreList = new ObservableArrayList<>();
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            partialScoreList.add("");
        }
    }

    private void initColorList() {
        colorList = new ObservableArrayList<>();
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            colorList.add(0);
        }
    }

    private void initCurrentName() {
        currentName = new ObservableField<>();
        currentName.set("");
    }

    private void initCurrentColor() {
        currentColor = new ObservableField<>();
        currentColor.set(DEFAULT_COLORS[0]);
    }

    private void initCurrentDelta() {
        currentDelta = new ObservableField<>();
        currentDelta.set("");
    }

    private void initSessionName() {
        sessionName = new ObservableField<>();
        sessionName.set("");
    }

    private void initFontSize(int size) {
        fontSize = new ObservableField<>();
        fontSize.set(size);
    }

    private void updateVisibilityList() {
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            if (i < scoreboard.getNumberOfPlayers()) {
                visibilityList.set(i, true);
            } else {
                visibilityList.set(i, false);
            }
        }
    }

    private void updateIsCurrentList() {
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            if (i == scoreboard.getCurrentPlayerNumber()) {
                isCurrentList.set(i, true);
            } else {
                isCurrentList.set(i, false);
            }
        }
    }

    private void updateNameList() {
        for (int i = 0; i < scoreboard.getNumberOfPlayers(); i++) {
            nameList.set(i, scoreboard.getPlayer(i).getName());
        }
    }

    private void updateScoreList() {
        for (int i = 0; i < scoreboard.getNumberOfPlayers(); i++) {
            scoreList.set(i, Integer.toString(scoreboard.getPlayer(i).getScore()));
        }
    }

    private void updatePartialScoreList() {
        String temp;
        for (int i = 0; i < scoreboard.getNumberOfPlayers(); i++) {
            temp = "";
            if (scoreboard.getPlayer(i).getPartialScoreList().size() == 0) {
                partialScoreList.set(i, temp);
            } else {
                for (int k = 0; k < scoreboard.getPlayer(i).getPartialScoreList().size(); k++) {
                    temp += scoreboard.getPlayer(i).getPartialScoreList().get(k) + " / ";
                    partialScoreList.set(i, temp);
                }
            }
        }
    }

    private void updateColorList() {
        for (int i = 0; i < scoreboard.getNumberOfPlayers(); i++) {
            colorList.set(i, scoreboard.getPlayer(i).getColor());
        }
    }

    private void updateCurrentName() {
        currentName.set(scoreboard.getCurrentPlayer().getName());
    }

    private void updateCurrentColor() {
        currentColor.set(scoreboard.getCurrentPlayer().getColor());
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

    private void updateFontSize(int size) {
        fontSize.set(size);
    }

    public void onNewSession(int numberOfPlayers, int defaultScore, String sessionName) {
        scoreboard = new Scoreboard(numberOfPlayers, defaultScore, sessionName, DEFAULT_COLORS);
        updateVisibilityList();
        updateIsCurrentList();
        updateNameList();
        updateScoreList();
        updatePartialScoreList();
        updateColorList();
        updateCurrentName();
        updateCurrentColor();
        updateCurrentDelta();
        updateSessionName();
        updateFontSize(30);
    }

    public boolean onActivatePlayer(int playerNumber) {
        scoreboard.setCurrentPlayerNumber(playerNumber);
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
        scoreboard.getCurrentPlayer().setName(playerName);
        scoreboard.getCurrentPlayer().setColor(playerColor);
        updateNameList();
        updateColorList();
        updateCurrentName();
        updateCurrentColor();
    }

    public void onChangeCurrentPlayerScore() {
        if (scoreboard.getDelta() != 0) {
            scoreboard.getCurrentPlayer().setScore(
                    scoreboard.getCurrentPlayer().getScore() + scoreboard.getDelta());
            scoreboard.getCurrentPlayer().getPartialScoreList().add(scoreboard.getDelta());
            updateScoreList();
            updatePartialScoreList();
            scoreboard.setDelta(0);
            updateCurrentDelta();
        }
    }

    public void onResetSession() {
        scoreboard.resetScoreboard();
        updateScoreList();
        updatePartialScoreList();
        scoreboard.setDelta(0);
        updateCurrentDelta();
    }

    public void onSaveSession() {

    }

    public void onUndo() {
        scoreboard.getCurrentPlayer().setScore(
                scoreboard.getCurrentPlayer().getScore() - scoreboard.getCurrentPlayer().getLastPartialScore());
        scoreboard.getCurrentPlayer().removeLastPartialScore();
        updateScoreList();
        updatePartialScoreList();
    }

    public LiveData<Boolean> getPlayerSettingsEvent() {
        return playerSettingsEvent;
    }

    public void disablePlayerSettingsEvent() {
        this.playerSettingsEvent.setValue(false);
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
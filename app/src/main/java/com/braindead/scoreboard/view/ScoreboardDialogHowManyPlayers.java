package com.braindead.scoreboard.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.braindead.scoreboard.R;
import com.braindead.scoreboard.model.Scoreboard;

public class ScoreboardDialogHowManyPlayers extends DialogFragment {

    private View rootView;
    private ScoreboardActivity scoreboardActivity;

    private int numberOfPlayers;

    public static ScoreboardDialogHowManyPlayers newInstance(ScoreboardActivity activity) {
        ScoreboardDialogHowManyPlayers dialog = new ScoreboardDialogHowManyPlayers();
        dialog.scoreboardActivity = activity;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initParams();
        initViews();
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setCancelable(false)
                .setNegativeButton(R.string.cancel,  ((dialog, which) -> onCancelClicked()))
                .setPositiveButton(R.string.ok,   ((dialog, which) -> onDoneClicked()))
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

    private void initParams() {
        numberOfPlayers = Scoreboard.DEFAULT_NUMBER_OF_PLAYERS;
    }

    private void initViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_how_many_players, null, false);

        NumberPicker numberPicker = rootView.findViewById(R.id.dialog_how_many_players);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setValue(numberOfPlayers);
        numberPicker.setOnValueChangedListener(onValueChangeListener);
    }

    private NumberPicker.OnValueChangeListener onValueChangeListener =
            (picker, oldVal, newVal) -> {
                numberOfPlayers = newVal;
            };

    private void onDoneClicked() {
        scoreboardActivity.onNewSessionSet(numberOfPlayers);
        dismiss();
    }

    private void onCancelClicked() {
        dismiss();
    }
}
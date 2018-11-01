package com.braindead.scoreboard.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.braindead.scoreboard.R;

public class ScoreboardHowManyPlayersDialog extends DialogFragment {

    private View rootView;
    private ScoreboardActivity scoreboardActivity;

    private int numberOfPlayers;

    public static ScoreboardHowManyPlayersDialog newInstance(ScoreboardActivity activity) {
        ScoreboardHowManyPlayersDialog dialog = new ScoreboardHowManyPlayersDialog();
        dialog.scoreboardActivity = activity;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        numberOfPlayers = 2;
        initViews();

        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setCancelable(false)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.ok, null)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setOnShowListener(dialog -> {
            onDialogShow(alertDialog);
        });
        return alertDialog;
    }

    private void initViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.how_many_players_dialog, null, false);

        NumberPicker numberPicker = rootView.findViewById(R.id.dialog_how_many_players);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setValue(2);
        numberPicker.setOnValueChangedListener(onValueChangeListener);
    }

    private NumberPicker.OnValueChangeListener onValueChangeListener =
            (picker, oldVal, newVal) -> {
                numberOfPlayers = newVal;
            };

    private void onDialogShow(AlertDialog dialog) {
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(v -> {
            onDoneClicked();
        });

        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setOnClickListener(v -> {
            onCancelClicked();
        });
    }

    private void onDoneClicked() {
        scoreboardActivity.onNewSessionSet(numberOfPlayers);
        dismiss();
    }

    private void onCancelClicked() {
        dismiss();
    }
}

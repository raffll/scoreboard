package com.braindead.scoreboard.ui.scoreboard;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.braindead.scoreboard.R;
import com.braindead.scoreboard.ui.main.MainViewModel;

public class NewSessionDialog extends DialogFragment {

    private View rootView;
    private ScoreboardFragment scoreboardFragment;

    private int numberOfPlayers;
    private int defaultScore;
    private String sessionName;

    private EditText sessionNameView;

    public static NewSessionDialog newInstance(ScoreboardFragment scoreboardFragment,
                                               int numberOfPlayers,
                                               int defaultScore,
                                               String sessionName) {
        NewSessionDialog dialog = new NewSessionDialog();
        dialog.scoreboardFragment = scoreboardFragment;
        dialog.numberOfPlayers = numberOfPlayers;
        dialog.defaultScore = defaultScore;
        dialog.sessionName = sessionName;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initViews();
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setTitle("New Session Settings")
                .setCancelable(false)
                .setPositiveButton(R.string.ok, ((dialog, which) -> onDoneClicked()))
                .setNegativeButton(R.string.cancel, ((dialog, which) -> onCancelClicked()))
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

    private void initViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_new_session, null, false);

        NumberPicker numberOfPlayersView = rootView.findViewById(R.id.dialog_number_of_players);
        numberOfPlayersView.setMinValue(1);
        numberOfPlayersView.setMaxValue(MainViewModel.MAX_NUMBER_OF_PLAYERS);
        numberOfPlayersView.setValue(numberOfPlayers);
        numberOfPlayersView.setOnValueChangedListener(onNumberOfPlayersChangeListener);

        NumberPicker defaultScoreView = rootView.findViewById(R.id.dialog_default_score);
        defaultScoreView.setMinValue(0);
        defaultScoreView.setMaxValue(10);
        defaultScoreView.setValue(0);
        defaultScoreView.setOnValueChangedListener(onDefaultScoreChangeListener);

        NumberPicker.Formatter formatter = value -> {
            int temp = value * 10;
            return "" + temp;
        };
        defaultScoreView.setFormatter(formatter);

        sessionNameView = rootView.findViewById(R.id.dialog_session_name);
        sessionNameView.setText(sessionName);
        sessionNameView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        sessionNameView.setSelection(sessionNameView.getText().length());
        addTextWatchers();
    }

    private NumberPicker.OnValueChangeListener onNumberOfPlayersChangeListener =
            (picker, oldVal, newVal) -> {
                numberOfPlayers = newVal;
            };

    private NumberPicker.OnValueChangeListener onDefaultScoreChangeListener =
            (picker, oldVal, newVal) -> {
                defaultScore = newVal * 10;
            };

    private void addTextWatchers() {
        sessionNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                sessionName = s.toString();
            }
        });
    }

    private void onDoneClicked() {
        scoreboardFragment.onNewSessionSet(numberOfPlayers, defaultScore, sessionName);
        dismiss();
    }

    private void onCancelClicked() {
        dismiss();
    }
}
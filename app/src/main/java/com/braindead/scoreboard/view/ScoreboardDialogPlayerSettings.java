package com.braindead.scoreboard.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import com.braindead.scoreboard.R;

public class ScoreboardDialogPlayerSettings extends DialogFragment {

    private View rootView;
    private ScoreboardActivity scoreboardActivity;

    private String playerName;

    private TextInputEditText nameEditText;

    public static ScoreboardDialogPlayerSettings newInstance(ScoreboardActivity activity) {
        ScoreboardDialogPlayerSettings dialog = new ScoreboardDialogPlayerSettings();
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
                .setNegativeButton(R.string.cancel, ((dialog, which) -> onCancelClicked()))
                .setPositiveButton(R.string.ok, ((dialog, which) -> onDoneClicked()))
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

    private void initParams() {
        //playerName = scoreboardActivity.getCurrentPlayerName();
    }

    private void initViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_player_settings, null, false);

        nameEditText = rootView.findViewById(R.id.name_edit_text);
        addTextWatchers();
    }

    private void onDoneClicked() {
        scoreboardActivity.onPlayerSettingSet(playerName);
        dismiss();
    }

    private void onCancelClicked() {
        dismiss();
    }

    private void addTextWatchers() {
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                playerName = s.toString();
            }
        });
    }
}


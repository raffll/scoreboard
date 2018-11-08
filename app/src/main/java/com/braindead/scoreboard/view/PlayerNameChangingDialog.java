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

public class PlayerNameChangingDialog extends DialogFragment {

    private View rootView;
    private ScoreboardActivity scoreboardActivity;

    private String playerName;

    private TextInputEditText nameEditText;

    public static PlayerNameChangingDialog newInstance(ScoreboardActivity activity) {
        PlayerNameChangingDialog dialog = new PlayerNameChangingDialog();
        dialog.scoreboardActivity = activity;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initViews();
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setTitle("Type new player name")
                .setCancelable(false)
                .setPositiveButton(R.string.ok, ((dialog, which) -> onDoneClicked()))
                .setNegativeButton(R.string.cancel, ((dialog, which) -> onCancelClicked()))
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

    public void setText(String playerName) {
        this.playerName = playerName;
    }

    private void initViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_player_name_changing, null, false);

        nameEditText = rootView.findViewById(R.id.name_edit_text);
        nameEditText.setText(playerName);
        addTextWatchers();
    }

    private void onDoneClicked() {
        scoreboardActivity.onPlayerNameSet(playerName);
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
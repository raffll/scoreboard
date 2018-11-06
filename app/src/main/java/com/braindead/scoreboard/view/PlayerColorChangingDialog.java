package com.braindead.scoreboard.view;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.braindead.scoreboard.R;

public class PlayerColorChangingDialog extends DialogFragment {

    private View rootView;
    private ScoreboardActivity scoreboardActivity;

    public static PlayerColorChangingDialog newInstance(ScoreboardActivity activity) {
        PlayerColorChangingDialog dialog = new PlayerColorChangingDialog();
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

    }

    private void initViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_player_color_changing, null, false);
    }

    private void onDoneClicked() {
        scoreboardActivity.onPlayerColorSet(Color.BLACK); // TODO
        dismiss();
    }

    private void onCancelClicked() {
        dismiss();
    }
}
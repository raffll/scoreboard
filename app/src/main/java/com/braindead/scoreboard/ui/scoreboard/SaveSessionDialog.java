package com.braindead.scoreboard.ui.scoreboard;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.braindead.scoreboard.R;

public class SaveSessionDialog extends DialogFragment {

    private View rootView;
    private ScoreboardFragment scoreboardFragment;

    public static SaveSessionDialog newInstance(ScoreboardFragment scoreboardFragment) {
        SaveSessionDialog dialog = new SaveSessionDialog();
        dialog.scoreboardFragment = scoreboardFragment;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Save session?")
                .setCancelable(false)
                .setPositiveButton(R.string.ok, ((dialog, which) -> onDoneClicked()))
                .setNegativeButton(R.string.cancel, ((dialog, which) -> onCancelClicked()))
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

    private void onDoneClicked() {
        scoreboardFragment.onSaveSessionSet();
        dismiss();
    }

    private void onCancelClicked() {
        dismiss();
    }
}
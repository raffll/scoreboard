package com.braindead.scoreboard.ui.scoreboard;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.braindead.scoreboard.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ResetSessionDialog extends DialogFragment {

    private View rootView;
    private ScoreboardFragment scoreboardFragment;

    public static ResetSessionDialog newInstance(ScoreboardFragment scoreboardFragment) {
        ResetSessionDialog dialog = new ResetSessionDialog();
        dialog.scoreboardFragment = scoreboardFragment;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Reset session?")
                .setCancelable(false)
                .setPositiveButton(R.string.ok, ((dialog, which) -> onDoneClicked()))
                .setNegativeButton(R.string.cancel, ((dialog, which) -> onCancelClicked()))
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

    private void onDoneClicked() {
        scoreboardFragment.onResetSessionSet();
        dismiss();
    }

    private void onCancelClicked() {
        dismiss();
    }
}
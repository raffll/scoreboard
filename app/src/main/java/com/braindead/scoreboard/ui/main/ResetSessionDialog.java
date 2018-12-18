package com.braindead.scoreboard.ui.main;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.braindead.scoreboard.R;

public class ResetSessionDialog extends DialogFragment {

    private View rootView;
    private MainActivity mainActivity;

    public static ResetSessionDialog newInstance(MainActivity activity) {
        ResetSessionDialog dialog = new ResetSessionDialog();
        dialog.mainActivity = activity;
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
        mainActivity.onResetSessionSet();
        dismiss();
    }

    private void onCancelClicked() {
        dismiss();
    }
}
package com.braindead.scoreboard.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.braindead.scoreboard.R;

public class ScoreboardPlayerSettingsDialog extends DialogFragment {

    private View rootView;
    private ScoreboardActivity scoreboardActivity;

    private TextInputLayout nameLayout;
    private TextInputEditText nameEditText;
    private String name;

    public static ScoreboardPlayerSettingsDialog newInstance(ScoreboardActivity activity) {
        ScoreboardPlayerSettingsDialog dialog = new ScoreboardPlayerSettingsDialog();
        dialog.scoreboardActivity = activity;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
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
                .inflate(R.layout.player_settings_dialog, null, false);

        nameLayout = rootView.findViewById(R.id.name_layout);
        nameEditText = rootView.findViewById(R.id.name_edit_text);
        //addTextWatchers();
    }

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
        dismiss();
    }

    private void onCancelClicked() {
        dismiss();
    }
}


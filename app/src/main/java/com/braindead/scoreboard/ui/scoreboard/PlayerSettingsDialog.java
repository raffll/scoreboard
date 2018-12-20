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

import com.braindead.scoreboard.R;
import com.braindead.scoreboard.utilities.AppConstants;
import com.thebluealliance.spectrum.SpectrumPalette;

public class PlayerSettingsDialog extends DialogFragment {

    private View rootView;
    private ScoreboardFragment scoreboardFragment;

    private String playerName;
    private int playerColor;

    private EditText playerNameView;

    public static PlayerSettingsDialog newInstance(ScoreboardFragment scoreboardFragment,
                                                   String playerName,
                                                   int playerColor) {
        PlayerSettingsDialog dialog = new PlayerSettingsDialog();
        dialog.scoreboardFragment = scoreboardFragment;
        dialog.playerName = playerName;
        dialog.playerColor = playerColor;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initViews();
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setTitle("Player Settings")
                .setCancelable(false)
                .setPositiveButton(R.string.ok, ((dialog, which) -> onDoneClicked()))
                .setNegativeButton(R.string.cancel, ((dialog, which) -> onCancelClicked()))
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

    private void initViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_player_settings, null, false);

        playerNameView = rootView.findViewById(R.id.dialog_player_name);
        playerNameView.setText(playerName);
        playerNameView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        playerNameView.setSelection(playerNameView.getText().length());
        addTextWatchers();

        SpectrumPalette spectrumPalette = rootView.findViewById(R.id.dialog_color_palette);
        spectrumPalette.setColors(AppConstants.DEFAULT_COLORS);
        spectrumPalette.setSelectedColor(playerColor);
        spectrumPalette.setOnColorSelectedListener((color) -> playerColor = color);
    }

    private void onDoneClicked() {
        scoreboardFragment.onPlayerSettingsSet(playerName, playerColor);
        dismiss();
    }

    private void onCancelClicked() {
        dismiss();
    }

    private void addTextWatchers() {
        playerNameView.addTextChangedListener(new TextWatcher() {
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
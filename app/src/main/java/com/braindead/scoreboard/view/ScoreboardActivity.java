package com.braindead.scoreboard.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.braindead.scoreboard.R;
import com.braindead.scoreboard.databinding.ActivityScoreboardBinding;
import com.braindead.scoreboard.viewmodel.ScoreboardViewModel;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.util.Objects;

public class ScoreboardActivity extends AppCompatActivity {

    private ScoreboardViewModel scoreboardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startNewSessionDialog();
    }

    private void startNewSessionDialog() {
        NewSessionDialog dialog = NewSessionDialog.newInstance(this);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "TAG");
    }

    public void onNewSessionSet(int numberOfPlayers, int defaultScore, String sessionName) {
        initDataBinding(numberOfPlayers, defaultScore, sessionName);
        setActionBarColor(ScoreboardViewModel.DEFAULT_COLORS[0]);
    }

    private void setActionBarColor(int playerColor) {
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(playerColor));
    }

    private void initDataBinding(int numberOfPlayers, int defaultScore, String sessionName) {
        ActivityScoreboardBinding activityScoreboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_scoreboard);
        scoreboardViewModel = ViewModelProviders.of(this).get(ScoreboardViewModel.class);
        scoreboardViewModel.init(numberOfPlayers, defaultScore, sessionName);
        activityScoreboardBinding.setScoreboardViewModel(scoreboardViewModel);

        setUpOnPlayerActivateEventListener();
        setUpOnPlayerNameChangingEventListener();
        setUpOnPlayerColorChangingEventListener();
    }

    private void setUpOnPlayerActivateEventListener() {
        scoreboardViewModel.getPlayerActivateEvent().observe(this, this::onPlayerActivateEventTriggered);
    }

    private void setUpOnPlayerNameChangingEventListener() {
        scoreboardViewModel.getPlayerNameChangingEvent().observe(this, this::onPlayerNameChangingEventTriggered);
    }

    private void setUpOnPlayerColorChangingEventListener() {
        scoreboardViewModel.getPlayerColorChangingEvent().observe(this, this::onPlayerColorChangingEventTriggered);
    }

    private void onPlayerActivateEventTriggered(Boolean playerActivateEvent) {
        if (playerActivateEvent) {
            setActionBarColor(scoreboardViewModel.getCurrentPlayer().getColor());
            scoreboardViewModel.disablePlayerActivateEvent();
        }
    }

    private void onPlayerNameChangingEventTriggered(Boolean playerNameChangingEvent) {
        if (playerNameChangingEvent) {
            startNameChangingDialog();
            scoreboardViewModel.disablePlayerNameChangingEvent();
        }
    }

    private void onPlayerColorChangingEventTriggered(Boolean playerColorChangingEvent) {
        if (playerColorChangingEvent) {
            startColorChangingDialog();
            scoreboardViewModel.disablePlayerColorChangingEvent();
        }
    }

    private void startNameChangingDialog() {
        PlayerNameDialog dialog = PlayerNameDialog.newInstance(this);
        dialog.setText(scoreboardViewModel.getCurrentPlayer().getName());
        dialog.show(getSupportFragmentManager(), "TAG");
    }

    private void startColorChangingDialog() {
        SpectrumDialog.Builder dialog = new SpectrumDialog.Builder(this);
        dialog.setColors(ScoreboardViewModel.DEFAULT_COLORS);
        dialog.setSelectedColor(scoreboardViewModel.getCurrentPlayer().getColor());
        dialog.setPositiveButtonText(R.string.ok);
        dialog.setNegativeButtonText(R.string.cancel);
        dialog.setDismissOnColorSelected(false);
        dialog.setOnColorSelectedListener((positiveResult, color) -> {
            if (positiveResult) {
                onPlayerColorSet(color);
            }
        });
        dialog.build().show(getSupportFragmentManager(), "TAG");
    }

    public void onPlayerNameSet(String playerName) {
        scoreboardViewModel.onChangeCurrentPlayerName(playerName);
    }

    public void onPlayerColorSet(int playerColor) {
        scoreboardViewModel.onChangeCurrentPlayerColor(playerColor);
        setActionBarColor(playerColor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_session:
                startNewSessionDialog();
                return true;
            case R.id.menu_reset_session:
                startResetSessionDialog();
                return true;
            case R.id.menu_save_session:
                startSaveSessionDialog();
                return true;
            case R.id.menu_player_name:
                startNameChangingDialog();
                return true;
            case R.id.menu_player_color:
                startColorChangingDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startResetSessionDialog() {
        ResetSessionDialog dialog = ResetSessionDialog.newInstance(this);
        dialog.show(getSupportFragmentManager(), "TAG");
    }

    private void startSaveSessionDialog() {
        SaveSessionDialog dialog = SaveSessionDialog.newInstance(this);
        dialog.show(getSupportFragmentManager(), "TAG");
    }

    public void onResetSessionSet() {
        scoreboardViewModel.onResetSession();
    }

    public void onSaveSessionSet() {
        scoreboardViewModel.onSaveSession();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
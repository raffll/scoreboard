package com.braindead.scoreboard.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.braindead.scoreboard.R;
import com.braindead.scoreboard.databinding.ActivityScoreboardBinding;
import com.braindead.scoreboard.viewmodel.ScoreboardViewModel;

public class ScoreboardActivity extends AppCompatActivity {

    ScoreboardViewModel scoreboardViewModel;

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
    }

    private void initDataBinding(int numberOfPlayers, int defaultScore, String sessionName) {
        ActivityScoreboardBinding activityScoreboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_scoreboard);
        scoreboardViewModel = ViewModelProviders.of(this).get(ScoreboardViewModel.class);
        scoreboardViewModel.init(numberOfPlayers, defaultScore, sessionName);
        activityScoreboardBinding.setScoreboardViewModel(scoreboardViewModel);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        setUpOnPlayerActivateEventListener();
        setUpOnPlayerSettingsEventListener();
    }

    private void setUpOnPlayerActivateEventListener() {
        scoreboardViewModel.getPlayerActivateEvent().observe(this, this::onPlayerActivateEventTriggered);
    }

    private void setUpOnPlayerSettingsEventListener() {
        scoreboardViewModel.getPlayerSettingsEvent().observe(this, this::onPlayerSettingsEventTriggered);
    }

    private void onPlayerActivateEventTriggered(Boolean playerActivateEvent) {
        if (playerActivateEvent) {
            //setActionBarColor(scoreboardViewModel.getCurrentPlayer().getColor());
            scoreboardViewModel.disablePlayerActivateEvent();
        }
    }

    private void onPlayerSettingsEventTriggered(Boolean playerSettingsEvent) {
        if (playerSettingsEvent) {
            startPlayerSettingsDialog();
            scoreboardViewModel.disablePlayerSettingsEvent();
        }
    }

    private void startPlayerSettingsDialog() {
        PlayerSettingsDialog dialog = PlayerSettingsDialog.newInstance(this);
        dialog.show(getSupportFragmentManager(), "TAG");
    }

    public void onPlayerSettingsSet(String playerName, int playerColor) {
        scoreboardViewModel.onChangeCurrentPlayerSettings(playerName, playerColor);
        //setActionBarColor(playerColor);
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
                startPlayerSettingsDialog();
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
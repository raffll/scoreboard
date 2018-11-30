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
        createNewSessionDialog();
    }

    private void createNewSessionDialog() {
        NewSessionDialog dialog = NewSessionDialog.newInstance(this,
                ScoreboardViewModel.DEFAULT_NUMBER_OF_PLAYERS,
                0,
                "Scoreboard");
        dialog.show(getSupportFragmentManager(), "TAG");
    }

    public void onNewSessionSet(int numberOfPlayers, int defaultScore, String sessionName) {
        initDataBinding(numberOfPlayers, defaultScore, sessionName);
    }

    private void initDataBinding(int numberOfPlayers, int defaultScore, String sessionName) {
        ActivityScoreboardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_scoreboard);
        scoreboardViewModel = ViewModelProviders.of(this).get(ScoreboardViewModel.class);
        scoreboardViewModel.init(numberOfPlayers, defaultScore, sessionName);
        binding.setScoreboardViewModel(scoreboardViewModel);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        setUpOnPlayerSettingsEventListener();
    }

    private void setUpOnPlayerSettingsEventListener() {
        scoreboardViewModel.getPlayerSettingsEvent().observe(this, this::onPlayerSettingsEventTriggered);
    }

    private void onPlayerSettingsEventTriggered(Boolean playerSettingsEvent) {
        if (playerSettingsEvent) {
            createPlayerSettingsDialog();
            scoreboardViewModel.disablePlayerSettingsEvent();
        }
    }

    private void createPlayerSettingsDialog() {
        PlayerSettingsDialog dialog = PlayerSettingsDialog.newInstance(this,
                scoreboardViewModel.currentName.get(),
                scoreboardViewModel.currentColor.get());
        dialog.show(getSupportFragmentManager(), "TAG");
    }

    public void onPlayerSettingsSet(String playerName, int playerColor) {
        scoreboardViewModel.onChangeCurrentPlayerSettings(playerName, playerColor);
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
                createNewSessionDialog();
                return true;
            case R.id.menu_reset_session:
                createResetSessionDialog();
                return true;
            case R.id.menu_save_session:
                createSaveSessionDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createResetSessionDialog() {
        ResetSessionDialog dialog = ResetSessionDialog.newInstance(this);
        dialog.show(getSupportFragmentManager(), "TAG");
    }

    public void onResetSessionSet() {
        scoreboardViewModel.onResetSession();
    }

    private void createSaveSessionDialog() {
        SaveSessionDialog dialog = SaveSessionDialog.newInstance(this);
        dialog.show(getSupportFragmentManager(), "TAG");
    }

    public void onSaveSessionSet() {
        scoreboardViewModel.onSaveSession();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
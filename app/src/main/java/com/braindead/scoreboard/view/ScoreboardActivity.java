package com.braindead.scoreboard.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.braindead.scoreboard.R;
import com.braindead.scoreboard.databinding.ActivityScoreboardBinding;
import com.braindead.scoreboard.viewmodel.ScoreboardViewModel;

public class ScoreboardActivity extends AppCompatActivity {

    private static final String TAG_HOW_MANY_PLAYERS = "TAG_HOW_MANY_PLAYERS";
    private static final String TAG_PLAYER_SETTINGS = "TAG_PLAYER_SETTINGS";

    private ScoreboardViewModel scoreboardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        promptForNewSession();
    }

    private void promptForNewSession() {
        ScoreboardDialogHowManyPlayers dialog = ScoreboardDialogHowManyPlayers.newInstance(this);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), TAG_HOW_MANY_PLAYERS);
    }

    public void onNewSessionSet(int numberOfPlayers) {
        initDataBinding(numberOfPlayers);
    }

    private void initDataBinding(int numberOfPlayers) {
        ActivityScoreboardBinding activityScoreboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_scoreboard);
        scoreboardViewModel = ViewModelProviders.of(this).get(ScoreboardViewModel.class);
        scoreboardViewModel.init(numberOfPlayers);
        activityScoreboardBinding.setScoreboardViewModel(scoreboardViewModel);
        setUpOnPlayerSettingsEventListener();
    }

    private void setUpOnPlayerSettingsEventListener() {
        scoreboardViewModel.getPlayerSettingsEvent().observe(this, this::onPlayerSettingsEventTriggered);
    }

    private void onPlayerSettingsEventTriggered(Boolean playerSettingsEvent) {
        if(playerSettingsEvent) {
            ScoreboardDialogPlayerSettings dialog = ScoreboardDialogPlayerSettings.newInstance(this);
            dialog.setCancelable(false);
            dialog.show(getSupportFragmentManager(), TAG_PLAYER_SETTINGS);
            scoreboardViewModel.disablePlayerSettingsEvent();
        }
    }

    public void onPlayerSettingSet(String playerName) {
        scoreboardViewModel.onChangeCurrentPlayerName(playerName);
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
                promptForNewSession();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
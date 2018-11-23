package com.braindead.scoreboard.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.braindead.scoreboard.R;
import com.braindead.scoreboard.databinding.ActivityScoreboardBinding;
import com.braindead.scoreboard.viewmodel.ScoreboardViewModel;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardActivity extends AppCompatActivity {

    private ScoreboardViewModel scoreboardViewModel;

    List<TextView> playerColorTextViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        promptForNewSession();
    }

    private void promptForNewSession() {
        NumberOfPlayersDialog dialog = NumberOfPlayersDialog.newInstance(this);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "TAG");
    }

    public void onNewSessionSet(int numberOfPlayers) {
        initDataBinding(numberOfPlayers);
        initDynamicViews(numberOfPlayers);
    }

    private void initDataBinding(int numberOfPlayers) {
        ActivityScoreboardBinding activityScoreboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_scoreboard);
        scoreboardViewModel = ViewModelProviders.of(this).get(ScoreboardViewModel.class);
        scoreboardViewModel.init(numberOfPlayers);
        activityScoreboardBinding.setScoreboardViewModel(scoreboardViewModel);

        setUpOnPlayerNameChangingEventListener();
        setUpOnPlayerColorChangingEventListener();
    }

    private void initDynamicViews(int numberOfPlayers) {
        playerColorTextViewList = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            String playerColorTextView = "player_" + i + "_color";
            int playerColorTextViewID = getResources().getIdentifier(playerColorTextView, "id", getPackageName());
            playerColorTextViewList.add((TextView) findViewById(playerColorTextViewID));
            setDynamicViewPlayerColor(i, scoreboardViewModel.observablePlayerColorList.get(i));
        }
    }

    private void setUpOnPlayerNameChangingEventListener() {
        scoreboardViewModel.getPlayerNameChangingEvent().observe(this, this::onPlayerNameChangingEventTriggered);
    }

    private void setUpOnPlayerColorChangingEventListener() {
        scoreboardViewModel.getPlayerColorChangingEvent().observe(this, this::onPlayerColorChangingEventTriggered);
    }

    private void onPlayerNameChangingEventTriggered(Boolean playerNameChangingEvent) {
        if (playerNameChangingEvent) {
            PlayerNameChangingDialog dialog = PlayerNameChangingDialog.newInstance(this);
            dialog.setText(scoreboardViewModel.getCurrentPlayerName());
            dialog.setCancelable(false);
            dialog.show(getSupportFragmentManager(), "TAG");
            scoreboardViewModel.disablePlayerNameChangingEvent();
        }
    }

    private void onPlayerColorChangingEventTriggered(Boolean playerColorChangingEvent) {
        if (playerColorChangingEvent) {
            SpectrumDialog.Builder spectrumDialog = new SpectrumDialog.Builder(this);
            spectrumDialog.setColors(ScoreboardViewModel.DEFAULT_COLORS);
            spectrumDialog.setSelectedColor(scoreboardViewModel.getCurrentPlayerColor());
            spectrumDialog.setPositiveButtonText(R.string.ok);
            spectrumDialog.setNegativeButtonText(R.string.cancel);
            spectrumDialog.setDismissOnColorSelected(false);
            spectrumDialog.setOnColorSelectedListener((positiveResult, color) -> {
                if (positiveResult) {
                    onPlayerColorSet(color);
                }
            });
            spectrumDialog.build().show(getSupportFragmentManager(), "TAG");
            scoreboardViewModel.disablePlayerColorChangingEvent();
        }
    }

    public void onPlayerNameSet(String playerName) {
        scoreboardViewModel.onChangeCurrentPlayerName(playerName);
    }

    public void onPlayerColorSet(int playerColor) {
        scoreboardViewModel.onChangeCurrentPlayerColor(playerColor);
        setDynamicViewPlayerColor(scoreboardViewModel.getCurrentPlayerNumber(), playerColor);
    }

    private void setDynamicViewPlayerColor(int playerNumber, int playerColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(playerColor);
        playerColorTextViewList.get(playerNumber).setBackground(shape);
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
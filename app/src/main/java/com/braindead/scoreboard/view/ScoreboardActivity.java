package com.braindead.scoreboard.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
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
import java.util.Objects;

public class ScoreboardActivity extends AppCompatActivity {

    private ScoreboardViewModel scoreboardViewModel;

    List<TextView> playerColorTextViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startNewSessionDialog();
    }

    private void initDataBinding(int numberOfPlayers) {
        ActivityScoreboardBinding activityScoreboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_scoreboard);
        scoreboardViewModel = ViewModelProviders.of(this).get(ScoreboardViewModel.class);
        scoreboardViewModel.init(numberOfPlayers);
        activityScoreboardBinding.setScoreboardViewModel(scoreboardViewModel);

        setUpOnPlayerActivateEventListener();
        setUpOnPlayerNameChangingEventListener();
        setUpOnPlayerColorChangingEventListener();
    }

    private void initDynamicViews(int numberOfPlayers) {
        playerColorTextViewList = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            String playerColorTextView = "player_" + i + "_color";
            int playerColorTextViewID = getResources().getIdentifier(playerColorTextView, "id", getPackageName());
            playerColorTextViewList.add((TextView) findViewById(playerColorTextViewID));
            setDynamicViewPlayerColor(i, ScoreboardViewModel.DEFAULT_COLORS[i]);
            setActionBarColor(ScoreboardViewModel.DEFAULT_COLORS[0]);
        }
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

    private void startNewSessionDialog() {
        NewSessionDialog dialog = NewSessionDialog.newInstance(this);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "TAG");
    }

    private void startNameChangingDialog() {
        PlayerNameDialog dialog = PlayerNameDialog.newInstance(this);
        dialog.setText(scoreboardViewModel.getCurrentPlayerName());
        dialog.show(getSupportFragmentManager(), "TAG");
    }

    private void startColorChangingDialog() {
        SpectrumDialog.Builder dialog = new SpectrumDialog.Builder(this);
        dialog.setColors(ScoreboardViewModel.DEFAULT_COLORS);
        dialog.setSelectedColor(scoreboardViewModel.getCurrentPlayerColor());
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

    private void onPlayerActivateEventTriggered(Boolean playerActivateEvent) {
        if (playerActivateEvent) {
            setActionBarColor(scoreboardViewModel.getCurrentPlayerColor());
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

    public void onNewSessionSet(int numberOfPlayers) {
        initDataBinding(numberOfPlayers);
        initDynamicViews(numberOfPlayers);
    }

    public void onPlayerNameSet(String playerName) {
        scoreboardViewModel.onChangeCurrentPlayerName(playerName);
    }

    public void onPlayerColorSet(int playerColor) {
        scoreboardViewModel.onChangeCurrentPlayerColor(playerColor);
        setDynamicViewPlayerColor(scoreboardViewModel.getCurrentPlayerNumber(), playerColor);
        setActionBarColor(playerColor);
    }

    private void setDynamicViewPlayerColor(int playerNumber, int playerColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(playerColor);
        playerColorTextViewList.get(playerNumber).setBackground(shape);
    }

    private void setActionBarColor(int playerColor) {
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(playerColor));
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

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
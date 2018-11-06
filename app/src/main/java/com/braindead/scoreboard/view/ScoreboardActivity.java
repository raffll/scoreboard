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
import com.braindead.scoreboard.utilities.DefaultColors;
import com.braindead.scoreboard.viewmodel.ScoreboardViewModel;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardActivity extends AppCompatActivity {

    private static final String TAG_HOW_MANY_PLAYERS = "TAG_HOW_MANY_PLAYERS";
    private static final String TAG_PLAYER_SETTINGS = "TAG_PLAYER_SETTINGS";

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
        dialog.show(getSupportFragmentManager(), TAG_HOW_MANY_PLAYERS);
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

            GradientDrawable shape = new GradientDrawable();
            shape.setShape(GradientDrawable.OVAL);
            shape.setColor(DefaultColors.COLORS[i]);
            playerColorTextViewList.get(i).setBackground(shape);
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
            dialog.setCancelable(false);
            dialog.show(getSupportFragmentManager(), TAG_PLAYER_SETTINGS);
            scoreboardViewModel.disablePlayerNameChangingEvent();
        }
    }

    private void onPlayerColorChangingEventTriggered(Boolean playerColorChangingEvent) {
        if (playerColorChangingEvent) {
            PlayerColorChangingDialog dialog = PlayerColorChangingDialog.newInstance(this);
            dialog.setCancelable(false);
            dialog.show(getSupportFragmentManager(), TAG_PLAYER_SETTINGS);
            scoreboardViewModel.disablePlayerColorChangingEvent();
        }
    }

    public void onPlayerNameSet(String playerName) {
        scoreboardViewModel.onChangeCurrentPlayerName(playerName);
    }

    public void onPlayerColorSet(int playerColor) {
        scoreboardViewModel.onChangeCurrentPlayerColor(playerColor);

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(playerColor);
        playerColorTextViewList.get(scoreboardViewModel.getCurrentPlayerNumber()).setBackground(shape);
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
package com.braindead.scoreboard.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.braindead.scoreboard.R;
import com.braindead.scoreboard.databinding.ActivityScoreboardBinding;
import com.braindead.scoreboard.utilities.DefaultColors;
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
            setPlayerColor(i, DefaultColors.COLOR[i]);
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
            dialog.show(getSupportFragmentManager(), "TAG");
            scoreboardViewModel.disablePlayerNameChangingEvent();
        }
    }

    private void onPlayerColorChangingEventTriggered(Boolean playerColorChangingEvent) {
        if (playerColorChangingEvent) {

            SpectrumDialog.Builder spectrumDialog = new SpectrumDialog.Builder(this);
            spectrumDialog.setColors(R.array.rainbow);
            //int color = some_color_constant;
            //spectrumDialog.setSelectedColor(color);
            spectrumDialog.setNegativeButtonText(R.string.cancel);
            spectrumDialog.setPositiveButtonText(R.string.ok);
            spectrumDialog.setDismissOnColorSelected(false);
            spectrumDialog.setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                @Override
                public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                    if (positiveResult) {
                        onPlayerColorSet(color);
                    }
                }
            });
            spectrumDialog.build().show(getSupportFragmentManager(), "tag");
            scoreboardViewModel.disablePlayerColorChangingEvent();
        }
    }

    public void onPlayerNameSet(String playerName) {
        scoreboardViewModel.onChangeCurrentPlayerName(playerName);
    }

    public void onPlayerColorSet(int playerColor) {
        setPlayerColor(scoreboardViewModel.getCurrentPlayerNumber(), playerColor);
    }

    private void setPlayerColor(int playerNumber, int playerColor) {
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
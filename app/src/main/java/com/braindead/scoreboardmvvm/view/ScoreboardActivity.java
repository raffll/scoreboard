package com.braindead.scoreboardmvvm.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.braindead.scoreboardmvvm.R;
import com.braindead.scoreboardmvvm.databinding.ActivityScoreboardBinding;
import com.braindead.scoreboardmvvm.viewmodel.ScoreboardViewModel;

public class ScoreboardActivity extends AppCompatActivity {

    private static final String BEGIN_DIALOG_TAG = "begin_dialog_tag";
    private ScoreboardViewModel scoreboardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        promptForNewSession();
    }

    public void promptForNewSession() {
        ScoreboardBeginDialog dialog = ScoreboardBeginDialog.newInstance(this);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), BEGIN_DIALOG_TAG);
    }

    public void onNewSessionSet(int numberOfPlayers) {
        initDataBinding(numberOfPlayers);
    }

    private void initDataBinding(int numberOfPlayers) {
        ActivityScoreboardBinding activityScoreboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_scoreboard);
        scoreboardViewModel = ViewModelProviders.of(this).get(ScoreboardViewModel.class);
        scoreboardViewModel.init(numberOfPlayers);
        activityScoreboardBinding.setScoreboardViewModel(scoreboardViewModel);
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
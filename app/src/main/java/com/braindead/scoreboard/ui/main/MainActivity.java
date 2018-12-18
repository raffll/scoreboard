package com.braindead.scoreboard.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.braindead.scoreboard.R;
import com.braindead.scoreboard.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            initDataBinding();
            initActionBar();
            createNewSessionDialog();
        }
    }

    private void initDataBinding() {
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.init();
        binding.setMainViewModel(mainViewModel);
        binding.setHandler(this);
        binding.setManager(getSupportFragmentManager());
    }

    private void initActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void createNewSessionDialog() {
        NewSessionDialog dialog = NewSessionDialog.newInstance(this,
                MainViewModel.DEFAULT_NUMBER_OF_PLAYERS,
                MainViewModel.DEFAULT_SCORE,
                MainViewModel.DEFAULT_SESSION_NAME);
        dialog.show(getSupportFragmentManager(), "TAG");
    }

    public void onNewSessionSet(int numberOfPlayers, int defaultScore, String sessionName) {
        mainViewModel.onNewSession(numberOfPlayers, defaultScore, sessionName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuCompat.setGroupDividerEnabled(menu, true);
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
            case R.id.undo:
                onUndoSet();
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
        mainViewModel.onResetSession();
    }

    private void createSaveSessionDialog() {
        SaveSessionDialog dialog = SaveSessionDialog.newInstance(this);
        dialog.show(getSupportFragmentManager(), "TAG");
    }

    public void onSaveSessionSet() {
        mainViewModel.onSaveSession();
    }

    public void onUndoSet() {
        mainViewModel.onUndo();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("scoreboard", mainViewModel.getScoreboard());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mainViewModel.setScoreboard(savedInstanceState.getParcelable("scoreboard"));
    }

    @Override
    public void onBackPressed() {
        // Disabled
    }
}
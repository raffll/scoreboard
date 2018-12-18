package com.braindead.scoreboard.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
        }
    }

    private void initDataBinding() {
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.init();
        mainViewModel.onNewSession(MainViewModel.DEFAULT_NUMBER_OF_PLAYERS,
                MainViewModel.DEFAULT_SCORE,
                MainViewModel.DEFAULT_SESSION_NAME);
        binding.setMainViewModel(mainViewModel);
        binding.setHandler(this);
        binding.setManager(getSupportFragmentManager());
    }

    private void initActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
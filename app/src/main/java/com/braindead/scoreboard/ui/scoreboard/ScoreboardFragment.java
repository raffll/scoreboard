package com.braindead.scoreboard.ui.scoreboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.braindead.scoreboard.R;
import com.braindead.scoreboard.databinding.FragmentScoreboardBinding;
import com.braindead.scoreboard.ui.main.MainViewModel;
import com.braindead.scoreboard.utilities.AppConstants;

import androidx.annotation.Nullable;
import androidx.core.view.MenuCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class ScoreboardFragment extends Fragment {

    private MainViewModel mainViewModel;

    public static Fragment newInstance() {
        ScoreboardFragment fragment = new ScoreboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentScoreboardBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_scoreboard,
                container,
                false);
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        binding.setMainViewModel(mainViewModel);
        setUpOnPlayerSettingsEventListener();

        View view = binding.getRoot();
        return view;
    }

    private void setUpOnPlayerSettingsEventListener() {
        mainViewModel.getPlayerSettingsEvent().observe(this, this::onPlayerSettingsEventTriggered);
    }

    private void onPlayerSettingsEventTriggered(Boolean playerSettingsEvent) {
        if (playerSettingsEvent) {
            createPlayerSettingsDialog();
            mainViewModel.disablePlayerSettingsEvent();
        }
    }

    private void createPlayerSettingsDialog() {
        PlayerSettingsDialog dialog = PlayerSettingsDialog.newInstance(
                this,
                mainViewModel.currentName.get(),
                mainViewModel.currentColor.get());
        dialog.show(getActivity().getSupportFragmentManager(), "TAG");
    }

    public void onPlayerSettingsSet(String playerName, int playerColor) {
        mainViewModel.onChangeCurrentPlayerSettings(playerName, playerColor);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_scoreboard, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuCompat.setGroupDividerEnabled(menu, true);
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

    private void createNewSessionDialog() {
        NewSessionDialog dialog = NewSessionDialog.newInstance(
                this,
                AppConstants.DEFAULT_NUMBER_OF_PLAYERS,
                AppConstants.DEFAULT_SCORE,
                AppConstants.DEFAULT_SESSION_NAME);
        dialog.show(getActivity().getSupportFragmentManager(), "TAG");
    }

    public void onNewSessionSet(int numberOfPlayers, int defaultScore, String sessionName) {
        mainViewModel.onNewSession(numberOfPlayers, defaultScore, sessionName);
    }

    private void createResetSessionDialog() {
        ResetSessionDialog dialog = ResetSessionDialog.newInstance(this);
        dialog.show(getActivity().getSupportFragmentManager(), "TAG");
    }

    public void onResetSessionSet() {
        mainViewModel.onResetSession();
    }

    private void createSaveSessionDialog() {
        SaveSessionDialog dialog = SaveSessionDialog.newInstance(this);
        dialog.show(getActivity().getSupportFragmentManager(), "TAG");
    }

    public void onSaveSessionSet() {
        mainViewModel.onSaveSession();
    }

    public void onUndoSet() {
        mainViewModel.onUndo();
    }
}

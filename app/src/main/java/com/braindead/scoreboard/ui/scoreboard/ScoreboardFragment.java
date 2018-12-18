package com.braindead.scoreboard.ui.scoreboard;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.braindead.scoreboard.R;
import com.braindead.scoreboard.databinding.FragmentScoreboardBinding;
import com.braindead.scoreboard.ui.main.MainViewModel;

public class ScoreboardFragment extends Fragment {

    private MainViewModel mainViewModel;

    public static Fragment newInstance() {
        ScoreboardFragment fragment = new ScoreboardFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentScoreboardBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scoreboard, container, false);
        View view = binding.getRoot();
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        binding.setMainViewModel(mainViewModel);
        setUpOnPlayerSettingsEventListener();
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
        PlayerSettingsDialog dialog = PlayerSettingsDialog.newInstance(this,
                mainViewModel.currentName.get(),
                mainViewModel.currentColor.get());
        dialog.show(getActivity().getSupportFragmentManager(), "TAG");
    }

    public void onPlayerSettingsSet(String playerName, int playerColor) {
        mainViewModel.onChangeCurrentPlayerSettings(playerName, playerColor);
    }
}

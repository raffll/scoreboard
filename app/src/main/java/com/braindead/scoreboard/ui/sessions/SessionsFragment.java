package com.braindead.scoreboard.ui.sessions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.braindead.scoreboard.R;
import com.braindead.scoreboard.databinding.FragmentSessionsBinding;
import com.braindead.scoreboard.ui.main.MainViewModel;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class SessionsFragment extends Fragment {

    private MainViewModel mainViewModel;

    public static Fragment newInstance() {
        SessionsFragment fragment = new SessionsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSessionsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sessions, container, false);
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        binding.setMainViewModel(mainViewModel);

        View view = binding.getRoot();
        return view;
    }
}

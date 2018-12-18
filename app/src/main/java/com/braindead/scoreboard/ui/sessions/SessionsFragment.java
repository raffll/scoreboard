package com.braindead.scoreboard.ui.sessions;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.braindead.scoreboard.R;
import com.braindead.scoreboard.databinding.FragmentSessionsBinding;

public class SessionsFragment extends Fragment {

    private SessionsViewModel sessionsViewModel;

    public static Fragment newInstance() {
        SessionsFragment fragment = new SessionsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSessionsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sessions, container, false);
        View view = binding.getRoot();
        sessionsViewModel = ViewModelProviders.of(getActivity()).get(SessionsViewModel.class);
        binding.setSessionsViewModel(sessionsViewModel);
        return view;
    }
}

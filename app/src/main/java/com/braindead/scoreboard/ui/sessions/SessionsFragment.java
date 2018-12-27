package com.braindead.scoreboard.ui.sessions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.braindead.scoreboard.R;
import com.braindead.scoreboard.databinding.FragmentSessionsBinding;
import com.braindead.scoreboard.model.Player;
import com.braindead.scoreboard.ui.main.MainViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SessionsFragment extends Fragment {

    private MainViewModel mainViewModel;

    public static Fragment newInstance() {
        SessionsFragment fragment = new SessionsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentSessionsBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_sessions,
                container,
                false);
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        binding.setMainViewModel(mainViewModel);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final SessionsAdapter adapter = new SessionsAdapter();
        recyclerView.setAdapter(adapter);

        mainViewModel.getAllPlayers().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(@Nullable List<Player> notes) {
                adapter.setNotes(notes);
            }
        });
    }
}

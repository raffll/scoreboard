package com.braindead.scoreboard.ui.sessions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.braindead.scoreboard.R;
import com.braindead.scoreboard.model.Player;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.SessionsHolder> {
    private List<Player> players = new ArrayList<>();

    @NonNull
    @Override
    public SessionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_item, parent, false);
        return new SessionsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionsHolder holder, int position) {
        Player currentPlayer = players.get(position);
        //holder.textViewTitle.setText(currentPlayer.getName());
        //holder.textViewPriority.setText(String.valueOf(currentNote.getScore()));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
        notifyDataSetChanged();
    }

    class SessionsHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewScore;

        public SessionsHolder(View itemView) {
            super(itemView);
            //textViewTitle = itemView.findViewById(R.id.text_view_title);
            //textViewDescription = itemView.findViewById(R.id.text_view_description);
            //textViewPriority = itemView.findViewById(R.id.text_view_priority);
        }
    }
}

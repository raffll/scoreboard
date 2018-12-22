package com.braindead.scoreboard.ui.main;

import android.app.Application;

import com.braindead.scoreboard.db.PlayerRepository;
import com.braindead.scoreboard.model.Player;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RoomViewModel extends AndroidViewModel {

    private PlayerRepository repository;
    private LiveData<List<Player>> allPlayers;

    public RoomViewModel(@NonNull Application application) {
        super(application);
        repository = new PlayerRepository(application);
        allPlayers = repository.getAllPlayers();
    }

    public void insert(Player player) {
        repository.insert(player);
    }

    public void update(Player player) {
        repository.update(player);
    }

    public void delete(Player player) {
        repository.delete(player);
    }

    public void deleteAllPlayers() {
        repository.deleteAllPlayers();
    }

    public LiveData<List<Player>> getAllPlayers() {
        return allPlayers;
    }
}

package com.braindead.scoreboard.db;

import com.braindead.scoreboard.model.Player;

import java.util.List;

import io.reactivex.Observable;

public interface DbHelper {

    Observable<List<Player>> getAllUsers();
}

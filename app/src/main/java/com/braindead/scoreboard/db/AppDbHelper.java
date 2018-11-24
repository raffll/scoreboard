package com.braindead.scoreboard.db;

import com.braindead.scoreboard.model.Player;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

public class AppDbHelper implements DbHelper {

    private final AppDatabase appDatabase;

    public AppDbHelper(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public Observable<List<Player>> getAllUsers() {
        return Observable.fromCallable(new Callable<List<Player>>() {
            @Override
            public List<Player> call() throws Exception {
                return appDatabase.playerDao().getAll();
            }
        });
    }
}

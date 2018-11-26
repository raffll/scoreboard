package com.braindead.scoreboard.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.braindead.scoreboard.dao.PlayerDao;
import com.braindead.scoreboard.model.Player;

@Database(entities = Player.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PlayerDao playerDao();
}

package com.braindead.scoreboard.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.braindead.scoreboard.model.Player;

@Database(entities = {Player.class}, version = 1, exportSchema = false)
public abstract class PlayerDatabase extends RoomDatabase {

    private static PlayerDatabase instance;

    public abstract PlayerDao playerDao();

    public static synchronized PlayerDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PlayerDatabase.class, "player_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

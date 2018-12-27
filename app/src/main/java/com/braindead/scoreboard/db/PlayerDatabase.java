package com.braindead.scoreboard.db;

import android.content.Context;
import android.os.AsyncTask;

import com.braindead.scoreboard.model.Player;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

    //Test
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private PlayerDao playerDao;

        private PopulateDbAsyncTask(PlayerDatabase db) {
            playerDao = db.playerDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            playerDao.insert(new Player("Title 1", 10, 1));
            playerDao.insert(new Player("Title 2", 20, 2));
            playerDao.insert(new Player("Title 3", 30, 3));
            return null;
        }
    }
}

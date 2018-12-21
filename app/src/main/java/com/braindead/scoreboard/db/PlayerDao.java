package com.braindead.scoreboard.db;

import com.braindead.scoreboard.model.Player;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Player player);

    @Update
    void update(Player player);

    @Delete
    void delete(Player player);

    @Query("DELETE FROM player_table")
    void deleteAllPlayers();

    @Query("SELECT * FROM player_table")
    LiveData<List<Player>> getAllPlayers();
}
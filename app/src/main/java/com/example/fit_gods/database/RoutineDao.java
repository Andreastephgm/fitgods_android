package com.example.fit_gods.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fit_gods.model.Routine;

import java.util.List;

@Dao
public interface RoutineDao {

    @Insert
    void insertRoutine(Routine routine);

    @Update
    void updateRoutine(Routine routine);

    @Delete
    void deleteRoutine(Routine routine);

    @Query("SELECT * FROM routines WHERE idUser = :userId")
    List<Routine> getRoutinesByUser(Long userId);
}

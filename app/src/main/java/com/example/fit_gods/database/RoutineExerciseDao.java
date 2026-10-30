package com.example.fit_gods.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fit_gods.model.RoutineExercise;

import java.util.List;

@Dao
public interface RoutineExerciseDao {

    @Insert
    void insertExercise(RoutineExercise exercise);

    @Update
    void updateExercise(RoutineExercise exercise);

    @Delete
    void deleteExercise(RoutineExercise exercise);

    @Query("SELECT * FROM routineExercises WHERE idRoutine = :routineId")
    List<RoutineExercise> getExercisesByRoutine(Long routineId);
}
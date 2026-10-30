package com.example.fit_gods.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "routineExercises")
public class RoutineExercise {

    @PrimaryKey(autoGenerate = true)
    private Long idRoutineExercise;

    private Long idRoutine; // Foreign key hacia la rutina
    private String exerciseName;
    private Integer series;
    private Integer repetitions;
    private Integer rest;

    public RoutineExercise(Long idRoutine, String exerciseName, Integer series, Integer repetitions, Integer rest) {
        this.idRoutine = idRoutine;
        this.exerciseName = exerciseName;
        this.series = series;
        this.repetitions = repetitions;
        this.rest = rest;
    }

    // Getters y Setters
    public Long getIdRoutineExercise() { return idRoutineExercise; }
    public void setIdRoutineExercise(Long idRoutineExercise) { this.idRoutineExercise = idRoutineExercise; }

    public Long getIdRoutine() { return idRoutine; }
    public void setIdRoutine(Long idRoutine) { this.idRoutine = idRoutine; }

    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public Integer getSeries() { return series; }
    public void setSeries(Integer series) { this.series = series; }

    public Integer getRepetitions() { return repetitions; }
    public void setRepetitions(Integer repetitions) { this.repetitions = repetitions; }

    public Integer getRest() { return rest; }
    public void setRest(Integer rest) { this.rest = rest; }
}
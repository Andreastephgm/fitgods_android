package com.example.fit_gods.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "routines")
public class Routine {

    @PrimaryKey(autoGenerate = true)
    private Long idRoutine;

    private String name;
    private String objective;
    private Long idUser; // Relación con el usuario logueado

    public Routine(String name, String objective, Long idUser) {
        this.name = name;
        this.objective = objective;
        this.idUser = idUser;
    }

    // Getters y Setters
    public Long getIdRoutine() { return idRoutine; }
    public void setIdRoutine(Long idRoutine) { this.idRoutine = idRoutine; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getObjective() { return objective; }
    public void setObjective(String objective) { this.objective = objective; }

    public Long getIdUser() { return idUser; }
    public void setIdUser(Long idUser) { this.idUser = idUser; }
}
package com.example.fit_gods.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "progress")
public class Progress {
    @PrimaryKey(autoGenerate = true)
    private Long idProgress;
    private String date;
    private Double weight;
    private Integer percentage;
    private String notes;
    private Long idUser;

    public Progress(String date, Double weight, Integer percentage, String notes, Long idUser) {
        this.date = date;
        this.weight = weight;
        this.percentage = percentage;
        this.notes = notes;
        this.idUser = idUser;
    }

    // Getters y Setters
    public Long getIdProgress() { return idProgress; }
    public void setIdProgress(Long idProgress) { this.idProgress = idProgress; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public Integer getPercentage() { return percentage; }
    public void setPercentage(Integer percentage) { this.percentage = percentage; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Long getIdUser() { return idUser; }
    public void setIdUser(Long idUser) { this.idUser = idUser; }
}
package com.example.fit_gods.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fit_gods.model.Progress;

import java.util.List;

@Dao
public interface ProgressDao {

    @Insert
    void insertProgress(Progress progress);

    @Query("SELECT * FROM progress WHERE idUser = :userId ORDER BY idProgress DESC")
    List<Progress> getProgressByUser(Long userId);
}

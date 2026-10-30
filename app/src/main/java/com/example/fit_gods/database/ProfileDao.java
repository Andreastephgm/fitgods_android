package com.example.fit_gods.database;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fit_gods.model.User;

@Dao
public interface ProfileDao {

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    User getUserProfileById(Long userId);

    @Update
    void updateUserProfile(User user);
}

package com.example.fit_gods.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fit_gods.model.Progress;
import com.example.fit_gods.model.Routine;
import com.example.fit_gods.model.RoutineExercise;
import com.example.fit_gods.model.User;

@Database(entities = {User.class, Routine.class, RoutineExercise.class, Progress.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract RoutineDao routineDao();

    public abstract ProgressDao progressDao();

    public abstract ProfileDao profileDao();
    public abstract RoutineExerciseDao routineExerciseDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "fitgods_db"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
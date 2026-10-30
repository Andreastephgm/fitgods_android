package com.example.fit_gods.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.fit_gods.database.AppDatabase;
import com.example.fit_gods.database.RoutineExerciseDao;
import com.example.fit_gods.model.RoutineExercise;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RoutineExerciseRepository {
    private RoutineExerciseDao exerciseDao;
    private ExecutorService executorService;
    private Handler mainHandler;

    public RoutineExerciseRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        exerciseDao = db.routineExerciseDao();
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public interface ExerciseCallback<T> {
        void onSuccess(T result);
    }

    public void getExercisesForRoutine(Long routineId, ExerciseCallback<List<RoutineExercise>> callback) {
        executorService.execute(() -> {
            List<RoutineExercise> list = exerciseDao.getExercisesByRoutine(routineId);
            mainHandler.post(() -> callback.onSuccess(list));
        });
    }

    public void saveExercise(RoutineExercise exercise, Runnable onComplete) {
        executorService.execute(() -> {
            exerciseDao.insertExercise(exercise);
            mainHandler.post(onComplete);
        });
    }

    public void deleteExercise(RoutineExercise exercise, Runnable onComplete) {
        executorService.execute(() -> {
            exerciseDao.deleteExercise(exercise);
            mainHandler.post(onComplete);
        });
    }
}

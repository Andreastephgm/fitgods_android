package com.example.fit_gods.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.fit_gods.database.AppDatabase;
import com.example.fit_gods.database.RoutineDao;
import com.example.fit_gods.model.Routine;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RoutineRepository {

    private RoutineDao routineDao;
    private ExecutorService executorService;
    private Handler mainHandler;

    public RoutineRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        routineDao = db.routineDao();
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public interface RoutineCallback<T> {
        void onSuccess(T result);
        void onError(String message);
    }

    public void getRoutines(Long userId, RoutineCallback<List<Routine>> callback) {
        executorService.execute(() -> {
            List<Routine> routines = routineDao.getRoutinesByUser(userId);
            mainHandler.post(() -> callback.onSuccess(routines));
        });
    }

    public void saveRoutine(Routine routine, Runnable onComplete) {
        executorService.execute(() -> {
            routineDao.insertRoutine(routine);
            mainHandler.post(onComplete);
        });
    }

    public void updateRoutine(Routine routine, Runnable onComplete) {
        executorService.execute(() -> {
            routineDao.updateRoutine(routine);
            mainHandler.post(onComplete);
        });
    }

    public void deleteRoutine(Routine routine, Runnable onComplete) {
        executorService.execute(() -> {
            routineDao.deleteRoutine(routine);
            mainHandler.post(onComplete);
        });
    }
}

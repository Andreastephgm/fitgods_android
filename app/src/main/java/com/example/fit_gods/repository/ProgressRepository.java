package com.example.fit_gods.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.fit_gods.database.AppDatabase;
import com.example.fit_gods.database.ProgressDao;
import com.example.fit_gods.model.Progress;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProgressRepository {
    private ProgressDao progressDao;
    private ExecutorService executorService;
    private Handler mainHandler;

    public ProgressRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        progressDao = db.progressDao();
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public interface ProgressCallback<T> {
        void onSuccess(T result);
        void onError(String message);
    }

    public void getProgressForUser(Long userId, ProgressCallback<List<Progress>> callback) {
        executorService.execute(() -> {
            List<Progress> list = progressDao.getProgressByUser(userId);
            mainHandler.post(() -> callback.onSuccess(list));
        });
    }

    public void saveProgress(Progress progress, Runnable onComplete) {
        executorService.execute(() -> {
            progressDao.insertProgress(progress);
            mainHandler.post(onComplete);
        });
    }
}

package com.example.fit_gods.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.fit_gods.database.AppDatabase;
import com.example.fit_gods.database.UserDao;
import com.example.fit_gods.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {

    private UserDao userDao;
    private ExecutorService executorService;
    private Handler mainHandler;

    public UserRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        userDao = db.userDao();
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public interface RegisterCallback {
        void onSuccess();
        void onError(String message);
    }

    public interface LoginCallback {
        void onSuccess(User user);
        void onError(String message);
    }

    public void loginUser(String email, String password, LoginCallback callback) {
        executorService.execute(() -> {
            User user = userDao.login(email, password);
            mainHandler.post(() -> {
                if (user != null) {
                    callback.onSuccess(user);
                } else {
                    callback.onError("Correo o contraseña incorrectos");
                }
            });
        });
    }
    public void registerUser(User user, RegisterCallback callback) {
        executorService.execute(() -> {
            // Validar si el correo ya está registrado
            User existingUser = userDao.getUserByEmail(user.getEmail());
            if (existingUser != null) {
                mainHandler.post(() -> callback.onError("El correo ya está registrado"));
            } else {
                userDao.insertUser(user);
                mainHandler.post(callback::onSuccess);
            }
        });
    }
}
package com.example.fit_gods.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.fit_gods.repository.UserRepository;

public class LoginViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void login(String email, String password, UserRepository.LoginCallback callback) {
        userRepository.loginUser(email, password, callback);
    }
}
package com.example.fit_gods.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.fit_gods.model.User;
import com.example.fit_gods.repository.UserRepository;

public class RegisterViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void register(User user, UserRepository.RegisterCallback callback) {
        userRepository.registerUser(user, callback);
    }
}

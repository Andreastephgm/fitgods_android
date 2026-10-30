package com.example.fit_gods.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.fit_gods.ViewModel.LoginViewModel;
import com.example.fit_gods.databinding.ActivityLoginBinding;
import com.example.fit_gods.model.User;
import com.example.fit_gods.repository.UserRepository;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        setupListeners();
    }

    private void setupListeners() {
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etCorreo.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (email.isEmpty()) {
                binding.etCorreo.setError("Por favor ingresa tu correo");
                binding.etCorreo.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                binding.etPassword.setError("Por favor ingresa tu contraseña");
                binding.etPassword.requestFocus();
                return;
            }

            loginViewModel.login(email, password, new UserRepository.LoginCallback() {
                @Override
                public void onSuccess(User user) {
                    Toast.makeText(LoginActivity.this, "¡Bienvenido, " + user.getName() + "!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("USER_ID", user.getId());
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        });

        binding.txtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
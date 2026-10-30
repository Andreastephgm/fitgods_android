package com.example.fit_gods.View;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.fit_gods.ViewModel.RegisterViewModel;
import com.example.fit_gods.databinding.ActivityRegisterBinding;
import com.example.fit_gods.model.User;
import com.example.fit_gods.repository.UserRepository;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        setupListeners();
    }

    private void setupListeners() {
        binding.btnRegister.setOnClickListener(v -> executeRegister());

        binding.txtLogin.setOnClickListener(v -> finish());
    }

    private void executeRegister() {
        String name = binding.etNombre.getText().toString().trim();
        String surname = binding.etApellido.getText().toString().trim();
        String email = binding.etCorreo.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String confirmPassword = binding.etConfirmPassword.getText().toString().trim();

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!binding.checkTerms.isChecked()) {
            Toast.makeText(this, "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show();
            return;
        }

        // Creamos el objeto User con los datos ingresados
        User newUser = new User(name, surname, email, password, "2000-01-01", 60.0, 1.70, "Ganar músculo");

        // Llamamos al ViewModel para guardarlo en Room de forma local
        registerViewModel.register(newUser, new UserRepository.RegisterCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(RegisterActivity.this, "¡Cuenta creada con éxito!", Toast.LENGTH_SHORT).show();
                finish(); // Regresa automáticamente a la pantalla de Login
            }

            @Override
            public void onError(String message) {
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
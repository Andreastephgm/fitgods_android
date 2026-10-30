package com.example.fit_gods.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fit_gods.database.AppDatabase;
import com.example.fit_gods.databinding.ActivityProfileBinding;
import com.example.fit_gods.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private AppDatabase db;
    private ExecutorService executorService;
    private Long currentUserId = 1L;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = AppDatabase.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();

        if (getIntent().hasExtra("USER_ID")) {
            currentUserId = getIntent().getLongExtra("USER_ID", 1L);
        }

        loadUserData();
        setupListeners();
    }

    private void loadUserData() {
        executorService.execute(() -> {
            currentUser = db.userDao().getUserById(currentUserId);
            runOnUiThread(() -> {
                if (currentUser != null) {
                    binding.tvUserNameHeader.setText(currentUser.getName() + " " + currentUser.getSurname());
                    binding.tvUserEmailHeader.setText(currentUser.getEmail());

                    binding.etProfileName.setText(currentUser.getName());
                    binding.etProfileSurname.setText(currentUser.getSurname());
                    binding.etProfileEmail.setText(currentUser.getEmail());
                    binding.etProfileWeight.setText(String.valueOf(currentUser.getWeight()));
                    binding.etProfileHeight.setText(String.valueOf(currentUser.getHeight()));
                }
            });
        });
    }

    private void setupListeners() {
        // Botón Guardar Cambios
        binding.btnUpdateProfile.setOnClickListener(v -> updateProfile());

        // Opción Cerrar Sesión
        binding.btnLogoutOption.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        binding.navInicio.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
            intent.putExtra("USER_ID", currentUserId);
            startActivity(intent);
        });

        binding.navRutinas.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, RoutinesActivity.class);
            intent.putExtra("USER_ID", currentUserId);
            startActivity(intent);
        });

        binding.navProgreso.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, ProgressActivity.class);
            intent.putExtra("USER_ID", currentUserId);
            startActivity(intent);
        });

        binding.navPerfil.setOnClickListener(v -> {
        });
    }

    private void updateProfile() {
        if (currentUser == null) return;

        String name = binding.etProfileName.getText().toString().trim();
        String surname = binding.etProfileSurname.getText().toString().trim();
        String email = binding.etProfileEmail.getText().toString().trim();
        String weightStr = binding.etProfileWeight.getText().toString().trim();
        String heightStr = binding.etProfileHeight.getText().toString().trim();

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Completa los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        currentUser.setName(name);
        currentUser.setSurname(surname);
        currentUser.setEmail(email);
        if (!weightStr.isEmpty()) currentUser.setWeight(Double.parseDouble(weightStr));
        if (!heightStr.isEmpty()) currentUser.setHeight(Double.parseDouble(heightStr));

        executorService.execute(() -> {
            db.userDao().updateUser(currentUser);
            runOnUiThread(() -> {
                Toast.makeText(ProfileActivity.this, "Perfil actualizado con éxito", Toast.LENGTH_SHORT).show();
                loadUserData();
            });
        });
    }
}
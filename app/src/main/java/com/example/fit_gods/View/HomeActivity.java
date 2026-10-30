package com.example.fit_gods.View;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fit_gods.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private Long currentUserId = 1L; // Valor de respaldo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. RECUPERAR EL ID DEL USUARIO LOGUEADO
        if (getIntent().hasExtra("USER_ID")) {
            currentUserId = getIntent().getLongExtra("USER_ID", 1L);
        }

        setupListeners();
    }

    private void setupListeners() {
        binding.btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        binding.navRutinas.setOnClickListener(v -> abrirRutinas());
        binding.cardMisRutinas.setOnClickListener(v -> abrirRutinas());
        binding.btnVerRutina1.setOnClickListener(v -> abrirRutinas());
        binding.btnVerRutina2.setOnClickListener(v -> abrirRutinas());

        binding.navProgreso.setOnClickListener(v -> abrirProgreso());
        binding.cardMiProgreso.setOnClickListener(v -> abrirProgreso());
        binding.txtVerMasProgreso.setOnClickListener(v -> abrirProgreso());

        binding.navPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            intent.putExtra("USER_ID", currentUserId);
            startActivity(intent);
        });
    }

    private void abrirRutinas() {
        Intent intent = new Intent(HomeActivity.this, RoutinesActivity.class);
        intent.putExtra("USER_ID", currentUserId); // 👈 Propagamos el ID
        startActivity(intent);
    }

    private void abrirProgreso() {
        Intent intent = new Intent(HomeActivity.this, ProgressActivity.class);
        intent.putExtra("USER_ID", currentUserId); // 👈 Propagamos el ID
        startActivity(intent);
    }
}
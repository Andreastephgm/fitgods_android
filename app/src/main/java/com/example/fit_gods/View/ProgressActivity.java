package com.example.fit_gods.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fit_gods.adapter.ProgressAdapter;
import com.example.fit_gods.databinding.ActivityProgressBinding;
import com.example.fit_gods.model.Progress;
import com.example.fit_gods.repository.ProgressRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProgressActivity extends AppCompatActivity {

    private ActivityProgressBinding binding;
    private ProgressRepository progressRepository;
    private ProgressAdapter progressAdapter;
    private List<Progress> progressList;
    private Long currentUserId = 1L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProgressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressRepository = new ProgressRepository(this);

        if (getIntent().hasExtra("USER_ID")) {
            currentUserId = getIntent().getLongExtra("USER_ID", 1L);
        }

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        binding.etFecha.setText(currentDate);

        setupRecyclerView();
        setupListeners();
        cargarHistorial();
    }

    private void setupRecyclerView() {
        progressList = new ArrayList<>();
        progressAdapter = new ProgressAdapter(progressList);
        binding.recyclerProgress.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerProgress.setAdapter(progressAdapter);
    }

    private void setupListeners() {
        binding.btnGuardarRegistro.setOnClickListener(v -> guardarProgreso());

        binding.navInicio.setOnClickListener(v -> {
            Intent intent = new Intent(ProgressActivity.this, HomeActivity.class);
            intent.putExtra("USER_ID", currentUserId);
            startActivity(intent);
        });

        binding.navRutinas.setOnClickListener(v -> {
            Intent intent = new Intent(ProgressActivity.this, RoutinesActivity.class);
            intent.putExtra("USER_ID", currentUserId);
            startActivity(intent);
        });

        binding.navPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(ProgressActivity.this, ProfileActivity.class);
            intent.putExtra("USER_ID", currentUserId);
            startActivity(intent);
        });
    }

    private void guardarProgreso() {
        String fecha = binding.etFecha.getText().toString().trim();
        String pesoStr = binding.etPeso.getText().toString().trim();
        String porcentajeStr = binding.etPorcentaje.getText().toString().trim();
        String observaciones = binding.etObservaciones.getText().toString().trim();

        if (pesoStr.isEmpty() || porcentajeStr.isEmpty()) {
            Toast.makeText(this, "Completa el peso y el porcentaje", Toast.LENGTH_SHORT).show();
            return;
        }

        Double peso = Double.parseDouble(pesoStr);
        Integer porcentaje = Integer.parseInt(porcentajeStr);

        Progress nuevoProgreso = new Progress(fecha, peso, porcentaje, observaciones, currentUserId);

        progressRepository.saveProgress(nuevoProgreso, () -> {
            Toast.makeText(this, "Progreso guardado con éxito", Toast.LENGTH_SHORT).show();
            binding.etPeso.setText("");
            binding.etPorcentaje.setText("");
            binding.etObservaciones.setText("");
            cargarHistorial(); // 👈 Recarga automáticamente la lista e historial
        });
    }

    private void cargarHistorial() {
        progressRepository.getProgressForUser(currentUserId, new ProgressRepository.ProgressCallback<List<Progress>>() {
            @Override
            public void onSuccess(List<Progress> result) {
                if (result != null && !result.isEmpty()) {
                    Progress ultimo = result.get(0);
                    binding.tvPesoActual.setText(ultimo.getWeight() + " kg");
                    binding.tvAvanceGeneral.setText(ultimo.getPercentage() + " %");
                    binding.tvTotalRegistros.setText(String.valueOf(result.size()));

                    progressAdapter.updateData(result);
                } else {
                    binding.tvPesoActual.setText("0 kg");
                    binding.tvAvanceGeneral.setText("0 %");
                    binding.tvTotalRegistros.setText("0");
                    progressAdapter.updateData(new ArrayList<>());
                }
            }

            @Override
            public void onError(String message) {
                Toast.makeText(ProgressActivity.this, "Error al cargar historial", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
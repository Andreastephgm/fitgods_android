package com.example.fit_gods.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fit_gods.R;
import com.example.fit_gods.adapter.RoutineAdapter;
import com.example.fit_gods.databinding.ActivityRoutinesBinding;
import com.example.fit_gods.model.Routine;
import com.example.fit_gods.repository.RoutineRepository;

import java.util.ArrayList;
import java.util.List;

public class RoutinesActivity extends AppCompatActivity {
    private ActivityRoutinesBinding binding;
    private RoutineRepository routineRepository;
    private RoutineAdapter routineAdapter;
    private List<Routine> routineList;
    private Long currentUserId = 1L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoutinesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        routineRepository = new RoutineRepository(this);

        if (getIntent().hasExtra("USER_ID")) {
            currentUserId = getIntent().getLongExtra("USER_ID", 1L);
        }

        setupRecyclerView();
        setupListeners();
        cargarRutinas();
    }

    private void setupRecyclerView() {
        routineList = new ArrayList<>();
        routineAdapter = new RoutineAdapter(routineList, new RoutineAdapter.OnRoutineListener() {
            @Override
            public void onEditClick(Routine routine) {
                showRoutineDialog(routine);
            }

            @Override
            public void onDeleteClick(Routine routine) {
                routineRepository.deleteRoutine(routine, () -> {
                    Toast.makeText(RoutinesActivity.this, "Rutina eliminada", Toast.LENGTH_SHORT).show();
                    cargarRutinas();
                });
            }

            @Override
            public void onExercisesClick(Routine routine) {
                Intent intent = new Intent(RoutinesActivity.this, RoutineDetailActivity.class);
                intent.putExtra("ROUTINE_ID", routine.getIdRoutine());
                intent.putExtra("ROUTINE_NAME", routine.getName());
                startActivity(intent);
            }
        });

        binding.recyclerRutinas.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerRutinas.setAdapter(routineAdapter);
    }

    private void setupListeners() {
        binding.fabAddRoutine.setOnClickListener(v -> showRoutineDialog(null));

        // NAVEGACIÓN INFERIOR: Aseguramos que al cambiar de pantalla se siga llevando el USER_ID
        binding.navInicio.setOnClickListener(v -> {
            Intent intent = new Intent(RoutinesActivity.this, HomeActivity.class);
            intent.putExtra("USER_ID", currentUserId);
            startActivity(intent);
        });

        binding.navProgreso.setOnClickListener(v -> {
            Intent intent = new Intent(RoutinesActivity.this, ProgressActivity.class);
            intent.putExtra("USER_ID", currentUserId);
            startActivity(intent);
        });

        binding.navPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(RoutinesActivity.this, ProfileActivity.class);
            intent.putExtra("USER_ID", currentUserId);
            startActivity(intent);
        });
    }

    private void cargarRutinas() {
        // Consulta a Room filtrando estrictamente por el idUser actual
        routineRepository.getRoutines(currentUserId, new RoutineRepository.RoutineCallback<List<Routine>>() {
            @Override
            public void onSuccess(List<Routine> result) {
                routineList = result;
                routineAdapter.updateData(routineList);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(RoutinesActivity.this, "Error al cargar rutinas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showRoutineDialog(Routine routineToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_routine_form, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.etDialogRoutineName);
        EditText etObjective = dialogView.findViewById(R.id.etDialogRoutineObjective);

        boolean isEditing = (routineToEdit != null);
        if (isEditing) {
            etName.setText(routineToEdit.getName());
            etObjective.setText(routineToEdit.getObjective());
            builder.setTitle("Modificar Rutina");
        } else {
            builder.setTitle("Nueva Rutina");
        }

        builder.setPositiveButton(isEditing ? "Actualizar" : "Guardar", (dialog, which) -> {
            String name = etName.getText().toString().trim();
            String objective = etObjective.getText().toString().trim();

            if (name.isEmpty() || objective.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEditing) {
                routineToEdit.setName(name);
                routineToEdit.setObjective(objective);
                routineRepository.updateRoutine(routineToEdit, () -> {
                    Toast.makeText(this, "Rutina actualizada", Toast.LENGTH_SHORT).show();
                    cargarRutinas();
                });
            } else {
                // 3. Crear la rutina enlazada estrictamente al usuario actual (currentUserId)
                Routine nuevaRutina = new Routine(name, objective, currentUserId);
                routineRepository.saveRoutine(nuevaRutina, () -> {
                    Toast.makeText(this, "Rutina creada con éxito", Toast.LENGTH_SHORT).show();
                    cargarRutinas();
                });
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}
package com.example.fit_gods.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.fit_gods.R;
import com.example.fit_gods.adapter.ExerciseAdapter;
import com.example.fit_gods.model.RoutineExercise;
import com.example.fit_gods.repository.RoutineExerciseRepository;

import java.util.ArrayList;
import java.util.List;

public class RoutineDetailActivity extends AppCompatActivity {

    private TextView tvRoutineTitleDetail;
    private RecyclerView recyclerExercises;
    private FloatingActionButton fabAddExercise;
    private ImageView btnBack;

    private RoutineExerciseRepository exerciseRepository;
    private ExerciseAdapter exerciseAdapter;
    private List<RoutineExercise> exerciseList;
    private Long routineId;
    private String routineName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_detail);

        tvRoutineTitleDetail = findViewById(R.id.tvRoutineTitleDetail);
        recyclerExercises = findViewById(R.id.recyclerExercises);
        fabAddExercise = findViewById(R.id.fabAddExercise);
        btnBack = findViewById(R.id.btnBack); // 👈 Enlazamos el botón de atrás

        routineId = getIntent().getLongExtra("ROUTINE_ID", -1);
        routineName = getIntent().getStringExtra("ROUTINE_NAME");

        if (routineName != null) {
            tvRoutineTitleDetail.setText("Rutina: " + routineName);
        }

        exerciseRepository = new RoutineExerciseRepository(this);

        setupRecyclerView();
        setupListeners();
        cargarEjercicios();
    }

    private void setupRecyclerView() {
        exerciseList = new ArrayList<>();
        exerciseAdapter = new ExerciseAdapter(exerciseList, exercise -> {
            exerciseRepository.deleteExercise(exercise, () -> {
                Toast.makeText(this, "Ejercicio eliminado", Toast.LENGTH_SHORT).show();
                cargarEjercicios();
            });
        });

        recyclerExercises.setLayoutManager(new LinearLayoutManager(this));
        recyclerExercises.setAdapter(exerciseAdapter);
    }

    private void setupListeners() {
        // Botón flotante para agregar ejercicio
        fabAddExercise.setOnClickListener(v -> showAddExerciseDialog());

        btnBack.setOnClickListener(v -> finish());
    }

    private void cargarEjercicios() {
        exerciseRepository.getExercisesForRoutine(routineId, exercises -> {
            exerciseList = exercises;
            exerciseAdapter.updateData(exerciseList);
        });
    }

    private void showAddExerciseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_exercise_form, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.etDialogExerciseName);
        EditText etSeries = dialogView.findViewById(R.id.etDialogExerciseSeries);
        EditText etReps = dialogView.findViewById(R.id.etDialogExerciseRepetitions);
        EditText etRest = dialogView.findViewById(R.id.etDialogExerciseRest);

        builder.setTitle("Agregar Ejercicio");

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String name = etName.getText().toString().trim();
            String seriesStr = etSeries.getText().toString().trim();
            String repsStr = etReps.getText().toString().trim();
            String restStr = etRest.getText().toString().trim();

            if (name.isEmpty() || seriesStr.isEmpty() || repsStr.isEmpty()) {
                Toast.makeText(this, "Completa los campos obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            int series = Integer.parseInt(seriesStr);
            int reps = Integer.parseInt(repsStr);
            int rest = restStr.isEmpty() ? 0 : Integer.parseInt(restStr);

            RoutineExercise newExercise = new RoutineExercise(routineId, name, series, reps, rest);

            exerciseRepository.saveExercise(newExercise, () -> {
                Toast.makeText(this, "¡Ejercicio guardado!", Toast.LENGTH_SHORT).show();
                cargarEjercicios();
            });
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}
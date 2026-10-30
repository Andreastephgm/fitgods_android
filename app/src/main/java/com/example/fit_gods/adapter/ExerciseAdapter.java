package com.example.fit_gods.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fit_gods.R;
import com.example.fit_gods.model.RoutineExercise;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private List<RoutineExercise> exerciseList;
    private OnExerciseDeleteListener deleteListener;

    public interface OnExerciseDeleteListener {
        void onDeleteClick(RoutineExercise exercise);
    }

    public ExerciseAdapter(List<RoutineExercise> exerciseList, OnExerciseDeleteListener deleteListener) {
        this.exerciseList = exerciseList;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        RoutineExercise exercise = exerciseList.get(position);
        holder.tvName.setText(exercise.getExerciseName());

        String details = "Series: " + exercise.getSeries() +
                "  |  Reps: " + exercise.getRepetitions() +
                "  |  Descanso: " + exercise.getRest() + "s";
        holder.tvDetails.setText(details);

        holder.btnDelete.setOnClickListener(v -> deleteListener.onDeleteClick(exercise));
    }

    @Override
    public int getItemCount() {
        return exerciseList != null ? exerciseList.size() : 0;
    }

    public void updateData(List<RoutineExercise> newExercises) {
        this.exerciseList = newExercises;
        notifyDataSetChanged();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDetails;
        Button btnDelete;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvExerciseName);
            tvDetails = itemView.findViewById(R.id.tvExerciseDetails);
            btnDelete = itemView.findViewById(R.id.btnDeleteExercise);
        }
    }
}

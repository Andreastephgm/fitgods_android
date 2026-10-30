package com.example.fit_gods.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fit_gods.R;
import com.example.fit_gods.model.Routine;

import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> {

    private List<Routine> routineList;
    private OnRoutineListener listener;

    public interface OnRoutineListener {
        void onEditClick(Routine routine);
        void onDeleteClick(Routine routine);
        void onExercisesClick(Routine routine); // Nuevo listener para los ejercicios
    }

    public RoutineAdapter(List<Routine> routineList, OnRoutineListener listener) {
        this.routineList = routineList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_routine, parent, false);
        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        Routine routine = routineList.get(position);
        holder.tvName.setText(routine.getName());
        holder.tvObjective.setText(routine.getObjective());

        holder.btnEdit.setOnClickListener(v -> listener.onEditClick(routine));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(routine));
        holder.btnManageExercises.setOnClickListener(v -> listener.onExercisesClick(routine));
    }

    @Override
    public int getItemCount() {
        return routineList != null ? routineList.size() : 0;
    }

    public void updateData(List<Routine> newRoutines) {
        this.routineList = newRoutines;
        notifyDataSetChanged();
    }

    public static class RoutineViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvObjective;
        Button btnEdit, btnDelete, btnManageExercises;

        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvRoutineName);
            tvObjective = itemView.findViewById(R.id.tvRoutineObjective);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnManageExercises = itemView.findViewById(R.id.btnManageExercises);
        }
    }
}
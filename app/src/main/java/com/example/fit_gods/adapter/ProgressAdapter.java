package com.example.fit_gods.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fit_gods.R;
import com.example.fit_gods.model.Progress;

import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder> {

    private List<Progress> progressList;

    public ProgressAdapter(List<Progress> progressList) {
        this.progressList = progressList;
    }

    public void updateData(List<Progress> newList) {
        this.progressList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProgressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false);
        return new ProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressViewHolder holder, int position) {
        Progress progress = progressList.get(position);

        holder.tvDate.setText(progress.getDate());
        holder.tvWeight.setText(progress.getWeight() + " kg");
        holder.tvPercentage.setText("Avance registrado: " + progress.getPercentage() + "%");

        if (progress.getNotes() != null && !progress.getNotes().trim().isEmpty()) {
            holder.tvNotes.setText(progress.getNotes());
            holder.tvNotes.setVisibility(View.VISIBLE);
        } else {
            holder.tvNotes.setText("Sin observaciones");
            holder.tvNotes.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return progressList != null ? progressList.size() : 0;
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvPercentage, tvNotes, tvWeight;

        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvItemDate);
            tvPercentage = itemView.findViewById(R.id.tvItemPercentage);
            tvNotes = itemView.findViewById(R.id.tvItemNotes);
            tvWeight = itemView.findViewById(R.id.tvItemWeight);
        }
    }
}

package com.christopherwmurphy.boringbutbigapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.ViewHolder.ExerciseViewHolder;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseEntity;

import java.util.List;

public class ExerciseAdapater extends RecyclerView.Adapter<ExerciseViewHolder> {

    private List<ExerciseEntity> exercises;

    public ExerciseAdapater(List<ExerciseEntity> exercises){
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater mInflater = LayoutInflater.from(context);
        View v = mInflater.inflate(R.layout.exercise_list_layout, parent, false);

        return new ExerciseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        holder.bind(exercises.get(position));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }
}


package com.christopherwmurphy.boringbutbigapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.christopherwmurphy.boringbutbigapp.Callbacks.WorkoutHistoryCallback;
import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.ViewHolder.WorkoutHistoryViewHolder;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutHistoryEntity;


import java.util.List;

public class WorkoutHistoryAdapter extends RecyclerView.Adapter<WorkoutHistoryViewHolder>{
   private List<WorkoutHistoryEntity> workouts;
    private WorkoutHistoryCallback callback;

    public WorkoutHistoryAdapter(List<WorkoutHistoryEntity> workouts, WorkoutHistoryCallback callback){
        this.workouts = workouts;
        this.callback = callback;
    }

    @NonNull
    @Override
    public WorkoutHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater mInflater = LayoutInflater.from(context);
        View v = mInflater.inflate(R.layout.workout_history_recyclerview, parent, false);

        return new WorkoutHistoryViewHolder(v, callback);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutHistoryViewHolder holder, int position) {
        holder.bind(workouts.get(position));
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }
}

package com.christopherwmurphy.boringbutbigapp.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutViewHolder extends RecyclerView.ViewHolder {

    private WorkoutEntity workout;
    private Context context;

    @BindView(R.id.WorkoutName)
    TextView workoutName;

    public WorkoutViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public void bind(WorkoutEntity workout){
        this.workout = workout;
        workoutName.setText(workout.getName());
    }
}

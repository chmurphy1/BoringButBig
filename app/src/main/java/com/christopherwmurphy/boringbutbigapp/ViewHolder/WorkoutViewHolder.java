package com.christopherwmurphy.boringbutbigapp.ViewHolder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.christopherwmurphy.boringbutbigapp.Callbacks.WorkoutCallback;
import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class WorkoutViewHolder extends RecyclerView.ViewHolder {

    private WorkoutEntity workout;
    private Context context;
    private WorkoutCallback callback;

    @BindView(R.id.WorkoutName)
    TextView workoutName;

    @BindView(R.id.WorkoutCard)
    CardView card;

    public WorkoutViewHolder(View itemView, WorkoutCallback callback) {
        super(itemView);
        context = itemView.getContext();
        this.callback = callback;
        ButterKnife.bind(this, itemView);
    }

    public void bind(WorkoutEntity workout){
        this.workout = workout;
        workoutName.setText(workout.getName());
    }

    @OnClick(R.id.WorkoutCard)
    public void onClick(){

        String[] lifts = workout.getLifts().split(",");
        callback.callback(workout.getWorkoutId(), lifts);
    }

    @OnFocusChange(R.id.WorkoutCard)
    public void onFocusChanged(boolean focused){
        if(focused) {
            card.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent, null));
            workoutName.setTextColor(context.getResources().getColor(R.color.white, null));
        }
        else{
            card.setCardBackgroundColor(context.getResources().getColor(R.color.white, null));
            workoutName.setTextColor(context.getResources().getColor(R.color.black, null));
        }
    }
}

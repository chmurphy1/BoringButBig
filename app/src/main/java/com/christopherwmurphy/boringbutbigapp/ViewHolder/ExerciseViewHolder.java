package com.christopherwmurphy.boringbutbigapp.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseViewHolder extends RecyclerView.ViewHolder {

   private ExerciseEntity exercise;
   private Context context;

   @BindView(R.id.exerciseName)
   TextView exerciseName;

    public ExerciseViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public void bind(ExerciseEntity exercise){
        this.exercise = exercise;
        exerciseName.setText(exercise.getName());
    }
}
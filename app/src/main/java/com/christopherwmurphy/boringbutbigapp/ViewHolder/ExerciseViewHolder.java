package com.christopherwmurphy.boringbutbigapp.ViewHolder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.christopherwmurphy.boringbutbigapp.Callbacks.ExerciseCallback;
import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class ExerciseViewHolder extends RecyclerView.ViewHolder {

   private ExerciseEntity exercise;
   private Context context;
   private ExerciseCallback exListener;

   @BindView(R.id.exerciseName)
   TextView exerciseName;

    @BindView(R.id.ExerciseCard)
    CardView card;

    public ExerciseViewHolder(View itemView, ExerciseCallback callback) {
        super(itemView);
        context = itemView.getContext();
        this.exListener = callback;
        ButterKnife.bind(this, itemView);
    }

    public void bind(ExerciseEntity exercise){
        this.exercise = exercise;
        exerciseName.setText(exercise.getName());
    }

    @OnClick(R.id.ExerciseCard)
    public void onClick(){
        exListener.callback(exercise.getId(), exercise.getVideoId());
    }

    @OnFocusChange(R.id.ExerciseCard)
    public void onFocusChanged(boolean focused){
        if(focused) {
            card.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent, null));
            exerciseName.setTextColor(context.getResources().getColor(R.color.white, null));
        }
        else{
            card.setCardBackgroundColor(context.getResources().getColor(R.color.white, null));
            exerciseName.setTextColor(context.getResources().getColor(R.color.black, null));
        }
    }
}

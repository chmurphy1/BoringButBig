package com.christopherwmurphy.boringbutbigapp.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseMaxEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseMaxViewHolder extends RecyclerView.ViewHolder {

    private Context context;

    @BindView(R.id.ExerciseName)
    TextView exerciseName;

    @BindView(R.id.numberInput)
    EditText numberInput;

    public ExerciseMaxViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public void bind(Object exercise){

        if(exercise instanceof ExerciseMaxEntity){
            ExerciseMaxEntity e = (ExerciseMaxEntity) exercise;
            exerciseName.setText(e.getExercise().getName());
            numberInput.setText(e.getMax());
        }
        else if(exercise instanceof ExerciseEntity){
            ExerciseEntity e = (ExerciseEntity) exercise;
            exerciseName.setText(e.getName());
            numberInput.setText("0");
        }

    }
}

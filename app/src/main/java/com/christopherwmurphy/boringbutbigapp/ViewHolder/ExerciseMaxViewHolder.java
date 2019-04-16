package com.christopherwmurphy.boringbutbigapp.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.christopherwmurphy.boringbutbigapp.Util.Constants;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseMaxEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

import static com.christopherwmurphy.boringbutbigapp.R.*;

public class ExerciseMaxViewHolder extends RecyclerView.ViewHolder {

    private Context context;

    @BindView(id.ExerciseName)
    TextView exerciseName;

    @BindView(id.numberInput)
    EditText numberInput;

    private Object exercise;
    private Integer newMax;

    public ExerciseMaxViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public void bind(Object exercise) {

        this.exercise = exercise;

        if (exercise instanceof ExerciseMaxEntity) {
            ExerciseMaxEntity e = (ExerciseMaxEntity) exercise;
            exerciseName.setText(e.getExercise().getName());
            numberInput.setText(e.getMax().toString());
        } else if (exercise instanceof ExerciseEntity) {
            ExerciseEntity e = (ExerciseEntity) exercise;
            exerciseName.setText(e.getName());
            numberInput.setText("0");
        }
    }

    @OnTextChanged(value = id.numberInput, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChanged(){
        try{
            String s = numberInput.getText().toString();
            if(!s.equals(Constants.EMPTY)) {
                newMax = Integer.parseInt(s);
            }
        }catch(NumberFormatException e){
            e.printStackTrace();
            Toast.makeText(context, context.getResources().getString(string.valid_input_number), Toast.LENGTH_LONG).show();
        }
    }

    public Integer getNewMax() {
        return newMax;
    }

    public Object getExercise() {
        return exercise;
    }
}

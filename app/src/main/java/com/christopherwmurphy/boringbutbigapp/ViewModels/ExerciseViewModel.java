package com.christopherwmurphy.boringbutbigapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseEntity;
import com.christopherwmurphy.boringbutbigapp.database.WorkoutDB;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {
    private LiveData<List<ExerciseEntity>> exercises;

    public ExerciseViewModel(@NonNull Application application) {
        super(application);

        WorkoutDB db = WorkoutDB.getInstance(application.getApplicationContext());
        exercises = db.exerciseDao().getAllExercises();
    }

    public LiveData<List<ExerciseEntity>> getExercises() {
        return exercises;
    }
}

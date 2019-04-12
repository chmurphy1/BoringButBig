package com.christopherwmurphy.boringbutbigapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseStepsEntity;
import com.christopherwmurphy.boringbutbigapp.database.WorkoutDB;

import java.util.List;

public class ExerciseStepsViewModel extends AndroidViewModel {

    private LiveData<List<ExerciseStepsEntity>> steps;

    public ExerciseStepsViewModel(@NonNull Application application, int id) {
        super(application);

        WorkoutDB db = WorkoutDB.getInstance(application.getApplicationContext());
        steps = db.exerciseStepsDao().getStepsByExerciseId(id);
    }

    public LiveData<List<ExerciseStepsEntity>> getSteps() {
        return steps;
    }
}

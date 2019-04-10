package com.christopherwmurphy.boringbutbigapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutEntity;
import com.christopherwmurphy.boringbutbigapp.database.WorkoutDB;

import java.util.List;

public class WorkoutViewModel extends AndroidViewModel {

    private LiveData<List<WorkoutEntity>> workouts;

    public WorkoutViewModel(@NonNull Application application) {
        super(application);

        WorkoutDB db = WorkoutDB.getInstance(application.getApplicationContext());
        workouts = db.workoutDao().getAllWorkouts();
    }

    public LiveData<List<WorkoutEntity>> getWorkouts() {
        return workouts;
    }
}

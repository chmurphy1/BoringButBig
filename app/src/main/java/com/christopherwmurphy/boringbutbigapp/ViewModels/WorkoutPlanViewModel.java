package com.christopherwmurphy.boringbutbigapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.database.WorkoutDB;

import java.util.List;

public class WorkoutPlanViewModel extends AndroidViewModel{

    private LiveData<List<WorkoutPlanEntity>> plans;

    public WorkoutPlanViewModel(@NonNull Application application, int id) {
        super(application);

        WorkoutDB db = WorkoutDB.getInstance(application.getApplicationContext());
        plans = db.workoutPlanDao().getAllWorkoutPlans(id);
    }

    public LiveData<List<WorkoutPlanEntity>> getPlans() {
        return plans;
    }
}

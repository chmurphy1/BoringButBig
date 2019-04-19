package com.christopherwmurphy.boringbutbigapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutHistoryEntity;
import com.christopherwmurphy.boringbutbigapp.database.WorkoutDB;

import java.util.List;

public class WorkoutHistoryViewModel extends AndroidViewModel {

    private LiveData<List<WorkoutHistoryEntity>> history;

    public WorkoutHistoryViewModel(@NonNull Application application) {
        super(application);

        WorkoutDB db = WorkoutDB.getInstance(application.getApplicationContext());
        history = db.workoutHistoryDao().getAllWorkoutsGroupByTimestamp();
    }

    public LiveData<List<WorkoutHistoryEntity>> getHistory() {
        return history;
    }
}

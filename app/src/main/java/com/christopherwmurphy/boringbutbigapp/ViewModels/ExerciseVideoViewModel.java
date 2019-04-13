package com.christopherwmurphy.boringbutbigapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseVideosEntity;
import com.christopherwmurphy.boringbutbigapp.database.WorkoutDB;

public class ExerciseVideoViewModel extends AndroidViewModel {

    LiveData<ExerciseVideosEntity> video;

    public ExerciseVideoViewModel(@NonNull Application application, int id) {
        super(application);

        WorkoutDB db = WorkoutDB.getInstance(application.getApplicationContext());
        video = db.exerciseVideosDao().getVideoById(id);
    }

    public LiveData<ExerciseVideosEntity> getVideo() {
        return video;
    }
}

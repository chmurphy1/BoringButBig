package com.christopherwmurphy.boringbutbigapp.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutEntity;

import java.util.List;

@Dao
public interface WorkoutDao {
    @Insert
    void insert(WorkoutEntity workout);

    @Insert
    void insertAll(List<WorkoutEntity> workouts);

    @Query("select * from workout")
    LiveData<List<WorkoutEntity>> getAllWorkouts();
}

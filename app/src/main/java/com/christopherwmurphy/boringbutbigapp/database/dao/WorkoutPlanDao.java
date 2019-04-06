package com.christopherwmurphy.boringbutbigapp.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutPlanEntity;

import java.util.List;

@Dao
public interface WorkoutPlanDao {

    @Insert
    void insert(WorkoutPlanEntity plan);

    @Insert
    void insertAll(List<WorkoutPlanEntity> plan);

    @Query("select * from workout_plan where workout_id = :id")
    LiveData<List<WorkoutPlanEntity>> getAllWorkoutPlans(int id);
}

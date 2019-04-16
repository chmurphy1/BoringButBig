package com.christopherwmurphy.boringbutbigapp.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.christopherwmurphy.boringbutbigapp.database.Entity.TodaysWorkoutPlanEntity;

@Dao
public interface TodaysWorkoutPlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TodaysWorkoutPlanEntity plan);

    @Query("Select * from todays_workout_plan LIMIT 1")
    public TodaysWorkoutPlanEntity getTodaysWorkout();

    @Query("delete from todays_workout_plan")
    public void deleteAll();
}

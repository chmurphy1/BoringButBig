package com.christopherwmurphy.boringbutbigapp.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutEntity;

import java.util.List;

@Dao
public interface WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WorkoutEntity workout);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WorkoutEntity> workouts);

    @Query("select * from workout")
    LiveData<List<WorkoutEntity>> getAllWorkouts();

    @Query("select * from workout")
    List<WorkoutEntity> geListOfWorkouts();

    @Query("select * from workout where workout_id = :id")
    WorkoutEntity getWorkouById(int id);
}

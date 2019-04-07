package com.christopherwmurphy.boringbutbigapp.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseStepsEntity;

import java.util.List;

@Dao
public interface ExerciseStepsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ExerciseStepsEntity step);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ExerciseStepsEntity> steps);

    @Query("Select * from exercise_steps where exercise_id = :exerciseId")
    LiveData<List<ExerciseStepsEntity>> getStepsByExerciseId(int exerciseId);
}

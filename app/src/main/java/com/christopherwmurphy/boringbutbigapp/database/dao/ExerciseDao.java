package com.christopherwmurphy.boringbutbigapp.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseEntity;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    void insert(ExerciseEntity exercise);

    @Insert
    void insertAll(List<ExerciseEntity> exercises);

    @Query("Select * FROM exercise where id = :id")
    ExerciseEntity getExerciseById(int id);

    @Query("Select * FROM exercise")
    LiveData<List<ExerciseEntity>> getAllExercises();
}

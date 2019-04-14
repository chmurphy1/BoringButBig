package com.christopherwmurphy.boringbutbigapp.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseEntity;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ExerciseEntity exercise);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ExerciseEntity> exercises);

    @Query("Select * FROM exercise where id = :id")
    ExerciseEntity getExerciseById(int id);

    @Query("Select * FROM exercise")
    LiveData<List<ExerciseEntity>> getAllExercises();

    @Query("Select * FROM exercise")
    List<ExerciseEntity> getListOfExercises();

    @Query("Select * FROM exercise where id in(:list)")
    List<ExerciseEntity> getListOfExercisesInList(List<Integer> list);
}

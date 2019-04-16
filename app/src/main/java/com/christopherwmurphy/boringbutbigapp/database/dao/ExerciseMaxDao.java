package com.christopherwmurphy.boringbutbigapp.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseMaxEntity;

import java.util.List;

@Dao
public interface ExerciseMaxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ExerciseMaxEntity exercise);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ExerciseMaxEntity> exercise);

    @Query("Select * FROM exercise_max m join exercise e on (m.max_id = e.id) where max_id in(:id) order by date desc")
    List<ExerciseMaxEntity> getExerciseMaxById(List<Integer> id);
}

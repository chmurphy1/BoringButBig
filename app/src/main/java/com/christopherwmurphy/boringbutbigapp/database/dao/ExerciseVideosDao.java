package com.christopherwmurphy.boringbutbigapp.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseVideosEntity;

import java.util.List;

@Dao
public interface ExerciseVideosDao {
    @Insert
    void insert(ExerciseVideosEntity video);

    @Insert
    void insertAll(List<ExerciseVideosEntity> videos);

    @Query("select * from exercise_videos where id = :id")
    LiveData<ExerciseVideosEntity> getVideoById(int id);
}

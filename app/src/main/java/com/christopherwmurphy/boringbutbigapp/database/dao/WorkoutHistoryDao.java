package com.christopherwmurphy.boringbutbigapp.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutHistoryEntity;

import java.util.List;

@Dao
public interface WorkoutHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WorkoutHistoryEntity> plan);
}

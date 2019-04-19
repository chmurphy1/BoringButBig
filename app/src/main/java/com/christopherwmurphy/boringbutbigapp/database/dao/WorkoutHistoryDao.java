package com.christopherwmurphy.boringbutbigapp.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutHistoryEntity;

import java.util.List;

@Dao
public interface WorkoutHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WorkoutHistoryEntity> plan);

    @Query("select * from workout_history")
    LiveData<List<WorkoutHistoryEntity>> getAllWorkouts();

    @Query("select * from workout_history Group by date")
    LiveData<List<WorkoutHistoryEntity>> getAllWorkoutsGroupByTimestamp();

    @Query("select * from workout_history p join exercise e on (p.exercise_id = e.id) " +
            "join set_scheme s on (p.setId = s.set_id) where date = :date order by seq_num")
    List<WorkoutHistoryEntity> getAllWorkoutsByTimestamp(long date);
}

package com.christopherwmurphy.boringbutbigapp.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.christopherwmurphy.boringbutbigapp.database.Entity.CurrentWorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutPlanEntity;

import java.util.List;

@Dao
public interface CurrentWorkoutPlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CurrentWorkoutPlanEntity plan);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CurrentWorkoutPlanEntity> plan);

    @Query( "select * " +
            "from current_workout_plan p join exercise e on (p.exercise_id = e.id) join set_scheme s on (p.setId = s.set_id) " +
            "where workout_id = :id order by week, plan_id, seq_num")
    LiveData<List<CurrentWorkoutPlanEntity>> getAllWorkoutPlans(int id);

    @Query("select * from workout_plan")
    List<CurrentWorkoutPlanEntity> getAllWorkoutPlans();

    @Query("Delete from current_workout_plan")
    public void deleteAll();
}

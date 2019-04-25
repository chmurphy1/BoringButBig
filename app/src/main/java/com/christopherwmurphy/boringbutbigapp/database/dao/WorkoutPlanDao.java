package com.christopherwmurphy.boringbutbigapp.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.Workout;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutPlanEntity;

import java.util.List;

@Dao
public interface WorkoutPlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WorkoutPlanEntity plan);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WorkoutPlanEntity> plan);

    @Query( "select * " +
            "from workout_plan p join exercise e on (p.exercise_id = e.id) join set_scheme s on (p.setId = s.set_id) " +
            "where workout_id = :id order by week, plan_id, seq_num")
    LiveData<List<WorkoutPlanEntity>> getAllWorkoutPlans(int id);

    @Query("select * from workout_plan")
    List<WorkoutPlanEntity> getAllWorkoutPlans();

    @Query("select * from workout_plan where workout_id = :id")
    List<WorkoutPlanEntity> getAllWorkoutPlanById(int id);

    @Query("delete from workout_plan where week = :week and plan_id = :planId and seq_num = :seqNum and workout_id = :workoutId")
    public void delete(int week, int planId, int seqNum, int workoutId);
}

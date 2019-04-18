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
            "where week = :week and plan_id = :planId order by seq_num")
    List<CurrentWorkoutPlanEntity> getTodaysWorkoutPlans(int week, int planId);

    @Query("select * from current_workout_plan")
    List<CurrentWorkoutPlanEntity> getCurrentWorkoutPlan();

    @Query("Delete from current_workout_plan")
    public void deleteAll();

    @Query("Select max(week) from current_workout_plan")
    public Integer getMaxWeek();

    @Query("Select max(plan_id) from current_workout_plan where week = :week")
    public Integer getMaxPlanIdByWeek(int week);

    @Query("delete from current_workout_plan where week = :week and plan_id = :planId")
    public void deleteFromTableByWeekAndPlanId(int week, int planId);
}

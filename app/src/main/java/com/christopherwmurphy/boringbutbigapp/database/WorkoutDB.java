package com.christopherwmurphy.boringbutbigapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.database.Entity.CurrentWorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseMaxEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseStepsEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseVideosEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.SetSchemeEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.TodaysWorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.database.converter.TimestampConverter;
import com.christopherwmurphy.boringbutbigapp.database.dao.CurrentWorkoutPlanDao;
import com.christopherwmurphy.boringbutbigapp.database.dao.ExerciseDao;
import com.christopherwmurphy.boringbutbigapp.database.dao.ExerciseMaxDao;
import com.christopherwmurphy.boringbutbigapp.database.dao.ExerciseStepsDao;
import com.christopherwmurphy.boringbutbigapp.database.dao.ExerciseVideosDao;
import com.christopherwmurphy.boringbutbigapp.database.dao.SetSchemeDao;
import com.christopherwmurphy.boringbutbigapp.database.dao.TodaysWorkoutPlanDao;
import com.christopherwmurphy.boringbutbigapp.database.dao.WorkoutDao;
import com.christopherwmurphy.boringbutbigapp.database.dao.WorkoutPlanDao;

@Database(entities = {ExerciseEntity.class, ExerciseStepsEntity.class,
                      ExerciseVideosEntity.class, SetSchemeEntity.class,
                      WorkoutEntity.class, WorkoutPlanEntity.class,
                      ExerciseMaxEntity.class, CurrentWorkoutPlanEntity.class,
                      TodaysWorkoutPlanEntity.class}, version = 5, exportSchema = false)
@TypeConverters(TimestampConverter.class)
public abstract class WorkoutDB extends RoomDatabase {
    private static WorkoutDB dbInstance;

    public static WorkoutDB getInstance(final Context context){
        if(dbInstance == null){
            synchronized (WorkoutDB.class){
                if(dbInstance == null){
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(),
                            WorkoutDB.class,
                            context.getResources().getString(R.string.DATABASE_NAME)).fallbackToDestructiveMigration().build();
                }
            }
        }
        return dbInstance;
    }

    public abstract ExerciseDao exerciseDao();
    public abstract ExerciseStepsDao exerciseStepsDao();
    public abstract ExerciseVideosDao exerciseVideosDao();
    public abstract SetSchemeDao setSchemeDao();
    public abstract WorkoutDao workoutDao();
    public abstract WorkoutPlanDao workoutPlanDao();
    public abstract ExerciseMaxDao exerciseMaxDao();
    public abstract CurrentWorkoutPlanDao currentWorkoutPlanDao();
    public abstract TodaysWorkoutPlanDao todaysWorkoutPlanDao();
}

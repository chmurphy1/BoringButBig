package com.christopherwmurphy.boringbutbigapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.christopherwmurphy.boringbutbigapp.R;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseStepsEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseVideosEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.SetSchemeEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutPlanEntity;

@Database(entities = {ExerciseEntity.class, ExerciseStepsEntity.class,
                      ExerciseVideosEntity.class, SetSchemeEntity.class,
                      WorkoutEntity.class, WorkoutPlanEntity.class}, version = 1)
public abstract class WorkoutDB extends RoomDatabase {
    private static WorkoutDB dbInstance;

    public static WorkoutDB getInstance(final Context context){
        if(dbInstance == null){
            synchronized (WorkoutDB.class){
                if(dbInstance == null){
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(),
                            WorkoutDB.class,
                            context.getResources().getString(R.string.DATABASE_NAME)).build();
                }
            }
        }
        return dbInstance;
    }
}

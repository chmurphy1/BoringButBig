package com.christopherwmurphy.boringbutbigapp.database.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "workout",
        primaryKeys = {"workout_id", "language"},
        indices = {@Index(value = {"workout_id", "language"}, unique = true)})
public class WorkoutEntity {

    @PrimaryKey
    @ColumnInfo(name = "workout_id")
    private Integer workoutId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "language")
    private String language;

    public WorkoutEntity(Integer workoutId,
                         String name,
                         String language) {
        this.workoutId = workoutId;
        this.name = name;
        this.language = language;
    }

    public Integer getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Integer workoutId) {
        this.workoutId = workoutId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

package com.christopherwmurphy.boringbutbigapp.database.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.SET_NULL;

@Entity(tableName = "todays_workout_plan")
public class TodaysWorkoutPlanEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name="week")
    private Integer week;

    @NonNull
    @ColumnInfo(name="plan_id")
    private Integer planId;

    public TodaysWorkoutPlanEntity(Integer week,
                                    Integer planId) {
        this.week = week;
        this.planId = planId;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

}


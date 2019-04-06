package com.christopherwmurphy.boringbutbigapp.database.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.SET_NULL;

@Entity(tableName = "workout_plan",
        primaryKeys = {"week", "plan_id", "seq_num", "workout_id"},
        foreignKeys = { @ForeignKey(entity = ExerciseEntity.class,
                                    parentColumns = "id",
                                    childColumns = "exercise_id",
                                    onDelete = SET_NULL),
                        @ForeignKey(entity = SetSchemeEntity.class,
                                    parentColumns = "set_id",
                                    childColumns = "set_id",
                                    onDelete = SET_NULL)},
        indices = {@Index(value = {"week", "plan_id", "seq_num", "workout_id"}, unique = true),
                   @Index("exercise_id"),
                   @Index("set_id"),
                   @Index("workout_id")})
public class WorkoutPlanEntity {

    @NonNull
    @ColumnInfo(name="week")
    private Integer week;

    @NonNull
    @ColumnInfo(name="plan_id")
    private Integer planId;

    @NonNull
    @ColumnInfo(name="seq_num")
    private Integer seqNum;

    @ColumnInfo(name="exercise_id")
    private Integer exerciseId;

    @ColumnInfo(name="set_id")
    private Integer setId;

    @NonNull
    @ColumnInfo(name="workout_id")
    private Integer workoutId;

    @ColumnInfo(name = "optional")
    private Boolean optional;

    public WorkoutPlanEntity(Integer week,
                             Integer planId,
                             Integer seqNum,
                             Integer exerciseId,
                             Integer setId,
                             Integer workoutId,
                             Boolean optional) {
        this.week = week;
        this.planId = planId;
        this.seqNum = seqNum;
        this.exerciseId = exerciseId;
        this.setId = setId;
        this.workoutId = workoutId;
        this.optional = optional;
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

    public Integer getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    public Integer getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Integer workoutId) {
        this.workoutId = workoutId;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }
}

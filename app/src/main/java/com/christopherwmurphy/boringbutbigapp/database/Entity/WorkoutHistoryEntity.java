package com.christopherwmurphy.boringbutbigapp.database.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import java.sql.Timestamp;

import static android.arch.persistence.room.ForeignKey.SET_NULL;

@Entity(tableName = "workout_history",
        primaryKeys = {"week", "plan_id", "seq_num", "workout_id"},
        foreignKeys = { @ForeignKey(entity = ExerciseEntity.class,
                parentColumns = "id",
                childColumns = "exercise_id",
                onDelete = SET_NULL),
                @ForeignKey(entity = SetSchemeEntity.class,
                        parentColumns = "set_id",
                        childColumns = "setId",
                        onDelete = SET_NULL)},
        indices = {@Index(value = {"week", "plan_id", "seq_num", "workout_id"}, unique = true),
                @Index("exercise_id"),
                @Index("setId"),
                @Index("workout_id")})
public class WorkoutHistoryEntity {

    @Embedded
    SetSchemeEntity scheme;

    @Embedded
    ExerciseEntity exercise;

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

    @ColumnInfo(name="setId")
    private Integer setId;

    @NonNull
    @ColumnInfo(name="workout_id")
    private Integer workoutId;

    @ColumnInfo(name = "optional")
    private Boolean optional;

    @ColumnInfo(name = "weight")
    private Integer weight;

    @ColumnInfo(name = "date")
    private Timestamp date;

    public WorkoutHistoryEntity(Integer week,
                                Integer planId,
                                Integer seqNum,
                                Integer exerciseId,
                                Integer setId,
                                Integer workoutId,
                                Boolean optional,
                                Integer weight,
                                Timestamp date) {
        this.week = week;
        this.planId = planId;
        this.seqNum = seqNum;
        this.exerciseId = exerciseId;
        this.setId = setId;
        this.workoutId = workoutId;
        this.optional = optional;
        this.weight = weight;
        this.date = date;
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

    public SetSchemeEntity getScheme() {
        return scheme;
    }

    public void setScheme(SetSchemeEntity scheme) {
        this.scheme = scheme;
    }

    public ExerciseEntity getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseEntity exercise) {
        this.exercise = exercise;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}


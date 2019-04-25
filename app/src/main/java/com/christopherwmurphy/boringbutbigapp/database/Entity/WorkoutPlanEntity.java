package com.christopherwmurphy.boringbutbigapp.database.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import java.util.Objects;

import static android.arch.persistence.room.ForeignKey.SET_NULL;

@Entity(tableName = "workout_plan",
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
public class WorkoutPlanEntity {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutPlanEntity that = (WorkoutPlanEntity) o;
        return Objects.equals(scheme, that.scheme) &&
                Objects.equals(exercise, that.exercise) &&
                Objects.equals(week, that.week) &&
                Objects.equals(planId, that.planId) &&
                Objects.equals(seqNum, that.seqNum) &&
                Objects.equals(exerciseId, that.exerciseId) &&
                Objects.equals(setId, that.setId) &&
                Objects.equals(workoutId, that.workoutId) &&
                Objects.equals(optional, that.optional);
    }

    @Override
    public int hashCode() {

        return Objects.hash(scheme, exercise, week, planId, seqNum, exerciseId, setId, workoutId, optional);
    }

    @Override
    public String toString() {
        return "WorkoutPlanEntity{" +
                "scheme=" + scheme +
                ", exercise=" + exercise +
                ", week=" + week +
                ", planId=" + planId +
                ", seqNum=" + seqNum +
                ", exerciseId=" + exerciseId +
                ", setId=" + setId +
                ", workoutId=" + workoutId +
                ", optional=" + optional +
                '}';
    }
}

package com.christopherwmurphy.boringbutbigapp.database.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "workout",
        indices = {@Index(value = {"seq_num"}),
                   @Index(value = {"workout_id"})})
public class WorkoutEntity {

    @ColumnInfo(name = "workout_id")
    private Integer workoutId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "language")
    private String language;

    @PrimaryKey
    @ColumnInfo(name = "seq_num")
    private Integer seqNum;

    @ColumnInfo(name = "period")
    private Integer period;

    @ColumnInfo(name = "increments")
    private String increments;

    @ColumnInfo(name = "primary_lifts")
    private String lifts;

    public WorkoutEntity(Integer workoutId,
                         String name,
                         String language,
                         Integer seqNum,
                         String lifts,
                         String increments,
                         Integer period) {
        this.workoutId = workoutId;
        this.name = name;
        this.language = language;
        this.seqNum = seqNum;
        this.lifts = lifts;
        this.increments = increments;
        this.period = period;
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
    
    public Integer getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }

    public String getLifts() {
        return lifts;
    }

    public void setLifts(String lifts) {
        this.lifts = lifts;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getIncrements() {
        return increments;
    }

    public void setIncrements(String increments) {
        this.increments = increments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutEntity that = (WorkoutEntity) o;
        return Objects.equals(workoutId, that.workoutId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(language, that.language) &&
                Objects.equals(seqNum, that.seqNum) &&
                Objects.equals(period, that.period) &&
                Objects.equals(increments, that.increments) &&
                Objects.equals(lifts, that.lifts);
    }

    @Override
    public int hashCode() {

        return Objects.hash(workoutId, name, language, seqNum, period, increments, lifts);
    }

    @Override
    public String toString() {
        return "WorkoutEntity{" +
                "workoutId=" + workoutId +
                ", name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", seqNum=" + seqNum +
                ", period=" + period +
                ", increments='" + increments + '\'' +
                ", lifts='" + lifts + '\'' +
                '}';
    }
}

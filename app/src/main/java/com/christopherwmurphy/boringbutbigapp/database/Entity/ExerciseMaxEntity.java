package com.christopherwmurphy.boringbutbigapp.database.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "exercise_max",
        foreignKeys = @ForeignKey(entity = ExerciseEntity.class,
                                   parentColumns = {"id"},
                                   childColumns = {"max_id"}),
        indices = @Index("max_id"))
public class ExerciseMaxEntity {

    @Embedded
    private ExerciseEntity exercise;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "seq_num")
    private Integer seqNum;

    @ColumnInfo(name = "max_id")
    private Integer maxId;

    @ColumnInfo(name = "max")
    private Integer max;

    @ColumnInfo(name = "date")
    Timestamp date;

    public ExerciseMaxEntity(ExerciseEntity exercise, Integer maxId, Integer max, Timestamp date) {
        this.exercise = exercise;
        this.maxId = maxId;
        this.max = max;
        this.date = date;
    }

    public ExerciseEntity getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseEntity exercise) {
        this.exercise = exercise;
    }

    public Integer getMaxId() {
        return maxId;
    }

    public void setMaxId(Integer maxId) {
        this.maxId = maxId;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }
}

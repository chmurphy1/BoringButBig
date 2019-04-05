package com.christopherwmurphy.boringbutbigapp.database.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "exercise_steps",
        primaryKeys = {"exercise_id", "step_seq"},
        foreignKeys = @ForeignKey(entity = ExerciseEntity.class,
                                           parentColumns = {"id", "language"},
                                           childColumns = {"exercise_id", "language"},
                                           onDelete = CASCADE),
        indices = {@Index(value = {"exercise_id", "step_seq"}, unique = true),
                   @Index(value = {"exercise_id", "lang"})})
public class ExerciseStepsEntity {

    @ColumnInfo(name = "exercise_id")
    private Integer exerciseId;

    @ColumnInfo(name = "step_seq")
    private Integer stepSeq;

    @ColumnInfo(name = "step_text")
    private String stepText;

    @ColumnInfo(name = "language")
    private String language;

    public ExerciseStepsEntity(Integer exerciseId,
                               Integer stepSeq,
                               String stepText,
                               String language) {
        this.exerciseId = exerciseId;
        this.stepSeq = stepSeq;
        this.stepText = stepText;
        this.language = language;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Integer getStepSeq() {
        return stepSeq;
    }

    public void setStepSeq(Integer stepSeq) {
        this.stepSeq = stepSeq;
    }

    public String getStepText() {
        return stepText;
    }

    public void setStepText(String stepText) {
        this.stepText = stepText;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

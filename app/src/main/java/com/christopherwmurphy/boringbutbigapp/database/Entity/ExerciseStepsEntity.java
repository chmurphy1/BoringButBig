package com.christopherwmurphy.boringbutbigapp.database.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import java.util.Objects;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "exercise_steps",
        primaryKeys = {"exercise_id", "step_seq"},
        foreignKeys = @ForeignKey(entity = ExerciseEntity.class,
                                           parentColumns = {"id", "language"},
                                           childColumns = {"exercise_id", "language"},
                                           onDelete = CASCADE),
        indices = {@Index(value = {"exercise_id", "step_seq"}, unique = true),
                   @Index(value = {"exercise_id", "language"})})
public class ExerciseStepsEntity {

    @NonNull
    @ColumnInfo(name = "exercise_id")
    private Integer exerciseId;

    @NonNull
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseStepsEntity that = (ExerciseStepsEntity) o;
        return Objects.equals(exerciseId, that.exerciseId) &&
                Objects.equals(stepSeq, that.stepSeq) &&
                Objects.equals(stepText, that.stepText) &&
                Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {

        return Objects.hash(exerciseId, stepSeq, stepText, language);
    }

    @Override
    public String toString() {
        return "ExerciseStepsEntity{" +
                "exerciseId=" + exerciseId +
                ", stepSeq=" + stepSeq +
                ", stepText='" + stepText + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}

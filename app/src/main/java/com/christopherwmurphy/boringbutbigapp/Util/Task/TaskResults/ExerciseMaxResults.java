package com.christopherwmurphy.boringbutbigapp.Util.Task.TaskResults;

import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseMaxEntity;

import java.util.List;

public class ExerciseMaxResults {

    private List<ExerciseMaxEntity> exerciseMaxes;
    private List<ExerciseEntity> exercises;

    public ExerciseMaxResults(List<ExerciseMaxEntity> exerciseMaxes, List<ExerciseEntity> exercises) {
        this.exerciseMaxes = exerciseMaxes;
        this.exercises = exercises;
    }

    public List<ExerciseMaxEntity> getExerciseMaxes() {
        return exerciseMaxes;
    }

    public void setExerciseMaxes(List<ExerciseMaxEntity> exerciseMaxes) {
        this.exerciseMaxes = exerciseMaxes;
    }

    public List<ExerciseEntity> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseEntity> exercises) {
        this.exercises = exercises;
    }
}

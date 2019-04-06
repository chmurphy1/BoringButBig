package com.christopherwmurphy.boringbutbigapp.JsonUtil.Model;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.keys.WorkoutPlanPk;

public class WorkoutPlan {
    private WorkoutPlanPk id;
    private Exercise exercise;
    private SetScheme setScheme;
    private Workout workout;
    private Boolean optional;

    public WorkoutPlan(){
        this.exercise = null;
        this.setScheme = null;
        this.workout = null;
        this.optional = false;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public SetScheme getSetScheme() {
        return setScheme;
    }

    public void setSetScheme(SetScheme setScheme) {
        this.setScheme = setScheme;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public WorkoutPlanPk getId() {
        return id;
    }

    public void setId(WorkoutPlanPk id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "WorkoutPlan{" +
                "id=" + id +
                ", exercise=" + exercise +
                ", setScheme=" + setScheme +
                ", workout=" + workout +
                ", optional=" + optional +
                '}';
    }
}

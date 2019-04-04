package com.christopherwmurphy.boringbutbigapp.JsonUtil.Model;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;

public class Workout {
    private Integer workoutId;
    private String name;
    private String language;

    public Workout() {
        this.workoutId = 0;
        this.name = Constants.EMPTY;
        this.language = Constants.EMPTY;
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
}


package com.christopherwmurphy.boringbutbigapp.JsonUtil.Model;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;

public class Workout {
    private Integer workoutId;
    private String name;
    private String language;
    private Integer seqNum;

    public Workout() {
        this.workoutId = 0;
        this.name = Constants.EMPTY;
        this.language = Constants.EMPTY;
        this.seqNum = 0;
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

    @Override
    public String toString() {
        return "Workout{" +
                "workoutId=" + workoutId +
                ", name='" + name + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}


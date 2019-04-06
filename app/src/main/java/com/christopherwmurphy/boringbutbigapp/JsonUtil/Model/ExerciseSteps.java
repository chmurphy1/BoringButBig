package com.christopherwmurphy.boringbutbigapp.JsonUtil.Model;
import com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.keys.ExerciseStepsPk;

public class ExerciseSteps {
    private ExerciseStepsPk id;
    private String stepText;
    private String language;

    public ExerciseSteps() {
    }

    public ExerciseStepsPk getId() {
        return this.id;
    }

    public void setId(ExerciseStepsPk id) {
        this.id = id;
    }

    public String getStepText() {
        return this.stepText;
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
    public String toString() {
        return "ExerciseSteps{" +
                "id=" + id +
                ", stepText='" + stepText + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}

package com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.keys;

public class ExerciseStepsPk {
    private Integer exerciseId;
    private Integer stepSeq;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((exerciseId == null) ? 0 : exerciseId.hashCode());
        result = prime * result + ((stepSeq == null) ? 0 : stepSeq.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ExerciseStepsPk other = (ExerciseStepsPk) obj;
        if (exerciseId == null) {
            if (other.exerciseId != null)
                return false;
        } else if (!exerciseId.equals(other.exerciseId))
            return false;
        if (stepSeq == null) {
            if (other.stepSeq != null)
                return false;
        } else if (!stepSeq.equals(other.stepSeq))
            return false;
        return true;
    }
}


package com.christopherwmurphy.boringbutbigapp.JsonUtil.Model.keys;

public class WorkoutPlanPk{
    private Integer week;
    private Integer planId;
    private Integer seqNum;
    private Integer workoutId;

    public WorkoutPlanPk() {
    }

    public Integer getWeek() {
        return this.week;
    }
    public void setWeek(Integer week) {
        this.week = week;
    }
    public Integer getPlanId() {
        return this.planId;
    }
    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
    public Integer getSeqNum() {
        return this.seqNum;
    }
    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }
    public Integer getWorkoutId() {
        return this.workoutId;
    }
    public void setWorkoutId(Integer workoutId) {
        this.workoutId = workoutId;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WorkoutPlanPk)) {
            return false;
        }
        WorkoutPlanPk castOther = (WorkoutPlanPk)other;
        return
                (this.week.intValue() == castOther.week.intValue())
                        && (this.planId.intValue() == castOther.planId.intValue())
                        && (this.seqNum.intValue() == castOther.seqNum.intValue())
                        && (this.workoutId.intValue() == castOther.workoutId.intValue());
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.week;
        hash = hash * prime + this.planId;
        hash = hash * prime + this.seqNum;
        hash = hash * prime + this.workoutId;

        return hash;
    }

    @Override
    public String toString() {
        return "WorkoutPlanPk{" +
                "week=" + week +
                ", planId=" + planId +
                ", seqNum=" + seqNum +
                ", workoutId=" + workoutId +
                '}';
    }
}

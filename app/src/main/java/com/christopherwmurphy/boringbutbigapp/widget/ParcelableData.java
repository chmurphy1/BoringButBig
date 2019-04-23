package com.christopherwmurphy.boringbutbigapp.widget;
import com.christopherwmurphy.boringbutbigapp.Util.Constants;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;


@Parcel(Parcel.Serialization.BEAN)
public class ParcelableData {

    private String lift;
    private String reps;
    private String weight;

    public ParcelableData(){
        this.lift = Constants.EMPTY;
        this.reps = Constants.EMPTY;
        this.weight = Constants.EMPTY;
    }

    @ParcelConstructor
    public ParcelableData(String lift, String reps, String weight) {
        this.lift = lift;
        this.reps = reps;
        this.weight = weight;
    }

    public String getLift() {
        return lift;
    }

    public void setLift(String lift) {
        this.lift = lift;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}

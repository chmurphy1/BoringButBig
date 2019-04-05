package com.christopherwmurphy.boringbutbigapp.database.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "set_scheme",
        indices = {@Index(value = {"set_id"}, unique = true)})
public class SetSchemeEntity {

    @PrimaryKey
    @ColumnInfo(name = "set_id")
    private Integer setId;

    @ColumnInfo(name = "set")
    private Integer set;

    @ColumnInfo(name = "reps")
    private Integer reps;

    @ColumnInfo(name = "percentage")
    private Double percentage;

    public SetSchemeEntity(Integer setId,
                           Integer set,
                           Integer reps,
                           Double percentage) {
        this.setId = setId;
        this.set = set;
        this.reps = reps;
        this.percentage = percentage;
    }

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    public Integer getSet() {
        return set;
    }

    public void setSet(Integer set) {
        this.set = set;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}

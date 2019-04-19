package com.christopherwmurphy.boringbutbigapp.Callbacks;

import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutHistoryEntity;

import java.util.List;

public interface HistoryDetailCallback {
    public void callback(List<WorkoutHistoryEntity> results);
}

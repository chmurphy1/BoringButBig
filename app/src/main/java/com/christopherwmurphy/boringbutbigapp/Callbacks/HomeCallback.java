package com.christopherwmurphy.boringbutbigapp.Callbacks;

import com.christopherwmurphy.boringbutbigapp.database.Entity.CurrentWorkoutPlanEntity;

import java.util.HashMap;
import java.util.List;

public interface HomeCallback {
    public void callback(List<CurrentWorkoutPlanEntity> todaysPlan, HashMap<Integer, Long> calculatedWeight);
}

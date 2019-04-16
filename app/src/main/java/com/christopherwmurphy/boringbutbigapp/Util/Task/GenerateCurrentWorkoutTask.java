package com.christopherwmurphy.boringbutbigapp.Util.Task;

import android.content.Context;
import android.os.AsyncTask;

import com.christopherwmurphy.boringbutbigapp.Callbacks.HomeCallback;
import com.christopherwmurphy.boringbutbigapp.database.Entity.CurrentWorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseMaxEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.TodaysWorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.database.WorkoutDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenerateCurrentWorkoutTask extends AsyncTask<Void,Void,Void> {

    private Context context;
    private HashMap<Integer, Long> calculatedWeight;
    private List<CurrentWorkoutPlanEntity> todaysPlan;
    private HomeCallback callback;

    public GenerateCurrentWorkoutTask(Context context, HomeCallback callback){
        this.context = context;
        calculatedWeight = new HashMap<>();
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        WorkoutDB db = WorkoutDB.getInstance(context);

        TodaysWorkoutPlanEntity today = db.todaysWorkoutPlanDao().getTodaysWorkout();
        todaysPlan = db.currentWorkoutPlanDao().getTodaysWorkoutPlans(today.getWeek(),today.getPlanId());

        //Get exercise ids
        Set<Integer> ids = new HashSet<>();
        for(CurrentWorkoutPlanEntity p : todaysPlan){
            if(p.getScheme().getPercentage() > 0.0){
                ids.add(p.getExerciseId());
            }
        }
        //Do not return to user
        List<ExerciseMaxEntity> maxes =  db.exerciseMaxDao().getExerciseMaxByIdLimitSize(ids, ids.size());

        //Calculate weight used for workout
        //link by seq_num
        for(ExerciseMaxEntity max : maxes){

            for(CurrentWorkoutPlanEntity plan: todaysPlan){
                if(max.getExercise().getId().intValue() == plan.getExerciseId().intValue()){
                    Long weight = Math.round(max.getMax() * plan.getScheme().getPercentage());
                    calculatedWeight.put(plan.getSeqNum(), weight);
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        callback.callback(todaysPlan,calculatedWeight);
    }
}

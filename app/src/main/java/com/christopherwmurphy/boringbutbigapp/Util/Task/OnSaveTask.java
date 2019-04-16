package com.christopherwmurphy.boringbutbigapp.Util.Task;

import android.content.Context;
import android.os.AsyncTask;

import com.christopherwmurphy.boringbutbigapp.Callbacks.OnSaveCallback;
import com.christopherwmurphy.boringbutbigapp.database.Entity.CurrentWorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseMaxEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.TodaysWorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.database.WorkoutDB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OnSaveTask extends AsyncTask<ExerciseMaxEntity, Void, Void> {

    private Context context;
    private int workoutId;
    private OnSaveCallback callback;

    public OnSaveTask(Context context, int workoutId, OnSaveCallback callback) {
        this.context = context;
        this.workoutId = workoutId;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ExerciseMaxEntity... exerciseMaxEntities) {
        WorkoutDB db = WorkoutDB.getInstance(context);

        if(exerciseMaxEntities.length > 0){
            ArrayList<ExerciseMaxEntity> newMaxes = new ArrayList();
            Collections.addAll(newMaxes, exerciseMaxEntities);

            db.exerciseMaxDao().insertAll(newMaxes);
        }

        List<WorkoutPlanEntity> plans = db.workoutPlanDao().getAllWorkoutPlanById(workoutId);
        List<CurrentWorkoutPlanEntity> currentPlans = new ArrayList<>();

        for(WorkoutPlanEntity p : plans){
            CurrentWorkoutPlanEntity current = new CurrentWorkoutPlanEntity(p.getWeek(),
                    p.getPlanId(),
                    p.getSeqNum(),
                    p.getExerciseId(),
                    p.getSetId(),
                    p.getWorkoutId(),
                    p.getOptional());
            currentPlans.add(current);
        }

        currentPlans.sort(new Comparator<CurrentWorkoutPlanEntity>() {
            @Override
            public int compare(CurrentWorkoutPlanEntity o1, CurrentWorkoutPlanEntity o2) {

                if(o1.getWeek().intValue() == o2.getWeek().intValue()){
                    if(o1.getPlanId().intValue() == o2.getPlanId().intValue()){
                        return 0;
                    }else if(o1.getPlanId().intValue() > o2.getPlanId().intValue()){
                        return 1;
                    }else if(o1.getPlanId().intValue() < o2.getPlanId().intValue()){
                        return -1;
                    }
                }
                else if(o1.getWeek().intValue() > o2.getWeek().intValue()){
                    return 1;
                }
                else if(o1.getWeek().intValue() < o2.getWeek().intValue()){
                    return -1;
                }
                return -1;
            }
        });

        //delete old plan
        db.currentWorkoutPlanDao().deleteAll();
        db.todaysWorkoutPlanDao().deleteAll();

        //insert new plan
        db.currentWorkoutPlanDao().insertAll(currentPlans);
        db.todaysWorkoutPlanDao().insert(new TodaysWorkoutPlanEntity(currentPlans.get(0).getWeek(),currentPlans.get(0).getPlanId()));

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        callback.callback();
    }
}

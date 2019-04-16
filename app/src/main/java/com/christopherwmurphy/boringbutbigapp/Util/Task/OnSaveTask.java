package com.christopherwmurphy.boringbutbigapp.Util.Task;

import android.content.Context;
import android.os.AsyncTask;

import com.christopherwmurphy.boringbutbigapp.Callbacks.OnSaveCallback;
import com.christopherwmurphy.boringbutbigapp.database.Entity.CurrentWorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.ExerciseMaxEntity;
import com.christopherwmurphy.boringbutbigapp.database.Entity.WorkoutPlanEntity;
import com.christopherwmurphy.boringbutbigapp.database.WorkoutDB;

import java.util.ArrayList;
import java.util.Collections;
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

        //delete old plan
        db.currentWorkoutPlanDao().deleteAll();
        db.currentWorkoutPlanDao().insertAll(currentPlans);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        callback.callback();
    }
}
